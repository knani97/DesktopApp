/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Medicament;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import pipharmacie.connexion.connexionBD;


public class StatistiqueController implements Initializable {
    NumberAxis yAxis = new NumberAxis();
 CategoryAxis xAxis = new CategoryAxis();
     
  
      
    @FXML
      private BarChart<String, Number> barChart=new BarChart<String,Number>(xAxis,yAxis);;
     connexionBD connection = null;
        private Connection cnx;

    /**
     * Initializes the controller class.
     */
        
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loadChart();
    }    
      private void loadChart() {
       try {
             xAxis.setLabel("Nom Medicament");
              yAxis.setLabel("Prix ");
       barChart.setTitle("Classement des Médicaments par prix");
 
            String query="select nomMed,prix_achat,poids from medicament ORDER BY  prix_achat and poids DESC";
            XYChart.Series<String,Number> series = new XYChart.Series<>();
                    cnx= connexionBD.getInstance().getCnx();
            ResultSet rss = null;
            try {
                rss = cnx.createStatement().executeQuery(query);
            } catch (SQLException ex) {
                Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
            }
            while (rss.next())
            {
                try {
                    series.getData().add(new XYChart.Data<>(rss.getString(1), rss.getInt(2)));

                } catch (SQLException ex) {
                    Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
                }

            }                                                barChart.getData().add(series);

        } catch (SQLException ex) {
            Logger.getLogger(StatistiqueController.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }

}
