package com.enchere.appli.model;

import com.enchere.appli.dao.WinnerDao;

import java.sql.Connection;
import java.sql.Timestamp;

public class Winner {
    private int id;

    private Produit produit;
    private Utilisateur utilisateur;
    private double montant_winner;
    private Timestamp date_win;




    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    public double getMontant_winner() {
        return montant_winner;
    }

    public void setMontant_winner(double montant_winner) {
        this.montant_winner = montant_winner;
    }

    public Timestamp getDate_win() {
        return date_win;
    }

    public void setDate_win(Timestamp date_win) {
        this.date_win = date_win;
    }

    @Override
    public String toString() {
        return "Winner{" +
                "id=" + id +
                ", produit=" + produit +
                ", utilisateur=" + utilisateur +
                ", montant_winner=" + montant_winner +
                ", date_win=" + date_win +
                '}';
    }

    public void save(Connection c) throws Exception{
        WinnerDao.saveWinner(this.getProduit().getId(),this.getUtilisateur().getId(),this.getDate_win(),this.getMontant_winner(),this.getProduit().getIdcategorie().getCommission(c),c);

    }

    public double getCommissionPerWin(Connection c) throws Exception {
        return (this.getMontant_winner()*this.getProduit().getIdcategorie().getCommission(c))/100;
    }


    public double getValeurNetEnchere(Connection c) throws  Exception{
        return this.getMontant_winner()-getCommissionPerWin(c);
    }
}
