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
import doctourna.calendrier.InfoCalController;
import doctourna.calendrier.test;
import doctourna.console.Console;
import doctourna.models.Disponibilite;
import doctourna.models.Tache;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.utils.Session;

import java.net.URL;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalTime;
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
public class AjoutTacheController implements Initializable {
    
    ServiceTache st = new ServiceTache();
    ServiceCalendrier sc = new ServiceCalendrier();

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
        Tache tache = new Tache(
                null, 
                sc.findByUid(Session.getId()), 
                txtFieldLibelle.getText(), 
                txtAreaDesc.getText(), 
                Console.getType(cmbBoxType.getValue()).toString(), 
                clrPickerCouleur.getValue().toString(), 
                date, 
                duree
        );
        
        st.ajouter(tache);
        test.resetStats();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succ??s");
        alert.setHeaderText("T??che ajout??e avec Succ??s");
        alert.setContentText("Date : " + dtePickerDate.getValue().toString().replace('-', '/') + " " + tmePickerDate.getValue() + "\n"
                + "Dur??e : " + txtFieldDuree.getText());

        alert.showAndWait();
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbBoxType.getItems().addAll(
                "RDV Perso",
                "Prise M??dicament",
                "Personnelle",
                "Disponibilit??"
        );
    }

}
