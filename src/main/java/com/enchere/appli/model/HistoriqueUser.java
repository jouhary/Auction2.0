package com.enchere.appli.model;

public class HistoriqueUser {

    public Utilisateur utilisateur;
    private Produit produit;
    private boolean isEncheravle =true;


    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public boolean isEncheravle() {
        return isEncheravle;
    }

    public void setEncheravle(boolean encheravle) {
        isEncheravle = encheravle;
    }


    @Override
    public String toString() {
        return "HistoriqueUser{" +
                "utilisateur=" + utilisateur +
                ", produit=" + produit +
                ", isEncheravle=" + isEncheravle +
                '}';
    }
}
