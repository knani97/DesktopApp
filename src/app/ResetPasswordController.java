/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import java.awt.HeadlessException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.stage.Stage;
import javax.swing.JOptionPane;


public class ResetPasswordController implements Initializable {
   @FXML
    private TextField passtxt;

    @FXML
    private TextField cpasstxt;

    @FXML
    private Button reset;
     PreparedStatement ps= null ; 
    ResultSet rs;
    int randomCode ;
    Connection con;
    public static String user ; 
    
   
    
    
    @FXML
    void reset () {
        ConnectSql connect = new ConnectSql();
        if (passtxt.getText() == null ? cpasstxt.getText() == null : passtxt.getText().equals(cpasstxt.getText())){
        try {
        Connection con = ConnectSql.ConnectDb();
        String sql = "UPDATE `user` SET password=? WHERE email = ? ";
         ps = con.prepareStatement(sql);
         ps.setString(1, cpasstxt.getText());
         ps.setString(2, user);
          ps.executeUpdate();
          JOptionPane.showMessageDialog(null, "Reset Succes");
            
            
           
    } catch (SQLException | HeadlessException e) {
        JOptionPane.showMessageDialog(null, e);
    }
    
}else {

JOptionPane.showMessageDialog(null, "Password do not match");}
        
        
}
   @FXML
    void backLogin(ActionEvent event) throws IOException {
       Parent view4=FXMLLoader.load(getClass().getResource("GetPassword.fxml"));
                Scene scene4=new Scene(view4);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene4);
                window.show();
     
    }  
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
    }    
    
}
