package com.enchere.appli.service;

import com.enchere.appli.model.Utilisateur;

import java.sql.Connection;

public class ObjectEnchere {

    private int idProduit;
    private double somme;
    private String token;

    public int getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(int idProduit) {
        this.idProduit = idProduit;
    }

    public double getSomme() {
        return somme;
    }

    public void setSomme(double somme) {
        this.somme = somme;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getUser(Connection c) throws Exception{
        return TokenService.getByUser(this.getToken(),c);

    }
}
