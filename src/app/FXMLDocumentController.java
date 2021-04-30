package app;

import Services.UserSession;
import doctourna.DOCTOURNA;
import doctourna.calendrier.test;
import doctourna.utils.Session;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author hoxha
 */
public class FXMLDocumentController {

    private LoggingFX main;

    // connect main class to controller
    public void setMain(LoggingFX main) {
        this.main = main;

    }

    @FXML
    private Label label;

    @FXML
    private TextField txt_email;

    @FXML
    private Button loginBtn;

    @FXML
    private Label errorLabel;

    @FXML
    private Label errorMsg;

    @FXML
    private PasswordField txtpass;

    @FXML
    private Button forgotBtn;

    @FXML
    private Button exitBtn;

    Connection con;
    PreparedStatement pst;
    ResultSet rs;

    @FXML
    public void exit(ActionEvent event) throws IOException {
        exitBtn.setOnAction(e -> Platform.exit());
    }

    @FXML
    void login(ActionEvent event) throws IOException {
        ConnectSql connect = new ConnectSql();
        try {
            Connection con = connect.ConnectDb();

            String email = txt_email.getText().trim();
            String password = txtpass.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                errorMsg.setText("Please insert email and password");
            } else {

                PreparedStatement ps = con.prepareStatement("select * from user where email=?");

                ps.setString(1, txt_email.getText().trim());

                ResultSet rs = ps.executeQuery();

                if (rs.next() && MD5.matches(rs.getString("password"), txtpass.getText().trim())) {

                    String role = rs.getString("roles");
                    if (role.toUpperCase().contains("ADMIN")) {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                        Session.setId(rs.getInt("id"));
                        Session.setType(rs.getInt("type"));
                        Session.setNom(rs.getString("nom"));
                        Session.setPrenom(rs.getString("prenom"));
                        Session.setEmail(rs.getString("email"));
                        UserSession us = new UserSession();
                        us.setIdUser(Session.getId());
                        us.setNom(Session.getNom());
                        us.setPrenom(Session.getPrenom());
                        try {
                            // Because we need to init the JavaFX toolkit - which usually Application.launch does
                            // I'm not sure if this way of launching has any effect on anything
                            new JFXPanel();
                            
                            Platform.setImplicitExit(false);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // Your class that extends Application
                                    try {
                                        new doctourna.dashboard.Main().start(new Stage());
                                    } catch (Exception ex) {
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        ((Node) event.getSource()).getScene().getWindow().hide();
                        Session.setId(rs.getInt("id"));
                        Session.setType(rs.getInt("type"));
                        Session.setNom(rs.getString("nom"));
                        Session.setPrenom(rs.getString("prenom"));
                        Session.setEmail(rs.getString("email"));
                        try {
                            // Because we need to init the JavaFX toolkit - which usually Application.launch does
                            // I'm not sure if this way of launching has any effect on anything
                            new JFXPanel();

                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    // Your class that extends Application
                                    try {
                                        new DOCTOURNA().start(new Stage());
                                    } catch (Exception ex) {
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    errorMsg.setText("Invalid credentials. Please try again");
                }
            }
        } catch (Exception ex) {
            System.out.println("error" + ex.toString());
        }

    }

    @FXML
    public void signupScene(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignupPage.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }

    @FXML
    public void forgotPsw(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        Scene scene = stage.getScene();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GetPassword.fxml"));
        Parent root = (Parent) fxmlLoader.load();

        scene.setRoot(root);
    }

}
