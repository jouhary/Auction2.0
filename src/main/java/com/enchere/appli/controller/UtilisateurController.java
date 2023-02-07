package com.enchere.appli.controller;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.CompteDAO;
import com.enchere.appli.dao.UtilisateurDAO;
import com.enchere.appli.exception.AuthentificationFailedException;
import com.enchere.appli.model.HistoriqueUser;
import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.model.Utilisateur;

import org.springframework.web.bind.annotation.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin("*")
@RestController
@RequestMapping("utilisateurs")
public class UtilisateurController
{
    @PostMapping("inscription")
    public void createUtilisateur(@RequestBody Utilisateur utilisateur) throws Exception
    {
        Connection c = null;
        boolean b = false;
        try
        {
            c = Connexion.connect();
            utilisateur = UtilisateurDAO.newUtilisateur(utilisateur,c);
            CompteDAO.saveCompte(utilisateur.getId(),b,c);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally {
            c.close();
        }

    }


    @PostMapping({"login"})
    public ObjectReturn login(@RequestBody Utilisateur utilisateur) throws Exception {
        ObjectReturn ans = null;
        Object[] data = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            data = UtilisateurDAO.toLog(utilisateur,c);
            ans = new ObjectReturn();
            ans.setData(data);
            ans.setMessage("Authentification success");
            System.out.println("Authentification success");
        }
        catch (AuthentificationFailedException e)
        {
            // TODO Auto-generated catch block
            throw e;
        }
        finally {
            c.close();
        }
        return ans;
    }

    @GetMapping("historiques")
    public ObjectReturn historique(@RequestBody Utilisateur util) throws SQLException {
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            objet = new ObjectReturn();

            ArrayList<HistoriqueUser> h = util.getHistoriques(c);
            objet.setData(h);
            objet.setMessage("Success");
        } catch (Exception e)
        {
            throw new RuntimeException(e);
        }
        finally {
            c.close();
        }
        return objet;
    }

}
