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
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.pharmacieService;


public class PharmacieController implements Initializable {

    @FXML
    private TableView<Pharmacie> pharmacies;
    @FXML
    private TextField nom;
    @FXML
    private TextField adresse;
    @FXML
    private TextField gouvernourat;
    @FXML
    private Button image;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    private Button ajouter;
    @FXML
    private ImageView img;
     List<String> typee;
    @FXML
    private TextField  searchField;
    String imgg;
             String imag;

pharmacieService ph=new pharmacieService();
    @FXML
    private TableColumn<Pharmacie, String> nomCol;
    @FXML
    private TableColumn<Pharmacie, String> adresseCol;
    @FXML
    private TableColumn<Pharmacie, String> gouvernouratCol;
    @FXML
    private TableColumn<Pharmacie, String> imageCol;
    @FXML
    private Button retour;
    
  public static int id=0;  
    @FXML
    private ImageView imgAnim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherPharmacie();
            typee =new ArrayList();
        typee.add("*.jpg");
         typee.add("*.png");
         FadeTransition fade = new FadeTransition();
       fade.setNode(imgAnim);
        fade.setDuration(Duration.millis(1500));
        fade.setCycleCount(FadeTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
        
       fade.setFromValue(0);
       fade.setToValue(1);
         fade.play();
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
    private void modifier(ActionEvent event) {
            Pharmacie a = pharmacies.getSelectionModel().getSelectedItem();

        if( nom.getText().equals("") || adresse.getText().equals("") || gouvernourat.getText().equals(""))
       {Error("Veuillez remplir tous les champs");
       }
        else if(!nom.getText().matches("[a-zA-Z\\s]*"))
        {        Error("Le nom doit contenir que des lettres ");

        }  else if(!adresse.getText().matches("[a-zA-Z0-9\\s]*"))
        {        Error("L'adresse doit contenir que des lettres et des chiffres");

        } 
        else if(!gouvernourat.getText().matches("[a-zA-Z\\s]*"))
        {        Error("Le gouvernourat doit contenir que des lettres ");

        } 
       else {
       System.out.println(" hethi "+a.getImg_patente());
        if(imag==null)
        {imag=a.getImg_patente();}
             Pharmacie m =new Pharmacie(nom.getText(),adresse.getText(),gouvernourat.getText(),imag);
       m.setId(a.getId());
             if(ph.modifierPharmacie(m))
        {
        Success("modification effectué avec succés");
       nom.setText("");
       adresse.setText("");
       gouvernourat.setText("");
       img.setImage(null);
        afficherPharmacie();
        }
    
      }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        
          Pharmacie m =pharmacies.getSelectionModel().getSelectedItem();

        if(ph.deletePharmacie(m.getId()))
        {Success("Suppression effectué avec succés");
        afficherPharmacie();
           nom.setText("");
       adresse.setText("");
       gouvernourat.setText("");
       img.setImage(null);
        
        }else 
        { Error("Erreur");}
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Pharmacie/ajoutPharmacie.fxml");
    }
         public void afficherPharmacie()
   {
      List<Pharmacie> ls=ph.afficherPharmacie();
        ObservableList<Pharmacie> Liste=FXCollections.observableArrayList(ls);
 
       nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
       adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
            gouvernouratCol.setCellValueFactory(new PropertyValueFactory<>("gouvernourat"));
       imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

pharmacies.setItems(Liste);
   
   FilteredList<Pharmacie> filteredData = new FilteredList<>(Liste, p -> true);
   searchField.textProperty().addListener((observable, oldValue, newValue) -> {
	filteredData.setPredicate(p -> {
				// If filter text is empty, display all pharmacies.
	if (newValue == null || newValue.isEmpty()) {
		return true;
	}
        String lowerCaseFilter = newValue.toLowerCase();
				
	if (p.getNom().toLowerCase().contains(lowerCaseFilter)) {
	return true; // Filter matches first name.
	} else if (p.getGouvernourat().toLowerCase().contains(lowerCaseFilter)) {
		return true; // Filter matches last name.
	}
		return false; // Does not match.
				
   
   });
           });
   SortedList<Pharmacie> sortedData = new SortedList<>(filteredData);
   sortedData.comparatorProperty().bind(this.pharmacies.comparatorProperty());
   this.pharmacies.setItems(sortedData);
 
           }

         
         
         
    @FXML
    private void afficherDetail(MouseEvent event) {
            Pharmacie a = pharmacies.getSelectionModel().getSelectedItem();
          
nom.setText(a.getNom());
adresse.setText(a.getAdresse());
gouvernourat.setText(a.getGouvernourat());
  Image i = new Image("file:/C:/Users/user/Desktop/imagepi/"+a.getImg_patente());
           img.setImage(i);
            
            
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
        //Navigator.navigate("/pipharmacie/back.fxml");
    }
}
