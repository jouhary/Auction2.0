package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Compte;
import com.enchere.appli.model.RechargeCompte;
import com.enchere.appli.model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

public class RechargeCompteDAO {

    public static ArrayList<RechargeCompte> listeDemandeRecharge(Connection c) throws Exception {
        PreparedStatement prst = null;
        ResultSet result = null;
        ArrayList<RechargeCompte> list = new ArrayList<RechargeCompte>();
        try {

            String sql = "Select * from rechargecompte join compte on compte.id = rechargecompte.idcompte join utilisateur on utilisateur.id=Compte.idutilisateur where rechargecompte.isvalid='false'";

            prst = c.prepareStatement(sql);
            result = prst.executeQuery();
            while (result.next()) {
                RechargeCompte temp = new RechargeCompte();
                temp.setId(result.getInt("id"));
                Utilisateur util = new Utilisateur();
                util.setId(result.getInt("idUtilisateur"));
                util.setNom(result.getString("nom"));
                util.setPrenom(result.getString("prenom"));
                util.setEmail(result.getString("email"));
                util.setPassword(result.getString("password"));
                util.setRegistration_date(result.getDate("registration_date"));

                Compte cpt = new Compte();
                cpt.setId(result.getInt("idCompte"));
                cpt.setUtilisateur(util);

                temp.setCompte(cpt);
                temp.setValeur_recharge(result.getDouble("valeur_recharge"));
                temp.setDaterechargement(result.getDate("daterechargement"));
                temp.setIsValid(result.getBoolean("isValid"));
                list.add(temp);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw e;
        } finally {
            if (result != null)
                result.close();
            if (prst != null)
                prst.close();

        }
        return list;
    }

    public static RechargeCompte findById(int id,Connection c) throws SQLException {
        RechargeCompte rcpt = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            String sql = "select * from rechargecompte where id = " + id;
            stmt = c.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Compte cpt = new Compte();
                cpt.setId(res.getInt("idcompte"));
                cpt = CompteDAO.findCompteById(res.getInt("idcompte"),c);

                rcpt = new RechargeCompte();
                rcpt.setId(res.getInt("id"));
                rcpt.setCompte(cpt);
                rcpt.setValeur_recharge(res.getDouble("valeur_recharge"));
                rcpt.setDaterechargement(res.getDate("daterechargement"));
                rcpt.setIsValid(res.getBoolean("isvalid"));

            }
        } catch (SQLException e) {
            // TODO: handle exception
            throw e;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (res != null) {
                res.close();
            }
            if (stmt != null) {
                stmt.close();
            }
        }

        return rcpt;
    }

    public static void updateRechargeCompte(int id,Connection c) throws Exception {
        PreparedStatement stmt = null;

        String sql = "update rechargecompte set isvalid = 'true' where id = ? ";
        try
        {
            stmt = c.prepareStatement(sql);
            stmt.setInt(1, id);
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

    public static void newRechargeUtilisateur(RechargeCompte recharge,Connection c) throws Exception {
        Statement stmt = c.createStatement();
        try {
            String sql = String.format(
                    "INSERT INTO rechargecompte(idcompte,valeur_recharge,daterechargement,isvalid) values ('%s','%s',current_date,false)",
                    recharge.getCompte().getId(), recharge.getValeur_recharge());
            stmt.execute(sql);
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            stmt.close();

        }
    }

}
