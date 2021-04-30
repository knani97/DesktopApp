/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Medicament;

import doctourna.utils.Navigator;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import pipharmacie.entities.Categorie;
import pipharmacie.entities.Medicament;
import pipharmacie.entities.Pharmacie;
import pipharmacie.services.categorieService;
import pipharmacie.services.medicamentService;

public class MedicamentController implements Initializable {

    @FXML
    private TableView<Medicament> medicaments;
    @FXML
    private TableColumn<Medicament, String> nomCol;
    @FXML
    private TableColumn<Medicament, String> fourniseurCol;
    @FXML
    private TableColumn<Medicament, Float> prixCol;
    @FXML
    private TableColumn<Medicament, Float> poidsCol;
    @FXML
    private TextField nom;
    @FXML
    private TextField fourniseur;
    @FXML
    private TextField prix;
    @FXML
    private TextField poids;
    @FXML
    private Button ajouter;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    medicamentService md = new medicamentService();
    @FXML
    private Button retour;
    @FXML
    private Button ajoutfiche;
    @FXML
    private TableColumn<Medicament, String> imgCol;
    @FXML
    private Button image;
    @FXML
    private ImageView img;
    List<String> typee;
    String imgg;
    String imag;
    public static int id = 0;
    /* @FXML
    private Button stat;*/
    categorieService cs = new categorieService();
    @FXML
    private ComboBox<String> categorie;
    @FXML
    private Button ajoutcategorie;
    @FXML
    private TableColumn<Medicament, String> categorieCol;
    @FXML
    private ImageView imgAnim;
    @FXML
    private Pane BoxAdminDashboard;
    @FXML
    private Button stat;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        afficherMedicament();
        typee = new ArrayList();
        typee.add("*.jpg");
        typee.add("*.png");
        afficherCombo();

    }

    @FXML
    private void upload(ActionEvent event) {
        FileChooser f = new FileChooser();
        f.getExtensionFilters().add(new FileChooser.ExtensionFilter("jpeg,png", typee));
        File fc = f.showOpenDialog(null);
        if (f != null) {
            System.out.println(fc.getName());
            imgg = fc.getAbsoluteFile().toURI().toString();
            imag = fc.getName();

            System.out.println(img);
            // System.out.print(img);
            Image i = new Image(imgg);
            img.setImage(i);
        }
    }

    @FXML
    private void ajouter(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Medicament/ajoutMedicament.fxml");
    }

    @FXML
    private void modifier(ActionEvent event) {
        Medicament a = medicaments.getSelectionModel().getSelectedItem();

        if (nom.getText().equals("") || fourniseur.getText().equals("") || prix.getText().equals("") || poids.equals("")) {
            Error("Veuillez remplir tous les champs");
        } else if (!nom.getText().matches("[a-zA-Z0-9\\s]*")) {
            Error("Le nom doit contenir que des lettres et des chiffres");

        } else if (!fourniseur.getText().matches("[a-zA-Z\\s]*")) {
            Error("Le fourniseur doit contenir que des lettres");

        } else if (!prix.getText().matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?")) {
            Error("Le prix doit contenir que des chiffres");

        } else if (!poids.getText().matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?")) {
            Error("Le poids doit contenir que  des chiffres");

        } else {
            System.out.println(" hethi " + a.getImg_med());
            if (imag == null) {
                imag = a.getImg_med();
            }
            Medicament m = new Medicament(nom.getText(), fourniseur.getText(), Float.valueOf(prix.getText()), Float.valueOf(poids.getText()), imag, cs.recupererID(categorie.getValue()));
            m.setId(a.getId());
            if (md.modifierMedicament(m)) {
                Success("Modification effectué avec succés");
                nom.setText("");
                fourniseur.setText("");
                prix.setText("");
                poids.setText("");
                categorie.setValue("");
                img.setImage(null);
                afficherMedicament();
            }

        }

    }

    @FXML
    private void supprimer(ActionEvent event) {

        Medicament m = medicaments.getSelectionModel().getSelectedItem();

        if (md.deleteMed(m.getId())) {
            Success("Suppression effectué avec succés");
            afficherMedicament();
            nom.setText("");
            fourniseur.setText("");
            prix.setText("");
            poids.setText("");
            img.setImage(null);

        } else {
            Error("Erreur");
        }
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
        alert.setTitle("Ajouter un medicament");

        // Header Text: nullCla
        alert.setHeaderText(null);
        alert.setContentText(msg);

        alert.showAndWait();
    }

    public void afficherMedicament() {
        List<Medicament> ls = md.afficherMedicament();
        ObservableList<Medicament> Liste = FXCollections.observableArrayList(ls);

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
        fourniseurCol.setCellValueFactory(new PropertyValueFactory<>("fourniseur"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix_achat"));
        poidsCol.setCellValueFactory(new PropertyValueFactory<>("poids"));
        imgCol.setCellValueFactory(new PropertyValueFactory<>("image"));
        categorieCol.setCellValueFactory(new PropertyValueFactory<>("nom"));

        medicaments.setItems(Liste);

    }

    @FXML
    private void afficherDetail(MouseEvent event) {
        Medicament a = medicaments.getSelectionModel().getSelectedItem();
        nom.setText(a.getNomMed());
        fourniseur.setText(a.getFourniseur());
        prix.setText(String.valueOf(a.getPrix_achat()));
        poids.setText(String.valueOf(a.getPoids()));
        Image i = new Image("file:/C:/Users/user/Desktop/imagepi/" + a.getImg_med());
        img.setImage(i);
        categorie.setValue(a.getNom());

    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        //Navigator.navigate("/pipharmacie/back.fxml");
    }

    @FXML
    private void ajoutfiche(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Fiche/fiche.fxml");
    }

    @FXML
    private void stat(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Medicament/statistique.fxml");
    }

    private void afficherCombo() {
        List<Categorie> L = cs.afficherCategorie();
        List<String> Li = new ArrayList<>();
        for (int i = 0; i < L.size(); i++) {
            Li.add(L.get(i).getNom());
        }
        ObservableList<String> Liste = FXCollections.observableArrayList(Li);
        categorie.setItems(Liste);
    }

    @FXML
    private void ajoutcategorie(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Medicament/categorie.fxml");
    }

}
