/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.GUI.Back.Fiche;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import doctourna.utils.Navigator;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import pipharmacie.entities.Fiche;
import pipharmacie.entities.Medicament;
import pipharmacie.services.ficheService;
import pipharmacie.services.medicamentService;

public class FicheController implements Initializable {

    @FXML
    private TableView<Fiche> fiches;
    @FXML
    private ComboBox<String> medicaments;
    @FXML
    private Button ajouter;
    @FXML
    private Button modifier;
    @FXML
    private Button supprimer;
    @FXML
    private Spinner<Integer> quantite;
    ficheService fs = new ficheService();
    medicamentService md = new medicamentService();
    @FXML
    private TableColumn<Fiche, String> nomCol;
    @FXML
    private TableColumn<Fiche, Integer> quantiteCol;
    @FXML
    private TableColumn<Fiche, Float> prixCol;
    @FXML
    private Button retour;
    @FXML
    private TextField prix;
    public static Fiche fiche = new Fiche();

    private static Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18,
            Font.BOLD);
    private static Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 24,
            Font.BOLD, BaseColor.RED);
    private static Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16,
            Font.BOLD);
    private static Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12,
            Font.BOLD);
    @FXML
    private Button quantites;
    private TableColumn<Fiche, String> utilisation;
    @FXML
    private TableColumn<?, ?> colU;
    @FXML
    private TextArea u;
    @FXML
    private Button pdf;
    @FXML
    private Pane BoxAdminDashboard;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        afficherCombo();
        afficherFiche();
        final int initialValue = 0;

        // Value factory.
        SpinnerValueFactory<Integer> valueFactory
                = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, initialValue);

        quantite.setValueFactory(valueFactory);
        quantite.setEditable(true);

    }

    @FXML
    private void ajouter(ActionEvent event) {
        if (medicaments.getValue().equals("") || quantite.getValue() == 0 || prix.getText().equals("")) {
            Error("Veuillez remplir tous les champs");
        } /*   else if(!prix.getText().matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?*"))
        {        Error("Le prix doit contenir que des chiffres");

        }*/ else {
            Fiche m = new Fiche(md.recupererID(medicaments.getValue()), quantite.getValue(), Float.valueOf(prix.getText()), this.u.getText());
            if (fs.ajoutFiche(m)) {
                Success("Ajout effectué avec succés");
                SpinnerValueFactory<Integer> valueFactory
                        = //
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 0);

                quantite.setValueFactory(valueFactory);
                medicaments.setValue("");
                prix.setText("");
                u.setText("");
                afficherFiche();

            }

        }
    }

    @FXML
    private void modifier(ActionEvent event) {
        if (medicaments.getValue().equals("") || quantite.getValue() == 0 || prix.getText().equals("")) {
            Error("Veuillez remplir tous les champs");
        } else if (!prix.getText().matches("[+-]?[0-9]+(\\.[0-9]+)?([Ee][+-]?[0-9]+)?")) {
            Error("Le prix doit contenir que  des chiffres");

        } else {
            Fiche m = new Fiche(md.recupererID(medicaments.getValue()), quantite.getValue(), Float.valueOf(prix.getText()), this.u.getText());
            Fiche a = fiches.getSelectionModel().getSelectedItem();
            m.setId(a.getId());
            if (fs.modifierFiche(m)) {
                Success("Modification effectué avec succés");
                SpinnerValueFactory<Integer> valueFactory
                        = //
                        new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 0);

                quantite.setValueFactory(valueFactory);
                medicaments.setValue("");
                prix.setText("");
                u.setText("");
                afficherFiche();

            }

        }
    }

    @FXML
    private void supprimer(ActionEvent event) {
        Fiche m = fiches.getSelectionModel().getSelectedItem();

        if (fs.deleteFiche(m.getId())) {
            Success("Suppression effectué avec succés");
            afficherFiche();
            afficherCombo();
            SpinnerValueFactory<Integer> valueFactory
                    = //
                    new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100000, 0);

            quantite.setValueFactory(valueFactory);
            medicaments.setValue("");
            prix.setText("");
            u.setText("");

        } else {
            Error("Erreur");
        }
    }

    private void afficherCombo() {
        List<Medicament> L = md.afficherMedicament();
        List<String> Li = new ArrayList<>();
        for (int i = 0; i < L.size(); i++) {
            Li.add(L.get(i).getNomMed());
        }
        ObservableList<String> Liste = FXCollections.observableArrayList(Li);
        medicaments.setItems(Liste);
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

    public void afficherFiche() {
        List<Fiche> ls = fs.afficherFiche();
        ObservableList<Fiche> Liste = FXCollections.observableArrayList(ls);

        nomCol.setCellValueFactory(new PropertyValueFactory<>("nomMed"));
        quantiteCol.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        prixCol.setCellValueFactory(new PropertyValueFactory<>("prix_vente"));
        this.colU.setCellValueFactory(new PropertyValueFactory<>("utilisation"));
        fiches.setItems(Liste);
    }

    @FXML
    private void afficherDetail(MouseEvent event) {
        Fiche a = fiches.getSelectionModel().getSelectedItem();
        SpinnerValueFactory<Integer> valueFactory
                = //
                new SpinnerValueFactory.IntegerSpinnerValueFactory(a.getQuantite(), 100000, 0);

        quantite.setValueFactory(valueFactory);
        medicaments.setValue(a.getNomMed());
    }

    @FXML
    private void retour(ActionEvent event) throws IOException {
        Navigator.navigate("/pipharmacie/GUI/Back/Medicament/medicament.fxml");
    }

    @FXML
    private void pdf(ActionEvent event) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\mouhe\\OneDrive\\Bureau\\fiche.pdf"));
            document.open();

            Paragraph ph1 = new Paragraph("Liste des Fiches - DOCTOURNA !", redFont);
            ph1.setAlignment(Element.ALIGN_CENTER);

            Paragraph ph2 = new Paragraph(" ", redFont);
            ph1.setAlignment(Element.ALIGN_CENTER);

            PdfPTable table = new PdfPTable(3);
            //On créer l'objet cellule.
            PdfPCell cell;

            //contenu du tableau.
            cell = new PdfPCell(new Phrase("Médicament", smallBold));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("prix vente", smallBold));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            cell = new PdfPCell(new Phrase("utilisation", smallBold));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(cell);

            fs.afficherFiche().forEach(e
                    -> {
                table.addCell(String.valueOf(e.getNomMed()));
                table.addCell(String.valueOf(e.getPrix_vente()));

                table.addCell(String.valueOf(e.getUtilisation()));
            }
            );
            document.add(ph1);
            document.add(ph2);

            document.add(table);
            document.addAuthor("Pharmacie");
            JOptionPane.showMessageDialog(null, "PDF ajouté");
            //  AlertDialog.showNotification("Creation PDF ", "Votre fichier PDF a ete cree avec success", AlertDialog.image_checked);
        } catch (Exception e) {
            System.out.println(e);
        }
        document.close();

    }

    @FXML
    private void quantite(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        fiche = fiches.getSelectionModel().getSelectedItem();

        Navigator.navigate("/pipharmacie/GUI/Back/Fiche/quantite.fxml");
    }

}
