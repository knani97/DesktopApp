/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.doctourna.Service;

import com.doctourna.IService.IService;
import com.doctourna.Utils.MyConnexion;
import com.doctourna.models.Commande;
import com.doctourna.models.panier;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author yass
 */
public class Commande_service implements IService<Commande> {



    private Connection c = MyConnexion.getInsCon().getcnx();

    @Override
    public void Ajouter(Commande u) throws SQLException {
        PreparedStatement ps;

        
        String query = "INSERT INTO `commande`(`prix`, `date_achat`) VALUES (?,?)";
        try {
            ps = c.prepareStatement(query);
             ps.setFloat(1, u.getPrix());
          ps.setString(2, new java.util.Date().toString());
       
            
         
            ps.execute();
            
            System.out.println(u);
        } catch (Exception e) {
              
       
            System.out.println(e);

        }
    }

    @Override
    public void Supprimer(int t) throws SQLException {
    }

    @Override
    public void Modifier(Commande t, int id) throws SQLException {
    }

    @Override
    public List<Commande> Affichertout() throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
