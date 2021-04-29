/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.Service;

import com.doctourna.IService.IService;
import com.doctourna.Utils.MyConnexion;
import com.doctourna.models.panier;
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
public class panier_Service implements IService<panier> {



    private Connection c = MyConnexion.getInsCon().getcnx();

    @Override
    public void Ajouter(panier u) throws SQLException {
      PreparedStatement ps;

        
        String query = "INSERT INTO `panier`( `nom`, `prix`) VALUES (?,?)";
        try {
            ps = c.prepareStatement(query);
             ps.setString(1, u.getNom());
          ps.setInt(2, u.getPrix());
       
            
         
            ps.execute();
            
            System.out.println(u);
        } catch (Exception e) {
              
       
            System.out.println(e);

        }
    
    }
 public int prixtotal()
       {
           int total=0;
               String requete = "SELECT SUM(prix) as total FROM `panier`";
                   
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
    @Override
    public void Supprimer(int t) throws SQLException {
    PreparedStatement ps;

        String query = "DELETE FROM `panier` WHERE `id`=?  ";
      //  Acheter_Service s = new Acheter_Service();
      //  s.SupprimerU(id);
        try {
            ps = c.prepareStatement(query);

            ps.setInt(1, t);

            ps.execute();

        
        } catch (Exception e) {
            System.out.println(e);
        }  }

    @Override
    public void Modifier(panier t, int id) throws SQLException {
      }

    @Override
    public ObservableList<panier> Affichertout() throws SQLException {
        ObservableList<panier> list = FXCollections.observableArrayList();
      String requete = "SELECT * FROM `panier`";

       try {
            PreparedStatement ps = c.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                list.add(new panier(rs.getInt("id"),rs.getString("nom"),rs.getInt("prix")));

            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return list;
  
    
    }
}
