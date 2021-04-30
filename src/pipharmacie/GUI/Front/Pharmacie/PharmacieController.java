/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Front.Pharmacie;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.pharmacieService;


public class PharmacieController implements Initializable {

    @FXML
    private TableView<Pharmacie> pharmacies;
    @FXML
    private TableColumn<Pharmacie, String> nomCol;
    @FXML
    private TableColumn<Pharmacie, String> imgCol;
    @FXML
    private Button details;
    pharmacieService ph=new pharmacieService();
    public static int id=0;
    @FXML
    private Button retour;
    @FXML
    private AnchorPane filtredField;
    @FXML
    private TextField searchField;
    //private ListView<Pharmacie> listP;
  
    @FXML
    private Pagination page;
    
    private final static int rowMax=4 ;
 
   List<Pharmacie> ls=ph.afficherPharmacie();
   
           
           
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       
       afficherPharmacie();
         page.setPageFactory(this::createPage);
   
           
        //this.loadData();
     
       
    }  
    
  private Node createPage(int pageIndex ){
        
       int fromIndex = pageIndex * rowMax;
       int toIndex = Math.min(fromIndex + rowMax, ls.size());
       pharmacies.setItems(FXCollections.observableArrayList(ls.subList(fromIndex, toIndex)));
        FilteredList<Pharmacie> filteredData = new FilteredList<>(FXCollections.observableArrayList(ls.subList(fromIndex, toIndex)), p -> true);
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
       return pharmacies;
   }

    @FXML
    private void details(ActionEvent event) throws IOException {
            Stage stage = new Stage();
            Pharmacie a = pharmacies.getSelectionModel().getSelectedItem();
id=a.getId();
        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/GUI/Front/Pharmacie/detail.fxml"));
       
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
       public void afficherPharmacie()
   {
        
      List<Pharmacie> ls=ph.afficherPharmacie();
        ObservableList<Pharmacie> Liste=FXCollections.observableArrayList(ls);
 
       nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
     
       imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));

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
    private void retour(ActionEvent event) throws IOException {
                Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/front.fxml"));
         Stage stage1 = (Stage) retour.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
  /*  private void loadData(){
         List<Pharmacie> ls=ph.afficherPharmacie();
        ObservableList<Pharmacie> Liste=FXCollections.observableArrayList(ls);
       this.listP.setItems(Liste);
        
    }*/
}
