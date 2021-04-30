/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import doctourna.console.Console;
import doctourna.models.Rdv;
import doctourna.models.User;
import doctourna.services.ServiceRdv;
import doctourna.utils.Session;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.Pagination;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableRow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import javafx.util.Callback;
import nl.captcha.Captcha;
import nl.captcha.gimpy.BlockGimpyRenderer;
import nl.captcha.servlet.CaptchaServletUtil;
import nl.captcha.text.producer.ChineseTextProducer;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.PopOver;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class ListeRDVsController implements Initializable {

    class Rdv extends RecursiveTreeObject<Rdv> {

        IntegerProperty id;
        StringProperty date;
        StringProperty patient;
        StringProperty medecin;
        StringProperty description;
        StringProperty etat;

        public Rdv(Integer id, Timestamp date, User patient, User medecin, String description, int etat) {
            this.id = new SimpleIntegerProperty(id);
            this.date = new SimpleStringProperty(date.toString());
            this.patient = new SimpleStringProperty(medecin.getNom() + " " + medecin.getPrenom());
            this.medecin = new SimpleStringProperty(medecin.getNom() + " " + medecin.getPrenom());
            this.description = new SimpleStringProperty(description);

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

    ServiceRdv sr = new ServiceRdv();
    int uid = Session.getId();
    int type = Session.getType();

    @FXML
    private JFXTreeTableView<Rdv> treTableRDVs;

    @FXML
    void triDate(ActionEvent event) {
        reset(sr.triDate(uid));
    }

    public void reset(List<doctourna.models.Rdv> list) {
        treTableRDVs.getColumns().clear();

        JFXTreeTableColumn<Rdv, String> rdvDate = new JFXTreeTableColumn("Date");
        rdvDate.setPrefWidth(400);
        rdvDate.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Rdv, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Rdv, String> param) {
                return param.getValue().getValue().date;
            }
        });

        JFXTreeTableColumn<Rdv, String> user = new JFXTreeTableColumn("");
        if (type == 1) {
            user = new JFXTreeTableColumn("Médecin");
            user.setPrefWidth(300);
            user.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Rdv, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Rdv, String> param) {
                    return param.getValue().getValue().medecin;
                }
            });
        } else if (type == 2) {
            user = new JFXTreeTableColumn("Patient");
            user.setPrefWidth(300);
            user.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Rdv, String>, ObservableValue<String>>() {
                @Override
                public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Rdv, String> param) {
                    return param.getValue().getValue().patient;
                }
            });
        }

        JFXTreeTableColumn<Rdv, String> etat = new JFXTreeTableColumn("Etat");
        etat.setPrefWidth(150);
        etat.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Rdv, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Rdv, String> param) {
                return param.getValue().getValue().etat;
            }
        });
        etat.setCellFactory((TreeTableColumn<Rdv, String> param) -> {
            TreeTableCell cell = new TreeTableCell<Rdv, String>() {
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    TreeTableRow<Rdv> ttr = getTreeTableRow();
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

        JFXTreeTableColumn<Rdv, String> annulerBtn = new JFXTreeTableColumn("");
        annulerBtn.setPrefWidth(150);
        annulerBtn.setCellFactory(new Callback<TreeTableColumn<Rdv, String>, TreeTableCell<Rdv, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Rdv, String> param) {
                final TreeTableCell<Rdv, String> cell = new TreeTableCell<Rdv, String>() {

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
                                    reset(list);
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

        JFXTreeTableColumn<Rdv, String> reporterBtn = new JFXTreeTableColumn("");
        reporterBtn.setPrefWidth(150);
        reporterBtn.setCellFactory(new Callback<TreeTableColumn<Rdv, String>, TreeTableCell<Rdv, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Rdv, String> param) {
                final TreeTableCell<Rdv, String> cell = new TreeTableCell<Rdv, String>() {

                    final JFXButton btn = new JFXButton("Reporter");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || type != 2 || (super.getTreeTableRow().getItem().etat.getValue() != "Disponible" && super.getTreeTableRow().getItem().etat.getValue() != "Reporté")) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                //Button Action here
                                TextInputDialog dialog = new TextInputDialog(LocalDate.now().plusDays(1).toString().replace('-', '/') + " 00:00:00");

                                dialog.setTitle("Reporter");
                                dialog.setHeaderText("Enter la nouvelle date:");
                                dialog.setContentText("Date:");

                                Optional<String> result = dialog.showAndWait();

                                result.ifPresent(date -> {
                                    if (Console.toDate(date).before(new Timestamp(System.currentTimeMillis()))) {
                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                        alert.setTitle("Erreur");
                                        alert.setContentText("La date doit être supérieur à la date courante.");

                                        alert.showAndWait();
                                    } else {
                                        sr.reporter(sr.find(super.getTreeTableRow().getItem().id.getValue()), Console.toDate(date.replace('-', '/')));
                                    }
                                });
                                reset(list);
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

        JFXTreeTableColumn<Rdv, String> terminerBtn = new JFXTreeTableColumn("");
        terminerBtn.setPrefWidth(150);
        terminerBtn.setCellFactory(new Callback<TreeTableColumn<Rdv, String>, TreeTableCell<Rdv, String>>() {
            @Override
            public TreeTableCell call(final TreeTableColumn<Rdv, String> param) {
                final TreeTableCell<Rdv, String> cell = new TreeTableCell<Rdv, String>() {

                    final JFXButton btn = new JFXButton("Terminer");

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || type != 2 || (super.getTreeTableRow().getItem().etat.getValue() != "Disponible" && super.getTreeTableRow().getItem().etat.getValue() != "Reporté")) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            btn.setButtonType(JFXButton.ButtonType.RAISED);
                            btn.setOnAction(event -> {
                                //Button Action here
                                sr.terminer(sr.find(super.getTreeTableRow().getItem().id.getValue()));
                                Alert alert = new Alert(AlertType.INFORMATION);
                                alert.setTitle("RDV");
                                alert.setContentText("RDV terminé avec succés");

                                alert.showAndWait();
                                reset(list);
                            });
                            btn.setStyle("-jfx-button-type: RAISED;\n"
                                    + "     -fx-background-color: orange;\n"
                                    + "     -fx-text-fill: black;");
                            setGraphic(btn);
                            setText(null);
                        }
                    }
                };
                return cell;
            }
        });

        ObservableList<Rdv> rdvs = FXCollections.observableArrayList();
        for (doctourna.models.Rdv rdv : list) {
            rdvs.add(new Rdv(rdv.getId(), rdv.getDate(), rdv.getPatientId(), rdv.getMedecinId(), rdv.getDescription(), rdv.getEtat()));
        }

        final TreeItem<Rdv> root = new RecursiveTreeItem<Rdv>(rdvs, RecursiveTreeObject::getChildren);

        treTableRDVs.getColumns().setAll(rdvDate, user, etat, annulerBtn, reporterBtn, terminerBtn);
        treTableRDVs.setRoot(root);
        treTableRDVs.setShowRoot(false);
        treTableRDVs.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                PopOver popOver = new PopOver();
                popOver.setContentNode(new Label("   Description: " + newSelection.getValue().description.getValue() + "   "));
                popOver.setArrowLocation(null);
                popOver.show((Stage)treTableRDVs.getScene().getWindow());
            }
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reset(sr.findByUid(uid));
        treTableRDVs.setPlaceholder(new Label("Aucun RDV trouvé."));
        new Captcha.Builder(22, 22).addText(new ChineseTextProducer()).gimp(new BlockGimpyRenderer()).build();
    }

}
