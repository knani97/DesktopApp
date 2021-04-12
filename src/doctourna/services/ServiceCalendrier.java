/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.services;

import doctourna.models.Calendrier;
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
public class ServiceCalendrier implements IService<Calendrier> {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Calendrier calendrier) {
        String query = "INSERT INTO calendrier(uid_id,email,type,couleur,timezone,format) "
                    + "VALUES(?,?,?,?,?,?)";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, calendrier.getUidId().getId());
            ps.setBoolean(2, calendrier.getEmail());
            ps.setInt(3, calendrier.getType());
            ps.setString(4, calendrier.getCouleur());
            ps.setString(5, calendrier.getTimezone());
            ps.setInt(6, calendrier.getFormat());
            ps.executeUpdate();
            System.out.println("Calendrier ajouté avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Calendrier calendrier) {
        String query = "DELETE FROM calendrier "
                    + "WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, calendrier.getId());
            ps.executeUpdate();
            System.out.println("Calendrier supprimé avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Calendrier calendrier) {
        String query = "UPDATE calendrier SET "
                    + "uid_id=?,email=?,type=?,couleur=?,timezone=?,format=? WHERE id=?";
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, calendrier.getUidId().getId());
            ps.setBoolean(2, calendrier.getEmail());
            ps.setInt(3, calendrier.getType());
            ps.setString(4, calendrier.getCouleur());
            ps.setString(5, calendrier.getTimezone());
            ps.setInt(6, calendrier.getFormat());
            ps.setInt(7, calendrier.getId());
            ps.executeUpdate();
            System.out.println("Calendrier modifié avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Calendrier> afficher() {
        List<Calendrier> list = new ArrayList<Calendrier>();
        String query = "SELECT * FROM calendrier";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                Calendrier calendrier = new Calendrier(
                        rs.getInt("id"), 
                        new User(rs.getInt("uid_id")), 
                        rs.getInt("email"), 
                        rs.getBoolean("type"), 
                        rs.getString("couleur"), 
                        rs.getString("timezone"), 
                        rs.getInt("format")
                        );
                list.add(calendrier);
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public Calendrier findByUid(int uid) {
        String query = "SELECT * FROM calendrier WHERE uid_id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, uid);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if (rs.getInt("uid_id") == uid)
                return new Calendrier(
                        rs.getInt("id"), 
                        new User(rs.getInt("uid_id")), 
                        rs.getInt("email"), 
                        rs.getBoolean("type"), 
                        rs.getString("couleur"), 
                        rs.getString("timezone"), 
                        rs.getInt("format")
                        );
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
