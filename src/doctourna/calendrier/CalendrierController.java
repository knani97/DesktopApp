/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.calendrier;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.AllDayView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import com.jfoenix.controls.JFXButton;
import doctourna.controllers.ConsultTacheController;
import doctourna.controllers.ModifTacheController;
import doctourna.models.Tache;
import doctourna.services.ServiceCalendrier;
import doctourna.utils.Session;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.PopOver;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class CalendrierController implements Initializable {

    ServiceCalendrier sc = new ServiceCalendrier();
    Integer uid = Session.getId();

    @FXML
    private Label lblNoCal;

    @FXML
    private JFXButton btnAjoutCal;

    @FXML
    void ajoutCal(ActionEvent event) {
        try {
            Stage stage = (Stage) lblNoCal.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/ajoutcalendrier.fxml"));
            Parent root = (Parent) fxmlLoader.load();
            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if (sc.findByUid(uid) != null) {
            try {
                Stage stage = (Stage) lblNoCal.getScene().getWindow();
                Scene scene = stage.getScene();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/plagehoraire.fxml"));
                Parent root = (Parent) fxmlLoader.load();
                scene.setRoot(root);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            lblNoCal.setVisible(false);
            btnAjoutCal.setVisible(false);
        } else {
            lblNoCal.setVisible(true);
            btnAjoutCal.setVisible(true);
        }
    }

}
