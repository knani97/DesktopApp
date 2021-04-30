/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Lenovo
 */
public class Connexion {
    
    private static Connexion obj;
    private Connexion(){
    }
    public static Connexion getInstance(){
       if(obj==null){
           obj = new Connexion();
       }
       return obj;
    }
    
}
