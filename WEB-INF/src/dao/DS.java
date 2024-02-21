package dao;

import java.sql.*;

public class DS {
    public static Connection getConnection(){
        try {
            Class.forName("org.postgresql.Driver");

            String url = "jdbc:postgresql://psqlserv/but2";
            String nom = "paulcanceletu";
            String mdp = "moi";
            return DriverManager.getConnection(url, nom, mdp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
