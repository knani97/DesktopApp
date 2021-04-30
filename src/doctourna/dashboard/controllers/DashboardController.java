/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.dashboard.controllers;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class DashboardController implements Initializable, MainController {

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
        makeActive(1);
        makeActiveBtn(1);
        Navigator.navigate("../ui/calendriers.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, false);

        btnOpt1.setText("Calendriers");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../ui/calendriers.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt2.setText("Stats");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("../ui/calendrierscharts.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu2Select(ActionEvent event) throws IOException {
        makeActive(2);
        makeActiveBtn(1);
        Navigator.navigate("../ui/rdvs.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, false);

        btnOpt1.setText("Liste RDVs");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../ui/rdvs.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt2.setText("Stats");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("../ui/rdvscharts.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu3Select(ActionEvent event) throws IOException {
        makeActive(3);
        makeActiveBtn(1);
        Navigator.navigate("../../../pipharmacie/GUI/Back/Medicament/medicament.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, true);

        btnOpt1.setText("  Méds");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../../../pipharmacie/GUI/Back/Medicament/medicament.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt2.setText("Pharmacies");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("../../../pidev/controllers/FXML.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        btnOpt3.setText("Inventaires");
        btnOpt3.setOnAction((e) -> {
            makeActiveBtn(3);
            try {
                Navigator.navigate("../../../pidev/controllers/FXML1.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu4Select(ActionEvent event) throws IOException {
        makeActive(4);
        makeActiveBtn(1);
        Navigator.navigate("../../../com/doctourna/GUI/Home_Video.fxml");

        enableBtn(1, true);
        enableBtn(2, false);
        enableBtn(3, false);

        btnOpt1.setText("Vidéos");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../../../com/doctourna/GUI/Home_Video.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void btnMenu5Select(ActionEvent event) throws IOException {
        makeActive(5);
        makeActiveBtn(1);
        Navigator.navigate("../../../Interfaces/mesArticles/Categories.fxml");

        enableBtn(1, true);
        enableBtn(2, true);
        enableBtn(3, false);

        btnOpt1.setText("Catégories");
        btnOpt1.setOnAction((e) -> {
            makeActiveBtn(1);
            try {
                Navigator.navigate("../../../Interfaces/mesArticles/Categories.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        
        btnOpt2.setText("Articles");
        btnOpt2.setOnAction((e) -> {
            makeActiveBtn(2);
            try {
                Navigator.navigate("../../../Interfaces/mesArticles/ArticleVerif.fxml");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    @FXML
    void logout(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm logout");
        alert.setHeaderText(null);
        alert.setContentText("Continue logging out?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.get() == ButtonType.OK) {
            Parent view5 = FXMLLoader.load(getClass().getResource("../../../app/FXMLDocument.fxml"));
            Scene scene5 = new Scene(view5);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene5);
            window.show();
        }
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
                btnMenu1.getChildren().get(0).setStyle("-fx-background-color: #e61ceb;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 2:
                btnMenu2.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu2.getChildren().get(0).setStyle("-fx-background-color: #e61ceb;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 3:
                btnMenu3.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu3.getChildren().get(0).setStyle("-fx-background-color: #e61ceb;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 4:
                btnMenu4.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu4.getChildren().get(0).setStyle("-fx-background-color: #e61ceb;\n"
                        + "    -fx-background-radius: 10px;");
                break;
            case 5:
                btnMenu5.setStyle("-fx-background-color: #F5F5F5;\n"
                        + "-fx-background-radius: 18 0 0 18;");
                btnMenu5.getChildren().get(0).setStyle("-fx-background-color: #e61ceb;\n"
                        + "    -fx-background-radius: 10px;");
                break;
        }
        activeMenu = ind;
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
                btnOpt1.setStyle(DEFAULT_STYLE + "-fx-text-fill: #d640d1");
                lineOpt1.setStroke(Paint.valueOf("#d640d1"));
                break;
            case 2:
                btnOpt2.setStyle(DEFAULT_STYLE + "-fx-text-fill: #d640d1");
                lineOpt2.setStroke(Paint.valueOf("#d640d1"));
                break;
            case 3:
                btnOpt3.setStyle(DEFAULT_STYLE + "-fx-text-fill: #d640d1");
                lineOpt3.setStroke(Paint.valueOf("#d640d1"));
                break;
        }
    }

    private void initBtn(Button btn, Line line) {
        final String IDLE_BUTTON_STYLE = DEFAULT_STYLE + "-fx-text-fill: #AFAFAF";
        final String HOVERED_BUTTON_STYLE = DEFAULT_STYLE + "-fx-text-fill: #ecbbed";
        final String CLICKED_BUTTON_STYLE = DEFAULT_STYLE + "-fx-text-fill: #d640d1";
        btn.setOnMouseEntered(e -> {
            btn.setStyle(HOVERED_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#ecbbed"));
        });
        btn.setOnMouseExited(e -> {
            btn.setStyle(IDLE_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#d3d3d3"));
            makeActiveBtn(activeBtn);
        });
        btn.setOnMousePressed(e -> {
            btn.setStyle(CLICKED_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#d640d1"));
        });
        btn.setOnMouseReleased(e -> {
            btn.setStyle(IDLE_BUTTON_STYLE);
            line.setStroke(Paint.valueOf("#d3d3d3"));
            makeActiveBtn(activeBtn);
        });
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
                    Navigator.navigate("../ui/accueil.fxml");
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });

        activeMenu = 1;
        activeBtn = 1;
        initBtn(btnOpt1, lineOpt1);
        initBtn(btnOpt2, lineOpt2);
        initBtn(btnOpt3, lineOpt3);

        makeActive(0);
        enableBtn(1, false);
        enableBtn(2, false);
        enableBtn(3, false);
        try {
            Navigator.navigate("../ui/accueil.fxml");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
