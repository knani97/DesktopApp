/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import doctourna.console.Console;
import doctourna.models.Tache;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.utils.Session;
import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class ModifTacheController implements Initializable {

    ServiceTache st = new ServiceTache();
    ServiceCalendrier sc = new ServiceCalendrier();
    Tache tache = new Tache();

    @FXML
    private Button btnClick;

    @FXML
    private JFXTimePicker tmePickerDate;

    @FXML
    private JFXDatePicker dtePickerDate;

    @FXML
    private JFXTextField txtFieldDuree;

    @FXML
    private JFXTextField txtFieldLibelle;

    @FXML
    private JFXTextArea txtAreaDesc;

    @FXML
    private JFXComboBox<String> cmbBoxType;

    @FXML
    private JFXColorPicker clrPickerCouleur;

    @FXML
    void display(ActionEvent event) {
        Timestamp date = Console.toDate(dtePickerDate.getValue().toString().replace('-', '/') + " " + tmePickerDate.getValue());
        Time duree = Time.valueOf(LocalTime.parse(txtFieldDuree.getText()));
        Tache tache2 = new Tache(
                tache.getId(),
                sc.findByUid(Session.getId()),
                txtFieldLibelle.getText(),
                txtAreaDesc.getText(),
                Console.getType(cmbBoxType.getValue()).toString(),
                clrPickerCouleur.getValue().toString(),
                date,
                duree
        );

        st.modifier(tache2);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succés");
        alert.setHeaderText("Tâche modifiée avec Succés");
        alert.setContentText("Date : " + dtePickerDate.getValue().toString().replace('-', '/') + " " + tmePickerDate.getValue() + "\n"
                + "Durée : " + txtFieldDuree.getText());

        alert.showAndWait();
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        cmbBoxType.getItems().addAll(
                "RDV Perso",
                "Prise Médicament",
                "Personnelle",
                "Disponibilité"
        );
    }

    public void setTache(int tacheId) {
        tache = st.find(tacheId);
        txtFieldLibelle.setText(tache.getLibelle());
        txtAreaDesc.setText(tache.getDescription());
        dtePickerDate.setValue(tache.getDate().toLocalDateTime().toLocalDate());
        tmePickerDate.setValue(tache.getDate().toLocalDateTime().toLocalTime());
        txtFieldDuree.setText(tache.getDuree().toString());
        switch (tache.getType()) {
            case "5":
                cmbBoxType.setValue("RDV Perso");
                break;
            case "2":
                cmbBoxType.setValue("Prise Médicament");
                break;
            case "3":
                cmbBoxType.setValue("Personnelle");
                break;
            case "4":
                cmbBoxType.setValue("Disponibilité");
                break;
            default:
                break;
        }
        clrPickerCouleur.setValue(Color.web(tache.getCouleur()));
    }

}
