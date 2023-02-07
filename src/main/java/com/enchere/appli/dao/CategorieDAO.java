package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Categorie;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class CategorieDAO
{
        public static ArrayList<Categorie> getAllCategorie(Connection c) throws Exception {
            ArrayList<Categorie> cat = new ArrayList<Categorie>();
            Statement stmt = null;
            ResultSet res = null;

            try
            {
                String sql = "select * from categorie";
                stmt = c.createStatement();
                res = stmt.executeQuery(sql);

                while(res.next())
                {
                    Categorie ca = new Categorie(res.getInt("id"),res.getString("nom_categorie"),res.getString("description"));
                    cat.add(ca);
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
            }
            return cat;
        }

        //return id
        public static Categorie newCategorie(Categorie cat,Connection c) throws Exception
        {
            Statement stmt = c.createStatement();
            ResultSet res = null;
            try {
                String sql = String.format(
                        "INSERT INTO Categorie(nom_categorie,description) values ('%s','%s') returning id", cat.getNom_categorie(), cat.getDescription());
                res = stmt.executeQuery(sql);
                while(res.next())
                {
                    System.out.println(res.getInt("id"));
                    cat.setId(res.getInt("id"));
                }
            }
            catch (Exception e)
            {
                // TODO: handle exception
                throw e;
            } finally {
                stmt.close();
            }
            return cat;
        }

        public static ArrayList<Categorie> pagination(int numPages, int size, Connection c) throws Exception{
            int offset = (numPages-1)*size;
            ArrayList<Categorie> prods = new ArrayList<Categorie>();
            PreparedStatement stat = null;
            ResultSet res =null;
            try{
                stat = c.prepareStatement("SELECT * FROM categorie OFFSET ? LIMIT ?");
                stat.setInt(1,offset);
                stat.setInt(2,size);
                System.out.println(stat.toString());
                res = stat.executeQuery();
                while(res.next()){
                    Categorie cat =new Categorie();
                    cat.setId(res.getInt("idcategorie"));
                    cat.setNom_categorie(res.getString("nom_categorie"));
                    cat.setDescription(res.getString("description"));
                }
    
            }
            catch (Exception e){
    
            }
            finally {
                if(stat!= null) stat.close();
                if(res!=null) res.close();
            }
            return prods;
    
    
        }
}

