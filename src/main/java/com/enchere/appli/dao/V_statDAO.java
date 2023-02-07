package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Compte;
import com.enchere.appli.model.RechargeCompte;
import com.enchere.appli.model.Utilisateur;
import com.enchere.appli.model.V_stat;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class V_statDAO
{
    public static V_stat getAllStat(Connection c) throws Exception,SQLException {
        PreparedStatement prst = null;
        ResultSet result = null;
        ArrayList<V_stat> list = new ArrayList<V_stat>();
        ArrayList<String> nom = new ArrayList<String>();
        ArrayList<Double> valeur = new ArrayList<Double>();
        V_stat stats = new V_stat();

        try {
            String sql = "Select * from v_stat";

            prst = c.prepareStatement(sql);
            result = prst.executeQuery();
            while (result.next()) {

                /*stat.setNom_categorie(result.getString("nom_categorie"));
                stat.setPrix_moyenne(result.getDouble("prix_moyenne"));
                list.add(stat);*/
                nom.add(result.getString("nom_categorie"));
                valeur.add(result.getDouble("prix_moyenne"));
                System.out.println(result.getString("nom_categorie"));
                System.out.println(result.getDouble("prix_moyenne"));

            }
            stats.setNom_categorie(nom);
            stats.setPrix_moyenne(valeur);

        } catch (SQLException e) {
            throw e;
        } finally {
            if (result != null)
                result.close();
            if (prst != null)
                prst.close();

        }
        return stats;
    }
}