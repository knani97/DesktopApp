/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXColorPicker;
import doctourna.models.Tache;
import doctourna.services.ServiceTache;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class ConsultTacheController implements Initializable {

    ServiceTache st = new ServiceTache();
    Tache tache = new Tache();
    
    @FXML
    private TextFlow txtAreaDesc;

    @FXML
    private Text txtLibelle;

    @FXML
    private Text txtDate;

    @FXML
    private Label txtType;
    
    @FXML
    private Text txtDuree;
    
    @FXML
    private JFXColorPicker clrPickerCouleur;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    public void setTache(int tacheId) {
        tache = st.find(tacheId);
        txtLibelle.setText(tache.getLibelle());
        txtAreaDesc.getChildren().add(new Text(tache.getDescription()));
        txtDate.setText(tache.getDate().toString());
        txtDuree.setText(tache.getDuree().toString());
        switch (tache.getType()) {
            case "1":
                txtType.setText("RDV");
                txtType.setStyle("-fx-background-color: red;\n"
                        + "-fx-fill: white;");
                break;
            case "2":
                txtType.setText("Prise Médicament");
                txtType.setStyle("-fx-background-color: blue;\n"
                        + "-fx-fill: white;");
                break;
            case "3":
                txtType.setText("Personnelle");
                txtType.setStyle("-fx-background-color: yellow;\n"
                        + "-fx-fill: white;");
                break;
            case "4":
                txtType.setText("Disponibilité");
                txtType.setStyle("-fx-background-color: green;\n"
                        + "-fx-fill: white;");
                break;
            case "5":
                txtType.setText("RDV Perso");
                txtType.setStyle("-fx-background-color: darkred;\n"
                        + "-fx-fill: white;");
                break;
            default:
                break;
        }
        clrPickerCouleur.setValue(Color.web(tache.getCouleur()));
        clrPickerCouleur.setDisable(true);
    }
}
