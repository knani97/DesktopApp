/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Pharmacie;

import doctourna.utils.Navigator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.pharmacieService;


public class AjoutPharmacieController implements Initializable {

    @FXML
    private TextField nom;
    @FXML
    private TextField adresse;
    @FXML
    private TextField gouvernourat;
    @FXML
    private ImageView img;
    @FXML
    private Button upload;
    @FXML
    private Button ajout;
   List<String> typee;
String imgg;
             String imag;

pharmacieService ph=new pharmacieService();
    @FXML
    private Button retour;
    /**;
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       typee =new ArrayList();
        typee.add("*.jpg");
         typee.add("*.png");
                
    }    

    @FXML
    private void upload(ActionEvent event) {
              FileChooser f=new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png", typee));
        File fc=f.showOpenDialog(null);
        if(f!= null)
        {
            System.out.println(fc.getName());
             imgg=fc.getAbsoluteFile().toURI().toString();
                         imag=fc.getName();

             System.out.println(img);
          // System.out.print(img);
             Image i = new Image(imgg);
           img.setImage(i);
        }
    }

    @FXML
    private void ajout(ActionEvent event) {
        if( nom.getText().equals("") || adresse.getText().equals("") || gouvernourat.getText().equals("")|| imgg.equals(""))
       {Error("Veuillez remplir tous les champs");
       }
      else if(!nom.getText().matches("[a-zA-Z\\s]*"))
        {        Error("Le nom doit contenir que des lettres");

        }  else if(!adresse.getText().matches("[a-zA-Z0-9\\s]*"))
        {        Error("L'adresse doit contenir que des lettres et des chiffres");

        } 
        else if(!gouvernourat.getText().matches("[a-zA-Z\\s]*"))
        {        Error("Le gouvernourat doit contenir que des lettres ");

        } 
       else {
             Pharmacie m =new Pharmacie(nom.getText(),adresse.getText(),gouvernourat.getText(),imag);
     
     
             if(ph.ajoutPharmacie(m))
        { 
        Success("Ajout effectué avec succés");
       nom.setText("");
       adresse.setText("");
       gouvernourat.setText("");
       img.setImage(null);
        
        } 
    
       }
        
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
        alert.setTitle("Ajouter un examen");
 
        // Header Text: nullCla
        alert.setHeaderText(null);
        alert.setContentText(msg);
 
        alert.showAndWait();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Pharmacie/pharmacie.fxml");
    }

}
