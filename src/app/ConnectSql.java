package app;
import java.sql.Connection ; 
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.swing.JOptionPane;
public class ConnectSql {
    Connection conn = null ; 
    public static Connection ConnectDb(){
        try {
          Class.forName("com.mysql.jdbc.Driver");
          Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/pidev","root","");
            JOptionPane.showMessageDialog(null, "Connection Established");
            return conn ; 
        } catch (Exception e) {
         JOptionPane.showMessageDialog(null, e);
         return null;
        }
 
    }
    public static ObservableList<user> getDatauser(){
    Connection conn = ConnectDb() ; 
    ObservableList<user> list = FXCollections.observableArrayList();
        try {
            PreparedStatement ps = conn.prepareCall("select * from user");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
           list.add(new user (Integer.parseInt(rs.getString("id")),rs.getString("email"),rs.getString("password"),rs.getString("roles"),rs.getString("nom"),rs.getString("prenom"),rs.getString("s_ques"),rs.getString("answer"))) ; 
            }
            
        } catch (Exception e) {
        }
        return list;
    } 
}
