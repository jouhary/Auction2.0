package com.enchere.appli.accesBase;



import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;

public class Connexion {

    public static Connection connect() throws Exception {
        Class.forName("org.postgresql.Driver");
        Connection connect = DriverManager.getConnection("jdbc:postgresql://localhost:5432/enchere", "enchere",
                "UJApuGnSVjUQmSkSr8gSkyshgw_FM87p");
        return connect;
    }


}