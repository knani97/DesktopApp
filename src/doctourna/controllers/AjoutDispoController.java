/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import doctourna.console.Console;
import doctourna.models.Disponibilite;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.utils.Session;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class AjoutDispoController implements Initializable {

    ServiceTache st = new ServiceTache();
    ServiceCalendrier sc = new ServiceCalendrier();
    int uid = Session.getId();
    int type = Session.getType();
    
    @FXML
    private JFXDatePicker dteDateDebut;

    @FXML
    private JFXTimePicker tmeDateDebut;

    @FXML
    private JFXTimePicker tmeDateFin;

    @FXML
    private JFXDatePicker dteDateFin;

    @FXML
    private JFXTextField txtFldDureeRDV;

    @FXML
    private JFXTextField txtFldDureePause;

    @FXML
    void display(ActionEvent event) {
        Timestamp dateDeb = Console.toDate(dteDateDebut.getValue().toString().replace('-', '/') + " " + tmeDateDebut.getValue());
        Timestamp dateFin = Console.toDate(dteDateFin.getValue().toString().replace('-', '/') + " " + tmeDateFin.getValue());
        if (dateDeb.before(new Timestamp(System.currentTimeMillis()))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("Les dates doivent ??tre sup??rieur ?? la date courante.");

            alert.showAndWait();
            return;
        }
        if (dateDeb.after(dateFin)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La date fin doit ??tre sup??rieur ?? la date d??but.");

            alert.showAndWait();
            return;
        }
        st.ajouterDispos(new Disponibilite(
                null,
                dateDeb,
                dateFin,
                Time.valueOf(LocalTime.parse(txtFldDureeRDV.getText())),
                Time.valueOf(LocalTime.parse(txtFldDureePause.getText()))
        ), sc.findByUid(uid).getId());

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succ??s");
        alert.setHeaderText("Succ??s");
        alert.setContentText("Date D??but: " + dteDateDebut.getValue().toString().replace('-', '/') + " " + tmeDateDebut.getValue() + "\n"
                + "Date Fin: " + dteDateFin.getValue().toString().replace('-', '/') + " " + tmeDateFin.getValue() + "\n"
                + "Dur??e RDV: " + txtFldDureeRDV.getText());

        alert.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
