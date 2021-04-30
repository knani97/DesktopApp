/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.Utils;

/**
 *
 * @author yass
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyConnexion {
 private static MyConnexion instanceconnection ;
    private   String url="jdbc:mysql://localhost:3306/doctourna?serverTimezone=Africa/Tunis&useLegacyDatetimeCode=false";
    private   String login="root";
    private   String mdp="" ;
    private   Connection conx;
    
    private MyConnexion()
    {
        try {
            conx= DriverManager.getConnection(url,login,mdp);
            System.out.println("connection est établie");
        } catch (SQLException ex) {
            Logger.getLogger(MyConnexion.class.getName()).log(Level.SEVERE, null, ex);
        }
  
    }
    
    
    public static MyConnexion getInsCon(){
       if (instanceconnection==null){
            instanceconnection = new MyConnexion();
        } 
        return instanceconnection ;
    }
    
    
   public  Connection getcnx(){
       return conx; 
   }
   
   
}    
    

