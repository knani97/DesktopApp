package doctourna.dashboard.controllers;

import com.calendarfx.model.Calendar;
import com.calendarfx.model.CalendarSource;
import com.calendarfx.model.Entry;
import com.calendarfx.view.AllDayView;
import com.calendarfx.view.CalendarView;
import com.calendarfx.view.DateControl;
import com.calendarfx.view.VirtualGrid;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import doctourna.DOCTOURNA;
import doctourna.calendrier.test;
import doctourna.console.Console;
import doctourna.controllers.ListeRDVsController;
import doctourna.models.Calendrier;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.Session;
import static java.lang.Thread.sleep;
import java.net.URL;
import java.sql.Timestamp;
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
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

public class CalendriersController implements Initializable {

    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();
    ServiceUser su = new ServiceUser();
    Integer uid = Session.getId();
    Integer type = Session.getType();

    @FXML
    private Pane bgbtnmenu;

    @FXML
    private Button btnNews;

    @FXML
    private Pane BoxUserConnect;

    @FXML
    private Pane BoxUserWhiteConnect;

    @FXML
    private MenuButton NotificationBtn;

    @FXML
    private ImageView Notification;

    @FXML
    private Pane boxAjoutArticle2;

    @FXML
    private JFXTreeTableView<Calendrier> tableview1;
    
    @FXML
    void navRDVs(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/rdvs.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    class Calendrier extends RecursiveTreeObject<Calendrier> {

        IntegerProperty id;
        StringProperty user;
        IntegerProperty nbTaches;

        public Calendrier(Integer id, User user) {
            this.id = new SimpleIntegerProperty(id);
            this.user = new SimpleStringProperty(user.getPrenom() + " " + user.getNom());
            int nb = st.findByCalendrier(sc.findByUid(user.getId()).getId()).size();
            this.nbTaches = new SimpleIntegerProperty(nb);
        }
    }

    public void reset() {
        tableview1.getColumns().clear();

        JFXTreeTableColumn<CalendriersController.Calendrier, String> user = new JFXTreeTableColumn("User");
        user.setPrefWidth(150);
        user.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CalendriersController.Calendrier, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<CalendriersController.Calendrier, String> param) {
                return param.getValue().getValue().user;
            }
        });
        
        JFXTreeTableColumn<CalendriersController.Calendrier, Integer> nbTaches = new JFXTreeTableColumn("NB Tâches");
        nbTaches.setPrefWidth(300);
        nbTaches.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<CalendriersController.Calendrier, Integer>, ObservableValue<Integer>>() {
            @Override
            public ObservableValue<Integer> call(TreeTableColumn.CellDataFeatures<CalendriersController.Calendrier, Integer> param) {
                return param.getValue().getValue().nbTaches.asObject();
            }
        });

        JFXTreeTableColumn<CalendriersController.Calendrier, String> supprimerBtn = new JFXTreeTableColumn("");
        supprimerBtn.setPrefWidth(100);
        supprimerBtn.setCellFactory(new Callback<TreeTableColumn<CalendriersController.Calendrier, String>, TreeTableCell<CalendriersController.Calendrier, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<CalendriersController.Calendrier, String> param) {
                final TreeTableCell<CalendriersController.Calendrier, String> cell = new TreeTableCell<CalendriersController.Calendrier, String>() {

                    final JFXButton btn = new JFXButton("Supprimer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                //Button Action here
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Suppression Calendrier");
                                alert.setContentText("Voulez-vous vraiment supprimer la calendrier ?");
                                if (alert.showAndWait().get() == ButtonType.OK) {
                                    sc.supprimer(sc.find(super.getTreeTableRow().getItem().id.getValue()));
                                    reset();
                                }
                            });
                            btn.setStyle("-jfx-button-type: RAISED;\n"
                                    + "     -fx-background-color: red;\n"
                                    + "     -fx-text-fill: black;");
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });
        
        JFXTreeTableColumn<CalendriersController.Calendrier, String> consulterBtn = new JFXTreeTableColumn("");
        consulterBtn.setPrefWidth(100);
        consulterBtn.setCellFactory(new Callback<TreeTableColumn<CalendriersController.Calendrier, String>, TreeTableCell<CalendriersController.Calendrier, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<CalendriersController.Calendrier, String> param) {
                final TreeTableCell<CalendriersController.Calendrier, String> cell = new TreeTableCell<CalendriersController.Calendrier, String>() {

                    final JFXButton btn = new JFXButton("Consulter");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                //Button Action here
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Suppression Calendrier");
                                alert.setContentText("Voulez-vous vraiment supprimer la calendrier ?");
                                if (alert.showAndWait().get() == ButtonType.OK) {
                                    sc.supprimer(sc.find(super.getTreeTableRow().getItem().id.getValue()));
                                    reset();
                                }
                            });
                            btn.setStyle("-jfx-button-type: RAISED;\n"
                                    + "     -fx-background-color: blue;\n"
                                    + "     -fx-text-fill: black;");
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        ObservableList<CalendriersController.Calendrier> calendriers = FXCollections.observableArrayList();
        for (doctourna.models.Calendrier cal : sc.afficher()) {
            calendriers.add(new CalendriersController.Calendrier(cal.getId(), cal.getUidId()));
        }

        final TreeItem<CalendriersController.Calendrier> root = new RecursiveTreeItem<CalendriersController.Calendrier>(calendriers, RecursiveTreeObject::getChildren);

        tableview1.getColumns().setAll(user, nbTaches, supprimerBtn, consulterBtn);
        tableview1.setSelectionModel(null);
        tableview1.setRoot(root);
        tableview1.setShowRoot(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset();
    }

}
