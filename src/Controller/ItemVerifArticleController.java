/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Article;
import Entity.ArticleCat;
import Entity.Users;
import Services.ServiceArticle;
import Services.UserSession;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ItemVerifArticleController implements Initializable {

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
    private Button btnSuppArticle;
    @FXML
    private Button AjouterArticles;
    @FXML
    private Label idArticle;
    @FXML
    private ImageView imagePdp;
    Article article = new Article();
    Users users = new Users();
    UserSession US= new UserSession();
    ArticleCat ArticleCat = new ArticleCat();
    Services.ServiceArticle SA = new ServiceArticle();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void SupprimerArticle(ActionEvent event) {
        SA.supprimer(new Article(article.getId()));
        System.out.println(article.getId());
    }

    @FXML
    private void AjouterArticle(ActionEvent event) {
        SA.VerifArticle(article);
    }

    public void setData(Article article) {
        this.article  = article;
        
        CategorieArt.setText(article.ArticleCat.getCategorie());
        TitreNews.setText(article.getTitre().toUpperCase());
        TextNews.setText(article.getTitre().toUpperCase());
        idArticle.setText(article.getId().toString());
        String TextItem=TextNews.getText();
//        imagePdp.setImage(new Image("file:///C:/Users/Lenovo/Documents/NetBeansProjects/pidev/src/image/clients/"+ article.users.getImage()));
        if(TextItem.length()>209)
        {
            TextItem=TextItem.substring(0,209);
        }
        
        TextNews.setText(TextItem+"...");
        
        imageNews.setImage(new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/article/"+ article.getImage()));
    }
    
}
