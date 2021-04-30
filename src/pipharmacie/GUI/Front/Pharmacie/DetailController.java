/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Front.Pharmacie;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.pharmacieService;


public class DetailController implements Initializable {

    @FXML
    private ImageView image;
    @FXML
    private Label nom;
    @FXML
    private Label adresse;
    @FXML
    private Label gouvernourat;
pharmacieService ph = new pharmacieService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
    }    
    public void afficher()
    { Pharmacie p= ph.afficherUnePharmacie(PharmacieController.id);
    Image i = new Image("file:/C:/Users/user/Desktop/imagepi/"+p.getImg_patente());
    image.setImage(i);
    nom.setText(p.getNom());
    adresse.setText(p.getAdresse());
    gouvernourat.setText(p.getGouvernourat());
        
    }
    
}
