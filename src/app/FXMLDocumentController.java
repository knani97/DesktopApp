package app;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.application.Platform;
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
public class FXMLDocumentController  {
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
public void exit(ActionEvent event)throws IOException{
exitBtn.setOnAction(e -> Platform.exit());
} 
   
    @FXML
    void login(ActionEvent event) throws IOException {
          ConnectSql connect = new ConnectSql();
        try {
            Connection con = connect.ConnectDb();

            String email = txt_email.getText().trim();
            String password = txtpass.getText().trim();

            if(email.isEmpty() || password.isEmpty()){
               errorMsg.setText("Please insert email and password");
            }
            else
            {

                PreparedStatement ps = con.prepareStatement("select * from user where email=?"
                    + " and password=?");

                ps.setString(1,txt_email.getText().trim() );
                ps.setString(2, MD5.crypt(txtpass.getText().trim()));

                ResultSet rs = ps.executeQuery();
                
                if(rs.next()){
                    
                    
                    String role = rs.getString("roles");
            if (role.toUpperCase().contains("ADMIN")) {

                    
                    Parent view3=FXMLLoader.load(getClass().getResource("AdminPanel.fxml"));
                    
                Scene scene3=new Scene(view3);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene3);
               window.show();
                }
            else{
            
             Parent view3=FXMLLoader.load(getClass().getResource("WelcomePage.fxml"));
                    
                Scene scene3=new Scene(view3);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene3);
               window.show();
            }
            
            }
                

                else {
                    errorMsg.setText("Invalid credentials. Please try again");
                }
            }
        }
        catch(Exception ex){
            System.out.println("error" + ex.toString());
        }

    }
    
    @FXML
public void signupScene(ActionEvent event)throws IOException{
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SignupPage.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
}  

@FXML
public void forgotPsw(ActionEvent event)throws IOException{
Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            Scene scene = stage.getScene();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GetPassword.fxml"));
            Parent root = (Parent) fxmlLoader.load();

            scene.setRoot(root);
}  
  

}