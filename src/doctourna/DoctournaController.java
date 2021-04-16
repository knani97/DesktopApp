package doctourna;

import com.jfoenix.controls.JFXButton;
import doctourna.models.Calendrier;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.utils.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class DoctournaController implements Initializable {

    @FXML
    private JFXButton btnCal;

    @FXML
    private JFXButton btnRdvDispo;

    @FXML
    private JFXButton btnRdv;

    @FXML
    private Pane pneMain;    
    
    @FXML
    private Label lblNoCal;
    
    @FXML
    private JFXButton btnAjoutCal;

    int uid = Session.getId();
    int type = Session.getType();
    ServiceCalendrier sc = new ServiceCalendrier();

    @FXML
    void priseDispo(ActionEvent event) {
        pneMain.getChildren().clear();
        try {
            Pane newLoadedPane;
            if (type == 1) {
                newLoadedPane = FXMLLoader.load(getClass().getResource("ui/priserdv.fxml"));
            } else {
                newLoadedPane = FXMLLoader.load(getClass().getResource("ui/ajoutdispo.fxml"));
            }
            pneMain.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void rdvs(ActionEvent event) {
        pneMain.getChildren().clear();
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("ui/listerdvs.fxml"));
            pneMain.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    void showCalendrier(ActionEvent event) {
        pneMain.getChildren().clear();
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("ui/plagehoraire.fxml"));
            pneMain.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    @FXML
    void ajoutCal(ActionEvent event) {
        pneMain.getChildren().clear();
        try {
            Pane newLoadedPane = FXMLLoader.load(getClass().getResource("ui/ajoutcalendrier.fxml"));
            pneMain.getChildren().add(newLoadedPane);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (type == 1) {
            btnRdvDispo.setText("Prise RDV");
        } else {
            btnRdvDispo.setText("Config Dispos");
        }
        if (sc.findByUid(uid) != null) {
            lblNoCal.setVisible(false);
            btnAjoutCal.setVisible(false);
            btnCal.setDisable(false);
            btnRdvDispo.setDisable(false);
            btnRdv.setDisable(false);
        }
        else {
            lblNoCal.setVisible(true);
            btnAjoutCal.setVisible(true);
            btnCal.setDisable(true);
            btnRdvDispo.setDisable(true);
            btnRdv.setDisable(true);
        }
    }

}
