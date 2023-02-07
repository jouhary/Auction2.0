package com.enchere.appli.controller;


import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.CategorieDAO;
import com.enchere.appli.model.Categorie;
import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("categories")
public class CategorieController
{

    @GetMapping
    public ObjectReturn getAllCategorie(@RequestParam(name = "hash",required = true)String hash) throws Exception
    {
        TokenService.bearerToken(hash);
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            ArrayList<Categorie> cat = CategorieDAO.getAllCategorie(c);
            objet= new ObjectReturn();
            objet.setData(cat);
            objet.setMessage("succes");
        }
        catch (ClassNotFoundException | SQLException e) {
            // TODO Auto-generated catch block
            throw e;
        }
        finally
        {
            c.close();
        }
        return objet;

    }


    @PostMapping
    public ObjectReturn createCategorie(@RequestBody Categorie categorie,@RequestParam(name = "hash",required = true)String hash) throws Exception {
        TokenService.bearerToken(hash);
        ObjectReturn objet = null;
        Connection c = null;

        try
        {
            c = Connexion.connect();
            objet = new ObjectReturn();
            CategorieDAO.newCategorie(categorie,c);
            objet.setData(categorie);
            objet.setMessage("Categorie created");
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

}
