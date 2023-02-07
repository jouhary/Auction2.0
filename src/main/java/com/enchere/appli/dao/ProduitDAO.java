package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.*;

import java.sql.*;
import java.util.ArrayList;

public class ProduitDAO
{
    public static void updateProduit(int id,Connection c) throws Exception {
        PreparedStatement stmt = null;

        String sql = "update Produit set vendu = 'true' where id =? ";
        try {
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

    public static ArrayList<Produit> getAllProduit(Connection c) throws Exception {
        ArrayList<Produit> pdx = new ArrayList<Produit>();
        PreparedStatement stmt = null;
        ResultSet res = null;

        try
        {
            String sql = "select * from v_encheredetails join Categorie on categorie.id=v_encheredetails.idcategorie order by date_ajout desc";
            stmt = c.prepareStatement(sql);
            res = stmt.executeQuery();

            while(res.next())
            {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(res.getInt("id"));


                Categorie cat = new Categorie();
                cat.setId(res.getInt("id"));
                cat.setNom_categorie(res.getString("nom_categorie"));
                cat.setDescription(res.getString("description"));


                Produit px= new Produit();
                px.setId(res.getInt("id"));
                px.setIdutilisateur(utilisateur);
                px.setIdcategorie(cat);
                px.setNom_produit(res.getString("nom_produit"));
                px.setDate_ajout(res.getTimestamp("date_ajout"));
                px.setDate_fin(res.getTimestamp("date_fin"));
;               px.setDuree(res.getTime("duree"));
                px.setPrix_min(res.getDouble("prix_min"));
                pdx.add(px);
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
        return pdx;
    }

    public static Produit findById(int id,Connection c) throws Exception {
        Produit pdt = null;
        PreparedStatement stmt = null;
        ResultSet res = null;

        try {
            String sql = "select * from v_encheredetails join Categorie on categorie.id=v_encheredetails.idCategorie join Utilisateur on utilisateur.id = v_encheredetails.idUtilisateur where v_encheredetails.id = "+id;
            stmt = c.prepareStatement(sql);
            res = stmt.executeQuery();

            while (res.next()) {
                Utilisateur utilisateur = UtilisateurDAO.findById(res.getInt("idutilisateur"),c);
                /*utilisateur.setId(res.getInt("id"));
                utilisateur.setNom(res.getString("nom"));
                utilisateur.setPrenom(res.getString("prenom"));
                utilisateur.setEmail(res.getString("email")); */

                Categorie cat = new Categorie();
                cat.setId(res.getInt("id"));
                cat.setNom_categorie(res.getString("nom_categorie"));
                cat.setDescription(res.getString("description"));

                pdt = new Produit();
                pdt.setId(res.getInt("id"));
                pdt.setIdutilisateur(utilisateur);
                pdt.setIdcategorie(cat);
                pdt.setNom_produit(res.getString("nom_produit"));
                pdt.setDate_ajout(res.getTimestamp("date_ajout"));
                pdt.setVendu(res.getBoolean("etat_auction"));
                pdt.setDuree(res.getTime("duree"));
                pdt.setDate_fin(res.getTimestamp("date_fin"));
                pdt.setPrix_min(res.getDouble("prix_min"));

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
        return pdt;
    }

    public static Produit newProduit(Produit p, Connection c) throws Exception {
        Statement stmt = c.createStatement();
        ResultSet res = null;
        try {
            String sql = String.format(
                    "INSERT INTO Produit(idUtilisateur,idcategorie,nom_produit,date_ajout,duree,prix_min)  values ('%s','%s','%s',current_date,'%s','%s') returning id",
                    p.getIdutilisateur().getId(), p.getIdcategorie().getId(), p.getNom_produit(),p.getDuree(),p.getPrix_min());
            res = stmt.executeQuery(sql);
            while (res.next())
            {
                System.out.println(res.getInt("id"));
                p.setId(res.getInt("id"));
            }
        } catch (Exception e) {
            // TODO: handle exception
            throw e;
        } finally {
            stmt.close();

        }
        return  p;
    }


    public static Produit getById(int id,Connection c) throws Exception{
        PreparedStatement stmt =null;
        ResultSet res = null;
        Produit pdt = null;
        try{
            stmt = c.prepareStatement("select * from v_encheredetails join Categorie on categorie.id=v_encheredetails.idCategorie join Utilisateur on utilisateur.id = v_encheredetails.idUtilisateur where v_encheredetails.id = ?");
            stmt.setInt(1,id);
            res = stmt.executeQuery();
            while(res.next()){
                Utilisateur u = UtilisateurDAO.findById(res.getInt("idutilisateur"),c);
                Categorie cat =new Categorie();
                cat.setId(res.getInt("idcategorie"));
                cat.setNom_categorie(res.getString("nom_categorie"));
                cat.setDescription(res.getString("description"));
                pdt = new Produit();
                pdt.setId(res.getInt("id"));
                pdt.setIdutilisateur(u);
                pdt.setIdcategorie(cat);
                pdt.setNom_produit(res.getString("nom_produit"));
                pdt.setDate_ajout(res.getTimestamp("date_ajout"));
                pdt.setVendu(res.getBoolean("etat_auction"));
                pdt.setDuree(res.getTime("duree"));
                pdt.setDate_fin(res.getTimestamp("date_fin"));
                pdt.setPrix_min(res.getDouble("prix_min"));


            }


        }
        catch(Exception e){
            throw e;

        }
        finally {
            if(stmt != null) stmt.close();
            if(res != null) res.close();
        }
        return pdt;

     }



     public static ArrayList<Produit> pagination(int numPages,int size,Connection c) throws Exception{
        int offset = (numPages-1)*size;
        ArrayList<Produit> prods = new ArrayList<Produit>();
        PreparedStatement stat = null;
        ResultSet res =null;
        try{
            stat = c.prepareStatement("SELECT * FROM v_encheredetails OFFSET ? LIMIT ?");
            stat.setInt(1,offset);
            stat.setInt(2,size);
            System.out.println(stat.toString());
            res = stat.executeQuery();
            while(res.next()){
                Produit p = ProduitDAO.getById(res.getInt("id"),c);
                prods.add(p);
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

     public static ArrayList<Produit> myListProduit(int idUtilisateur,Connection c) throws Exception{
        //int offset = (numPages-1)*size;
        ArrayList<Produit> prods = new ArrayList<Produit>();
        PreparedStatement stat = null;
        ResultSet res =null;
        try{
            stat = c.prepareStatement("SELECT * FROM v_encheredetails where idUtilisateur = ? order by date_ajout desc ");
            stat.setInt(1,idUtilisateur);
            //stat.setInt(2,size);
            System.out.println(stat.toString());
            res = stat.executeQuery();
            while(res.next()){
                Produit p = ProduitDAO.getById(res.getInt("id"),c);
                prods.add(p);
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

    public static ArrayList<Produit> pagination(int numPages,int size,int idUtilisateur,Connection c) throws Exception{
        int offset = (numPages-1)*size;
        ArrayList<Produit> prods = new ArrayList<Produit>();
        PreparedStatement stat = null;
        ResultSet res =null;
        try{
            stat = c.prepareStatement("SELECT * FROM v_encheredetails OFFSET ? LIMIT ? order by date_ajout desc where idUtilisateur="+idUtilisateur);
            stat.setInt(1,offset);
            stat.setInt(2,size);
            System.out.println(stat.toString());
            res = stat.executeQuery();
            while(res.next()){
                Produit p = ProduitDAO.getById(res.getInt("id"),c);
                prods.add(p);
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
