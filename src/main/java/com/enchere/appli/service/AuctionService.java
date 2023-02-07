package com.enchere.appli.service;

import com.enchere.appli.model.Produit;
import com.enchere.appli.model.Rencherir;

public class AuctionService
{
    private Produit produit;
    private Rencherir new_Action;
    private Rencherir last_Action;

    private boolean runable = false;

    public boolean isRunable() {
        return runable;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) throws Exception {
        if(produit == null) throw new Exception("the current produit is null");
        if(produit.isVendu()==true) throw new Exception("the current produit ");
        this.produit = produit;
    }

    public Rencherir getNew_Action() {

        return new_Action;
    }

    public void setNew_Action(Rencherir new_Action) throws Exception {
        if(this.getProduit().getPrix_min()> new_Action.getMise()) throw new Exception("votre montant est inferieur aux prix de depart ");
        if((new_Action.getDatemise().after(this.getProduit().getDate_fin())==true) || (new_Action.getDatemise().before(this.getProduit().getDate_ajout()))==true) throw new Exception("produit non disponible");

        this.new_Action = new_Action;
    }

    public Rencherir getLast_Action() {
        return last_Action;
    }

    public void setLast_Action(Rencherir last_Action) throws Exception {

        if(last_Action != null){
            if(last_Action.getMise()>this.getNew_Action().getMise()) throw new Exception("mise inferieure");
            if(last_Action.getIdutilisateur().getId() == this.getNew_Action().getIdutilisateur().getId()) throw new Exception("action impossible");
            if(last_Action.getDatemise().after(this.getNew_Action().getDatemise())==true)throw new Exception("action impossible");
            this.last_Action = last_Action;
        }
        else{
            this.last_Action = null;
        }

    }

    public AuctionService(Rencherir new_Action,Rencherir last_Action , Produit produit) throws  Exception{
        this.setProduit(produit);
        this.setNew_Action(new_Action);
        this.setLast_Action(last_Action);
        this.runable = true;
    }

}
