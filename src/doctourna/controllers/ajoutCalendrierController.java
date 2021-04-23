package doctourna.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.AllDayView;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import com.jfoenix.controls.JFXButton;
import doctourna.DOCTOURNA;
import doctourna.calendrier.test;
import doctourna.models.Calendrier;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.Session;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.PopOver;

public class ajoutCalendrierController implements Initializable {

    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();
    ServiceUser su = new ServiceUser();
    Integer uid = Session.getId();
    Integer type = Session.getType();

    @FXML
    private Button btnClick;

    @FXML
    private ComboBox<String> chcFormat;

    @FXML
    private ComboBox<String> chcTimezone;

    @FXML
    private CheckBox chbEmail;

    @FXML
    private ColorPicker pickrClr;

    @FXML
    private Button btnEditCal;

    @FXML
    private Button btnDelCal;
    
    @FXML
    private Text txtExit;
    
    @FXML
    private JFXButton btnConsult;
    
    @FXML
    private Label lblCoul;
    
    @FXML
    private Text txtTitle;

    @FXML
    public void display(ActionEvent event) {
        switch (chcFormat.getValue()) {
            case "Plage Horaire":
                sc.ajouter(new Calendrier(null, su.find(uid), type, chbEmail.isSelected(), pickrClr.getValue().toString(), chcTimezone.getValue(), 1));
                break;
            case "Calendrier Standard":
                sc.ajouter(new Calendrier(null, su.find(uid), type, chbEmail.isSelected(), pickrClr.getValue().toString(), chcTimezone.getValue(), 2));
                break;
            default:
                break;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Succés");
        alert.setHeaderText("Succés");
        alert.setContentText("Calendrier ajouté avec succés.");

        alert.showAndWait();
        
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/ajoutcalendrier.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void delCal(ActionEvent event) {
        ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(AlertType.WARNING,
                "Voulez-vous vraiment supprimer votre calendrier ?",
                oui,
                non
        );
        alert.setTitle("Suppression de Calendrier");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.orElse(non) == oui) {
            sc.supprimer(sc.findByUid(uid));
        }
        
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/ajoutcalendrier.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void editCal(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/modifcalendrier.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/ajoutcalendrier.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void navRDV(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/listerdvs.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @FXML
    void showCal(ActionEvent event) {
        try {
                    // Because we need to init the JavaFX toolkit - which usually Application.launch does
                    // I'm not sure if this way of launching has any effect on anything
        new JFXPanel();

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Your class that extends Application
                try {new test().start(new Stage());}
                catch (Exception ex) {}
            }
        });
    } catch (Exception e) {
        e.printStackTrace();
    }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if (sc.findByUid(uid) == null) {
            txtExit.setVisible(false);
            btnConsult.setVisible(false);
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
        else {
            chcFormat.setVisible(false);
            chcTimezone.setVisible(false);
            chbEmail.setVisible(false);
            pickrClr.setVisible(false);
            btnClick.setVisible(false);
            lblCoul.setVisible(false);
            txtTitle.setText("Calendrier");
        }
    }

}
