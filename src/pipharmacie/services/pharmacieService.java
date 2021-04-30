/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pipharmacie.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pipharmacie.connexion.connexionBD;
import pipharmacie.entities.Pharmacie;

public class pharmacieService {
     private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
    public pharmacieService() {
    cnx = connexionBD.getInstance().getCnx();
  }
    
      public boolean ajoutPharmacie(Pharmacie m)
    {
          try {
            String req = "INSERT INTO pharmacie ( nom,adresse,gouvernourat,img_patente) VALUES "
                    + "('" + m.getNom() + "', '" +m.getAdresse() + "', '" +m.getGouvernourat() + "', '" +m.getImg_patente()+ "')";

            st = cnx.createStatement();

            st.executeUpdate(req);

            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
            
    
      
       public List<Pharmacie> afficherPharmacie()
    {
         List<Pharmacie> listP = new ArrayList<>();

        try {
            String req = "SELECT * FROM pharmacie ";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Pharmacie p = new Pharmacie();
                p.setId(res.getInt("id"));
                p.setNom(res.getString("nom"));
                p.setAdresse(res.getString("adresse"));
                p.setGouvernourat(res.getString("gouvernourat"));
                p.setImg_patente(res.getString("img_patente"));
                System.out.println("hethi image"+p.getImg_patente());
 ImageView v=new ImageView();
                   v.setImage(new Image("file:/C:/Users/user/Desktop/imagepi/"+res.getString("img_patente")));
                   v.setFitHeight(100);
                   v.setFitWidth(100);
                p.setImage(v);
                listP.add(p);
            }
            
            System.out.println(listP);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }
        public Pharmacie afficherUnePharmacie(int id)
    {
                Pharmacie p = new Pharmacie();

        try {
            String req = "SELECT * FROM pharmacie where id ='"+id+"'";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                p.setId(res.getInt("id"));
                p.setNom(res.getString("nom"));
                p.setAdresse(res.getString("adresse"));
                p.setGouvernourat(res.getString("gouvernourat"));
                p.setImg_patente(res.getString("img_patente"));
                System.out.println("hethi image"+p.getImg_patente());
 ImageView v=new ImageView();
                   v.setImage(new Image("file:/C:/Users/user/Desktop/imagepi/"+res.getString("img_patente")));
                   v.setFitHeight(100);
                   v.setFitWidth(100);
                p.setImage(v);
            }
            

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return p;
    }
         public boolean modifierPharmacie(Pharmacie m)
    {
      try {
            String req = "update pharmacie set nom=?, adresse=?,gouvernourat=?,img_patente=? where id = ?";

            pre = cnx.prepareStatement(req);
            pre.setString(1,m.getNom());
            pre.setString(2,m.getAdresse());
            pre.setString(3,m.getGouvernourat());
             pre.setString(4,m.getImg_patente());
pre.setInt(5, m.getId());

            pre.executeUpdate();

            System.out.println("Update 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
    
        public boolean deletePharmacie(int id)
    {
           try {
            String req = "delete from pharmacie where id = ?";

            pre = cnx.prepareStatement(req);

          
            pre.setInt(1, id);

            pre.executeUpdate();

            System.out.println("supprimer 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }  
       
     public ObservableList<Pharmacie> recherchePharmacie(String recherche) throws SQLException {
                   Pharmacie p = new Pharmacie();

        ObservableList<Pharmacie> list = FXCollections.observableArrayList();
        String requete = "select * FROM pharmacie WHERE nom LIKE '%"+recherche+"%' OR adresse LIKE '%"+recherche+"%'";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNom(rs.getString("nom"));
                p.setAdresse(rs.getString("adresse"));
                p.setGouvernourat(rs.getString("gouvernourat"));
             p.setImg_patente(rs.getString("img_patente"));
                 ImageView v=new ImageView();
                   v.setImage(new Image("file:/C:/xampp/htdocs/img/"+rs.getString("img_patente")));
                   v.setFitHeight(100);
                   v.setFitWidth(100);
                p.setImage(v);
                list.add(p);
 
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return list;
    }  
    
}
