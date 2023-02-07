package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.exception.AuthentificationFailedException;
import com.enchere.appli.model.Admin;
import com.enchere.appli.model.Produit;
import com.enchere.appli.model.Token;
import com.enchere.appli.model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

public class UtilisateurDAO {

    public static Utilisateur newUtilisateur(Utilisateur utilisateur,Connection c) throws Exception {
        Statement stmt = c.createStatement();
        ResultSet res = null;
        try {
            String sql = String.format(
                    "INSERT INTO Utilisateur(nom,prenom,email,password,registration_date) values ('%s','%s','%s','%s',current_date) returning id",
                    utilisateur.getNom(), utilisateur.getPrenom(), utilisateur.getEmail(), utilisateur.getPassword());
            res = stmt.executeQuery(sql);
            while (res.next())
            {
                System.out.println(res.getInt("id"));
                utilisateur.setId(res.getInt("id"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            stmt.close();
        }
        return utilisateur;
    }

    public static Object[] toLog(Utilisateur x,Connection c) throws Exception {
        PreparedStatement prst = null;
        ResultSet result = null;
        Utilisateur utilisateur = null;
        Object[] objet = new Object[2];

        try {
            String sql = "Select * from utilisateur WHERE email = ? AND password = ?";
            prst = c.prepareStatement(sql);
            prst.setString(1, x.getEmail());
            prst.setString(2, x.getPassword());
            result = prst.executeQuery();
            prst.toString();
            while (result.next()) {
                utilisateur = new Utilisateur();
                utilisateur.setEmail(result.getString("email"));
                utilisateur.setPassword(result.getString("password"));
                utilisateur.setId(result.getInt("id"));

            }
            if(utilisateur == null)
            {
                throw new AuthentificationFailedException();
            }
            Token token =  TokenDAO.insertTokenUtilisateur(utilisateur);
            objet[0] = utilisateur;
            objet[1] = token;
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (result != null)
                result.close();
            if (prst != null)
                prst.close();

        }
        return objet;
    }

    public static Utilisateur findById(int id,Connection c) throws Exception {
        Utilisateur utilisateur = null;
        Statement stmt = null;
        ResultSet res = null;
        // Connection c = null;

        try {
            String sql = "select * from utilisateur where id =  " + id;
            // c = Connexion.connect();
            //System.out.println(sql+" eee");
            stmt = c.createStatement();
            res = stmt.executeQuery(sql);

            while (res.next()) {

                utilisateur = new Utilisateur();
                utilisateur.setId(res.getInt("id"));
                utilisateur.setNom(res.getString("nom"));
                utilisateur.setPrenom(res.getString("prenom"));
                utilisateur.setEmail(res.getString("email"));
                utilisateur.setPassword(res.getString("password"));
                utilisateur.setRegistration_date(res.getDate("registration_date"));
            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw e;
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
        return utilisateur;
    }

    public static double avalableSolde(int idU,Connection c) throws Exception {
        double result =0.0;
        PreparedStatement stat = null;
        ResultSet res = null;
        try{
            stat = c.prepareStatement("SELECT * FROM v_AVALABLESOLDE WHERE idutilisateur=?");
            stat.setInt(1,idU);
            res = stat.executeQuery();
            while(res.next())
            {
                result = res.getDouble("avalable_solde");
            }
        }
        catch (Exception e){
            throw e;
        }
        finally {
            if(stat != null) stat.close();
            if(res != null) res.close();
        }
        return result;
    }

    public static ArrayList<Produit> enchereConcerne(int id, Connection c) throws Exception{
        ArrayList<Produit> result = new ArrayList<Produit>();
        PreparedStatement stat =null;
        ResultSet res = null;
        try {
            stat = c.prepareStatement("SELECT * FROM PRODUIT WHERE id IN(SELECT  idProduit FROM rencherir WHERE idutilisateur=?) AND id IN (SELECT idutilisateur FROM v_enchereactif)");
    
            stat.setInt(1,id);
            System.out.println(stat.toString());
            res =stat.executeQuery();
            while (res.next()){
                //System.out.println("ato");
                Produit p =ProduitDAO.getById(res.getInt("id"),c);
                result.add(p);
            }
    
        }
        catch (Exception e){
    throw  e;
        }
        finally {
            if(stat != null) stat.close();
            if(res != null) res.close();
        }
    
        return result;
    }

}
