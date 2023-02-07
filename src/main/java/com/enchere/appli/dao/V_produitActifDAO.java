package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Categorie;
import com.enchere.appli.model.V_produitActif;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class V_produitActifDAO
{
    public static ArrayList<V_produitActif> getProduitActif() throws Exception {
        ArrayList<V_produitActif> produit = new ArrayList<V_produitActif>();
        Statement stmt = null;
        ResultSet res = null;
        Connection c = null;

        try
        {
            String sql = "select * from v_produitActif";
            c = Connexion.connect();
            stmt = c.createStatement();
            res = stmt.executeQuery(sql);

            while(res.next())
            {
                V_produitActif prod = new V_produitActif(res.getInt("id"),res.getString("nom_produit"),res.getDouble("max"));
                produit.add(prod);
            }
        }
        catch (Exception e)
        {
            //TODO: handle exception
            throw e;
        }

        finally
        {
            if(res!=null)
            {
                res.close();
            }
            if(stmt!=null)
            {
                stmt.close();
            }
            if(c!=null)
            {
                c.close();
            }
        }
        return produit;
    }
}
