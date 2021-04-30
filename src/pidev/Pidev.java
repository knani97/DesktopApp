/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import pidev.models.Inventaire;
import pidev.models.Medicament;
import pidev.services.pharmacieService;
import pidev.utils.DbConnection;
import pidev.models.Pharmacie;
import pidev.services.InventaireService;
import pidev.services.medicamentService;

/**
 *
 * @author Meriem
 */
public class Pidev extends Application {
    private static Scene scene;
    
    @Override
   public void start(Stage primaryStage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("controllers/Chatbot.fxml"));

            scene = new Scene(root, 1125, 800);

            primaryStage.setTitle("DOCTOURNA");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Pidev.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       launch(args);
         DbConnection db = DbConnection.getInstance();
        Pharmacie ph = new Pharmacie(2,"pharmacie", "uf", "ugn", "iyt");
        InventaireService invs = new InventaireService();
        Inventaire inv = new Inventaire(0,2, 1887720);
         Inventaire inv2 = new Inventaire(1,5,12);
       // invs.add(inv2, ph);
       // invs.delete(inv,ph);
       // System.out.println(invs.read());
        //invs.update(inv,ph);
        
        Medicament med = new Medicament("test", "test", 20, 10, "test", 1);
        medicamentService meds = new medicamentService();
       // meds.ajoutMedicament(med);
        pharmacieService phs = new pharmacieService();
        
      // System.out.println( phs.filtre("test"));
    }

    /**
     * @param args the command line arguments
     */
    
    /*public static void main(String[] args) {
        //launch(args);
              //     DbConnection db = DbConnection.getInstance();

       // Pharmacie ph = new Pharmacie(2,"pharmacie", "uf", "ugn", "iyt");
       // pharmacieService phs = new pharmacieService();
       // System.out.println(phs.read());
        Inventaire inv = new Inventaire(0, 2, 1220);
        InventaireService invs = new InventaireService();
        invs.add(inv, ph);
        invs.update(inv,ph);
       launch(args);
        
     
    }*/
    
}
