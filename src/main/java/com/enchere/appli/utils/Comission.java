package com.enchere.appli.utils;

import com.enchere.appli.model.Categorie;

public class Comission {

    private Categorie categorie;
    private double commission_value;


    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public double getCommission_value() {
        return commission_value;
    }

    public void setCommission_value(double commission_value) {
        this.commission_value = commission_value;
    }


    @Override
    public String toString() {
        return "Comission{" +
                "categorie=" + categorie +
                ", commission_value=" + commission_value +
                '}';
    }
}
