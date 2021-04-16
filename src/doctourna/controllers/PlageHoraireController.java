/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXListView;
import doctourna.models.Tache;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.Session;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Stream;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class PlageHoraireController implements Initializable {

    ServiceTache st = new ServiceTache();
    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceUser su = new ServiceUser();
    int uid = Session.getId();
    boolean sortedDate = false, sortedLibelle = false, sortedDuree = false;
    
    @FXML
    private JFXCheckBox chkBox1;

    @FXML
    private JFXCheckBox chkBox2;

    @FXML
    private JFXCheckBox chkBox3;

    @FXML
    private JFXCheckBox chkBox4;

    @FXML
    private JFXCheckBox chkBox5;
    
    @FXML
    private JFXListView<String> lstViewTaches;
    
    @FXML
    private JFXButton btnTriDate;

    @FXML
    private JFXButton btnTriLibelle;

    @FXML
    private JFXButton btnTriDuree;
    
    @FXML
    private JFXButton btnAjout;

    @FXML
    private JFXButton btnModif;

    @FXML
    private JFXButton btnSupp;

    @FXML
    void ajouter(ActionEvent event) {
        try {
            Stage stage = (Stage)btnAjout.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ui/ajoutcalendrier.fxml")));
            stage.setScene(scene);
            stage.setTitle("Ajout d'une tâche");
            stage.show();
        }
        catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        try {
            Stage stage = (Stage)btnAjout.getScene().getWindow();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("ui/modifcalendrier.fxml")));
            stage.setScene(scene);
            stage.setTitle("Ajout d'une tâche");
            stage.show();
        }
        catch (IOException ie) {
            System.out.println(ie.getMessage());
        }
    }

    @FXML
    void supprimer(ActionEvent event) {

    }
    
    @FXML
    void triDate(ActionEvent event) {
        sortedDate = !sortedDate;
        reinit(event);
    }

    @FXML
    void triDuree(ActionEvent event) {
        sortedDuree = !sortedDuree;
        reinit(event);
    }

    @FXML
    void triLibelle(ActionEvent event) {
        sortedLibelle = !sortedLibelle;
        reinit(event);
    }

    @FXML
    void reinit(ActionEvent event) {
        Stream<Tache> s = st.findByCalendrier(sc.findByUid(uid).getId()).stream();
        
        lstViewTaches.getItems().clear();
        
        if (!chkBox1.isSelected())
            s = s.filter(t -> !t.getType().contains("1"));
        if (!chkBox2.isSelected())
            s = s.filter(t -> !t.getType().contains("2"));
        if (!chkBox3.isSelected())
            s = s.filter(t -> !t.getType().contains("3"));
        if (!chkBox4.isSelected())
            s = s.filter(t -> !t.getType().contains("4"));
        if (!chkBox5.isSelected())
            s = s.filter(t -> !t.getType().contains("5"));
        
        if (sortedDate)
            s = s.sorted((t1, t2) -> t1.getDate().compareTo(t2.getDate()));
        if (sortedLibelle)
            s = s.sorted((t1, t2) -> t1.getLibelle().compareTo(t2.getLibelle()));
        if (sortedDuree)
            s = s.sorted((t1, t2) -> t1.getDuree().compareTo(t2.getDuree()));
        
        s.forEach(t -> lstViewTaches.getItems().add(t.getDate() + " " + t.getDuree() + " " + t.getLibelle()));
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        chkBox1.setCheckedColor(Paint.valueOf("#e85046"));
        chkBox2.setCheckedColor(Paint.valueOf("#465ee8"));
        chkBox3.setCheckedColor(Paint.valueOf("#e0e846"));
        chkBox4.setCheckedColor(Paint.valueOf("#46e859"));
        chkBox5.setCheckedColor(Paint.valueOf("#e8aa46"));
        chkBox1.setSelected(true);
        chkBox2.setSelected(true);
        chkBox3.setSelected(true);
        chkBox4.setSelected(true);
        chkBox5.setSelected(true);
        st.findByCalendrier(sc.findByUid(uid).getId()).forEach(t -> lstViewTaches.getItems().add(t.getDate() + " " + t.getDuree() + " " + t.getLibelle()));
    }
    
}
