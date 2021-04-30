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
import doctourna.utils.Navigator;
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
public class ItemAutreArticleController implements Initializable {

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
    private Button LikeBtnArticle;
    @FXML
    private Label idArticle;
     @FXML
    private Text LikeAction;

    @FXML
    private Text DislikeAction;
    @FXML
    private ImageView imagePdp;
    int idArt=0;
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
    private void DetailsArticles(ActionEvent event) throws IOException {
        
        Navigator.navigate("../Interfaces/mesArticles/DetailsArticle.fxml", true).showInformation(article.getId(), TextNews.getText(), TitreNews.getText(), imageNews.getImage(), NomUser.getText(), CategorieArt.getText());
        
        
    }

    @FXML
    private void SupprimerArticle(ActionEvent event) {
    }



    public void setData(Article article) {
        this.article  = article;
        idArt=article.getId();
        NomUser.setText(article.users.getNom().toUpperCase()+" "+article.users.getPrenom().toUpperCase());
        CategorieArt.setText(article.ArticleCat.getCategorie());
        TitreNews.setText(article.getTitre().toUpperCase());
        idArticle.setText(article.getId().toString());
        imagePdp.setImage(new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/user.jpg"));
        String TextItem=article.getText();
        if(TextItem.length()>209)
        {
            TextItem=TextItem.substring(0,209);
        }
        
        TextNews.setText(TextItem+"...");
        
        
        imageNews.setImage(new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/"+ article.getImage()));
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
    
}

