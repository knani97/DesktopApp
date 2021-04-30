/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Front.Medicament;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import pipharmacie.GUI.Front.Pharmacie.PharmacieController;
import pipharmacie.entities.Fiche;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.ficheService;


public class FicheController implements Initializable {

    @FXML
    private Label medicament;
    @FXML
    private Label quantite;
    @FXML
    private Label prix;
    ficheService fs = new ficheService();
    @FXML
    private Label med;
    @FXML
    private Label qua;
    @FXML
    private Label pri;
    @FXML
    private Label util;
    @FXML
    private Label utilisation;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficher();
    }    
      public void afficher()
    {
      
 if(fs.ficheexist(MedicamentController.id)!=0)
 {   Fiche p=fs.afficherUneFiche(MedicamentController.id);
     medicament.setText(p.getNomMed());
    
     quantite.setText(String.valueOf(p.getQuantite()));
    
    prix.setText(String.valueOf(p.getPrix_vente()));
utilisation.setText(p.getUtilisation());

 }
 
 else 
  { medicament.setVisible(false);
    
     quantite.setVisible(false);
    
    prix.setVisible(false);
    qua.setText("Ce médicament n'a pas une fiche");
     
     med.setVisible(false);
    
    pri.setVisible(false);
    util.setVisible(false);
 }
   
    
        
    } 


}

