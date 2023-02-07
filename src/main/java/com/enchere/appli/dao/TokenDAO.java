package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Admin;
import com.enchere.appli.model.Token;
import com.enchere.appli.model.Utilisateur;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.time.LocalDateTime;

public class TokenDAO {

    public static Token insertToken(Admin x) throws Exception {
        Connection connexion = null;
        PreparedStatement stat = null;
        String hash = x.getUserName() + x.getPassWord() + LocalDateTime.now();
        Token ans = null;

        try {
            hash = Token.encrypt(hash);
            LocalDateTime expiration = LocalDateTime.now().plusMinutes(30);
            Timestamp exp = Timestamp.valueOf(expiration);
            connexion = Connexion.connect();
            String requete = "INSERT INTO Token (idAdmin, value,dateexpiration) VALUES (?, ?, ?)";
            stat = connexion.prepareStatement(requete);
            stat.setInt(1, x.getId());
            stat.setString(2, hash);
            stat.setTimestamp(3, exp);

            stat.execute();
            ans = new Token();
            ans.setAdmin(x);
            ans.setValue(hash);
            ans.setDateExpiration(exp);
        } catch (SQLException | NoSuchAlgorithmException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (stat != null)
                stat.close();
            if (connexion != null)
                connexion.close();
        }
        return ans;
    }

    public static Token insertTokenUtilisateur(Utilisateur x) throws Exception {
        Connection connexion = null;
        PreparedStatement stat = null;
        String hash = x.getEmail() + x.getPassword() + LocalDateTime.now();
        Token ans = null;

        try {
            hash = Token.encrypt(hash);
            LocalDateTime expiration = LocalDateTime.now().plusMinutes(30);
            Timestamp exp = Timestamp.valueOf(expiration);
            connexion = Connexion.connect();
            String requete = "INSERT INTO Token (idUtilisateur, value,dateexpiration) VALUES (?, ?, ?)";
            stat = connexion.prepareStatement(requete);
            stat.setInt(1, x.getId());
            stat.setString(2, hash);
            stat.setTimestamp(3, exp);

            stat.execute();
            ans = new Token();
            ans.setUtilisateur(x);
            ans.setValue(hash);
            ans.setDateExpiration(exp);
        } catch (SQLException | NoSuchAlgorithmException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (stat != null)
                stat.close();
            if (connexion != null)
                connexion.close();
        }
        return ans;
    }

    public static Token getToken(String token) throws Exception {
        Connection co = null;
        PreparedStatement prst = null;
        ResultSet result = null;
        Token ans = null;
        try {
            co = Connexion.connect();
            String sql = "Select * from Token WHERE value = '" + token + "'";
            prst = co.prepareStatement(sql);
            result = prst.executeQuery();
            while (result.next()) {
                ans = new Token();
                Admin x = AdminDAO.getById(result.getInt("idAdmin"));
                ans.setAdmin(x);
                ans.setValue(result.getString("value"));
                ans.setDateExpiration(result.getTimestamp("dateexpiration"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            // TODO: handle exception
            throw e;
        }
        return ans;
    }

    public static int getidUserByToken(String token,Connection c) throws Exception{
        PreparedStatement stat = null;
        ResultSet res = null;
        int id = 0;
        try{
            stat = c.prepareStatement("SELECT idutilisateur FROM token WHERE value=?");
            stat.setString(1,token);
            res = stat.executeQuery();
            while(res.next()){
                id = res.getInt("idutilisateur");
            }

        }
        catch(Exception e){
            throw e;
        }
        finally {
            if(stat!= null) stat.close();
            if(res!=null) res.close();
        }
        return  id;

    }
}
