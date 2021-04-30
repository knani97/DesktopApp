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

/**
 *
 * @author mouhe
 */
public class ServiceCv implements IService<Cv> {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Cv t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void supprimer(Cv t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(Cv t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Cv> afficher() {
        List<Cv> list = new ArrayList<Cv>();
        String query = "SELECT * FROM cv";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Cv(
                        rs.getInt("id"), 
                        rs.getString("specialite"), 
                        rs.getString("diplome"), 
                        rs.getString("file")
                        ));
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public Cv find(int id) {
        List<Cv> list = new ArrayList<Cv>();
        String query = "SELECT * FROM cv WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new Cv(
                        rs.getInt("id"), 
                        rs.getString("specialite"), 
                        rs.getString("diplome"), 
                        rs.getString("file")
                        );
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
