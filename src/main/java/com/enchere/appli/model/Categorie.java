package com.enchere.appli.model;

import com.enchere.appli.dao.CategorieDAO;
import com.enchere.appli.dao.ComissionDao;


import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Categorie
{

    private int id;
    private String nom_categorie;
    private String description;

    public Categorie(int id, String nom_categorie, String description) {
        this.id = id;
        this.nom_categorie = nom_categorie;
        this.description = description;
    }

    public Categorie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom_categorie() {
        return nom_categorie;
    }

    public void setNom_categorie(String nom_categorie) {
        this.nom_categorie = nom_categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Categorie{" +
                "id=" + id +
                ", nom_categorie='" + nom_categorie + '\'' +
                ", description='" + description + '\'' +
                '}';
    }


    public double getCommission(Connection c) throws Exception{
        return ComissionDao.getCurrentComission(this.getId(),c);
    }


    public ArrayList<Categorie> paginationListe(int numPages, int size, Connection c) throws  Exception{
        return CategorieDAO.pagination(numPages,size,c);
    }
}
