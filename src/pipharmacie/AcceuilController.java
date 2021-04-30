/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Point3D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;


public class AcceuilController implements Initializable {

    @FXML
    private Button back;
    @FXML
    private Button front;
    @FXML
    private ImageView anim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
       //Transition
     /*  TranslateTransition translate = new  TranslateTransition();
        translate.setNode(anim);
        translate.setDuration(Duration.millis(1500));
       // translate.setCycleCount(TranslateTransition.INDEFINITE);
         translate.setByX(420);
         translate.setAutoReverse(true);
         translate.play();
      //rotation
      RotateTransition rotate = new RotateTransition();
       rotate.setNode(anim);
        rotate.setDuration(Duration.millis(1000));
        //rotate.setCycleCount(TranslateTransition.INDEFINITE);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
         rotate.play();*/
    //fade 
    /* FadeTransition fade = new FadeTransition();
       fade.setNode(anim);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(TranslateTransition.INDEFINITE);
        fade.setInterpolator(Interpolator.LINEAR);
       fade.setFromValue(0);
       fade.setToValue(1);
         fade.play();*/
     /*ScaleTransition scale = new ScaleTransition();
       scale.setNode(anim);
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(TranslateTransition.INDEFINITE);
        scale.setInterpolator(Interpolator.LINEAR);
        scale.setByX(3.0);
       
         scale.play();*/
    }    

    @FXML
    private void versBack(ActionEvent event) throws IOException {
        Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/back.fxml"));
         Stage stage1 = (Stage) back.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void versFront(ActionEvent event) throws IOException {
              Stage stage = new Stage();

        Parent root = FXMLLoader.load(getClass().getResource("/pipharmacie/front.fxml"));
         Stage stage1 = (Stage) back.getScene().getWindow();
stage1.close();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
}
