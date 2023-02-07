package com.enchere.appli.controller;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.CategorieDAO;
import com.enchere.appli.model.Categorie;
import com.enchere.appli.model.ObjectReturn;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("categoriesutilisateurs")
public class CategorieControllerUtilisateur
{
    @GetMapping
    public ObjectReturn getAllCategorie() throws Exception
    {
        Connection c = null;
        ObjectReturn objet = null;
        try
        {
            c = Connexion.connect();
            ArrayList<Categorie> cat = CategorieDAO.getAllCategorie(c);
            objet= new ObjectReturn();
            objet.setData(cat);
            objet.setMessage("succes");
        }
        catch (Exception e)
        {
            throw e;
        }
        finally {
            c.close();
        }
        return objet;
    }
}
