package com.enchere.appli.model;

import com.enchere.appli.dao.CompteDAO;
import com.enchere.appli.dao.RencherirDAO;
import com.enchere.appli.dao.UtilisateurDAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;

import com.enchere.appli.service.AuctionService;


public class Utilisateur {

    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Date registration_date;





    public Utilisateur(int id, String nom, String prenom, String email, String password, Date registration_date) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.registration_date = registration_date;

    }
    public Utilisateur( String nom, String prenom, String email) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.registration_date = Date.valueOf(LocalDate.now());

    }

    public Utilisateur() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getRegistration_date() {
        return registration_date;
    }

    public void setRegistration_date(Date registration_date) {
        this.registration_date = registration_date;
    }





    @Override
    public String toString() {
        return "{" +
                " id='" + getId() + "'" +
                ", nom='" + getNom() + "'" +
                ", prenom='" + getPrenom() + "'" +
                ", email='" + getEmail() + "'" +
                ", pawsWord='" + getPassword() + "'" +
                ", registration_date='" + getRegistration_date() + "'" +
                "}";
    }



    public Utilisateur findById(Connection c) throws Exception {
        return UtilisateurDAO.findById(this.getId(),c);
    }

    public Compte getCompte(Connection c) throws Exception{
        return CompteDAO.findCompteByIdUtilisateur(this.getId(),c);
    }

    public ArrayList<Rencherir> listeOfAllSousEnchere(Connection c) throws Exception{
        return RencherirDAO.listAvalableSolde(this.getId(),c);
    }

    public double available_Sold(Connection c) throws Exception{
        return UtilisateurDAO.avalableSolde(this.getId(),c);
    }


    public void auction_GO(Produit p, Timestamp lera , double amount , Connection c) throws Exception
    {

        Rencherir new_Action = new Rencherir(p,this,lera,amount);
        Rencherir last_Action = p.last_Offre(c);
        Compte user_compte = this.getCompte(c);
        AuctionService auction = new AuctionService(new_Action,last_Action,p);
        if(auction.isRunable()){
            c.setAutoCommit(false);
            try{
                if(last_Action !=null){

                    last_Action.getIdutilisateur().getCompte(c).credite(last_Action.getMise(),c);
                    last_Action.change_status(Rencherir.RECLAMED_STATUS(),c);


                }
                user_compte.debite(amount,c);
                new_Action.save(c);
                c.commit();

            }
            catch (Exception e){
                c.rollback();
                throw e;
            }

        }
    }

    public static void auction_GO_Rencherir(Rencherir new_Action , Connection c) throws Exception
    {
        try
        {
            new_Action.getIdutilisateur().auction_GO(new_Action.getIdproduit(),new_Action.getDatemise(),new_Action.getMise(),c);
        }
        catch (Exception e)
        {
            throw e;
        }
    }

    public ArrayList<HistoriqueUser> getHistoriques(Connection c) throws Exception{
        ArrayList<HistoriqueUser> res = new ArrayList<HistoriqueUser>();
        ArrayList<Produit> prods = UtilisateurDAO.enchereConcerne(this.getId(),c);
        if(prods.isEmpty()==true)return res;
        for(Produit p : prods){
            HistoriqueUser hs = new HistoriqueUser();
            hs.setUtilisateur(this);
            hs.setProduit(p);
            if(p.last_Offre(c) != null){
                if(p.last_Offre(c).getIdutilisateur().getId()==this.getId())
                    hs.setEncheravle(false);
            }
            res.add(hs);
        }
        return res;
    }


    /*public ArrayList<Produit> myOwnProduit(Connection c) throws Exception{
        return UtilisateurDAO.getMyProduit(this.getId(),c);
    }*/

}
