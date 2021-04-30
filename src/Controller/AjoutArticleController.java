/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Article;
import Entity.ArticleCat;
import Services.ServiceArticle;
import Services.ServiceArticleCat;
import Services.UserSession;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import static java.util.Collections.list;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.swing.JOptionPane;
import utils.DataBase;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class AjoutArticleController implements Initializable {
    @FXML
    private TextField titreart;
    @FXML
    private TextArea textart;
    
    @FXML
    private ComboBox idCat;
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
        
        typee =new ArrayList();
        typee.add("*.jpg");
         typee.add("*.png");
         
        try {
            ObservableList<String> list = FXCollections.observableArrayList(sac.afficherCatName());
            idCat.setItems(list);
        } catch (SQLException ex) {
            Logger.getLogger(AjoutArticleController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

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
    private void AjoutArticle(ActionEvent event) throws SQLException {
        ServiceArticle sa = new ServiceArticle();
        UserSession Us = new UserSession();
        int idUser=Us.getIdUser();
        System.out.println(idUser);
        int etatAjout=0;
        
        long time = System.currentTimeMillis();
        java.sql.Date dateAjout = new java.sql.Date(time);
        
        
        sa.ajouter( new Article(0, titreart.getText(),textart.getText(),imag, dateAjout, etatAjout, idUser,5));
        JOptionPane.showMessageDialog(null,"Votre Article à été ajouté");
        titreart.clear();
        textart.clear();
    }
    
}
