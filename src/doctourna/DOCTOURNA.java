/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna;

import animatefx.animation.FadeIn;
import app.MD5;
import com.doctourna.Alert.AlertDialog;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import doctourna.console.Console;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
import doctourna.utils.Session;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

/**
 *
 * @author mouhe
 */
public class DOCTOURNA extends Application {

    public static Stage primaryStage2;
    public static List<Integer> blockedIds = new ArrayList<Integer>();

    public static Stage getPrimaryStage() {
        return primaryStage2;
    }

    public static void remind() {
        ServiceTache st = new ServiceTache();
        ServiceCalendrier sc = new ServiceCalendrier();
        if (!sc.findByUid(Session.getId()).getEmail()) {
            return;
        }
        st.findByCalendrier(sc.findByUid(Session.getId()).getId())
                .stream()
                .filter(t -> Console.secsDiff(t.getDate().toLocalDateTime(), LocalDateTime.now(), 1))
                .forEach(t -> {
                    if (!blockedIds.contains(t.getId())) {
                        com.doctourna.Alert.AlertDialog.showNotification("Rappel", t.getLibelle(), com.doctourna.Alert.AlertDialog.image_remind);
                        Notifications.create().title("Rappel").text(t.getLibelle()).showWarning();
                        blockedIds.add(t.getId());
                    }
                });
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            /*Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
            String pass = "Test1";
            String hash = argon2.hash(4, 65536, 1, pass);

            if (argon2.verify(hash, pass)) {
                System.out.println("YES");
            }
            else
                System.out.println("NO");*/
            Parent root = FXMLLoader.load(getClass().getResource("doctourna.fxml"));
            Scene scene = new Scene(root, 1300, 750);

            primaryStage.setTitle("DOCTOURNA");
            primaryStage.setScene(scene);
            primaryStage.show();
            new FadeIn(root).play();
            Timeline fiveSecondsWonder = new Timeline(
                    new KeyFrame(Duration.seconds(10),
                            new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            remind();
                        }
                    }));
            fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
            fiveSecondsWonder.play();
            primaryStage2 = primaryStage;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
