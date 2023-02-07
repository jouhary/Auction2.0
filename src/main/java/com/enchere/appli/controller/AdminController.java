package com.enchere.appli.controller;

import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.AdminDAO;
import com.enchere.appli.exception.AuthentificationFailedException;
import com.enchere.appli.model.Admin;
import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.model.Winner;
import com.enchere.appli.service.TokenService;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

@CrossOrigin("*")
@RestController
@RequestMapping("admins")
public class AdminController
{
    @PostMapping
    public ObjectReturn login(@RequestBody Admin admin) throws Exception
    {
        Connection c = null;
        ObjectReturn ans = null;
        Object[] data = null;
        try
        {
            c = Connexion.connect();
            System.out.println(admin.getUserName());
            data = AdminDAO.toLog(admin,c);
            ans = new ObjectReturn();
            ans.setData(data);
            ans.setMessage("Authentification success");

        }
        catch (ClassNotFoundException | NoSuchAlgorithmException | SQLException e) {
            // TODO Auto-generated catch block
            throw e;
        }
        catch (AuthentificationFailedException e) {
            // TODO Auto-generated catch block
            throw e;
        }
        finally {
            c.close();
        }
        return ans;
    }

    @GetMapping("listeReclamations")
    public ObjectReturn liste(@RequestParam(name = "hash",required = true)String hash) throws Exception {
        TokenService.bearerToken(hash);
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            objet = new ObjectReturn();
            ArrayList<Winner> win = Admin.getWinner(c);
            objet.setData(win);
            objet.setMessage("success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            c.close();
        }
        return objet;
    }

    @PostMapping("reclames")
    public ObjectReturn reclamer() throws SQLException {
        ObjectReturn objet = null;
        Connection c = null;
        try
        {
            c = Connexion.connect();
            objet = new ObjectReturn();
            Admin.reclame(c);
            objet.setMessage("Reclamation Success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            c.close();
        }
        return objet;
    }

}
