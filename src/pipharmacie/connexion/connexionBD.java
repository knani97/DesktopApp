/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.connexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class connexionBD {
        private static connexionBD instance;
        private String url = "jdbc:mysql://localhost:3306/doctourna?serverTimezone=Africa/Tunis";
        private String USERNAME="root";
        private  String PASSWORD="";
        private Connection cnx;

        private connexionBD() {
       
      
    
try {
            cnx = DriverManager.getConnection(url,USERNAME,PASSWORD); // driver qui va traduire la cnnx entre java et base de donnee
            System.out.println("connection etablie");
           
       
        } catch (SQLException ex) {
            Logger.getLogger(connexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }
 public static connexionBD getInstance(){
        if (instance == null) {
            instance = new connexionBD();
        }
        return instance ;
    }

    public Connection getCnx() {
        return cnx;
    }    

}
