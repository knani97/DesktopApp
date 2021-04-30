/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Medicament;

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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pipharmacie.entities.Categorie;
import pipharmacie.entities.Medicament;
import pipharmacie.services.categorieService;
import pipharmacie.services.medicamentService;


public class AjoutMedicamentController implements Initializable {

    @FXML
    private Button image;
    @FXML
    private ImageView img;
    @FXML
    private Button ajouter;
    @FXML
    private Button retour;
    @FXML
    private TextField nom;
    @FXML
    private TextField fourniseur;
    @FXML
    private TextField prix;
    @FXML
    private TextField poids;
 List<String> typee;
String imgg;
             String imag;
medicamentService md=new medicamentService();
    @FXML
    private ComboBox<String> categorie;
   categorieService cs = new categorieService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO   
        afficherCombo();
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
    private void ajouter(ActionEvent event) {
          if( nom.getText().equals("") || fourniseur.getText().equals("") || prix.getText().equals("")|| poids.equals("")|| imgg.equals(""))
       {Error("Veuillez remplir tous les champs");
       }
      else if(!nom.getText().matches("[a-zA-Z0-9\\s]*"))
        {        Error("Le nom doit contenir que des lettres et des chiffres");

        } else if(!fourniseur.getText().matches("[a-zA-Z\\s]*"))
        {        Error("Le fourniseur doit contenir que des lettres");

        }
      else if(!prix.getText().matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?"))
        {        Error("Le prix doit contenir que des chiffres");

        }
     
      else if(!poids.getText().matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?"))
        {        Error("Le poids doit contenir que  des chiffres");

        }
     
      
       else {
             Medicament m =new Medicament(nom.getText(),fourniseur.getText(),Float.valueOf(prix.getText()),Integer.valueOf(poids.getText()),imag,cs.recupererID(categorie.getValue()));
        if(md.ajoutMedicament(m))
        {
        Success("Ajout effectué avec succés");
       nom.setText("");
       fourniseur.setText("");
       prix.setText("");
       poids.setText("");
        img.setImage(null);
      
        }
    
       }
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Medicament/medicament.fxml");
    }
    private void afficherCombo()
    {
         List<Categorie> L=cs.afficherCategorie();
       List<String> Li=new ArrayList<>();
       for(int i=0;i<L.size();i++)
       { 
           Li.add(L.get(i).getNom());
       }
                        ObservableList<String> Liste=FXCollections.observableArrayList(Li);
                        categorie.setItems(Liste);
    }
}
