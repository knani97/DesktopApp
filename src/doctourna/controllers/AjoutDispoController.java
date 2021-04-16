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
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

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
    private Button btnClick;

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
            alert.setContentText("Les dates doivent être supérieur à la date courante.");

            alert.showAndWait();
            return;
        }
        if (dateDeb.after(dateFin)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText("La date fin doit être supérieur à la date début.");

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
        alert.setTitle("Succés");
        alert.setHeaderText("Succés");
        alert.setContentText("Date Début: " + dteDateDebut.getValue().toString().replace('-', '/') + " " + tmeDateDebut.getValue() + "\n"
                + "Date Fin: " + dteDateFin.getValue().toString().replace('-', '/') + " " + tmeDateFin.getValue() + "\n"
                + "Durée RDV: " + txtFldDureeRDV.getText());

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
