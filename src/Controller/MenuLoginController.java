/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Lenovo
 */
public class MenuLoginController implements Initializable {

    @FXML
    private Button AdminBtnCnct;
    @FXML
    private Button UserBtnCnct;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void Categories(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/mesArticles/Categories.fxml"));
        Parent root = loader.load();
        CategorieController  sc2Controller = loader.getController();
        UserBtnCnct.getScene().setRoot(root);
        
    }

    @FXML
    private void MesArticle(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interfaces/mesArticles/MesArticle.fxml"));
        Parent root = loader.load();
        MesArticleController  sc2Controller = loader.getController();
        UserBtnCnct.getScene().setRoot(root);
        
    }
    
}
