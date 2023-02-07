package com.enchere.appli.model;

import java.sql.Time;
import java.sql.Timestamp;

public class ResultatRecherche
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
    private double dernier_prix=0;

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

    public double getPrix_min() {
        return prix_min;
    }

    public void setPrix_min(double prix_min) {
        this.prix_min = prix_min;
    }

    public double getDernier_prix() {
        return dernier_prix;
    }

    public void setDernier_prix(double dernier_prix) {
        this.dernier_prix = dernier_prix;
    }
}
