/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.calendrier;

import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.utils.Session;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class InfoCalController implements Initializable {
    
    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();
    
    @FXML
    private Label nbRDVs;

    @FXML
    private Label nbDispos;

    @FXML
    private Label nbTaches;
    
    @FXML
    private Button btnDefaultStyle;

    @FXML
    private Button btnStyle1;

    @FXML
    private Button btnStyle2;

    @FXML
    private Button btnStyle3;

    @FXML
    private Button btnStyle4;

    @FXML
    void display(ActionEvent event) {
        if (((Button)event.getSource()).getId().contains("btnDefaultStyle")) {
            test.calendarView.setBackground(new Background(new BackgroundFill(
                    Paint.valueOf(sc.findByUid(Session.getId()).getCouleur()),
                    CornerRadii.EMPTY,
                    Insets.EMPTY)));
        }
        else if (((Button)event.getSource()).getId().contains("btnStyle1")) {
            test.calendarView.setBackground(new Background(new BackgroundImage(
                            new Image("/images/calendrier/background1.jpg", 1300, 1000, false, false),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT)));
        }
        else if (((Button)event.getSource()).getId().contains("btnStyle2")) {
            test.calendarView.setBackground(new Background(new BackgroundImage(
                            new Image("/images/calendrier/background2.jpg", 1300, 1000, false, true),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT)));
        }
        else if (((Button)event.getSource()).getId().contains("btnStyle3")) {
            test.calendarView.setBackground(new Background(new BackgroundImage(
                            new Image("/images/calendrier/background3.jpg", 1300, 1000, false, true),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT)));
        }
        else if (((Button)event.getSource()).getId().contains("btnStyle4")) {
            test.calendarView.setBackground(new Background(new BackgroundImage(
                            new Image("/images/calendrier/background4.jpg", 1300, 1000, false, true),
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundRepeat.NO_REPEAT,
                            BackgroundPosition.CENTER,
                            BackgroundSize.DEFAULT)));
        }
    }
    
    public void reset() {
        String nbRDVsS = String.valueOf(st.findRDVs(sc.findByUid(Session.getId()).getId()).size());
        String nbDisposS = String.valueOf(st.findDispos(sc.findByUid(Session.getId()).getId()).size());
        String nbTachesS = String.valueOf(st.findByCalendrier(sc.findByUid(Session.getId()).getId()).size());
        nbRDVs.setText(nbRDVsS);
        nbDispos.setText(nbDisposS);
        nbTaches.setText(nbTachesS);
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        reset();
    }
    
}
