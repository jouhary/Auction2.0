package com.enchere.appli.controller;


import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.RencherirDAO;
import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.model.Produit;
import com.enchere.appli.model.Rencherir;

import com.enchere.appli.model.Utilisateur;
import com.enchere.appli.service.ObjectEnchere;
import com.enchere.appli.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("rencherires")
public class RencherirController
{

    @GetMapping
    public ObjectReturn getHistorique(@PathVariable int idproduit) throws  Exception
    {
        Connection c = null;
        ObjectReturn objet = null;
        try
        {
            c = Connexion.connect();
            ArrayList<Rencherir> list = RencherirDAO.historiqueEnchere(idproduit,c);
            objet = new ObjectReturn();
            objet.setData(list);
            objet.setMessage("Success");
        }
        catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            throw e;
        }
        finally {
            c.close();
        }
        return objet;
    }

    @PostMapping("/createmise")
    public ObjectReturn createNewMise(@RequestBody Rencherir new_action,@RequestParam(name="hash", required = true) String hash) throws Exception {
        Connection c = null;
        ObjectReturn objet = null;
        TokenService.bearerToken(hash);
        Timestamp lera = Timestamp.valueOf(LocalDateTime.now());
        try
        {

            c = Connexion.connect();
            objet = new ObjectReturn();

            Utilisateur u = new Utilisateur();
            u.setId(new_action.getIdutilisateur().getId());
            u.findById(c);

            Produit p = new Produit();
            p.setId(new_action.getIdproduit().getId());
            p = p.findById(c);

            new_action.setIdutilisateur(u);
            new_action.setIdproduit(p);

            new_action.setDatemise(lera);
            u.auction_GO_Rencherir(new_action,c);

            objet.setMessage("Votre mise a ete place");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally
        {
            c.close();
        }
        return objet;
    }







    @PostMapping("/createmis")
    public ObjectReturn createNewMis(@RequestBody ObjectEnchere objectEnchere) throws Exception {
        Connection c = null;
        ObjectReturn objet = null;
        TokenService.bearerToken(objectEnchere.getToken());
        Timestamp lera = Timestamp.valueOf(LocalDateTime.now());
        Rencherir new_action = new Rencherir();
        try
        {

            c = Connexion.connect();
            objet = new ObjectReturn();

            Utilisateur u = new Utilisateur();
            u.setId(objectEnchere.getUser(c));
           u= u.findById(c);

            Produit p = new Produit();
            p.setId(objectEnchere.getIdProduit());
            p = p.findById(c);
            System.out.println(p.toString()+ "jj");
            new_action.setIdutilisateur(u);
            new_action.setIdproduit(p);
            new_action.setMise(objectEnchere.getSomme());
            new_action.setDatemise(lera);
            u.auction_GO_Rencherir(new_action,c);

            objet.setMessage("Votre mise a ete place");

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally
        {
            c.close();
        }
        return objet;
    }

}
