/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.services;

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
import pidev.utils.DbConnection;
import pidev.models.Medicament;
import pidev.models.Pharmacie;


public class medicamentService {
         private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
    public medicamentService() {
    cnx = DbConnection.getInstance().getCnx();
  }
    
      public boolean ajoutMedicament(Medicament m)
    {
          try {
            String req = "INSERT INTO medicament ( nomMed,fourniseur,prix_achat,poids,img_med,idCat) VALUES "
                    + "('" + m.getNomMed() + "', '" +m.getFourniseur() + "', '" +m.getPrix_achat() + "', '" +m.getPoids()+ "', '" +m.getImg_med()+ "', '" +m.getIdCat()+ "')";

            st = cnx.createStatement();

            st.executeUpdate(req);

            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
            
    
      
       public List<Medicament> afficherMedicament()
    {
         List<Medicament> listP = new ArrayList<>();

        try {
            String req = "SELECT m.*,c.nom FROM medicament  m inner join categorie c on m.idCat=c.id ";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Medicament p = new Medicament();
                p.setId(res.getInt("id"));
                p.setNomMed(res.getString("nomMed"));
                p.setFourniseur(res.getString("fourniseur"));
                p.setPrix_achat(res.getFloat("prix_achat"));
                p.setPoids(res.getFloat("poids"));
                p.setImg_med(res.getString("img_med"));
 ImageView v=new ImageView();
                   v.setImage(new Image("file:/C:/Users/user/Desktop/imagepi/"+res.getString("img_med")));
                   v.setFitHeight(100);
                   v.setFitWidth(100);
                p.setImage(v);
                     p.setIdCat(res.getInt("idCat"));
p.setNom(res.getString("nom"));
                listP.add(p);
            }
            
            System.out.println(listP);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }
       
      
         public boolean modifierMedicament(Medicament m)
    {
      try {
            String req = "update medicament set nomMed=?, fourniseur=?,prix_achat=?,poids=?,img_med=?,idCat=? where id = ?";

            pre = cnx.prepareStatement(req);
            pre.setString(1,m.getNomMed());
            pre.setString(2,m.getFourniseur());
            pre.setFloat(3,m.getPrix_achat());
             pre.setFloat(4,m.getPoids());
               pre.setString(5,m.getImg_med());

pre.setInt(6, m.getIdCat());

pre.setInt(7, m.getId());

            pre.executeUpdate();

            System.out.println("Update 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
    
        public boolean deleteMed(int id)
    {
           try {
            String req = "delete from medicament where id = ?";

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
        
          public int recupererID(String nom)
    {
int id =0;
        try {
            String req = "SELECT id FROM medicament where nomMed='"+nom+"'";;

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
               id= res.getInt(1);
            }
            

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return id;
    }
       
                public float recupererPrix(String nom)
    {
float id =0;
        try {
            String req = "SELECT prix_achat FROM medicament where nomMed='"+nom+"'";;

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
               id= res.getInt(1);
            }
            

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return id;
    }
            public ObservableList<Medicament> rechercheMedicament(String recherche) throws SQLException {
                   Medicament p = new Medicament();

        ObservableList<Medicament> list = FXCollections.observableArrayList();
        String requete = "SELECT m.*,c.nom FROM medicament  m inner join categorie c on m.idCat=c.id where m.nomMed LIKE '%"+recherche+"%' OR m.fourniseur LIKE '%"+recherche+"%' OR m.prix_achat LIKE '%"+recherche+"%' OR m.poids LIKE '%"+recherche+"%' OR c.nom LIKE '%"+recherche+"%'";
        try {
            PreparedStatement ps = cnx.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                p.setId(rs.getInt("id"));
                p.setNomMed(rs.getString("nomMed"));
                p.setFourniseur(rs.getString("fourniseur"));
                p.setPrix_achat(rs.getFloat("prix_achat"));
                p.setPoids(rs.getFloat("poids"));
                p.setIdCat(rs.getInt("idCat"));
                p.setImg_med(rs.getString("img_med"));
p.setNom(rs.getString("nom"));
  p.setImg_med(rs.getString("img_med"));
 ImageView v=new ImageView();
                   v.setImage(new Image("file:/C:/Users/user/Desktop/imagepi/"+rs.getString("img_med")));
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
