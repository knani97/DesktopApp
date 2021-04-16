package doctourna.controllers;

import doctourna.models.Calendrier;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceUser;
import doctourna.utils.Session;
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

public class modifCalendrierController implements Initializable {
    
    int uid = Session.getId();
    ServiceCalendrier sc = new ServiceCalendrier();
    int calendrierId;
    Calendrier calendrier;

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
        switch (chcFormat.getValue()) {
            case "Plage Horaire":
                sc.modifier(new Calendrier(sc.findByUid(uid).getId(), sc.findByUid(uid).getUidId(), 1, chbEmail.isSelected(), pickrClr.getValue().toString(), chcTimezone.getValue(), 1));
                break;
            case "Calendrier Standard":
                sc.modifier(new Calendrier(sc.findByUid(uid).getId(), sc.findByUid(uid).getUidId(), 1, chbEmail.isSelected(), pickrClr.getValue().toString(), chcTimezone.getValue(), 2));
                break;
            default:
                break;
        }
        
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succés");
        alert.setHeaderText("Succés");
        alert.setContentText("Calendrier modifié avec succés.");

        alert.showAndWait();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sc = new ServiceCalendrier();
        
        chcFormat.getItems().add("Plage Horaire");
        chcFormat.getItems().add("Calendrier Standard");
        
        chcTimezone.getItems().add("UTC -1");
        chcTimezone.getItems().add("UTC");
        chcTimezone.getItems().add("UTC +1");
        
        switch (calendrier.getFormat()) {
            case 1:
                chcFormat.setValue("Plage Horaire");
                break;
            case 2:
                chcFormat.setValue("Calendrier Standard");
                break;
            default:
                break;
        }
        chcTimezone.setValue(calendrier.getTimezone());
        chbEmail.setSelected(calendrier.getEmail());
    }

}
