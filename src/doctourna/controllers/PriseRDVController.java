/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import doctourna.console.Console;
import doctourna.models.Rdv;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.Emailer;
import doctourna.utils.Navigator;
import doctourna.utils.SMS;
import doctourna.utils.Session;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class PriseRDVController implements Initializable {

    ServiceUser su = new ServiceUser();
    ServiceTache st = new ServiceTache();
    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceRdv sr = new ServiceRdv();
    int uid = Session.getId();
    String email = "mouheb.benabdallah@esprit.tn";

    @FXML
    private Pane bgbtnmenu;

    @FXML
    private Button btnNews;

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
    private Button btnRDVs;

    @FXML
    private Pane boxAjoutArticle;

    @FXML
    private Button btnClick;

    @FXML
    private JFXListView<User> lstViewMed;

    @FXML
    private ComboBox<Tache> chcBoxDate;

    @FXML
    private JFXTextArea txtAreaDesc;

    @FXML
    private Button btnPriseRDV;

    @FXML
    private JFXCheckBox chcBox1;

    @FXML
    private JFXCheckBox chcBox2;

    @FXML
    private JFXCheckBox chcBox3;

    @FXML
    private JFXCheckBox chcBox4;

    @FXML
    private JFXTextField txtFieldNum;

    @FXML
    void resetMeds(ActionEvent event) {
        lstViewMed.getItems().clear();
        if (chcBox1.isSelected()) {
            su.rechercherMedecinsSpec("Psychiatre").forEach(m -> lstViewMed.getItems().add(m));
        }
        if (chcBox2.isSelected()) {
            su.rechercherMedecinsSpec("Chirirugien").forEach(m -> lstViewMed.getItems().add(m));
        }
        if (chcBox3.isSelected()) {
            su.rechercherMedecinsSpec("Cardiologue").forEach(m -> lstViewMed.getItems().add(m));
        }
        if (chcBox4.isSelected()) {
            su.rechercherMedecinsSpec("Dermatologue").forEach(m -> lstViewMed.getItems().add(m));
        }
    }

    @FXML
    void display(ActionEvent event) throws IOException {
        if (sr.rechercher(uid, lstViewMed.getSelectionModel().getSelectedItem().getId()).stream().filter(r -> r.getEtat() != 3).count() != 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("RDV");
            alert.setHeaderText("RDV déjà pris");
            alert.setContentText("Vous avez déjà pris un RDV avec ce docteur.");

            alert.showAndWait();
        } else {
            if (!txtFieldNum.getText().isEmpty()) {
                if (Console.isNumber(txtFieldNum.getText())) {
                    SMS.send("DOCTOURNA\nVous avez pris un RDV avec Dr. " + lstViewMed.getSelectionModel().getSelectedItem()
                            + ", le " + chcBoxDate.getSelectionModel().getSelectedItem().getDate(),
                            txtFieldNum.getText());
                } else {
                    Alert alert = new Alert(AlertType.WARNING);
                    alert.setTitle("SMS");
                    alert.setHeaderText("Numéro Invalide");
                    alert.setContentText("Vous avez entré un numéro invalide.");

                    alert.showAndWait();
                }
            }
            sr.prendre(new Rdv(
                    null,
                    chcBoxDate.getSelectionModel().getSelectedItem().getDate(),
                    1,
                    txtAreaDesc.getText(),
                    null,
                    chcBoxDate.getSelectionModel().getSelectedItem(),
                    lstViewMed.getSelectionModel().getSelectedItem(),
                    su.find(uid),
                    null
            ));
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("RDV pris avec succés");
            alert.setContentText("Voulez vous reçevoir un email ?");

            if (alert.showAndWait().get() == ButtonType.OK) {
                try {
                    Emailer.sendMail(email, "<h1> RDV Pris </h1> <br/> <h2><b>"
                            + "Vous avez pris un RDV avec Dr. " + lstViewMed.getSelectionModel().getSelectedItem()
                            + ", le " + chcBoxDate.getSelectionModel().getSelectedItem().getDate() + "</b></h2>");
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            } else {
                alert.close();
            }
            
            Navigator.navigate("ui/listerdvs.fxml");
        }
    }

    @FXML
    void priseRDV(ActionEvent event) {
        try {
            Navigator.navigate("../ui/ajoutdispo.fxml");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    void rdvsDisplay(ActionEvent event) {
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
    void navCal(ActionEvent event) {
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
    }

    public void reset() {
        chcBoxDate.getItems().clear();
        st.findDispos(sc.findByUid(lstViewMed.getSelectionModel().getSelectedItem().getId()).getId()).stream().filter(d -> d.getDate().after(new Timestamp(System.currentTimeMillis()))).forEach(t -> chcBoxDate.getItems().add(t));
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chcBoxDate.getItems().clear();
        lstViewMed.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                reset();
            }
        });
        su.rechercherMedecins().forEach(m -> lstViewMed.getItems().add(m));
        lstViewMed.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observable, User oldValue, User newValue) {
                reset();
            }
        });
    }

}
