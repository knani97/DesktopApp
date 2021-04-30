/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.controllers;

import com.doctourna.Alert.AlertDialog;
import com.doctourna.Service.Commande_service;
import com.doctourna.Service.video_Service;
import com.doctourna.Utils.MyConnexion;
import com.doctourna.models.Commande;
import com.doctourna.models.Video;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * FXML Controller class
 *
 * @author yass
 */
public class FrontController implements Initializable {

    @FXML
    private Pane pnl_accessoire;
    @FXML
    private ScrollPane scrollpaneProduit;
    @FXML
    private HBox hboxProduit;
    @FXML
    private Pane pnl_panier;
    @FXML
    private Label prixTotal;
    @FXML
    private TableView<Video> tabpanier;
    @FXML
    private TableColumn<Video, String> col_titre;
    @FXML
    private TableColumn<Video, Float> col_prix_produit;
    @FXML
    private Label username;
    @FXML
    private Button btn_product;
    @FXML
    private Button btn_Panier;
      private TableColumn<Video, String> col_btnDelet;
   static int indiceVideo = 0;
      private int tailleVideo=0;
         video_Service service_Video = new video_Service();
         Commande_service commande_service = new Commande_service();

   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
      prixTotal.setText(String.valueOf(service_Video.prixtotal()) + " DT");
           col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_prix_produit.setCellValueFactory(new PropertyValueFactory<>("prix"));
        try {
            tabpanier.setItems(service_Video.Affichertout_panier());
        } catch (SQLException ex) {
         }
        try {
            tailleVideo = service_Video.Affichertaille();
        } catch (SQLException ex) {
          
        }
          Node[] nodes_accessoire = new Node[tailleVideo];
           scrollpaneProduit.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
           
           
        for (indiceVideo = 0; indiceVideo < tailleVideo; indiceVideo++) {
            try {

                nodes_accessoire[indiceVideo] = FXMLLoader.load(getClass().getResource("../GUI/Item_Video.fxml"));

                hboxProduit.getChildren().add(nodes_accessoire[indiceVideo]);
            } catch (IOException e) {
                e.printStackTrace();
            }
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

                                u.setPanier_id(1);
                          
                              
                                try {
                                    service_Video.Modifier_Video(u,u.getId());
                                } catch (SQLException ex) {
                                 }
                               
                                AlertDialog.showNotification("suppression confirmée!", "suppression a bien faite", AlertDialog.image_checked);

                               col_titre.setCellValueFactory(new PropertyValueFactory<>("titre"));
        col_prix_produit.setCellValueFactory(new PropertyValueFactory<>("prix"));
                               try {
                                    tabpanier.setItems(service_Video.Affichertout_panier());
                                } catch (SQLException ex) {
                                    Logger.getLogger(FrontController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                    prixTotal.setText(String.valueOf(service_Video.prixtotal()) + " DT");
     

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
        tabpanier.getColumns().add(col_btnDelet);
    
        
    }    

    @FXML
    private void handleClicks(ActionEvent event) throws SQLException {
                 if (event.getSource() == btn_Panier) {
            pnl_panier.toFront();
            tabpanier.setItems(service_Video.Affichertout_panier());
            prixTotal.setText(String.valueOf(service_Video.prixtotal()) + " DT");
         }
            if (event.getSource() == btn_product) {
            pnl_accessoire.toFront();
        }
    }
  private Connection c = MyConnexion.getInsCon().getcnx();
    @FXML
    private void Excel(ActionEvent event) {
                try {
          
  String query = "SELECT * from commande";
  
            PreparedStatement pst = c.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            XSSFWorkbook wb = new XSSFWorkbook();
            XSSFSheet sheet = wb.createSheet("commande Infos");
            XSSFRow header = sheet.createRow(0);
            header.createCell(0).setCellValue("prix");
            header.createCell(1).setCellValue("date_achat");
           
        
        
           sheet.autoSizeColumn(0);

            sheet.autoSizeColumn(1);
         
          
           
            sheet.setColumnWidth(3, 256 * 25);
            sheet.setZoom(150);
            
            int index = 1;
            
            
            while (rs.next()) {
                XSSFRow row = sheet.createRow(index);
                row.createCell(0).setCellValue(rs.getInt("prix"));
                row.createCell(1).setCellValue(rs.getString("date_achat"));
             
              
              
               
                
                index++;
            }

            FileOutputStream fileOut = new FileOutputStream("EListe commande" + index + ".xlsx");
            index++;
            wb.write(fileOut);
            fileOut.close();

             AlertDialog.showNotification("Excel", "Excel", AlertDialog.image_checked);


            pst.close();
            rs.close();

        } catch (Exception ex) {
        }
    }

    @FXML
    private void Passer_Commande(ActionEvent event) throws SQLException {
        Commande c = new Commande();
        c.setPrix(Float.valueOf(service_Video.prixtotal()));
        Commande_service ser = new Commande_service();
        ser.Ajouter(c);
                    AlertDialog.showNotification("Commande", "Commande", AlertDialog.image_checked);

    }
    
}
