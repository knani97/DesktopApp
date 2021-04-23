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
import doctourna.services.ServiceRdv;
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
import java.util.List;
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
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;
import org.controlsfx.control.PopOver;

public class RDVsController implements Initializable {

    class Rdv extends RecursiveTreeObject<Rdv> {

        IntegerProperty id;
        StringProperty date;
        StringProperty patient;
        StringProperty medecin;
        StringProperty etat;

        public Rdv(Integer id, Timestamp date, User patient, User medecin, int etat) {
            this.id = new SimpleIntegerProperty(id);
            this.date = new SimpleStringProperty(date.toString());
            this.patient = new SimpleStringProperty(medecin.getNom() + " " + medecin.getPrenom());
            this.medecin = new SimpleStringProperty(medecin.getNom() + " " + medecin.getPrenom());

            switch (etat) {
                case 1:
                    this.etat = new SimpleStringProperty("Disponible");
                    break;
                case 2:
                    this.etat = new SimpleStringProperty("Reporté");
                    break;
                case 3:
                    this.etat = new SimpleStringProperty("Annulé");
                    break;
                case 4:
                    this.etat = new SimpleStringProperty("Terminé");
                    break;
                default:
                    this.etat = new SimpleStringProperty("Erreur");
                    break;
            }
        }
    }

    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();
    ServiceUser su = new ServiceUser();
    ServiceRdv sr = new ServiceRdv();
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
    private JFXTreeTableView<Rdv> treTableRDVs;

    @FXML
    private TextField txtFieldSearch;

    @FXML
    void search(InputMethodEvent event) {
        reset(sr.rechercherNom(txtFieldSearch.getText()));
    }
    
    @FXML
    void navCals(ActionEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../ui/calendriers.fxml"));
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

    public void reset(List<doctourna.models.Rdv> rdvsArray) {
        treTableRDVs.getColumns().clear();

        JFXTreeTableColumn<RDVsController.Rdv, String> rdvDate = new JFXTreeTableColumn("Date");
        rdvDate.setPrefWidth(150);
        rdvDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String> param) {
                return param.getValue().getValue().date;
            }
        });

        JFXTreeTableColumn<RDVsController.Rdv, String> patient = new JFXTreeTableColumn("");
        patient = new JFXTreeTableColumn("Patient");
        patient.setPrefWidth(150);
        patient.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String> param) {
                return param.getValue().getValue().patient;
            }
        });

        JFXTreeTableColumn<RDVsController.Rdv, String> medecin = new JFXTreeTableColumn("");
        medecin = new JFXTreeTableColumn("Médecin");
        medecin.setPrefWidth(150);
        medecin.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String> param) {
                return param.getValue().getValue().medecin;
            }
        });

        JFXTreeTableColumn<RDVsController.Rdv, String> etat = new JFXTreeTableColumn("Etat");
        etat.setPrefWidth(150);
        etat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<RDVsController.Rdv, String> param) {
                return param.getValue().getValue().etat;
            }
        });
        etat.setCellFactory((TreeTableColumn<RDVsController.Rdv, String> param) -> {
            TreeTableCell cell = new TreeTableCell<RDVsController.Rdv, String>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<RDVsController.Rdv> ttr = getTreeTableRow();
                    if (item == null || empty) {
                        setText(null);
                        ttr.setStyle("");
                        setStyle("");
                    } else {
                        setText(item.toString());
                        switch (item) {
                            case "Disponible":
                                setStyle("-fx-background-color:lightgreen");
                                break;
                            case "Reporté":
                                setStyle("-fx-background-color:#B0BEE4");
                                break;
                            case "Annulé":
                                setStyle("-fx-background-color:#DE7474");
                                break;
                            case "Terminé":
                                setStyle("-fx-background-color:#E49F81");
                                break;
                            default:
                                setStyle("-fx-background-color:red");
                                break;
                        }
                    }
                }
            };
            return cell;
        });

        JFXTreeTableColumn<RDVsController.Rdv, String> annulerBtn = new JFXTreeTableColumn("");
        annulerBtn.setPrefWidth(150);
        annulerBtn.setCellFactory(new Callback<TreeTableColumn<RDVsController.Rdv, String>, TreeTableCell<RDVsController.Rdv, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<RDVsController.Rdv, String> param) {
                final TreeTableCell<RDVsController.Rdv, String> cell = new TreeTableCell<RDVsController.Rdv, String>() {

                    final JFXButton btn = new JFXButton("Annuler");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || (super.getTreeTableRow().getItem().etat.getValue() != "Disponible" && super.getTreeTableRow().getItem().etat.getValue() != "Reporté")) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                //Button Action here
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("Annulation RDV");
                                alert.setContentText("Voulez-vous vraiment annuler votre RDV?");
                                if (alert.showAndWait().get() == ButtonType.OK) {
                                    sr.annuler(sr.find(super.getTreeTableRow().getItem().id.getValue()));
                                    reset(rdvsArray);
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

        ObservableList<RDVsController.Rdv> rdvs = FXCollections.observableArrayList();
        if (rdvsArray == null) {
            for (doctourna.models.Rdv rdv : sr.afficher()) {
                rdvs.add(new RDVsController.Rdv(rdv.getId(), rdv.getDate(), rdv.getPatientId(), rdv.getMedecinId(), rdv.getEtat()));
            }
        } else {
            for (doctourna.models.Rdv rdv : rdvsArray) {
                rdvs.add(new RDVsController.Rdv(rdv.getId(), rdv.getDate(), rdv.getPatientId(), rdv.getMedecinId(), rdv.getEtat()));
            }
        }

        final TreeItem<RDVsController.Rdv> root = new RecursiveTreeItem<RDVsController.Rdv>(rdvs, RecursiveTreeObject::getChildren);

        treTableRDVs.getColumns().setAll(rdvDate, patient, medecin, etat, annulerBtn);
        treTableRDVs.setRoot(root);
        treTableRDVs.setShowRoot(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        reset(null);
        txtFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            reset(sr.rechercherNom(newValue));
        });
    }

}
