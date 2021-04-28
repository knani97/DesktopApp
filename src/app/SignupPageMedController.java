/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import static oracle.net.aso.C00.f;
import static sun.security.krb5.KrbException.errorMessage;

public class SignupPageMedController implements Initializable {

    File f;
    Connection con;
    PreparedStatement ps,ps2;
    ResultSet rs;

    List<String> lstFile;
    Integer userId;

    @FXML
    private Button upload;

    @FXML
    private TextField spec_txt;

    @FXML
    private TextField dep_txt;
    @FXML
    private Button cv_file;
    @FXML
    private Button back;

    @FXML
    void file(ActionEvent event) {

        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new ExtensionFilter("Pdf Files", lstFile));
        f = fc.showOpenDialog(null);
        if (f != null) {
            cv_file.setText("Selected Files" + f.getAbsolutePath());

        }

    }

    @FXML
    void Upload(ActionEvent event) throws IOException {
        ConnectSql connect = new ConnectSql();

        try {

            Connection con = connect.ConnectDb();

            String specialite = spec_txt.getText().trim();
            String deplome = dep_txt.getText().trim();
            String file = f.getAbsolutePath();

            if (specialite.isEmpty() || deplome.isEmpty()) {
                errorMessage.setText("Please put your info");

            } else {
                String sql = "insert into cv (specialite,diplome,file) values(?,?,?)";
                ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, spec_txt.getText().trim());

                ps.setString(2, dep_txt.getText().trim());
                ps.setString(3, file);
                ps.execute();

                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        String sql2 = "update user set cv_id=? where id=?";
                        ps2 = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);

                        ps2.setInt(1, generatedKeys.getInt(1));
                        ps2.setInt(2, userId);
                        ps2.execute();
                    } else {
                        throw new SQLException("Création de tâche échoué, pas de ID obtenu.");
                    }
                }

                errorMessage.setText("Account successfully registered");

                JOptionPane.showMessageDialog(null, "Saved");

                 Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);

            }
        } catch (Exception e) {
            System.out.println("error" + e);

        }
    }
    @FXML
    void backLogin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }  

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        lstFile = new ArrayList<>();
        lstFile.add("*.doc");
        lstFile.add("*.docx");
        lstFile.add("*.DOC");
        lstFile.add("*.DOCX");
        lstFile.add("*.pdf");
        lstFile.add("*.PDF");
    }

}
