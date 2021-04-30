/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import pidev.models.Pharmacie;
import pidev.services.pharmacieService;
import pidev.utils.google_map;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class pharmaciesController implements Initializable {

    @FXML
    private TableView<Pharmacie> pharmacies;
    @FXML
    private TableColumn<?, ?> nomCol;
    @FXML
    private TableColumn<?, ?> adresseCol;
    @FXML
    private TableColumn<?, ?> gouvernouratCol;
    @FXML
    private TableColumn<?, ?> imageCol;
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
    @FXML
    private Button retour;
    @FXML
    private ImageView imgAnim;
    @FXML
    private TextField searchField;
    pharmacieService ph = new pharmacieService();
    String imag;
        String imgg;
        List<String> typee;
    // copi hedha l user
     private TableColumn<Pharmacie, String> col_btnMap;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      
        /*--------- tt hat f iniitialize hedha l user tcopih --*/
           col_btnMap = new TableColumn("Afficher Map");
         
               javafx.util.Callback<TableColumn<Pharmacie, String>, TableCell<Pharmacie, String>> cellFactory
                = new Callback<TableColumn<Pharmacie, String>, TableCell<Pharmacie, String>>() {
            public TableCell call(final TableColumn<Pharmacie, String> param) {
                final TableCell<Pharmacie, String> cell = new TableCell<Pharmacie, String>() {

                    final Button btn = new Button("Afficher Map");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                            
try{
                    google_map g =  new google_map();
    g.start(new Stage());
                                  
}
catch(Exception e)
{
    
}                                        

                          
                          
                            });
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        };
        col_btnMap.setCellFactory(cellFactory);
        pharmacies.getColumns().add(col_btnMap);
        
      /*----------*/  
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        assert pharmacies != null : "fx:id=\"pharmacies\" was not injected: check your FXML file 'FXML.fxml'.";
        assert nomCol != null : "fx:id=\"nomCol\" was not injected: check your FXML file 'FXML.fxml'.";
        assert adresseCol != null : "fx:id=\"adresseCol\" was not injected: check your FXML file 'FXML.fxml'.";
        assert gouvernouratCol != null : "fx:id=\"gouvernouratCol\" was not injected: check your FXML file 'FXML.fxml'.";
        assert imageCol != null : "fx:id=\"imageCol\" was not injected: check your FXML file 'FXML.fxml'.";
        assert nom != null : "fx:id=\"nom\" was not injected: check your FXML file 'FXML.fxml'.";
        assert adresse != null : "fx:id=\"adresse\" was not injected: check your FXML file 'FXML.fxml'.";
        assert gouvernourat != null : "fx:id=\"gouvernourat\" was not injected: check your FXML file 'FXML.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'FXML.fxml'.";
        assert modifier != null : "fx:id=\"modifier\" was not injected: check your FXML file 'FXML.fxml'.";
        assert supprimer != null : "fx:id=\"supprimer\" was not injected: check your FXML file 'FXML.fxml'.";
        assert ajouter != null : "fx:id=\"ajouter\" was not injected: check your FXML file 'FXML.fxml'.";
        assert img != null : "fx:id=\"img\" was not injected: check your FXML file 'FXML.fxml'.";
        assert retour != null : "fx:id=\"retour\" was not injected: check your FXML file 'FXML.fxml'.";
        assert imgAnim != null : "fx:id=\"imgAnim\" was not injected: check your FXML file 'FXML.fxml'.";
        assert searchField != null : "fx:id=\"searchField\" was not injected: check your FXML file 'FXML.fxml'.";
        afficherPharmacie();
       
        typee = new ArrayList();
        typee.add("*.jpg");
        typee.add("*.png");
    }    

    @FXML
    private void afficherDetail(MouseEvent event) {
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
    public void afficherPharmacie() {
       List<Pharmacie> ls = ph.read();
        ObservableList<Pharmacie> liste = FXCollections.observableArrayList(ls);

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        gouvernouratCol.setCellValueFactory(new PropertyValueFactory<>("gouvernourat"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        pharmacies.setItems(liste);

        FilteredList<Pharmacie> filteredData = new FilteredList<>(liste, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(p -> {
                // If filter text is empty, display all pharmacies.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (p.getNom().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } else return p.getGouvernourat().toLowerCase().contains(lowerCaseFilter); // Filter matches last name.
// Does not match.


            });
        });
        SortedList<Pharmacie> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(this.pharmacies.comparatorProperty());
        this.pharmacies.setItems(sortedData);

    }
    @FXML
    private void upload(ActionEvent event) {
               FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png", typee));
        File fc = f.showOpenDialog(null);
        if (f != null) {
            System.out.println(fc.getName());
            imgg = fc.getAbsoluteFile().toURI().toString();
            imag = fc.getName();

            System.out.println(img);
            // System.out.print(img);
            Image i = new Image(imgg);
            img.setImage(i);
        }
    }

    @FXML
    private void modifier(ActionEvent event) {
           Pharmacie a = pharmacies.getSelectionModel().getSelectedItem();

        if (nom.getText().equals("") || adresse.getText().equals("") || gouvernourat.getText().equals("")) {
            Error("Veuillez remplir tous les champs");
        } else if (!nom.getText().matches("[a-zA-Z\\s]*")) {
            Error("Le nom doit contenir que des lettres ");

        } else if (!adresse.getText().matches("[a-zA-Z0-9\\s]*")) {
            Error("L'adresse doit contenir que des lettres et des chiffres");

        } else if (!gouvernourat.getText().matches("[a-zA-Z\\s]*")) {
            Error("Le gouvernourat doit contenir que des lettres ");

        } else {
            System.out.println(" hethi " + a.getImg_patente());
            if (imag == null) {
                imag = a.getImg_patente();
            }
            Pharmacie m = new Pharmacie(nom.getText(), adresse.getText(), gouvernourat.getText(), imag);
            m.setId(a.getId());
            ph.update(m);
                System.out.println("modification effectué avec succés");
                nom.setText("");
                adresse.setText("");
                gouvernourat.setText("");
                img.setImage(null);
                afficherPharmacie();
            }
    }

    @FXML
    private void supprimer(ActionEvent event) {
         Pharmacie m = pharmacies.getSelectionModel().getSelectedItem();

        ph.delete(m);
            Success("Suppression effectué avec succés");
            afficherPharmacie();
            nom.setText("");
            adresse.setText("");
            gouvernourat.setText("");
            img.setImage(null);
    }

    @FXML
    private void ajouter(ActionEvent event) {
           if (nom.getText().equals("") || adresse.getText().equals("") || gouvernourat.getText().equals("") || imgg.equals("")) {
            System.out.println("Veuillez remplir tous les champs");
        } else if (!nom.getText().matches("[a-zA-Z\\s]*")) {
            System.out.println("Le nom doit contenir que des lettres");

        } else if (!adresse.getText().matches("[a-zA-Z0-9\\s]*")) {
            System.out.println("L'adresse doit contenir que des lettres et des chiffres");

        } else if (!gouvernourat.getText().matches("[a-zA-Z\\s]*")) {
            System.out.println("Le gouvernourat doit contenir que des lettres ");

        } else {
            Pharmacie m = new Pharmacie(nom.getText(), adresse.getText(), gouvernourat.getText(), imag);


            ph.add(m);
                Success("Ajout effectué avec succés");
                nom.setText("");
                adresse.setText("");
                gouvernourat.setText("");
                img.setImage(null);
                afficherPharmacie();
            

        }
 /* Stage stage = new Stage();
                
                

        Parent root = FXMLLoader.load(getClass().getResource("../ui/ajoutPharmacie.fxml"));
         Stage stage1 = (Stage) ajouter.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();*/
    }

    @FXML
    private void retour(ActionEvent event) {
    }
    
}
