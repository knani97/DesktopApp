/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author meriem
 */
public class DbConnection {
    private static DbConnection instance;
    
    private Connection cnx;
    
    private final String URL="jdbc:mysql://localhost:3306/Doctourna";
    private final String USERNAME = "root";
    private final String PASSWORD = "";
    
private DbConnection() {

        try {
            cnx= DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Connected to Doctourna");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }


}
public static DbConnection getInstance(){
    if(instance == null)
        instance= new DbConnection();
    return instance;
        

}

    public Connection getCnx() {
        return cnx;
    }

}
