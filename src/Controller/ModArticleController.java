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
import Services.ServiceArticleCat;
import Services.UserSession;
import java.io.File;
import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class ModArticleController implements Initializable {
    @FXML
    private TextField titre;
    @FXML
    private TextArea text;
    @FXML
    private ImageView imageNews;
    @FXML
    private Text TitreNews;
    @FXML
    private Text TextNews;
    int idArt=0;
    @FXML
    private ComboBox idCat;
    Users users = new Users();
    UserSession US= new UserSession();
    ServiceArticleCat sac = new ServiceArticleCat();
    ArticleCat ArticleCat = new ArticleCat();
    Services.ServiceArticle SA = new ServiceArticle();
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
     public  void setTextNews(String text) {
        this.text.setText(text);
    }

   public void TitreNews(String text) {
        this.titre.setText(text);
    }

    void SetArticle(int id) {
        this.idArt=id;
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
    private void ModifierArticle(ActionEvent event) {
        
        SA.modifier(new Article(this.idArt,titre.getText(),text.getText(), imag));
    }
   

  

  public void showInformation(Integer id, String text, String titre, Image image) {
        //        this.TextNews.setText(id);
int idArt=id;
        this.TextNews.setText(text);
        this.TitreNews.setText(titre);
        this.imageNews.setImage(image);
    }
    
}
