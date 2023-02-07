package com.enchere.appli.model;



import java.util.ArrayList;


public class V_stat
{
    private ArrayList<String> nom_categorie;
    private ArrayList<Double> prix_moyenne;

    public V_stat(ArrayList<String> nom_categorie, ArrayList<Double> prix_moyenne) {
        this.nom_categorie = nom_categorie;
        this.prix_moyenne = prix_moyenne;
    }

    public V_stat() {

    }

    public ArrayList<String> getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(ArrayList<String> nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public ArrayList<Double> getPrix_moyenne() {
        return prix_moyenne;
    }

    public void setPrix_moyenne(ArrayList<Double> prix_moyenne) {
        this.prix_moyenne = prix_moyenne;
    }
}
