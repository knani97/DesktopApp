/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;
/*import com.sun.java.swing.plaf.windows.resources.windows;*/
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Observable;
import java.util.Properties;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author hoxha
 */
public class GetPasswordController implements Initializable {
   
   PreparedStatement ps= null ; 
    ResultSet rs;
    int randomCode ;
    Connection con;
    

    
     @FXML
    private TextField emailtxt;

    @FXML
    private TextField nametxt;


    @FXML
    private Button backBtn;

    @FXML
    private Label errorLb;

    @FXML
    private Label errorAnswer;
    @FXML
    private Button send;
    @FXML
    private Button verf;


    /*
     @FXML
    void searchPsw(ActionEvent event) throws IOException {
        ConnectSql connect = new ConnectSql();
       try {
            Connection con = connect.ConnectDb();
            
            passtxt.setText("");
            answertxt.setText("");
            
            String u_email = emailtxt.getText().trim();
            if(u_email.isEmpty()){
                errorLb.setText("Please insert email");
            }
            
            else {
                String sql = "select nom, s_ques, answer, password from user where email=?";
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, u_email);
                
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    emailtxt.setText(rs.getString(1));
                    questiontxt.setText(rs.getString(2));
                    ans = rs.getString(3);
                    pass = rs.getString(4);
                    errorLb.setText("");
                    
                    ps.close();
                    rs.close();
                }
                else {
                    errorLb.setText("Error: Email is incorrect");
                }
                
                
            }
            
        } catch (Exception ex) {
            System.out.println("something wrong" + ex);
        } 
            
    } 
    */
    /*
     @FXML
    void retrivePsw(ActionEvent event) throws IOException {
        
          if(ans.equals(answertxt.getText().trim())){
            passtxt.setText(pass);
        }
        else {
         errorLb.setText("Your answer is wrong. Please try again");
        }
            
    }*/
    
    
    
    @FXML
     void Send (ActionEvent event) throws Exception {
    
       
        Random rand= new Random () ; 
        randomCode=rand.nextInt(999999);
        String host = "smtp.gmail.com";
        String user="docpidev@gmail.com";
        String pass="pidev123456";
        String to =emailtxt.getText () ;
        String subject= "Reseting Code";
        String message = "Your Reset Code is \n"+randomCode;
       
        Emailer.sendMail(to, message);
    }
     @FXML
    void Verify (ActionEvent event) throws IOException {
    if (Integer.valueOf(nametxt.getText())==randomCode){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ResetPassword.fxml"));
        Parent view3 = fxmlLoader.load();
        ResetPasswordController controller = fxmlLoader.getController();
        controller.user = emailtxt.getText();
                Scene scene=new Scene(view3);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene);
                window.show();
       
 
    }
    else {
          JOptionPane.showMessageDialog(null, "wrong code");
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
        // TODO
    }    

    
}