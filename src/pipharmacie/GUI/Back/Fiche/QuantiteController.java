/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Fiche;

import doctourna.utils.Navigator;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pipharmacie.services.ficheService;


public class QuantiteController implements Initializable {

    @FXML
    private Label quant;
    @FXML
    private TextField quantite;
    @FXML
    private Button add;
    @FXML
    private Button delete;
    @FXML
    private Button retour;
    
ficheService fs =new ficheService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        quant.setText("Quantité :"+FicheController.fiche.getQuantite());

    }    

    @FXML
    private void add(ActionEvent event) {
    FicheController.fiche.setQuantite(FicheController.fiche.getQuantite()+Integer.valueOf(quantite.getText()));
fs.modifierQuantite(FicheController.fiche);
    quant.setText("Quantité :"+FicheController.fiche.getQuantite());
    FicheController bc =new FicheController();
    bc.afficherFiche();
    
    }

    @FXML
    private void delete(ActionEvent event) {
         if(FicheController.fiche.getQuantite()<Integer.valueOf(quantite.getText()))
        {Error("Veuillez saisir une quantité inférieur");}
         else if(!quantite.getText().matches("[0-9\\s]*"))
        {        Error("La quantite doit contenir que  des chiffres");

        }
         else
        {FicheController.fiche.setQuantite(FicheController.fiche.getQuantite()-Integer.valueOf(quantite.getText()));
fs.modifierQuantite(FicheController.fiche);
 
       quant.setText("Quantité :"+FicheController.fiche.getQuantite());}
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Fiche/fiche.fxml");
    }
       private void Error(String msg) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText("Look, an Error Dialog");
alert.setContentText(msg);
alert.showAndWait();
    }
    private void Success(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Modifier une quantite");
 
        
        alert.setHeaderText(null);
        alert.setContentText(msg);
 
        alert.showAndWait();
    }
   
}
