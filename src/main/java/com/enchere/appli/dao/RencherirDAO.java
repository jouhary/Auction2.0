package com.enchere.appli.dao;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.model.Categorie;
import com.enchere.appli.model.Produit;
import com.enchere.appli.model.Rencherir;
import com.enchere.appli.model.Utilisateur;

import java.sql.*;
import java.util.ArrayList;

public class RencherirDAO
{
    public static ArrayList<Rencherir> historiqueEnchere(int idproduit,Connection c) throws Exception {
        ArrayList<Rencherir> pdx = new ArrayList<Rencherir>();
        PreparedStatement stmt = null;
        ResultSet res = null;

        try
        {
            String sql = "select * from rencherir join produit on produit.id=rencherir.idproduit join utilisateur on utilisateur.id=rencherir.idUtilisateur where idproduit = "+idproduit+" order by mise desc";
            stmt = c.prepareStatement(sql);
            res = stmt.executeQuery(sql);
            stmt.toString();
            while(res.next())
            {
                Utilisateur utilisateur = new Utilisateur();
                utilisateur.setId(res.getInt("id"));
                utilisateur.setNom(res.getString("nom"));
                utilisateur.setPrenom(res.getString("prenom"));

                Produit px= new Produit();
                px.setId(res.getInt("id"));
                px.setNom_produit(res.getString("nom_produit"));
                px.setDate_ajout(res.getTimestamp("date_ajout"));

                Rencherir rh = new Rencherir();
                rh.setId(res.getInt("id"));
                rh.setIdproduit(px);
                rh.setIdutilisateur(utilisateur);
                rh.setDatemise(res.getTimestamp("datemise"));
                rh.setMise(res.getDouble("mise"));

                pdx.add(rh);


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

    public static void update(int id,int next_status,Connection c) throws SQLException {
        PreparedStatement stat =  null;
        //ResultSet res = null;
        try{
            stat = c.prepareStatement("UPDATE RENCHERIR SET etat=? WHERE id=?");
            stat.setInt(1,next_status);
            stat.setInt(2,id);
            stat.executeUpdate();

        }
        catch (Exception e){
            throw e;
        }
        finally {
            if(stat != null) stat.close();
            //if(res != null) res.close();

        }
    }


    public static void save(int idp, int idu, Timestamp dateMise, double mise, Connection c) throws Exception{
        PreparedStatement stat =  null;
        //ResultSet res = null;
        try{
            stat = c.prepareStatement("INSERT INTO RENCHERIR (idProduit,idUtilisateur,datemise,mise) VALUES (?,?,?,?)");
            stat.setInt(1,idp);
            stat.setInt(2,idu);
            stat.setTimestamp(3,dateMise);
            stat.setDouble(4,mise);

            stat.executeUpdate();

        }
        catch (Exception e){
            throw e;
        }
        finally {
            if(stat != null) stat.close();
            //if(res != null) res.close();

        }
    }

    public Rencherir findById(Connection c,int id) throws Exception{
        PreparedStatement stat = null;
        ResultSet res = null;
        Rencherir rencherir = null;
        try{

            stat = c.prepareStatement("SELECT * FROM RENCHERIR WHERE ID=?");
            stat.setInt(1,id);
            res = stat.executeQuery();
            while(res.next()){
                Produit p = ProduitDAO.findById(res.getInt("idproduit"),c);
                Utilisateur u = UtilisateurDAO.findById(res.getInt("idutilisateur"),c);
                rencherir = new Rencherir(res.getInt("id"),p,u,res.getTimestamp("datemise"),res.getDouble("mise"),res.getInt("etat"));

            }

        }
        catch (Exception e){
            throw e;
        }
        finally {
            if(stat != null) stat.close();
            if(res != null) res.close();
        }
        return rencherir;
    }


    public static Rencherir lasMiseOnProduit(int idP,Connection c) throws Exception {
        PreparedStatement stat = null;
        ResultSet res = null;
        Rencherir rencherir = null;
        try{

            stat = c.prepareStatement("SELECT * FROM RENCHERIR WHERE idproduit=? AND ETAT=0");
            stat.setInt(1,idP);
            res = stat.executeQuery();
            while(res.next()){
                Produit p = ProduitDAO.findById(res.getInt("idproduit"),c);
                Utilisateur u = UtilisateurDAO.findById(res.getInt("idutilisateur"),c);
                rencherir = new Rencherir(res.getInt("id"),p,u,res.getTimestamp("datemise"),res.getDouble("mise"),res.getInt("etat"));

            }

        }
        catch (Exception e){
            throw e;
        }
        finally {
            if(stat != null) stat.close();
            if(res != null) res.close();
        }
        return rencherir;
    }


    public static ArrayList<Rencherir> listAvalableSolde(int idU, Connection c) throws Exception {
        ArrayList<Rencherir> result = new ArrayList<Rencherir>();
        PreparedStatement stat = null;
        ResultSet res = null;
        try{
            stat = c.prepareStatement("SELECT * FROM RENCHERIR WHERE  etat=1 AND idUtilisateur=?");
            stat.setInt(1,idU);
            res = stat.executeQuery();

            while(res.next()){

                Produit p = ProduitDAO.findById(res.getInt("idproduit"),c);
                Utilisateur u = UtilisateurDAO.findById(res.getInt("idutilisateur"),c);
                result.add(new Rencherir(res.getInt("id"),p,u,res.getTimestamp("datemise"),res.getDouble("mise"),res.getInt("etat")));

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

    public static Rencherir createRencherireForTest(int idU,int idP,double amount,Timestamp lera,Connection c) throws Exception {
        Utilisateur u = UtilisateurDAO.findById(idU,c);
        Produit p = ProduitDAO.findById(idP,c);

        Rencherir r = new Rencherir();
        r.setIdutilisateur(u);
        r.setIdproduit(p);
        r.setMise(amount);
        r.setDatemise(lera);
        return r;
    }


}
