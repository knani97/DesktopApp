/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.controllers;

import com.doctourna.Alert.AlertDialog;
import com.doctourna.Service.video_Service;
import com.doctourna.models.Video;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 * FXML Controller class
 *
 * @author yass
 */
public class home_VideoController implements Initializable {

    @FXML
    private VBox vboxdrawer;
    @FXML
    private ImageView imagechange;
    @FXML
    private Label fullName;
    @FXML
    private Button btn_affichage;
    @FXML
    private Pane pnl_video;
    @FXML
    private TextField txt_Seach;
    @FXML
    private TableView<Video> tabview;
    @FXML
    private TableColumn<Video, String> col_titre;
    @FXML
    private TableColumn<Video, String> col_source;
    @FXML
    private TableColumn<Video, Integer> col_paye;
    @FXML
    private TableColumn<Video, Float> col_prix;
    @FXML
    private TableColumn<Video, Integer> col_note;

    @FXML
    private TextField txt_titre;
    @FXML
    private TextField txt_source;
    @FXML
    private TextField txt_paye;
    @FXML
    private TextField txt_prix;
    @FXML
    private Button btn_ajout;
    @FXML
    private TextField txt_note;
    video_Service service = new video_Service();
     private TableColumn<Video, String> col_btnDelet;
          
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Modifier();
        tabview.setEditable(true);  
                search();
        try {
            refreche();
        } catch (SQLException ex) {
        }
           col_btnDelet = new TableColumn("Supprimer");
                javafx.util.Callback<TableColumn<Video, String>, TableCell<Video, String>> cellFactory
                = new Callback<TableColumn<Video, String>, TableCell<Video, String>>() {
            public TableCell call(final TableColumn<Video, String> param) {
                final TableCell<Video, String> cell = new TableCell<Video, String>() {

                    final Button btn = new Button("supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setOnAction(event -> {
                                Video u = getTableView().getItems().get(getIndex());

                            
                                             

                          
                              
                                try {
                                    service.Supprimer(u.getId());
                                } catch (SQLException ex) {
                                 }
                               
                                AlertDialog.showNotification("suppression confirmée!", "suppression a bien faite", AlertDialog.image_checked);

                                try {
                                    refreche();
                                } catch (SQLException ex) {
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
        col_btnDelet.setCellFactory(cellFactory);
        tabview.getColumns().add(col_btnDelet);
    }    

    @FXML
    private void handleClicks(ActionEvent event) {
    }
  public void refreche() throws SQLException {
    

 
   
      col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_titre.setCellFactory(TextFieldTableCell.<Video> forTableColumn());
       
        col_source.setCellValueFactory(new PropertyValueFactory<>("source"));
        col_source.setCellFactory(TextFieldTableCell.<Video> forTableColumn());
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_prix.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
   col_paye.setCellValueFactory(new PropertyValueFactory<>("paye"));
     col_paye.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_note.setCellValueFactory(new PropertyValueFactory<>("note"));
     col_note.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tabview.getItems().clear();

        tabview.setItems(service.Affichertout());
  }
    @FXML
    private void ajouter_produit(ActionEvent event) throws SQLException {
        
         if (txt_titre.getText().equals("")) {
            AlertDialog.showNotification("Error !", "Champ vide de col_titre", AlertDialog.image_cross);
        } else if (txt_titre.getText().matches("^[0-9]+$")) {
            AlertDialog.showNotification("Erreur col_titre !", "il faut saisir des caracteres  !", AlertDialog.image_cross);
        } else if (txt_prix.getText().equals("")) {
            AlertDialog.showNotification("Error !", "Champ vide de Prix", AlertDialog.image_cross);
        } else if (Float.valueOf(txt_prix.getText()) <= 0) {
            AlertDialog.showNotification("Error !", "Champ de Prix", AlertDialog.image_cross);
        } else if (txt_prix.getText().matches("^[a-zA-Z]+$")) {
            AlertDialog.showNotification("Erreur Telephone !", "Prix incorrect", AlertDialog.image_cross);
        } else if (col_paye.getText().equals("")) {
            AlertDialog.showNotification("Error !", "Champ vide de col_paye", AlertDialog.image_cross);
        } 
        
        else if (col_note.getText().equals("")) {
            AlertDialog.showNotification("Error !", "Champ vide de col_note", AlertDialog.image_cross);
        } 
        else if (col_source.getText().equals("")) {
            AlertDialog.showNotification("Error !", "Champ vide de col_source", AlertDialog.image_cross);
        } else if (col_source.getText().matches("^[0-9]+$")) {
            AlertDialog.showNotification("Erreur col_source !", "il faut saisir des caracteres  !", AlertDialog.image_cross);
        } 
         else
        {
              Video p = new Video(txt_titre.getText(),txt_source.getText(),Integer.valueOf(txt_paye.getText()),Float.valueOf(txt_prix.getText()),Integer.valueOf(txt_note.getText()),1);
            service.Ajouter(p);
                   
            refreche();
        }
        
        
        
        
    }
    public void search() {
        txt_Seach.setOnKeyReleased(e
                -> {
            if (txt_Seach.getText().equals("") ) {

                try {
                    refreche();
                } catch (SQLException ex) {
                }

            } else {

                try {
    
        col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_titre.setCellFactory(TextFieldTableCell.<Video> forTableColumn());
       
        col_source.setCellValueFactory(new PropertyValueFactory<>("source"));
        col_source.setCellFactory(TextFieldTableCell.<Video> forTableColumn());
        col_prix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col_prix.setCellFactory(TextFieldTableCell.forTableColumn(new FloatStringConverter()));
   col_paye.setCellValueFactory(new PropertyValueFactory<>("paye"));
     col_paye.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        col_note.setCellValueFactory(new PropertyValueFactory<>("note"));
     col_note.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
             tabview.getItems().clear();

                    tabview.setItems(service.serach(txt_Seach.getText()));

                } catch (SQLException ex) {
                    }
        

            }
        }
        );

    }
      public void Modifier()
    {
         
                   
                  
             
     
    

   
               
                col_titre.setOnEditCommit((TableColumn.CellEditEvent<Video, String> event) -> {
            TablePosition<Video, String> pos = event.getTablePosition();
                
            String titre = event.getNewValue();
                 
            int row = pos.getRow();
            Video ac = event.getTableView().getItems().get(row);
           
  
            ac.setTitre(titre);
                    try {
                        service.Modifier(ac,ac.getId());
                    } catch (SQLException ex) {
                    }
        });
                
                
          
            
              
              
                        col_source.setOnEditCommit((TableColumn.CellEditEvent<Video, String> event) -> {
            TablePosition<Video, String> pos = event.getTablePosition();
           
            String s = event.getNewValue();
                  
            int row = pos.getRow();
            Video ab = event.getTableView().getItems().get(row);
          
  
            ab.setSource(s);
                    try {
                        service.Modifier(ab,ab.getId());
                    } catch (SQLException ex) {
                    }
        });       
              
                        col_note.setOnEditCommit((TableColumn.CellEditEvent<Video, Integer> event) -> {
            TablePosition<Video, Integer> pos = event.getTablePosition();
           
            Integer note = event.getNewValue();
                  
            int row = pos.getRow();
            Video ab = event.getTableView().getItems().get(row);
          
  
            ab.setNote(note);
                    try {
                        service.Modifier(ab,ab.getId());
                    } catch (SQLException ex) {
                    }
        }); 
                        
                        
     col_paye.setOnEditCommit((TableColumn.CellEditEvent<Video, Integer> event) -> {
            TablePosition<Video, Integer> pos = event.getTablePosition();
           
            Integer paye = event.getNewValue();
                  
            int row = pos.getRow();
            Video ab = event.getTableView().getItems().get(row);
          
  
            ab.setPaye(paye);
                    try {
                        service.Modifier(ab,ab.getId());
                    } catch (SQLException ex) {
                    }
        });  
     
        col_prix.setOnEditCommit((TableColumn.CellEditEvent<Video, Float> event) -> {
            TablePosition<Video, Float> pos = event.getTablePosition();
           
            Float Prix_Ab = event.getNewValue();
                  
            int row = pos.getRow();
            Video ab = event.getTableView().getItems().get(row);
          
  
            ab.setPrix(Prix_Ab);
                    try {
                        service.Modifier(ab,ab.getId());
                    } catch (SQLException ex) {
                    }
        });
     
     
     
                
    }
    
}
