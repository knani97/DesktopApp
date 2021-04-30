/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.dashboard.controllers;

import com.doctourna.Service.video_Service;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceUser;
import doctourna.utils.Navigator;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import pipharmacie.services.medicamentService;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class AccueilController implements Initializable {
    
    ServiceRdv sr = new ServiceRdv();
    ServiceUser su = new ServiceUser();
    video_Service sv = new video_Service();
    medicamentService sm = new medicamentService();
    
    @FXML
    private Text nbRdvs;

    @FXML
    private Text nbArts;

    @FXML
    private Text nbVids;

    @FXML
    private Text nbMeds;

    @FXML
    private Text nbPharms;

    @FXML
    private Text nbUsers;
    
    @FXML
    void btnUsers(ActionEvent event) throws IOException {
        Navigator.navigate("../../../app/AdminPanel.fxml");
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nbRdvs.setText(" " + String.valueOf(sr.afficher().size()));
        nbUsers.setText(" " + String.valueOf(su.afficher().size()));
        try {
            nbVids.setText(" " + String.valueOf(sv.Affichertout().size()));
        } catch (SQLException ex) {
            Logger.getLogger(AccueilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        nbMeds.setText(" " + String.valueOf(sm.afficherMedicament().size()));
    }    
    
}
