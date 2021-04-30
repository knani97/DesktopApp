/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.controllers;

import java.net.URL;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import pidev.models.Inventaire;
import pidev.models.Pharmacie;
import pidev.services.InventaireService;
import pidev.services.pharmacieService;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class InventaireController implements Initializable {

    @FXML
    private TableView<Inventaire> inventaires;
    @FXML
    private TableColumn<?, ?> medicamentCol;
    @FXML
    private TableColumn<?, ?> quantiteCol;
    @FXML
    private TextField medicament;
    @FXML
    private TextField quantite;
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
    @FXML
    private ImageView image_qr;
    InventaireService inv = new InventaireService();
    pharmacieService phe= new pharmacieService();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    afficherStock();
    }    

    @FXML
    private void afficherDetail(MouseEvent event) {
                 Inventaire in = inventaires.getSelectionModel().getSelectedItem();
       medicament.setText(String.valueOf(in.getMedicaments_id()));
       quantite.setText(String.valueOf(in.getQuantite()));
        String ImageUrl = "file:///C:/Users/mouhe/OneDrive/Bureau/images/";
        Image image = new Image(ImageUrl + in.getQr());
          image_qr.setImage(image);
    }

    @FXML
    private void modifier(ActionEvent event) {
          Inventaire i = inventaires.getSelectionModel().getSelectedItem();
         Pharmacie ph =new Pharmacie(0, null, null, null, null);
                 ph.setId(i.getPharmacie_id());
        if (medicament.getText().equals("") || quantite.getText().equals("") ) {
            Error("Veuillez remplir tous les champs");
       
       
        }
           // int Pharmacie_id ;
           i.setMedicaments_id(Integer.parseInt(medicament.getText()));
           i.setQuantite(Integer.parseInt(quantite.getText()));
               inv.update(i,ph ); 
                System.out.println("modification effectué avec succés");
                medicament.setText("");
                quantite.setText("");
                
                afficherStock();
    }

    @FXML
    private void supprimer(ActionEvent event) {
          Inventaire in = inventaires.getSelectionModel().getSelectedItem();
            Pharmacie ph = new Pharmacie(0, null, null, null, null);
            ph.setId(in.getPharmacie_id());
        inv.delete(in,ph);
            Success("Suppression effectué avec succés");
            afficherStock();
            medicament.setText("");
            quantite.setText("");
    }
 private void Success(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajouter un examen");

        // Header Text: nullCla
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    

    }
     private void Error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }
    @FXML
    private void ajouter(ActionEvent event) {
                    if (medicament.getText().equals("") || quantite.getText().equals("") ) {
            System.out.println("Veuillez remplir tous les champs");
        
       
        } else {
            Inventaire i = new Inventaire(1,Integer.parseInt(medicament.getText()), Integer.parseInt(quantite.getText()));
            Pharmacie ph = new Pharmacie (2,"kk","hhh","hhhhhhh","yyyy");

           if(inv.add(i,ph )){
                Success("Ajout effectué avec succés");
                medicament.setText("");
                quantite.setText("");
                afficherStock();
           // InventaireService inv = new InventaireService();
               
           }
               

    }
   
    }

    @FXML
    private void retour(ActionEvent event) {
    }
    private void afficherStock() {
         List<Inventaire> ls = inv.read();
        ObservableList<Inventaire> liste = FXCollections.observableArrayList(ls);

        medicamentCol.setCellValueFactory(new PropertyValueFactory<>("medicaments_id"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        
       
        inventaires.setItems(liste);

        FilteredList<Inventaire> filteredData = new FilteredList<>(liste, p -> true);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(p -> {
                // If filter text is empty, display all pharmacies.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();

                if (String.valueOf(p.getMedicaments_id()).toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches first name.
                } 
                else
                    if (String.valueOf(p.getMedicaments_id()).toLowerCase().contains(lowerCaseFilter)) {
		return true; // Filter matches last name.
	}
		return false;
                  
            });
        });
        SortedList<Inventaire> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(this.inventaires.comparatorProperty());
        this.inventaires.setItems(sortedData);

    }
    
    
}
