/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.dashboard.controllers;

import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceTache;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.Axis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class CalendriersChartsController implements Initializable {
    
    static int t2020 = 0, t2021 = 0, t2022 = 0;
    
    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();

    @FXML
    private LineChart<Number, Number> calendriersChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        XYChart.Series series = new XYChart.Series();
        series.setName("Nombre de tâches dans une année");

        st.afficher().forEach(t -> {
            if (t.getDate().toLocalDateTime().getYear() == 2020) {
                t2020 += 1;
                series.getData().add(new XYChart.Data(String.valueOf(t.getDate().toLocalDateTime().getYear()), t2020));
            }
            else if (t.getDate().toLocalDateTime().getYear() == 2021) {
                t2021 += 1;
                series.getData().add(new XYChart.Data(String.valueOf(t.getDate().toLocalDateTime().getYear()), t2021));
            }
            else if (t.getDate().toLocalDateTime().getYear() == 2022) {
                t2022 += 1;
                series.getData().add(new XYChart.Data(String.valueOf(t.getDate().toLocalDateTime().getYear()), t2022));
            }
        });

        calendriersChart.getData().add(series);
    }

}
