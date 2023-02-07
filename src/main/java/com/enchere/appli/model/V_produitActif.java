package com.enchere.appli.model;

public class V_produitActif
{
    private int id;
    private String nom_produit;
    private double max;


    public V_produitActif(int id, String nom_produit, double max) {
        this.id = id;
        this.nom_produit = nom_produit;
        this.max = max;
    }

    public V_produitActif() {
    }

    public String getNom_produit() {
        return nom_produit;
    }

    public void setNom_produit(String nom_produit) {
        this.nom_produit = nom_produit;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
