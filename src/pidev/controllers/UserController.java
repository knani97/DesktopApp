/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.nashorn.internal.objects.NativeArray;
import pidev.models.Pharmacie;
import pidev.services.pharmacieService;
import pidev.utils.google_map;

/**
 * FXML Controller class
 *
 * @author Meriem
 */
public class UserController implements Initializable {

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
    private ImageView img;
    @FXML
    private Button retour;
    @FXML
    private ImageView imgAnim;
    @FXML
    private TextField searchField;
    pharmacieService phs = new pharmacieService();
    
    @FXML
    private TextField searchField1;
    @FXML
    private Button search;
    //List<Pharmacie> ls = phs.read();
     private TableColumn<Pharmacie, String> col_btnMap;
    ObservableList<Pharmacie> listeph ;
    /**
     * Initializes the controller class.
     */
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
             
        listeph = FXCollections.observableArrayList();
        for (Pharmacie u :phs.read()) {
            listeph.add(u);
        }
       // System.out.println(listeph);
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        gouvernouratCol.setCellValueFactory(new PropertyValueFactory<>("gouvernourat"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        pharmacies.setItems(listeph);
        
         FilteredList<Pharmacie> filteredData = new FilteredList<>(listeph, p -> true);
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
        
    }  
    
         
private void refreshTable() {
ObservableList<Pharmacie> listeph = FXCollections.observableArrayList(); 
        listeph.clear();
       

    }
    public void LoadData(ActionEvent event){
        //System.out.println(ls.size());
        
        // refreshTable();
         System.out.println ("aaaa"+searchField1.getText());
          List<Pharmacie> ls = phs.filtre(searchField1.getText());
          
                  
          System.out.println("ls:" + ls );
      //  System.out.println(ls.size());
        listeph = FXCollections.observableArrayList();
       for (Pharmacie f : ls )
       {
           listeph.add(f);
       }
        System.out.println("************"+listeph);
  /*     
        listeph = FXCollections.observableArrayList();
        for (Pharmacie u :phs.read()) {
            listeph.add(u);
        }*/
       // System.out.println(listeph);
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        adresseCol.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        gouvernouratCol.setCellValueFactory(new PropertyValueFactory<>("gouvernourat"));
        imageCol.setCellValueFactory(new PropertyValueFactory<>("image"));

        pharmacies.setItems(listeph);

      
    }
    @FXML
    private void afficherDetail(MouseEvent event) {
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
      /*           FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML.fxml"));
            
       Parent root = loader.load();
         Scene scene = new Scene(root);
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        primaryStage.show();*/
   /*     Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("pidev/ui/FXML.fxml"));
         Stage stage1 = (Stage) retour.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage1.setScene(scene);
        stage1.show();*/
  // FXMLLoader loader= new FXMLLoader(getClass().getResource("FXML.fxml"));
  
  
 /* Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
        Stage mainStage = new Stage();
        Scene scene = new Scene(root);
        mainStage.setScene(scene);
        mainStage.show();*/
   /*Parent root = FXMLLoader.load(getClass().getResource("FXML.fxml"));
    Stage primaryStage = new Stage();
    Scene scene = new Scene(root);
      primaryStage.setScene(scene);
      primaryStage.show();*/
   
    
    }

    @FXML
    private void medsearch(ActionEvent event) throws IOException  {
            List<Pharmacie> liste = phs.filtre(searchField1.getText());
         FXMLLoader loader = new FXMLLoader(getClass().getResource("FXML2.fxml"));
            
            Parent root = loader.load(); 
            searchField.getScene().setRoot(root);
        
        
    }
    
}