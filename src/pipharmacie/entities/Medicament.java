/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.entities;

import javafx.scene.image.ImageView;


public class Medicament {
    private int id;
    private String nomMed;
    private String fourniseur;
    private float prix_achat;
    private float poids;
     private String img_med;
     private int idCat ;
private String nom;

   private ImageView image= new ImageView();

    public Medicament() {
    }

    public Medicament(String nomMed, String fourniseur, float prix_achat, float poids,String img_med,int idCat) {
        this.nomMed = nomMed;
        this.fourniseur = fourniseur;
        this.prix_achat = prix_achat;
        this.poids = poids;
        this.img_med=img_med;
        this.idCat=idCat;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImg_med() {
        return img_med;
    }

    public void setImg_med(String img_med) {
        this.img_med = img_med;
    }

    public ImageView getImage() {
        return image;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNomMed() {
        return nomMed;
    }

    public void setNomMed(String nomMed) {
        this.nomMed = nomMed;
    }

    public String getFourniseur() {
        return fourniseur;
    }

    public void setFourniseur(String fourniseur) {
        this.fourniseur = fourniseur;
    }

    public float getPrix_achat() {
        return prix_achat;
    }

    public void setPrix_achat(float prix_achat) {
        this.prix_achat = prix_achat;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }
    
    
}
