/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Article;
import Services.ServiceArticle;
import Services.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ItemRecomController implements Initializable {

    @FXML
    private VBox Vbox;
    @FXML
    private Pane BoxNews;
    @FXML
    private Text CategorieArt;
    @FXML
    private Label NomUser;
    @FXML
    private ImageView imageNews;
    @FXML
    private Text TitreNews;
    @FXML
    private Text TextNews;
    @FXML
    private Button detailsArticles;
    @FXML
    private Button btnSuppArticle;
    @FXML
    private Button detailsArticle;
    @FXML
    private GridPane GridPaneNews;
    @FXML
    private Label idArticle;
    private  List<Article> ListF = new ArrayList<>();
    ServiceArticle SA = new ServiceArticle();
    UserSession US = new UserSession();
    Article article = new Article();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           
           
              
    }    

    @FXML
    private void DetailsArticles(ActionEvent event) {
    }

    @FXML
    private void SupprimerArticle(ActionEvent event) {
    }

    void setData(Article get) {
         this.article  = article;
        
        NomUser.setText(article.users.getNom().toUpperCase()+" "+article.users.getPrenom());
        CategorieArt.setText(article.ArticleCat.getCategorie());
        TitreNews.setText(article.getTitre().toUpperCase());
        idArticle.setText(article.getId().toString());
        
        String TextItem=article.getText();
        if(TextItem.length()>209)
        {
            TextItem=TextItem.substring(0,209);
        }
        
        TextNews.setText(TextItem+"...");
        
        imageNews.setImage(new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/article/"+ article.getImage()));
    }
    
}
