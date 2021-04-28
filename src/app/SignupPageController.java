package app;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class SignupPageController implements Initializable {
    
     
   
    ObservableList<String> questionBoxList = FXCollections.observableArrayList("What's your pet's name?","What's your favorite food?","Who was your childhood hero?");
   
    


    
    @FXML
    private TextField emailtxt;

    @FXML
    private PasswordField passwordTxt;

    @FXML
    private ComboBox<String> questionBox;

    @FXML
    private TextField answerTxt;

    @FXML
    private Button createBtn;

    @FXML
    private Label errorMessage;

    @FXML
    private ComboBox<String> roles;

    @FXML
    private TextField nametxt;

    @FXML
    private TextField prenomtxt;

    Connection con;
    PreparedStatement ps,ps1; 
    ResultSet rs;
    @FXML
    private Button Back;
    
     @FXML
    void register(ActionEvent event) throws IOException, SQLException {
       ConnectSql connect = new ConnectSql();
        
        try{
            
            Connection con = connect.ConnectDb();
            
            String email = emailtxt.getText().trim();
            String password = passwordTxt.getText().trim();
            String ques = questionBox.getValue().toString();
            String ans = answerTxt.getText().trim();
            String role = roles.getValue().toString();
            String name = nametxt.getText().trim();
            String prenom = prenomtxt.getText().trim();
            
            if(email.isEmpty() || password.isEmpty() || name.isEmpty() || ques.isEmpty() || ans.isEmpty()|| prenom.isEmpty() ||  role.isEmpty() ){
                errorMessage.setText("Please complete all the fills");
            }
            else {
               if (password.length()<6){
                   errorMessage.setText("Password is too weak, please choose atleast 6 characters");
               }
            
            else {
                   
            String sql = "select * from user where email = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, email);
            
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                errorMessage.setText("Email already taken, please try another Email");
            }
            
            else{
               
            String sql2 = "insert into user (email,password,roles,nom,prenom,s_ques,answer,type) values(?,?,?,?,?,?,?,?)";     
            ps = con.prepareStatement(sql2, Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, emailtxt.getText().trim());
            ps.setString(2, MD5.crypt(passwordTxt.getText().trim()));
            ps.setString(4, nametxt.getText().trim());
            ps.setString(5, prenomtxt.getText().trim());
            ps.setString(6, questionBox.getValue().toString());
            ps.setString(7, answerTxt.getText().trim());
            if (roles.getValue().toString().toUpperCase().contains("MEDECIN")) {
                ps.setString(3, "[\"ROLE_WORKER\"]");
                ps.setInt(8, 2);
            }
            else if (roles.getValue().toString().toUpperCase().contains("PHARMACIEN")) {
                ps.setString(3, "[\"ROLE_WORKER\"]");
                ps.setInt(8, 3);
            }
            else {
                ps.setString(3, "[\"ROLE_USER\"]");
                ps.setInt(8, 1);
            }
            
            ps.execute();
            
            errorMessage.setText("Account successfully registered");
             
         JOptionPane.showMessageDialog(null, "Saved");
        }
       }
        }
       }
        catch(Exception e)
        {
            System.out.println("error" + e);
            
           
        }
        if(roles.getValue().toString()=="Medecin"){
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
                Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignupPageMed.fxml"));
         Parent root = (Parent) fxmlLoader.load();
         SignupPageMedController controller = fxmlLoader.getController();
         try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    controller.userId = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Création de tâche échoué, pas de ID obtenu.");
                }
            }
                    
            

            scene.setRoot(root);
        
        
        
        }
    }
    @FXML
    void backLogin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }  
    
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        questionBox.setValue("Choose your question");
    questionBox.setItems(questionBoxList);
    roles.getItems().addAll("Medecin","Patient","Pharmacien");
      
    }  
    
}
