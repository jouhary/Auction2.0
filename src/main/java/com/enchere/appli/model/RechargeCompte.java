package com.enchere.appli.model;

import com.enchere.appli.dao.RechargeCompteDAO;
import com.enchere.appli.exception.NotFoundException;
import com.enchere.appli.exception.ResourceNotFoundException;

import java.sql.Connection;
import java.sql.Date;
import java.time.LocalDate;

import static com.enchere.appli.dao.RechargeCompteDAO.newRechargeUtilisateur;

public class RechargeCompte {

    private int id;
    private Compte compte;
    private double valeur_recharge;
    private boolean isValid;
    private Date daterechargement;
    private double after_recharge = 0;

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
     * @return Compte return the compte
     */
    public Compte getCompte() {
        return compte;
    }

    /**
     * @param compte the compte to set
     */
    public void setCompte(Compte compte) throws Exception {
        if (compte == null)
            throw new Exception("conmpte is null");
        this.compte = compte;
    }

    /**
     * @return double return the valeur_recharge
     */
    public double getValeur_recharge() {
        return valeur_recharge;
    }

    /**
     * @param valeur_recharge the valeur_recharge to set
     */
    public void setValeur_recharge(double valeur_recharge) throws Exception {
        if (valeur_recharge < 0)
            throw new Exception("action impossible");
        this.valeur_recharge = valeur_recharge;
    }

    /**
     * @return boolean return the isValid
     */
    public boolean isIsValid() {
        return isValid;
    }

    /**
     * @param isValid the isValid to set
     */
    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }

    public RechargeCompte(Compte compte, double valeur_recharge, boolean isValid) throws Exception {
        this.setCompte(compte);
        this.setIsValid(isValid);
        this.setValeur_recharge(valeur_recharge);
        this.setDaterechargement(Date.valueOf(LocalDate.now()));
        this.after_recharge = this.getCompte().getValeur_actuel() + this.getValeur_recharge();
    }

    public RechargeCompte() {
    }

    /**
     * @return Date return the daterechargement
     */
    public Date getDaterechargement() {
        return daterechargement;
    }

    /**
     * @param daterechargement the daterechargement to set
     */
    public void setDaterechargement(Date daterechargement) {
        this.daterechargement = daterechargement;
    }

    /**
     * @return double return the after_recharge
     */
    public double getAfter_recharge() {
        return after_recharge;
    }

    /**
     * @param after_recharge the after_recharge to set
     */
    public void setAfter_recharge(double after_recharge) {
        this.after_recharge = after_recharge;
    }

    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", compte='" + getCompte() + "'" +
                ", valeur_recharge='" + getValeur_recharge() + "'" +
                ", isValid='" + isIsValid() + "'" +
                ", daterechargement='" + getDaterechargement() + "'" +
                ", after_recharge='" + getAfter_recharge() + "'" +
                "}";
    }

    public void save(Connection c) throws Exception
    {
        RechargeCompteDAO.newRechargeUtilisateur(this,c);
    }

    public void valider(Connection c) throws Exception
    {
        if(this.isValid)
        {
            throw new ResourceNotFoundException("Transaction invalide");
        }
        this.after_recharge = this.getCompte().getValeur_actuel() + this.getValeur_recharge();

        System.out.println(this.after_recharge+" "+this.getCompte().getValeur_actuel());
        this.getCompte().setValeur_actuel(this.after_recharge);
        RechargeCompteDAO.updateRechargeCompte(this.getId(),c);
        this.getCompte().updateSolde(c);
    }
}
