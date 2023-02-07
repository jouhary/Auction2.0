package com.enchere.appli.controller;


import com.enchere.appli.accesBase.Connexion;
import com.enchere.appli.dao.V_statDAO;
import com.enchere.appli.model.ObjectReturn;
import com.enchere.appli.model.V_stat;

import org.springframework.web.bind.annotation.*;

import java.sql.Connection;
import java.sql.SQLException;
@CrossOrigin("*")
@RestController
@RequestMapping("statistiques")
public class V_statController
{
    @GetMapping
    public ObjectReturn getStat(@RequestParam(name = "hash", required = true) String hash) throws Exception
    {
        Connection c = null;
        ObjectReturn data = null;
        try
        {
            c = Connexion.connect();
            V_stat stat = V_statDAO.getAllStat(c);
            data = new ObjectReturn();
            data.setData(stat);
            data.setMessage("Succes");
        }
        catch (ClassNotFoundException | SQLException e)
        {
            throw e;
        }
        finally {
            c.close();
        }
        return data;
    }


}
