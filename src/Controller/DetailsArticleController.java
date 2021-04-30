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
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import org.controlsfx.control.Rating;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class DetailsArticleController implements Initializable {
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
    private GridPane GridPaneNews;
    @FXML
    private Rating rating;

    @FXML
    private Label raitingMoy;
    @FXML
    private Text LikeAction;

    @FXML
    private Text DislikeAction;

    
    private  List<Article> ListF = new ArrayList<>();
    ServiceArticle SA = new ServiceArticle();
    UserSession US = new UserSession();
    Article article=new Article();
    int idArt=0;
    /**
     * Initializes the controller class.
     */
         public void showInformation(int id,String text, String titre, Image image, String user, String cat) {
//        this.TextNews.setText(id);
        this.idArt=id;
        this.TextNews.setText(text);
        this.TitreNews.setText(titre);
        this.NomUser.setText(user);
        this.imageNews.setImage(image);
        this.NomUser.setText(user);
        this.CategorieArt.setText(cat);
         if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
                                             .filter(a -> a.getTypeReact()==0 )
                                             .filter(a -> a.getIdUserId()==US.getIdUser())
                                             .collect(Collectors.groupingBy((Reagit r)->r.getIdArtId(),Collectors.counting()))
                                     .size()>0)
                                     {LikeAction.setText("ADD Like");
                                     DislikeAction.setText("Supp Dislike");
                                     }
          if(SA.ReagitList().stream()
                                             .filter(a -> a.getIdArtId()==(idArt))
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
        /*ListF.addAll(SA.ArticleRecom());
           
         
           int column =0 ;
            int row = 1;
           
                for (int i = 0; i < ListF.size(); i++) {

               try {
                   FXMLLoader fxmlLoader = new FXMLLoader();
                   fxmlLoader.setLocation(getClass().getResource("../Interfaces/mesArticles/ItemRecom.fxml"));
                   AnchorPane anchorPane = fxmlLoader.load();
                   
                   ItemRecomController itemController = fxmlLoader.getController();
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
                }*/
                
                
                rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                    raitingMoy.setText("Raiting Moy"+newValue);
                    SA.ajouterRaitin( new Entity.Rating(0, US.getIdUser(), idArt,newValue));
                    JOptionPane.showMessageDialog(null,"Votre Avis comme "+newValue+" à été ajouté");
        });
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
