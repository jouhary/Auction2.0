package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Compte;
import com.enchere.appli.model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CompteDAO {
    public static void updateCompte(int idCompte, double valeur,Connection c) throws Exception {
        PreparedStatement stmt = null;

        String sql = "update Compte set valeuractuel = ? where id =? ";
        try {
            stmt = c.prepareStatement(sql);
            stmt.setDouble(1, valeur);
            stmt.setInt(2, idCompte);
            System.out.println(stmt.toString());
            stmt.executeUpdate();

        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            if(stmt!=null)
            {
                stmt.close();
            }
        }
    }

    public static Compte findCompteByIdUtilisateur(int id, Connection c) throws Exception {
        Compte cpt = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            String sql = "select * from Compte where Compte.idUtilisateur =  " + id;
            c = Connexion.connect();
            stmt = c.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(res.getInt("idUtilisateur"));
                cpt = new Compte();
                cpt.setId(res.getInt("id"));
                cpt.setUtilisateur(utilisateur);
                cpt.setIsBlocked(res.getBoolean("isblocked"));
                cpt.setValeur_actuel(res.getDouble("valeurActuel"));
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
        System.out.println(cpt.toString());
        return cpt;
    }

    public static Compte findCompteById( int id,Connection c) throws Exception {
        Compte cpt = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        //Connection c = null;

        try {
            String sql = "select * from v_compte where id=" + id;
            //c = Connexion.connect();
            stmt = c.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {

                Utilisateur utilisateur = new Utilisateur();

                cpt = new Compte();
                cpt.setId(res.getInt("id"));
                cpt.setUtilisateur(utilisateur);
                cpt.setIsBlocked(res.getBoolean("isblocked"));
                cpt.setValeur_actuel(res.getDouble("valeurActuel"));
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
        return cpt;
    }

    public static void saveCompte (int idU,boolean isBloked,Connection c) throws Exception{
        PreparedStatement stmt = null;
        ResultSet res = null;
        try{
            stmt = c.prepareStatement("INSERT INTO COMPTE(idutilisateur,isblocked) VALUES(?,?)");
            stmt.setInt(1,idU);
            stmt.setBoolean(2,isBloked);
            stmt.executeUpdate();

        }
        catch (Exception e){
            throw e;
        }
        finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }
    }
}
