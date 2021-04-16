package doctourna.controllers;

import doctourna.models.Calendrier;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.paint.Color;

public class ajoutCalendrierController implements Initializable {

    @FXML
    private Button btnClick;

    @FXML
    private ChoiceBox<String> chcFormat;

    @FXML
    private ChoiceBox<String> chcTimezone;

    @FXML
    private CheckBox chbEmail;

    @FXML
    private ColorPicker pickrClr;


    @FXML
    public void display(ActionEvent event) {
        ServiceCalendrier sc = new ServiceCalendrier();
        
        switch (chcFormat.getValue()) {
            case "Plage Horaire":
                sc.ajouter(new Calendrier(null, new User(1), 1, chbEmail.isSelected(), pickrClr.getValue().toString(), chcTimezone.getValue(), 1));
                break;
            case "Calendrier Standard":
                sc.ajouter(new Calendrier(null, new User(1), 1, chbEmail.isSelected(), pickrClr.getValue().toString(), chcTimezone.getValue(), 2));
                break;
            default:
                break;
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succés");
        alert.setHeaderText("Succés");
        alert.setContentText("Calendrier ajouté avec succés.");

        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chcFormat.getItems().add("Plage Horaire");
        chcFormat.getItems().add("Calendrier Standard");
        
        chcTimezone.getItems().add("UTC -1");
        chcTimezone.getItems().add("UTC");
        chcTimezone.getItems().add("UTC +1");
        
        chcFormat.setValue("Plage Horaire");
        chcTimezone.setValue("UTC");
        chbEmail.setSelected(true);
        pickrClr.setValue(Color.LIGHTBLUE);
    }

}
