/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.controllers;

import com.doctourna.Alert.AlertDialog;
import com.doctourna.Service.video_Service;
import com.doctourna.models.Video;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author yass
 */
public class Item_VideoController implements Initializable {

    @FXML
    private HBox itemC;
    @FXML
    private Button btn_acheter;
    @FXML
    private Label prix;
    @FXML
    private Label titre;
    @FXML
    private Label note;
    @FXML
    private Label source;
    Video acc = null;
    video_Service ser = new video_Service();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        acc = ser.get_Video_affichage(FrontController.indiceVideo);
        titre.setText(acc.getTitre());
        source.setText(acc.getSource());

        prix.setText(String.valueOf(acc.getPrix()) + " DT");
        note.setText(String.valueOf(acc.getNote()));
    }

    @FXML
    private void acheter(ActionEvent event) throws SQLException {
        Video p = acc;
        p.setPanier_id(2);
        ser.Modifier_Video(p, acc.getId());
        AlertDialog.showNotification("Achat !", "Achat aves sucess", AlertDialog.image_checked);
    }

}
