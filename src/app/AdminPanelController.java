package app;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class AdminPanelController implements Initializable {

    @FXML
    private TableView<user> table_users;

    @FXML
    private TableColumn<user, Integer> col_id;

    @FXML
    private TableColumn<user, String> col_email;

    @FXML
    private TableColumn<user, String> col_roles;

    @FXML
    private TableColumn<user, String> col_password;

    @FXML
    private TableColumn<user, String> col_nom;

    @FXML
    private TableColumn<user, String> col_prenom;

    @FXML
    private TableColumn<user, String> col_qes;

    @FXML
    private TableColumn<user, String> col_ans;
      @FXML
    private TextField txt_email;

    @FXML
    private TextField txt_role;

    @FXML
    private TextField txt_password;

    @FXML
    private TextField txt_nom;

    @FXML
    private TextField txt_prenom;

    @FXML
    private TextField txt_qes;

    @FXML
    private TextField txt_ans;

    @FXML
    private TextField txt_id;
    
    
    ObservableList<user> listM;
   
     int index = -1;
     
    Connection conn = null ; 
    ResultSet rs = null ; 
    PreparedStatement pst = null ; 
    final ObservableList user = FXCollections.observableArrayList();
    //////method get selcted//////
    @FXML
    private Button logout;
    @FXML
    private Button update;
    @FXML
    private Button delete;
    @FXML
    private ListView<user> list_v;
    
    ListView list = new ListView(user);
    
    
    @FXML
    public void Edit (){
        try {
            conn = ConnectSql.ConnectDb();
            String value1 = txt_id.getText();
            String value2 = txt_email.getText();
            String value3 = txt_password.getText();
            String value4 = txt_role.getText();
            String value5 = txt_nom.getText();
            String value6 = txt_prenom.getText();
           
            String value7 = txt_qes.getText();
            String value8 = txt_ans.getText();
            
            String sql = "update user set id='"+value1+"',email= '"+value2+"',password= '"
            +value3+"' , roles= '"+value4+"',nom='"+value5+"',prenom='"+value6+"',s_ques='"+value7+"',answer='"+value8+"' where id='"+value1+"'" ;
            pst = conn.prepareStatement(sql); 
            pst.execute();
            JOptionPane.showMessageDialog(null, "update");
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null, e);
        }
    
    }
    @FXML
     public void Delete (){
        conn = ConnectSql.ConnectDb(); 
        String sql = "delete from user where id = ?";
        try {
            pst = conn.prepareStatement(sql);
            pst.setString(1, txt_id.getText());
            pst.execute();
            JOptionPane.showMessageDialog(null, "delete");
            UpdateTable ();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    } 
      public void UpdateTable(){
        col_id.setCellValueFactory(new PropertyValueFactory<user,Integer>("id"));
        col_email.setCellValueFactory(new PropertyValueFactory<user,String>("email"));
        col_password.setCellValueFactory(new PropertyValueFactory<user,String>("password"));
        col_roles.setCellValueFactory(new PropertyValueFactory<user,String>("role"));
        col_nom.setCellValueFactory(new PropertyValueFactory<user,String>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<user,String>("prenom"));
        col_qes.setCellValueFactory(new PropertyValueFactory<user,String>("s_qes"));
        col_ans.setCellValueFactory(new PropertyValueFactory<user,String>("answer"));
        listM = ConnectSql.getDatauser(); 
        table_users.setItems(listM);
        }
     

  
    @FXML
    void logout(ActionEvent event) throws IOException {
        
        Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm logout");
        alert.setHeaderText(null);
        alert.setContentText("Continue logging out?");
        Optional <ButtonType> action =alert.showAndWait();
        
        if (action.get()==ButtonType.OK){
        Parent view5=FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                Scene scene5=new Scene(view5);
                Stage window =(Stage)((Node)event.getSource()).getScene().getWindow();
                window.setScene(scene5);
                window.show();
        }
      
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb)  {
     col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
      col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
      col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
      col_roles.setCellValueFactory(new PropertyValueFactory<>("roles"));
      col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
      col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
      col_qes.setCellValueFactory(new PropertyValueFactory<>("s_ques"));
      col_ans.setCellValueFactory(new PropertyValueFactory<>("answer"));
      listM = ConnectSql.getDatauser() ; 
      table_users.setItems(listM);
    }

    @FXML
    private void getSelected(javafx.scene.input.MouseEvent event) {
        
    index = table_users.getSelectionModel().getFocusedIndex() ; 
    if (index <= -1){
     
    return;
     }
    txt_id.setText(col_id.getCellData(index).toString());
    txt_email.setText(col_email.getCellData(index).toString());
    txt_role.setText(col_roles.getCellData(index).toString());
    txt_password.setText(col_password.getCellData(index).toString());
    
    txt_nom.setText(col_nom.getCellData(index).toString());
    txt_prenom.setText(col_prenom.getCellData(index).toString());
    txt_qes.setText(col_qes.getCellData(index).toString());
    txt_ans.setText(col_ans.getCellData(index).toString());
    }

}



      