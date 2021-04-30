/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.utils;

import java.io.IOException;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import static jfxtras.internal.scene.control.skin.agenda.icalendar.base24hour.Settings.resources;

/**
 *
 * @author mouhe
 */
public class WindowManager {
    public void newWindow(Event event, String path) throws IOException {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource(path), resources);
            Stage stage = new Stage();
            stage.setTitle("My New Stage Title");
            stage.setScene(new Scene(root, 450, 450));
            stage.show();
            // Hide this current window (if this is what you want)
            ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
