/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.dashboard.controllers;

import doctourna.services.ServiceRdv;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

/**
 * FXML Controller class
 *
 * @author mouhe
 */
public class RdvsChartsController implements Initializable {
    
    ServiceRdv sr = new ServiceRdv();

    @FXML
    private PieChart rdvsChart;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Disponibles", sr.findByEtat(1).size()),
                new PieChart.Data("Annulés", sr.findByEtat(3).size()),
                new PieChart.Data("Reportés", sr.findByEtat(2).size()),
                new PieChart.Data("Manqués", 0),
                new PieChart.Data("Terminés", sr.findByEtat(4).size()));
        rdvsChart.setData(pieChartData);
        rdvsChart.setTitle("Etats RDVs");
        rdvsChart.setClockwise(true);
        rdvsChart.setLabelLineLength(50);
        rdvsChart.setLabelsVisible(true);
        rdvsChart.setStartAngle(180); 
    }

}
