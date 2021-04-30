/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author imen
 */
public class BackController implements Initializable {

    @FXML
    private Button pharmacie;
    @FXML
    private Button medicament;
    @FXML
    private Button retour;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void versPharmacie(ActionEvent event) throws IOException {
      Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/GUI/Back/Pharmacie/pharmacie.fxml"));
         Stage stage1 = (Stage) pharmacie.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void versMedicament(ActionEvent event) throws IOException {
        
        
            Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/GUI/Back/Medicament/medicament.fxml"));
         Stage stage1 = (Stage) medicament.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
   Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/acceuil.fxml"));
         Stage stage1 = (Stage) retour.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
