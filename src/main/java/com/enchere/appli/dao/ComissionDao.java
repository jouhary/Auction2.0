package com.enchere.appli.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ComissionDao {

    public static double getCurrentComission(int idC , Connection c) throws Exception{
        PreparedStatement stat = null;
        ResultSet res = null;
        double result = 0 ;

        try{
            stat = c.prepareStatement("SELECT * FROM Commission WHERE idCategorie =?");
            stat.setInt(1,idC);
            res = stat.executeQuery();
            while(res.next()){
                result = res.getDouble("valeur");

            }

        }
        catch(Exception e){
            throw e;
        }
        finally {
            if(stat != null) stat.close();
            if(res!=null) res.close();
        }

        return result;

    }
}
