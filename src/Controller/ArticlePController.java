/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Article;
import Entity.Reagit;
import Services.ServiceArticle;
import Services.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ArticlePController implements Initializable {
    @FXML
    private GridPane GridPaneNews;
    private  List<Article> ListF = new ArrayList<>();
    ServiceArticle SA = new ServiceArticle();
    UserSession US = new UserSession();
    Reagit reagit = new Reagit();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
           ListF.addAll(SA.ArticlePrefere()
           );
           
         
           int column =0 ;
            int row = 1;
           
                for (int i = 0; i < ListF.size(); i++) {

               try {
                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getResource("../Interfaces/mesArticles/ItemAutreArticle.fxml"));
                   AnchorPane anchorPane = fxmlLoader.load();
                   
                   ItemAutreArticleController itemController = fxmlLoader.getController();
                   itemController.setData(ListF.get(i));
                   
                 
                   if (column ==3) {
                       column = 0;
                       row++;
                   }
                   GridPaneNews.add(anchorPane, column++, row); 
                   
                   GridPaneNews.setMinWidth(Region.USE_COMPUTED_SIZE);
                   GridPaneNews.setPrefWidth(Region.USE_COMPUTED_SIZE);
                   GridPaneNews.setMaxWidth(Region.USE_PREF_SIZE);
                   
                   
                   GridPaneNews.setMinHeight(Region.USE_COMPUTED_SIZE);
                   GridPaneNews.setPrefHeight(Region.USE_COMPUTED_SIZE);
                   GridPaneNews.setMaxHeight(Region.USE_PREF_SIZE);
                   GridPane.setMargin(anchorPane, new Insets(10));
               } catch (IOException ex) {
                   Logger.getLogger(MesArticleController.class.getName()).log(Level.SEVERE, null, ex);
               }
                }
    }
    
}
