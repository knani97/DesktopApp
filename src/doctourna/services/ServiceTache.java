/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.services;

import doctourna.models.Calendrier;
import doctourna.models.Tache;
import doctourna.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mouhe
 */
public class ServiceTache implements IService<Tache> {
    Connection connection = DataSource.getInstance().getConnection();

    @Override
    public void ajouter(Tache tache) {
        String query = "INSERT INTO tache(calendrier,libelle,description,type,couleur,date,duree) "
                    + "VALUES(?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tache.getCalendrier().getId());
            ps.setString(2, tache.getLibelle());
            ps.setString(3, tache.getDescription());
            ps.setString(4, tache.getType());
            ps.setString(5, tache.getCouleur());
            ps.setObject(6, tache.getDate());
            ps.setObject(7, tache.getDuree());
            ps.executeUpdate();
            System.out.println("Tâche ajoutée avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Tache tache) {
        String query = "DELETE FROM tache "
                    + "WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tache.getId());
            ps.executeUpdate();
            System.out.println("Tâche supprimée avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Tache tache) {
        String query = "UPDATE tache SET "
                    + "calendrier=?,libelle=?,description=?,type=?,couleur=?,date=?,duree=? WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tache.getCalendrier().getId());
            ps.setString(2, tache.getLibelle());
            ps.setString(3, tache.getDescription());
            ps.setString(4, tache.getType());
            ps.setString(5, tache.getCouleur());
            ps.setObject(6, tache.getDate());
            ps.setObject(7, tache.getDuree());
            ps.setObject(8, tache.getId());
            ps.executeUpdate();
            System.out.println("Tâche modifiée avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Tache> afficher() {
        List<Tache> list = new ArrayList<Tache>();
        String query = "SELECT * FROM tache";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Tache(
                        rs.getInt("id"), 
                        new Calendrier(rs.getInt("calendrier")), 
                        rs.getString("libelle"), 
                        rs.getString("description"), 
                        rs.getString("type"), 
                        rs.getString("couleur"), 
                        rs.getObject("date", Timestamp.class),
                        rs.getObject("duree", Time.class)
                        ));
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public Tache find(int id) {
        List<Tache> list = new ArrayList<Tache>();
        String query = "SELECT * FROM tache WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new Tache(
                        rs.getInt("id"), 
                        new Calendrier(rs.getInt("calendrier")), 
                        rs.getString("libelle"), 
                        rs.getString("description"), 
                        rs.getString("type"), 
                        rs.getString("couleur"), 
                        rs.getObject("date", Timestamp.class),
                        rs.getObject("duree", Time.class)
                        );
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public List<Tache> findByCalendrier(int calendrier) {
        List<Tache> list = new ArrayList<Tache>();
        String query = "SELECT * FROM tache WHERE calendrier=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, calendrier);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Tache(
                        rs.getInt("id"), 
                        new Calendrier(rs.getInt("calendrier")), 
                        rs.getString("libelle"), 
                        rs.getString("description"), 
                        rs.getString("type"), 
                        rs.getString("couleur"), 
                        rs.getObject("date", Timestamp.class),
                        rs.getObject("duree", Time.class)
                        ));
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
}
