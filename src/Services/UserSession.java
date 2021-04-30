/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Users;

/**
 *
 * @author Lenovo
 */
public class UserSession {
    int idUser=1;
    String nom="Hammami",prenom="Mongi",image="file:///file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/user.jpg";

    public UserSession() {
    }

    public UserSession(int id) {
        this.idUser=id;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    
    
    public String getImage() {
        return image;
    }

    
    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setImage(String image) {
        this.image = image;
    }
    
    
    
    
    
}
