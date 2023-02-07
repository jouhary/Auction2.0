package com.enchere.appli.utils;


import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.UtilisateurDAO;
import com.enchere.appli.model.Categorie;
import com.enchere.appli.model.Produit;
import com.enchere.appli.model.ResultatRecherche;
import com.enchere.appli.model.Utilisateur;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;

public class Search {

    private String def;
    private int categorie;
    private Date date ;

    private double prix_min = 0.0;
    private double prix_max = 0.0;
    private int etat = 2;  //tt confondue


    public double getPrix_max() {
        return prix_max;
    }

    public void setPrix_max(double prix_max) {
        this.prix_max = prix_max;
    }




    public double getPrix_min() {
        return prix_min;
    }

    public void setPrix_min(double prix_min) {
        this.prix_min = prix_min;
    }


    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getDef() {
        return def;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public int getCategorie() {
        return categorie;
    }

    public void setCategorie(int categorie) {
        this.categorie = categorie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    private String buildDef(){
        if((this.getDef() == null)||(this.getDef().length() == 0)||(this.getDef().compareTo(" ")==0))
            return null;
        return " nom_produit LIKE '%"+this.getDef()+"%' OR description LIKE '%"+this.getDef()+"%' ";
    }

    private String buildCategorie(){
        if(this.getCategorie()==0)
            return "idcategorie != 0";
        else
            return " idCategorie ="+this.getCategorie();
    }

    private String buildDate(){
        if(this.getDate() == null)
            return null;
        return "date_ajout ='"+this.getDate()+"'";
    }

    private String buildMin_Prix(){
        if(this.getPrix_min()==0)
            return null;
        return "dernier_prix >= "+this.getPrix_min();
    }
    private String buildMax_Prix(){
        if(this.getPrix_max() == 0)
            return null;
        return "dernier_prix <="+this.getPrix_max();
    }

    public String buildEtat(){
        if(this.getEtat() == 2)
            return null;
        if(this.getEtat() == 0)
            return "etat_auction = TRUE";
        return "etat_auction = FALSE";
    }
    public String buildQuery() throws Exception{
        String query = "Select *  from V_rechercheFarany  ";
        String [] condition = {this.buildDef(),this.buildDate(),this.buildCategorie(),this.buildMin_Prix(),this.buildMax_Prix(),this.buildEtat()};
        //query = query+this.buildDef();
        System.out.println(condition.length);
        boolean check = false;
        boolean temp =false;
        for(int i = 0 ; i<condition.length;i++){
            System.out.println(condition[i]+" et ");
            if(condition[i] != null){
                if(temp == true)
                    query = query+" AND "+condition[i];
                if((condition[i] != null) &&(check == false)){
                    query = query+" WHERE ";
                    check = true;
                }
                if((check == true)&&(temp == false)){
                    query = query+condition[i];
                    temp = true;
                }
            }
        }
        System.out.println(query);
        return query;

    }


    public  ArrayList<ResultatRecherche> getResult() throws Exception{
        Connection c = null;
        PreparedStatement stat = null;
        ResultSet res  = null;
        ArrayList<ResultatRecherche> result = null;
        try{
            c= Connexion.connect();
            stat = c.prepareStatement(this.buildQuery());
            res = stat.executeQuery();
            result  = new ArrayList<ResultatRecherche>();
            while(res.next()){
                Produit p = new Produit();
                Utilisateur user =UtilisateurDAO.findById(res.getInt("idutilisateur"),c);
                Categorie ct = new Categorie();
                ct.setId(res.getInt("idcategorie"));
                ct.setNom_categorie(res.getString("nom_categorie"));
                ct.setDescription(res.getString("description"));
                //ct.setDuree(res.getTimestamp("d"));
                ResultatRecherche rr = new ResultatRecherche();
                rr.setId(res.getInt("id"));
                rr.setIdutilisateur(user);
                rr.setIdcategorie(ct);
                rr.setNom_produit(res.getString("nom_produit"));
                rr.setDate_ajout(res.getTimestamp("date_ajout"));
                rr.setDuree(res.getTime("duree"));
                rr.setPrix_min(res.getDouble("prix_min"));
                rr.setDate_fin(res.getTimestamp("date_fin"));
                rr.setVendu(res.getBoolean("etat_auction"));


                rr.setDernier_prix(res.getDouble("dernier_prix"));
                result.add(rr);

            }

        }
        catch(Exception e){
            throw e;
        }
        finally{
            if (res != null) {
                res.close();
            }
            if (stat != null) {
                stat.close();
            }
            if(c!=null)
                c.close();

        }
        return result;
    }
}
