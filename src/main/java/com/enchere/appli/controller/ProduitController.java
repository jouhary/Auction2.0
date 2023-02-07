package com.enchere.appli.controller;


import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.ProduitDAO;
import com.enchere.appli.model.*;

import com.enchere.appli.utils.Search;
import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;



@CrossOrigin
@RestController
@RequestMapping("produits")
public class ProduitController
{

    @GetMapping
    public ObjectReturn getListProduit() throws Exception {
        ObjectReturn objet = null;
        Connection c = null;
        Search recherche = new Search();
        try
        {

            c = Connexion.connect();
            ArrayList<ResultatRecherche> pdx = recherche.getResult();
            objet = new ObjectReturn();
            objet.setData(pdx);
            objet.setMessage("Succes");
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



    @GetMapping("{id}")
    public ObjectReturn getProduitsById(@PathVariable int id) throws Exception
    {
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            Produit p =  ProduitDAO.findById(id,c);
            objet = new ObjectReturn();
            objet.setData(p);
            objet.setMessage("success");
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


    @PutMapping("{id}")
    public ObjectReturn updateProduit(@PathVariable int id) throws Exception
    {
        ObjectReturn objet = null;
        Connection c = null;
        try {
            objet = new ObjectReturn();
            c = Connexion.connect();
            ProduitDAO.updateProduit(id,c);
            objet.setMessage("Product updated");
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

    @PostMapping
    public ObjectReturn newProduct(@RequestBody Produit p)
    {
        Connection c = null;
        ObjectReturn objet = null;
        try
        {
            objet = new ObjectReturn();
            c = Connexion.connect();
            ProduitDAO.newProduit(p,c);
            objet.setData(p);
            objet.setMessage("insertion reussi");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return objet;
    }

    @GetMapping("lists/{id}")
    public ObjectReturn myListProduit(@PathVariable int id) throws SQLException {
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            objet = new ObjectReturn();
            ArrayList<Produit> list = ProduitDAO.myListProduit(id,c);
            objet.setData(list);
            objet.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            c.close();
        }
        return objet;
    }

    @GetMapping("/listes/{id}")
    public ObjectReturn getProduitLimit(@PathVariable int id,int numPages,int size) throws SQLException {
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            objet = new ObjectReturn();
            c = Connexion.connect();
            ArrayList<Produit> liste = ProduitDAO.pagination(numPages,size,id,c);
            objet.setData(liste);
            objet.setMessage("Success");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            c.close();
        }
        return objet;
    }


}