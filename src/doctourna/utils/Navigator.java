/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.utils;

import Controller.DetailsArticleController;
import doctourna.DoctournaController;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

/**
 *
 * @author mouhe
 */
public class Navigator {

    public static Pane boxContent;
    public static MainController mainController;

    public static void navigate(String path) throws IOException {
        boxContent.getChildren().clear();
        boxContent.getChildren().add(FXMLLoader.load(mainController.getClass().getResource(path)));

        new animatefx.animation.FadeIn(boxContent).play();
    }

    public static DetailsArticleController navigate(String path, boolean b) throws IOException {
        boxContent.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(mainController.getClass().getResource("../Interfaces/mesArticles/DetailsArticle.fxml"));
        Parent root = loader.load();
        DetailsArticleController sc2Controller = loader.getController();
        boxContent.getChildren().add(root);

        new animatefx.animation.FadeIn(boxContent).play();
        return sc2Controller;
    }
}
