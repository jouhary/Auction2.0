package com.enchere.appli.model;

import com.enchere.appli.dao.CompteDAO;

import java.sql.Connection;

public class Compte {

    private int id;
    private Utilisateur utilisateur;
    private boolean isBlocked;
    private double valeur_actuel;

    /**
     * @return int return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return Utilisateur return the utilisateur
     */
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    /**
     * @param utilisateur the utilisateur to set
     */
    public void setUtilisateur(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
    }

    /**
     * @return boolean return the isBlocked
     */
    public boolean isIsBlocked() {
        return isBlocked;
    }

    /**
     * @param isBlocked the isBlocked to set
     */
    public void setIsBlocked(boolean isBlocked) {
        this.isBlocked = isBlocked;
    }

    /**
     * @return double return the valeur_actuel
     */
    public double getValeur_actuel() {
        return valeur_actuel;
    }

    /**
     * @param valeur_actuel the valeur_actuel to set
     */
    public void setValeur_actuel(double valeur_actuel) {
        this.valeur_actuel = valeur_actuel;

    }

    public Compte() {
    }

    public Compte updateSolde(Connection c) throws Exception {

        CompteDAO.updateCompte(this.getId(), this.getValeur_actuel(),c);
        return this;

    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", utilisateur='" + getUtilisateur() + "'" +
                ", isBlocked='" + isIsBlocked() + "'" +
                ", valeur_actuel='" + getValeur_actuel() + "'" +
                "}";
    }

    public void credite(double amount, Connection c) throws Exception{
        if(amount<0) throw  new Exception("action inpossible montant credit negative:"+amount);
        if(amount==0)return ;
        double after_credit = this.getValeur_actuel()+amount;
        this.setValeur_actuel(after_credit);
        this.updateSolde(c);
    }

    public void debite(double amount ,Connection c) throws Exception{
        if(amount<0) throw  new Exception("action inpossible montant credit negative:"+amount);
        if(this.getValeur_actuel()<amount) throw new Exception("user id:"+this.getUtilisateur().getId()+" solde inssuffisante solde:"+this.getValeur_actuel()+" action:"+amount);
        double after_debite = this.getValeur_actuel()-amount;
        this.setValeur_actuel(after_debite);
        this.updateSolde(c);
    }
}
