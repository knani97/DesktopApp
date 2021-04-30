/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

//import Entity.Article_;
import java.awt.Desktop;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author Lenovo
 */
public class Notification {
    public void Notification(){
        Image img = new Image("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/logodocpdf.png");
        Notifications notificationBuilder = Notifications.create()
        .title("Téléchargement des données")
        .text("Votre Téléchargement des données à été bien installé  \n sur votre ordinateur ")
        .graphic(new ImageView(img))
        .hideAfter(Duration.seconds(5))
                .position(Pos.BOTTOM_RIGHT)
                .onAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Desktop.getDesktop().open(new java.io.File("C:\\Users\\mouhe\\OneDrive\\Bureau\\pidev\\srcDoctourna.pdf"));
                } catch (IOException ex) {
                    Logger.getLogger(Notification.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        notificationBuilder.show();
    
    }
    
}
