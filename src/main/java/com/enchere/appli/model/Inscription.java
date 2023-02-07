package com.enchere.appli.model;

import com.enchere.appli.dao.CompteDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Inscription {


    private Utilisateur utilisateur ;
    private String first_mdp;
    private String last_mdp;


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public String getFirst_mdp() {
        return first_mdp;
    }

    public void setFirst_mdp(String first_mdp) throws Exception {
        if(first_mdp==null) throw new Exception("mdp can't be null");
        this.first_mdp = first_mdp;
    }

    public String getLast_mdp() {
        return last_mdp;
    }

    public void setLast_mdp(String last_mdp) throws Exception {
        if(this.first_mdp.compareTo(last_mdp)!=0) throw  new Exception("modts de passe incorrect");
        this.last_mdp = last_mdp;
    }


    public Inscription(Utilisateur u , String fp,String lp) throws Exception {
        this.setUtilisateur(u);
        this.setFirst_mdp(fp);
        this.setLast_mdp(lp);
        this.utilisateur.setPassword(fp);
    }


    public void inscrire(Connection c) throws Exception{
        PreparedStatement stat = null;
        ResultSet res = null;
        System.out.println(utilisateur.toString());
        try{
            c.setAutoCommit(false);
            stat = c.prepareStatement("INSERT INTO Utilisateur(nom,prenom,email,password,registration_date) values (?,?,?,?,?) RETURNING id");
            stat.setString(1,utilisateur.getNom());
            stat.setString(2,utilisateur.getPrenom());
            stat.setString(3, utilisateur.getEmail());
            stat.setString(4, utilisateur.getPassword());
            stat.setDate(5,utilisateur.getRegistration_date());
            System.out.println(stat.toString());

            int current_id =0;
            System.out.println(current_id+" eto  ");
            res = stat.executeQuery();
            while(res.next()){
                current_id = res.getInt("id");
            }
             System.out.println(current_id);
            CompteDAO.saveCompte(current_id,false,c);
            c.commit();


        }
        catch (Exception e){
            c.rollback();
            throw e;
        }
        finally {

            if (res != null) {
                res.close();
            }
            if (stat != null) {
                stat.close();
            }

        }

    }

    @Override
    public String toString() {
        return "Inscription{" +
                "utilisateur=" + utilisateur +
                ", first_mdp='" + first_mdp + '\'' +
                ", last_mdp='" + last_mdp + '\'' +
                '}';
    }
}
