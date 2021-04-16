/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import doctourna.models.Rdv;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.Emailer;
import doctourna.utils.Session;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

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
    private JFXListView<User> lstViewMed;

    @FXML
    private ChoiceBox<Tache> chcBoxDate;

    @FXML
    private JFXTextArea txtAreaDesc;

    @FXML
    private JFXButton btnClick;

    @FXML
    void display(ActionEvent event) {
        if (sr.rechercher(uid, lstViewMed.getSelectionModel().getSelectedItem().getId()).stream().filter(r -> r.getEtat() != 3).count() != 0) {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("RDV");
            alert.setHeaderText("RDV déjà pris");
            alert.setContentText("Vous avez déjà pris un RDV avec ce docteur.");

            alert.showAndWait();
        } else {
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
        }
    }

    public void reset() {
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
    }

}
