/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.Service;

import com.doctourna.IService.IService;
import com.doctourna.Utils.MyConnexion;
import com.doctourna.models.Video;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author yass
 */
public class video_Service implements IService<Video>  {
    private Connection c = MyConnexion.getInsCon().getcnx();
   @Override
    public void Ajouter(Video u) throws SQLException {
         
        PreparedStatement ps;

        
        String query = "INSERT INTO `video`( `titre`, `source`, `paye`, `prix`, `note`, `panier_id`) VALUES (?,?,?,?,?,?)";
        try {
            ps = c.prepareStatement(query);
            
             ps.setString(1, u.getTitre());
             ps.setString(2, u.getSource());
             ps.setInt(3, u.getPaye());
             ps.setFloat(4, u.getPrix());
              ps.setInt(5, u.getNote());
            ps.setInt(6, 1);
         
            ps.execute();
            
            System.out.println(u);
        } catch (Exception e) {
              
       
            System.out.println(e);

        }
  }
     public int prixtotal()
       {
           int total=0;
               String requete = "SELECT SUM(prix) as total FROM `video` where panier_id=2";
                   
                     try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                total= rs.getInt("total");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }    
           
           return total;
       }
     public Video get_Video_affichage(int i) {
        Video p = null;
        int nombre = 0;
      String requete = "SELECT * FROM  video"       ;
         try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (i == nombre) {
                    
       p=new Video(rs.getInt("id"),rs.getString("titre"),rs.getString("source"),rs.getInt("paye"),rs.getFloat("prix"),rs.getInt("note"),rs.getInt("panier_id"));
               
                }
                nombre++;
                         }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return p;

    }

    @Override
    public void Supprimer(int t) throws SQLException {
     PreparedStatement ps;

        String query = "DELETE FROM `video` WHERE `id`=?  ";
      //  Acheter_Service s = new Acheter_Service();
      //  s.SupprimerU(id);
        try {
            ps = c.prepareStatement(query);

            ps.setInt(1, t);

            ps.execute();

     
        } catch (Exception e) {
            System.out.println(e);
        } }
  public int Affichertaille() throws SQLException {
        int nbr = 0;        
        String requete = "SELECT COUNT(*) as nbr FROM `video` "       ;
                try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
              nbr=rs.getInt("nbr");
            }
                   
        } catch (SQLException ex) {
            System.out.println(ex);
        }
                        System.out.println("le nombre est : "+nbr);   
        return nbr;

    }
    @Override
    public void Modifier(Video u, int id) throws SQLException {
   PreparedStatement ps;
        String query = "UPDATE `video` SET `titre`=?,`source`=?,`paye`=?,`prix`=?,`note`=?,`panier_id`=? WHERE  id = ?";
        try {
            
            ps = c.prepareStatement(query);
    
             ps.setString(1, u.getTitre());
             ps.setString(2, u.getSource());
             ps.setInt(3, u.getPaye());
               ps.setFloat(4, u.getPrix());
                    ps.setInt(5, u.getNote());
                    ps.setInt(6, u.getPanier_id());
                 ps.setInt(7, id);
           
             
           
            ps.execute();
    

        } catch (Exception e) {
        }
    
    }
 public void Modifier_Video(Video u, int id) throws SQLException {
   PreparedStatement ps;
        String query = "UPDATE `video` SET `panier_id`=? WHERE  id = ?";
        try {
            
            ps = c.prepareStatement(query);
    
                    ps.setInt(1, u.getPanier_id());
                 ps.setInt(2, id);
           
             
           
            ps.execute();
    

        } catch (Exception e) {
        }
    
    }
    @Override
    public ObservableList<Video> Affichertout() throws SQLException {
    ObservableList<Video> list = FXCollections.observableArrayList();
      String requete = "select * from video where panier_id=1";
        try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Video(rs.getInt("id"),rs.getString("titre"),rs.getString("source"),rs.getInt("paye"),rs.getFloat("prix"),rs.getInt("note"),rs.getInt("panier_id")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
    public ObservableList<Video> Affichertout_panier() throws SQLException {
    ObservableList<Video> list = FXCollections.observableArrayList();
      String requete = "select * from video  where panier_id=2";
        try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new Video(rs.getInt("id"),rs.getString("titre"),rs.getString("source"),rs.getInt("paye"),rs.getFloat("prix"),rs.getInt("note"),rs.getInt("panier_id")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
    }
       public ObservableList<Video> serach(String cas) throws SQLException {
        ObservableList<Video> list = FXCollections.observableArrayList();
        String requete = "SELECT * FROM  video where  titre LIKE '%" + cas + "%'or source LIKE '%" + cas   + "%' or  prix LIKE '%" + cas + "%' ";
       try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
       list.add(new Video(rs.getInt("id"),rs.getString("titre"),rs.getString("source"),rs.getInt("paye"),rs.getFloat("prix"),rs.getInt("note"),rs.getInt("panier_id")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }
    
}
