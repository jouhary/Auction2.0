package com.enchere.appli.controller;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.CompteDAO;
import com.enchere.appli.dao.RechargeCompteDAO;
import com.enchere.appli.model.Compte;
import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.model.RechargeCompte;
import com.enchere.appli.model.Utilisateur;
import com.enchere.appli.service.RechargeUtilisateur;
import com.enchere.appli.service.TokenService;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import static com.enchere.appli.dao.RechargeCompteDAO.newRechargeUtilisateur;


@CrossOrigin("*")
@RestController
@RequestMapping("recharges")
public class RechargeCompteController
{
    @GetMapping
    public ObjectReturn listeDemandeRecharge(@RequestParam(name = "hash", required = true)String hash) throws Exception
    {
        TokenService.bearerToken(hash);
        ObjectReturn data = new ObjectReturn();
        Connection c = null;
        try
        {
            c = Connexion.connect();
            ArrayList<RechargeCompte> recharge = RechargeCompteDAO.listeDemandeRecharge(c);
            data.setData(recharge);
            data.setMessage("succes");
        }
        catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            throw e;
        }
        finally {
            c.close();
        }
        return data;
    }

    @PostMapping
    public ObjectReturn findById(@RequestBody RechargeCompte re,@RequestParam(name="hash", required = true) String hash) throws Exception
    {
        TokenService.bearerToken(hash);
        ObjectReturn objet = null;
        System.out.println(re.getId());
        Connection c = null;
        try
        {
            c = Connexion.connect();
            RechargeCompte rc = RechargeCompteDAO.findById(re.getId(),c);
            rc.valider(c);

            int idcompte =  rc.getCompte().getId();
            Compte cpt = CompteDAO.findCompteById(idcompte,c);


            objet = new ObjectReturn();
            objet.setData(cpt);
            objet.setMessage("Succes");

        }
        catch (Exception e) {
            // TODO Auto-generated catch block
            throw e;
        }
        finally {
            c.close();
        }
        return objet;
    }

    @PostMapping("rechargeCompte")
    public ObjectReturn rechargeCompte(@RequestBody RechargeUtilisateur re) throws SQLException {
        ObjectReturn objet = null;
        Connection c = null;
        Utilisateur u = new Utilisateur();
        try
        {
            u.setId(re.getIdUtilisateur());
            Compte cpt = u.getCompte(c);
            c = Connexion.connect();
            objet = new ObjectReturn();
            RechargeCompte rechargeCompte = new RechargeCompte();
            rechargeCompte.setCompte(cpt);
            System.out.println("jafhiufhi"+re.getSomme());
            rechargeCompte.setValeur_recharge(re.getSomme());
            newRechargeUtilisateur(rechargeCompte,c);
            objet.setMessage("Votre demande a ete envoye");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            c.close();
        }
        return objet;
    }


}
