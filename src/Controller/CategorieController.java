/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.ArticleCat;
import Services.Notification;
import Services.PdfExport;
import Services.ServiceArticleCat;
import Services.qrCodeImage;
import com.itextpdf.text.Rectangle;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import utils.DataBase;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class CategorieController implements Initializable {
     @FXML
    private ImageView imageTop;
    @FXML
    private TextField titre;
    @FXML
    private TableView<ArticleCat> tableview1;
    @FXML
    private TableView<ArticleCat> tableview2;
    @FXML
    private TableColumn<?, ?> imagetab1;
    @FXML
    private TableColumn<?, ?> categorietab1;
    @FXML
    private TableColumn<?, ?> imagetab2;
    @FXML
    private TableColumn<?, ?> categorietab2;
     @FXML
    private Label NomCatTop;

    @FXML
    private Label NmbrCatTop;
    ObservableList<ArticleCat> cat = FXCollections.observableArrayList();
    ObservableList<ArticleCat> Top3cat = FXCollections.observableArrayList();
    ServiceArticleCat sac = new ServiceArticleCat();
    Connection con = DataBase.getInstance().getConnetion();
    List<String> typee;
    String imgg;
    String imag;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Connection con = DataBase.getInstance().getConnetion();
        ObservableList<ArticleCat> list = FXCollections.observableArrayList(sac.afficherList());
        cat.setAll(list);
        
        
        imagetab1.setCellValueFactory(new PropertyValueFactory<>("image"));
        categorietab1.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        categorietab1.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        ServiceArticleCat ArticleCat = new ServiceArticleCat();
        tableview1.setItems(cat);
        
        ObservableList<ArticleCat> Top3catList = FXCollections.observableArrayList(sac.top3ArticleCat());
        sac.top3ArticleCat().get(0);
        Top3cat.setAll(Top3catList);
        imagetab2.setCellValueFactory(new PropertyValueFactory<>("image"));
        categorietab2.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        tableview2.setItems(Top3cat);
        
        
        typee =new ArrayList();
        typee.add("*.jpg");
         typee.add("*.png");
         
         String nbrCatTop=sac.TopArticleCat().get(1);
         String TextCategorie=sac.TopArticleCat().get(0);
          NomCatTop.setText(TextCategorie.toUpperCase());
          NmbrCatTop.setText(nbrCatTop);
          String image=sac.TopArticleCat().get(2);
          Image imageCat = new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/Categories/"+image);
          imageTop.setImage(imageCat);
        //--------
          
       
        }
        // TODO
    

    @FXML
    private void uploadImage(ActionEvent event) {
          FileChooser f=new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png", typee));
        File fc=f.showOpenDialog(null);
        if(f!= null)
        {
            System.out.println(fc.getName());
             imgg=fc.getAbsoluteFile().toURI().toString();
                         imag=fc.getName();

        }
        
    }
    
    @FXML
    public void AjoutCategorie(ActionEvent event) {
        sac.ajouter(new ArticleCat(0, titre.getText(), imag));
    }
    
    @FXML
    void telechargerDATAPDF(ActionEvent event) {
        
        PdfExport pdfExport = new PdfExport();
        pdfExport.PdfExportTest();
        Services.Notification not = new Notification();
        not.Notification();

    }

}