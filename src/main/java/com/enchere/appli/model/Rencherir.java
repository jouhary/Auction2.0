package com.enchere.appli.model;

import com.enchere.appli.dao.RencherirDAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;

public class Rencherir
{
    private int id;
    private Produit idproduit;
    private Utilisateur idutilisateur;
    private Timestamp datemise;
    private double mise;

    private int auction_etat=0;

    private static int WIN_STATUS=0;
    private static int RECLAMED_STATUS=1;


    public static int WIN_STATUS() {
        return WIN_STATUS;
    }

    public static int RECLAMED_STATUS() {
        return RECLAMED_STATUS;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produit getIdproduit() {
        return idproduit;
    }

    public void setIdproduit(Produit idproduit) {
        this.idproduit = idproduit;
    }

    public Utilisateur getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Utilisateur idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Timestamp getDatemise() {

        return datemise;
    }

    public void setDatemise(Timestamp datemise) {
        this.datemise = datemise;
    }

    public int getAuction_etat() {
        return auction_etat;
    }

    public void setAuction_etat(int auction_etat) {
        this.auction_etat = auction_etat;
    }

    public double getMise() {
        return mise;
    }

    public void setMise(double mise) {
        this.mise = mise;
    }


    public Rencherir() {
    }

    public Rencherir(int id, Produit idproduit, Utilisateur idutilisateur, Timestamp datemise, double mise,int status) {
        this.id = id;
        this.idproduit = idproduit;
        this.idutilisateur = idutilisateur;
        this.datemise = datemise;
        this.mise = mise;
        this.auction_etat = status;
    }


    public Rencherir(Produit idproduit, Utilisateur idutilisateur, Timestamp datemise, double mise) {
        this.idproduit = idproduit;
        this.idutilisateur = idutilisateur;
        this.datemise = datemise;
        this.mise = mise;

    }


    @Override
    public String toString() {
        return "Rencherir{" +
                "id=" + id +
                ", idproduit=" + idproduit +
                ", idutilisateur=" + idutilisateur +
                ", datemise=" + datemise +
                ", mise=" + mise +
                ", auction_etat=" + auction_etat +
                '}';
    }

    public void save(Connection c) throws Exception{
        RencherirDAO.save(this.getIdproduit().getId(),this.getIdutilisateur().getId(),this.getDatemise(),this.getMise(),c);

    }

    public void change_status(int status,Connection c) throws Exception{
        RencherirDAO.update(this.getId(),status,c);
    }

}
