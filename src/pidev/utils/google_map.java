/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.utils;

import java.io.File;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author Aymen
 */

public class google_map extends Application {

    public static void main(String[] args) {
        launch(args);
    }

 

    @Override
    public void start(Stage primaryStage) throws Exception {
         WebView webView = new WebView();
File f = new File("C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/pidev/utils/map.html");
  System.out.println(f);
webView.getEngine().load(f.toURI().toString());

         Scene scene = new Scene(webView);

        primaryStage.setScene(scene);
        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);
        primaryStage.show();
    
    }
}
