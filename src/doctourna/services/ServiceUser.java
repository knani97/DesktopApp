/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.services;

import doctourna.models.Cv;
import doctourna.models.User;
import doctourna.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mouhe
 */
public class ServiceUser implements IService<User> {
    Connection connection = DataSource.getInstance().getConnection();
    ServiceCv scv = new ServiceCv();
    
    @Override
    public void ajouter(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(User t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<User> afficher() {
        List<User> list = new ArrayList<User>();
        String query = "SELECT * FROM user";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new User(
                        rs.getInt("id"), 
                        scv.find(rs.getInt("cv_id")), 
                        rs.getString("image"), 
                        rs.getInt("type"), 
                        rs.getString("nom"), 
                        rs.getString("prenom"), 
                        rs.getString("email"), 
                        rs.getString("roles"), 
                        rs.getString("password")
                        ));
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public User find(int id) {
        List<User> list = new ArrayList<User>();
        String query = "SELECT * FROM user WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs.getInt("id") == id)
                return new User(
                        rs.getInt("id"), 
                        scv.find(rs.getInt("cv_id")), 
                        rs.getString("image"), 
                        rs.getInt("type"), 
                        rs.getString("nom"), 
                        rs.getString("prenom"), 
                        rs.getString("email"), 
                        rs.getString("roles"), 
                        rs.getString("password")
                        );
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public List<User> rechercherMedecins() {
        return afficher().stream().filter(u -> u.getType() == 2).collect(Collectors.toList());
    }
    
    public List<User> rechercherMedecinsSpec(String spec) {
        return rechercherMedecins().stream().filter(u -> u.getCvId().getSpecialite().contains(spec)).collect(Collectors.toList());
    }
}
