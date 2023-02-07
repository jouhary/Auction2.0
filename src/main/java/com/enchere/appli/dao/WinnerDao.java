package com.enchere.appli.dao;

import java.sql.*;

public class WinnerDao {

    public static void saveWinner(int idp, int idU, Timestamp date, double montant, double commission ,Connection c) throws Exception{
        PreparedStatement stat = null;
        ResultSet res = null;
        try{
            stat = c.prepareStatement("INSERT INTO gagnant (IDUTILISATEUR,IDPRODUIT,MONTANT,DATERENCHERIR,COMMISSION) VALUES (?,?,?,?,?)");
            stat.setInt(1,idU);
            stat.setInt(2,idp);
            stat.setDouble(3,montant);
            stat.setTimestamp(4,date);
            stat.setDouble(5,commission);
            stat.executeUpdate();
        }
        catch (Exception e){
            throw e;
        }
        finally{
            if(stat != null) stat.close();
            if(res != null) res.close();
        }

    }

}
