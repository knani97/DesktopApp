/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.calendrier;

import com.jfoenix.controls.JFXButton;
import doctourna.controllers.ConsultTacheController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class CalendarHeaderController implements Initializable {
    
    public static Stage primaryStage;
    
    @FXML
    private JFXButton btnClick;

    @FXML
    void ajoutTacheClick(ActionEvent event) {
        try {
        PopOver popOver2 = new PopOver();
        FXMLLoader loader2 = new FXMLLoader(
                getClass().getResource(
                        "../ui/ajouttache.fxml"
                )
        );
        AnchorPane p2 = loader2.load();
        popOver2.setContentNode(p2);
        popOver2.setStyle(".popover > .border  {\n"
                + "    -fx-border-style: none;\n"
                + "    -fx-padding: 0;\n"
                + "    -fx-border-width: 0;\n"
                + "    -fx-fill: rgba(255,255,255,0.5);\n"
                + "}");
        popOver2.setArrowSize(0);
        popOver2.show(primaryStage);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
