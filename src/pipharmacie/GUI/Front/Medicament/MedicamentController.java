/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Front.Medicament;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import static pipharmacie.GUI.Front.Pharmacie.PharmacieController.id;
import pipharmacie.entities.Medicament;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.medicamentService;


public class MedicamentController implements Initializable {

    @FXML
    private TableView<Medicament> medicaments;
    @FXML
    private TableColumn<Medicament, String> nomCol;
    @FXML
    private TableColumn<Medicament, String> fourniseurCol;
    @FXML
    private TableColumn<Medicament, Float> prixCol;
    @FXML
    private TableColumn<Medicament, Float> poidsCol;
    @FXML
    private Button fiche;
    medicamentService md=new medicamentService();
    
    public static int id=0;
    @FXML
    private Button retour;
    @FXML
    private TableColumn<Medicament,String> imgCol;
    @FXML
    private TextField search;
    @FXML
    private TableColumn<Medicament, String> categoriECol;
      private final static int rowMax=4 ;
 
   List<Medicament> ls=md.afficherMedicament();
    @FXML
    private Pagination page;
   

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         afficherMedicament();
            page.setPageFactory(this::createPage);
   
    }  
    
    private Node createPage(int pageIndex ){
        
       int fromIndex = pageIndex * rowMax;
       int toIndex = Math.min(fromIndex + rowMax, ls.size());
       medicaments.setItems(FXCollections.observableArrayList(ls.subList(fromIndex, toIndex)));
        FilteredList<Medicament> filteredData = new FilteredList<>(FXCollections.observableArrayList(ls.subList(fromIndex, toIndex)), p -> true);
   search.textProperty().addListener((observable, oldValue, newValue) -> {
	filteredData.setPredicate(p -> {
				// If filter text is empty, display all pharmacies.
	if (newValue == null || newValue.isEmpty()) {
		return true;
	}
        String lowerCaseFilter = newValue.toLowerCase();
				
	if (p.getNom().toLowerCase().contains(lowerCaseFilter)) {
	return true; // Filter matches first name.
	} else if (p.getNomMed().toLowerCase().contains(lowerCaseFilter)) {
		return true; // Filter matches last name.
	}
		return false; // Does not match.
				
   
   });
           });
   SortedList<Medicament> sortedData = new SortedList<>(filteredData);
   sortedData.comparatorProperty().bind(this.medicaments.comparatorProperty());
   this.medicaments.setItems(sortedData);
       return medicaments;
   }

    @FXML
    private void fiche(ActionEvent event) throws IOException {
         Stage stage = new Stage();
            Medicament a = medicaments.getSelectionModel().getSelectedItem();
id=a.getId();
        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/GUI/Front/Medicament/fiche.fxml"));
       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
       public void afficherMedicament()
   {
      List<Medicament> ls=md.afficherMedicament();
        ObservableList<Medicament> Liste=FXCollections.observableArrayList(ls);
 
       nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
       fourniseurCol.setCellValueFactory(new PropertyValueFactory<>("fourniseur"));
            prixCol.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
       poidsCol.setCellValueFactory(new PropertyValueFactory<>("poids"));
       imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));
       categoriECol.setCellValueFactory(new PropertyValueFactory<>("nom"));

medicaments.setItems(Liste);
     FilteredList<Medicament> filteredData = new FilteredList<>(Liste, m -> true);
   search.textProperty().addListener((observable, oldValue, newValue) -> {
	filteredData.setPredicate(m -> {
				// If filter text is empty, display all pharmacies.
	if (newValue == null || newValue.isEmpty()) {
		return true;
	}
        String lowerCaseFilter = newValue.toLowerCase();
				
	if (m.getNom().toLowerCase().contains(lowerCaseFilter)) {
	return true; // Filter matches first name.
	} else if (m.getNomMed().toLowerCase().contains(lowerCaseFilter)) {
		return true; // Filter matches last name.
	}
		return false; // Does not match.
				
   
   });
           });
   SortedList<Medicament> sortedData = new SortedList<>(filteredData);
   sortedData.comparatorProperty().bind(this.medicaments.comparatorProperty());
   this.medicaments.setItems(sortedData);
 
     
   
   }

    @FXML
    private void retour(ActionEvent event) throws IOException {
           Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/front.fxml"));
         Stage stage1 = (Stage) retour.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

   /* @FXML
    private void search(KeyEvent event) throws SQLException {
     if(search.getText().equals(""))
          afficherMedicament();
      else
      { List<Medicament> ls=md.rechercheMedicament(search.getText());
        ObservableList<Medicament> Liste=FXCollections.observableArrayList(ls);
  nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
       fourniseurCol.setCellValueFactory(new PropertyValueFactory<>("fourniseur"));
            prixCol.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
       poidsCol.setCellValueFactory(new PropertyValueFactory<>("poids"));
       categoriECol.setCellValueFactory(new PropertyValueFactory<>("nom"));
              imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));

              

medicaments.setItems(Liste);}
    }*/

    @FXML
    private void search(KeyEvent event) {
    }
}
