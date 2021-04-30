/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Lenovo
 */
public class DataBase {
    static final String URL="jdbc:mysql://localhost:3306/doctourna?serverTimezone=Africa/Tunis";
    static final String USER="root";
    static final String PWD="";
    static Connection con;
    private static DataBase db;
    
    private DataBase(){
        try {
            
            con = DriverManager.getConnection(URL, USER, PWD);
            System.out.println("cnx etablie ...");
            
        } catch (SQLException ex) {
            System.out.println("cnx noooooooooon etablie ...");

        }    
    }
    public static DataBase getInstance(){
        if(db==null){
           db = new DataBase();
       }
       return db;
    }
    
    public Connection  getConnetion(){
        return con;
    }
    
}
