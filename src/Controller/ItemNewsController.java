/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entity.Article;
import Entity.ArticleCat;
import Entity.Reagit;
import Entity.Users;
import Services.ServiceArticle;
import Services.UserSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ItemNewsController implements Initializable {

    
    @FXML
    private Text CategorieArt;
    @FXML
    private VBox Vbox;
    @FXML
    private Pane BoxNews;
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
    private Label idArticle;
    private Button detailsLikeBtnArticle;
     @FXML
    private Button DislikeBtnArticle1;
     @FXML
    private Button ModArt;
     @FXML
    private ImageView imagePdp;
    Article article = new Article();
    Users users = new Users();
    UserSession US= new UserSession();
    ArticleCat ArticleCat = new ArticleCat();
    Services.ServiceArticle SA = new ServiceArticle();
    int idArt=0;
    @FXML
    private Text LikeAction;

    @FXML
    private Text DislikeAction;
    /**
     * Initializes the controller class.
     */
    
    public void setData(Article article) {
        this.article  = article;
        
        NomUser.setText(article.users.getNom().toUpperCase()+" "+article.users.getPrenom().toUpperCase());
        CategorieArt.setText(article.ArticleCat.getCategorie());
        TitreNews.setText(article.getTitre().toUpperCase());
        idArticle.setText(article.getId().toString());
        idArt=article.getId();
        imagePdp.setImage(new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/clients/"+ article.users.getImage()));
        
        String TextItem=article.getText();
        if(TextItem.length()>209)
        {
            TextItem=TextItem.substring(0,209);
        }
        
        TextNews.setText(TextItem+"...");
        
        imageNews.setImage(new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/article/"+ article.getImage()));
        if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==idArt)
                                             .filter(a -> a.getTypeReact()==0 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {LikeAction.setText("Add Like");
                                     DislikeAction.setText("Supp Dislike");
                                     }
          if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==idArt)
                                             .filter(a -> a.getTypeReact()==1 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {LikeAction.setText("Supp Like");
                                     DislikeAction.setText("Add Dislike");
                                     }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    


    @FXML
    private void DetailsArticles(ActionEvent event) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/mesArticles/DetailsArticle.fxml"));
        Parent root = loader.load();
        DetailsArticleController  sc2Controller = loader.getController();
        sc2Controller.showInformation(article.getId(),TextNews.getText(),TitreNews.getText(),imageNews.getImage(),NomUser.getText(),CategorieArt.getText());
        DislikeBtnArticle1.getScene().setRoot(root);
        
        
    }
public  void LikeBtnArticle(ActionEvent event) {
    if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getTypeReact()==0 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {LikeAction.setText("Supp Like");
                                     DislikeAction.setText("Add Dislike");
                                     
                                        SA.AjoutReajit(new Reagit(0,US.getIdUser(),idArt, 1));
                                        
                                     }
    else if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()==0)
                                     {
                                        SA.AjoutReajit(new Reagit(0,US.getIdUser(),idArt, 1));
                                        
                                     }
    else if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getTypeReact()==1 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {
                                         LikeAction.setText("Add Like");
                                     DislikeAction.setText("Add Dislike");
                                        SA.supprimerReagit(new Reagit(idArt));
                                        
                                        
                                     }
    }
public  void DisLikeBtnArticle(ActionEvent event) {
    if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getTypeReact()==1 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {
                                         LikeAction.setText("Add Like");
                                         DislikeAction.setText("Supp Dislike");
                                        SA.AjoutReajit(new Reagit(0,US.getIdUser(),idArt, 0));
                                        
                                     }
    else if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()==0)
                                     {
                                         LikeAction.setText("Add Like");
                                         DislikeAction.setText("Supp Dislike");
                                        SA.AjoutReajit(new Reagit(0,US.getIdUser(),idArt, 0));
                                        
                                     }
    else if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getTypeReact()==0 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {
                                         LikeAction.setText("Add Like");
                                         DislikeAction.setText("Add Dislike");
                                        SA.supprimerReagit(new Reagit(idArt));
                                        
                                     }
    System.out.println(idArt+"---------");
    }

 @FXML
    private void SupprimerArticle(ActionEvent event) {
        SA.supprimer(article);
    } 
    
    
    @FXML
    void ModArticle(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/mesArticles/ModArticle.fxml"));
        Parent root = loader.load();
        ModArticleController  sc2Controller = loader.getController();
        TextNews.getScene().setRoot(root);
        
       sc2Controller.setTextNews(TextNews.getText());
       sc2Controller.TitreNews(TitreNews.getText());
       sc2Controller.SetArticle(article.getId());
        
    }
    
    
}
