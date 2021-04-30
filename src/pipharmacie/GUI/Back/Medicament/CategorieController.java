/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Medicament;

import doctourna.utils.Navigator;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import pipharmacie.entities.Categorie;
import pipharmacie.services.categorieService;

public class CategorieController implements Initializable {

    @FXML
    private TableView<Categorie> medicaments;
    @FXML
    private TableColumn<Categorie, String> nomCol;
    @FXML
    private TableColumn<Categorie, String> descriptionCol;
    @FXML
    private TextField nom;
    @FXML
    private Button ajouter;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    private Button retour;
    @FXML
    private TextArea description;
    categorieService cs = new categorieService();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherCategorie();
    }

    @FXML
    private void afficherDetail(MouseEvent event) {
        Categorie a = medicaments.getSelectionModel().getSelectedItem();
        nom.setText(a.getNom());
        description.setText(a.getDescription());

    }

    @FXML
    private void ajouter(ActionEvent event) {
        if (nom.getText().equals("") || description.getText().equals("")) {
            Error("Veuillez remplir tous les champs");
        } else if (!nom.getText().matches("[a-zA-Z0-9\\s]*")) {
            Error("Le nom doit contenir que des lettres et des chiffres");

        } else {
            Categorie m = new Categorie(nom.getText(), description.getText());
            if (cs.ajoutCategorie(m)) {
                Success("Ajout effectué avec succés");
                nom.setText("");
                description.setText("");

                afficherCategorie();
            }

        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        Categorie a = medicaments.getSelectionModel().getSelectedItem();

        if (nom.getText().equals("") || description.getText().equals("")) {
            Error("Veuillez remplir tous les champs");
        } else if (!nom.getText().matches("[a-zA-Z0-9\\s]*")) {
            Error("Le nom doit contenir que des lettres et des chiffres");

        } else {
            Categorie m = new Categorie(nom.getText(), description.getText());

            m.setId(a.getId());
            if (cs.modifierCategorie(m)) {
                Success("Modfication effectué avec succés");
                nom.setText("");
                description.setText("");

                afficherCategorie();
            }

        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Categorie a = medicaments.getSelectionModel().getSelectedItem();
        if (cs.deleteCategorie(a.getId())) {
            Success("Suppression effectuée avec succés");
            afficherCategorie();
        } else {
            Error("Suppression n'a pas été effectuée");
        }

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Medicament/medicament.fxml");
    }

    private void Error(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Look, an Error Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }

    private void Success(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ajouter une categorie");

        // Header Text: nullCla
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void afficherCategorie() {
        List<Categorie> ls = cs.afficherCategorie();
        ObservableList<Categorie> Liste = FXCollections.observableArrayList(ls);

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        medicaments.setItems(Liste);

    }
}
