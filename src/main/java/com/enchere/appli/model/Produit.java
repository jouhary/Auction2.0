package com.enchere.appli.model;

import com.enchere.appli.dao.ProduitDAO;
import com.enchere.appli.dao.RencherirDAO;


import java.sql.Connection;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Produit
{

    private int id;
    private Utilisateur idutilisateur;
    private Categorie idcategorie;
    private String nom_produit;
    private Timestamp date_ajout;
    private boolean vendu;
    private Time duree;
    private Timestamp date_fin;

    private double prix_min;



    public double getPrix_min() {
        return prix_min;
    }

    public void setPrix_min(double prix_min) {
        this.prix_min = prix_min;
    }

    public Timestamp getDate_ajout() {
        return date_ajout;
    }

    public void setDate_ajout(Timestamp date_ajout) {
        this.date_ajout = date_ajout;
    }

    public boolean isVendu() {
        return vendu;
    }

    public void setVendu(boolean vendu) {
        this.vendu = vendu;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Timestamp getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Timestamp date_fin) {
        this.date_fin = date_fin;
    }



    public Produit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Utilisateur getIdutilisateur() {
        return idutilisateur;
    }

    public void setIdutilisateur(Utilisateur idutilisateur) {
        this.idutilisateur = idutilisateur;
    }

    public Categorie getIdcategorie() {
        return idcategorie;
    }

    public void setIdcategorie(Categorie idcategorie) {
        this.idcategorie = idcategorie;
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public Produit(int id, Utilisateur idutilisateur, Categorie idcategorie, String nom_produit, Timestamp date_ajout, boolean vendu, Time duree ,Timestamp date_fin,double prix) {
        this.id = id;
        this.idutilisateur = idutilisateur;
        this.idcategorie = idcategorie;
        this.nom_produit = nom_produit;
        this.date_ajout = date_ajout;
        this.vendu = vendu;
        this.duree = duree;
        this.date_fin = date_fin;
        this.prix_min = prix;
    }


    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", idutilisateur=" + idutilisateur +
                ", idcategorie=" + idcategorie +
                ", nom_produit='" + nom_produit + '\'' +
                ", date_ajout=" + date_ajout +
                ", vendu=" + vendu +
                ", duree=" + duree +
                ", date_fin=" + date_fin +
                '}';
    }

    public Produit findById(Connection c) throws Exception{
        return ProduitDAO.findById(this.getId(),c);
    }

    public Rencherir last_Offre(Connection c) throws Exception{
        return RencherirDAO.lasMiseOnProduit(this.getId(),c);
    }


    public static ArrayList<Produit> paginationList(int numPages, int size , Connection c) throws Exception{
        return ProduitDAO.pagination(numPages,size,c);
    }

    public ArrayList<Rencherir> allMiseFromTheProduit(Connection c) throws Exception{
        return RencherirDAO.historiqueEnchere(this.getId(),c);
    }
}