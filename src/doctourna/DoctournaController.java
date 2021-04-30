package doctourna;

import animatefx.animation.FadeIn;
import com.jfoenix.controls.JFXButton;
import doctourna.calendrier.test;
import doctourna.models.Calendrier;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceCv;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.MainController;
import doctourna.utils.Navigator;
import doctourna.utils.Session;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pidev.Pidev;

public class DoctournaController implements Initializable, MainController {

    ServiceUser su = new ServiceUser();
    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();
    ServiceCv scv = new ServiceCv();
    ServiceRdv sr = new ServiceRdv();
    Integer activeMenu;
    Integer activeBtn;

    private static final String DEFAULT_STYLE = "-fx-background-color: transparent;\n"
            + "-fx-font-size: 14;\n"
            + "-fx-effect: null;\n";

    @FXML
    private ImageView logo;

    @FXML
    private Pane btnMenu1;

    @FXML
    private Pane btnMenu2;

    @FXML
    private Pane btnMenu3;

    @FXML
    private Pane btnMenu4;

    @FXML
    private Pane btnMenu5;

    @FXML
    private Pane BoxUserConnect;

    @FXML
    private Pane BoxUserWhiteConnect;

    @FXML
    private Label lblUsername;

    @FXML
    private Button PanierBtn;

    @FXML
    private ImageView Panier;

    @FXML
    private MenuButton NotificationBtn;

    @FXML
    private ImageView Notification;

    @FXML
    private Button btnOpt1;

    @FXML
    private Line lineOpt1;

    @FXML
    private Button btnOpt2;

    @FXML
    private Line lineOpt2;

    @FXML
    private Button btnOpt3;

    @FXML
    private Line lineOpt3;

    @FXML
    private Pane boxContent;

    @FXML
    void btnMenu1Select(ActionEvent event) throws IOException {
        if (sc.findByUid(Session.getId()) == null) {
            btnMenu2.setDisable(true);
        } else
            btnMenu2.setDisable(false);
        
        makeActive(1);
        makeActiveBtn(1);
        Navigator.navigate("ui/ajoutcalendrier.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, true);

        btnOpt1.setText("Calendrier");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("ui/ajoutcalendrier.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt2.setText("Modifier");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("ui/modifcalendrier.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt3.setText("Supprimer");
        btnOpt3.setOnAction((e) -> {
            makeActiveBtn(3);
            ButtonType oui = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
            ButtonType non = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
            Alert alert = new Alert(AlertType.WARNING,
                    "Voulez-vous vraiment supprimer votre calendrier ?",
                    oui,
                    non
            );
            alert.setTitle("Suppression de Calendrier");
            Optional<ButtonType> result = alert.showAndWait();
            new animatefx.animation.ZoomIn(alert.getDialogPane()).play();

            if (result.orElse(non) == oui) {
                sc.supprimer(sc.findByUid(Session.getId()));
            }

            makeActiveBtn(1);
            try {
                boxContent.getChildren().add(FXMLLoader.load(getClass().getResource("ui/ajoutcalendrier.fxml")));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu2Select(ActionEvent event) throws IOException {
        makeActive(2);
        makeActiveBtn(1);
        Navigator.navigate("ui/listerdvs.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, false);

        btnOpt1.setText("Liste RDVs");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("ui/listerdvs.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        if (Session.getType() == 1) {
            btnOpt2.setText("Prise RDV");
            btnOpt2.setOnAction((e) -> {
                makeActiveBtn(2);
                try {
                    Navigator.navigate("ui/priserdv.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        } else if (Session.getType() == 2) {
            btnOpt2.setText("Config Dispos");
            btnOpt2.setOnAction((e) -> {
                makeActiveBtn(2);
                try {
                    Navigator.navigate("ui/ajoutdispo.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    @FXML
    void btnMenu3Select(ActionEvent event) throws IOException {
        makeActive(3);
        makeActiveBtn(1);
        Navigator.navigate("../pipharmacie/GUI/Front/Medicament/medicament.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, false);

        btnOpt1.setText("   Méds");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../pipharmacie/GUI/Front/Medicament/medicament.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt2.setText("Pharmacies");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("../pidev/controllers/FXML2.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu4Select(ActionEvent event) throws IOException {
        makeActive(4);
        makeActiveBtn(1);
        Navigator.navigate("../com/doctourna/GUI/Front.fxml");

        enableBtn(1, true);
        enableBtn(2, false);
        enableBtn(3, false);

        btnOpt1.setText("Vidéos");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../com/doctourna/GUI/Front.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu5Select(ActionEvent event) throws IOException {
        makeActive(5);
        makeActiveBtn(1);
        Navigator.navigate("../Interfaces/mesArticles/ListeDesArticle.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, true);

        btnOpt1.setText("Liste Arts");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../Interfaces/mesArticles/ListeDesArticle.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnOpt2.setText("Mes Arts");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("../Interfaces/mesArticles/MesArticle.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        btnOpt3.setText("Arts Préférés");
        btnOpt3.setOnAction((e) -> {
            makeActiveBtn(3);
            try {
                Navigator.navigate("../Interfaces/mesArticles/ArticleP.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void startChatBot(ActionEvent event) {
        makeActive(0);
        enableBtn(1, false);
        enableBtn(2, false);
        enableBtn(3, false);
        new JFXPanel();

        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                // Your class that extends Application
                try {
                    new Pidev().start(new Stage());
                } catch (Exception ex) {
                }
            }
        });
    }

    private void makeActive(Integer ind) {
        btnMenu1.setStyle("");
        btnMenu2.setStyle("");
        btnMenu3.setStyle("");
        btnMenu4.setStyle("");
        btnMenu5.setStyle("");
        btnMenu1.getChildren().get(0).setStyle("");
        btnMenu2.getChildren().get(0).setStyle("");
        btnMenu3.getChildren().get(0).setStyle("");
        btnMenu4.getChildren().get(0).setStyle("");
        btnMenu5.getChildren().get(0).setStyle("");
        switch (ind) {
            case 1:
                btnMenu1.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu1.getChildren().get(0).setStyle("-fx-background-color: #4A90E2;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 2:
                btnMenu2.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu2.getChildren().get(0).setStyle("-fx-background-color: #4A90E2;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 3:
                btnMenu3.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu3.getChildren().get(0).setStyle("-fx-background-color: #4A90E2;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 4:
                btnMenu4.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu4.getChildren().get(0).setStyle("-fx-background-color: #4A90E2;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 5:
                btnMenu5.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu5.getChildren().get(0).setStyle("-fx-background-color: #4A90E2;\n"
                        + "    -fx-background-radius: 10px;");
                break;
        }
        activeMenu = ind;
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm logout");
        alert.setHeaderText(null);
        alert.setContentText("Continue logging out?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Parent view5 = FXMLLoader.load(getClass().getResource("../app/FXMLDocument.fxml"));
            Scene scene5 = new Scene(view5);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene5);
            window.show();
        }
    }

    private void enableBtn(int num, boolean b) {
        switch (num) {
            case 1:
                btnOpt1.setVisible(b);
                lineOpt1.setVisible(b);
                break;
            case 2:
                btnOpt2.setVisible(b);
                lineOpt2.setVisible(b);
                break;
            case 3:
                btnOpt3.setVisible(b);
                lineOpt3.setVisible(b);
                break;
        }
    }

    private void makeActiveBtn(int num) {
        activeBtn = num;
        btnOpt1.setStyle(DEFAULT_STYLE + "-fx-text-fill: #AFAFAF");
        lineOpt1.setStroke(Paint.valueOf("#d3d3d3"));
        btnOpt2.setStyle(DEFAULT_STYLE + "-fx-text-fill: #AFAFAF");
        lineOpt2.setStroke(Paint.valueOf("#d3d3d3"));
        btnOpt3.setStyle(DEFAULT_STYLE + "-fx-text-fill: #AFAFAF");
        lineOpt3.setStroke(Paint.valueOf("#d3d3d3"));
        switch (num) {
            case 1:
                btnOpt1.setStyle(DEFAULT_STYLE + "-fx-text-fill: #468ada");
                lineOpt1.setStroke(Paint.valueOf("#468ada"));
                break;
            case 2:
                btnOpt2.setStyle(DEFAULT_STYLE + "-fx-text-fill: #468ada");
                lineOpt2.setStroke(Paint.valueOf("#468ada"));
                break;
            case 3:
                btnOpt3.setStyle(DEFAULT_STYLE + "-fx-text-fill: #468ada");
                lineOpt3.setStroke(Paint.valueOf("#468ada"));
                break;
        }
    }

    private void initBtn(Button btn, Line line) {
        final String IDLE_BUTTON_STYLE = DEFAULT_STYLE + "-fx-text-fill: #AFAFAF";
        final String HOVERED_BUTTON_STYLE = DEFAULT_STYLE + "-fx-text-fill: #0056bf";
        final String CLICKED_BUTTON_STYLE = DEFAULT_STYLE + "-fx-text-fill: #00013b";
        btn.setOnMouseEntered(e -> {
            btn.setStyle(HOVERED_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#0056bf"));
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(IDLE_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#d3d3d3"));
            makeActiveBtn(activeBtn);
        });
        btn.setOnMousePressed(e -> {
            btn.setStyle(CLICKED_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#00013b"));
        });
        btn.setOnMouseReleased(e -> {
            btn.setStyle(IDLE_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#d3d3d3"));
            makeActiveBtn(activeBtn);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Navigator.boxContent = boxContent;
        Navigator.mainController = this;

        logo.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                makeActive(0);
                enableBtn(1, false);
                enableBtn(2, false);
                enableBtn(3, false);
                try {
                    Navigator.navigate("ui/home.fxml");
                    if (sc.findByUid(Session.getId()) == null) {
                        btnMenu2.setDisable(true);
                    } else {
                        btnMenu2.setDisable(false);
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        lblUsername.setText(Session.getPrenom() + " " + Session.getNom());
        activeMenu = 1;
        activeBtn = 1;
        initBtn(btnOpt1, lineOpt1);
        initBtn(btnOpt2, lineOpt2);
        initBtn(btnOpt3, lineOpt3);

        if (sc.findByUid(Session.getId()) == null) {
            btnMenu2.setDisable(true);
        }

        makeActive(0);
        enableBtn(1, false);
        enableBtn(2, false);
        enableBtn(3, false);
        try {
            Navigator.navigate("ui/home.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
