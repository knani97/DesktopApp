/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna;

import doctourna.utils.Session;
import java.nio.file.Paths;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author mouhe
 */
public class DOCTOURNA extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Session.setId(1);
            Session.setType(1);
            Session.setEmail("mouheb.benabdallah@esprit.tn");
            Parent root = FXMLLoader.load(getClass().getResource("doctourna.fxml"));

            Scene scene = new Scene(root, 1125, 800);

            primaryStage.setTitle("DOCTOURNA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
