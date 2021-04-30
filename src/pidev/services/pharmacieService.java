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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pidev.interfaces.Initializable;
import pidev.utils.DbConnection;
import pidev.models.Pharmacie;
/**
 *
 * @author imen
 */
public class pharmacieService implements Initializable<Pharmacie>{

        Connection cnx = DbConnection.getInstance().getCnx();

    /*  public void ajoutPharmacie(Pharmacie m)
    {
         
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
            String req = "delete * from pharmacie where id = ?";

            pre = cnx.prepareStatement(req);

          
            pre.setInt(1, id);

            pre.executeUpdate();

            System.out.println("supprimer 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }  */

    @Override
    public void add(Pharmacie m) {
 try {
            String req = "INSERT INTO pharmacie ( nom,adresse,gouvernourat,img_patente) VALUES "
                    + "(?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, m.getNom());
            ps.setString(2, m.getAdresse());
            ps.setString(3, m.getGouvernourat());
            ps.setString(4, m.getImg_patente());
            ps.executeUpdate();
            System.out.println("pharmacie ajoutée");



        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(Pharmacie m) {
        String req ="delete from pharmacie where id = ? ";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, m.getId());
            ps.executeUpdate();
            System.out.println("pharmacie supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }

    }

    @Override
    public void update(Pharmacie m) {
        try {
            String req = "update pharmacie set nom=?, adresse=?,gouvernourat=?,img_patente=? where id = ?";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1,m.getNom());
            ps.setString(2,m.getAdresse());
            ps.setString(3,m.getGouvernourat());
            ps.setString(4,m.getImg_patente());
            ps.setInt(5, m.getId());

            ps.executeUpdate();

            System.out.println("pharmacie modifiée!");
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
    }

    @Override
    public List<Pharmacie> read() {
         List<Pharmacie> list= new ArrayList<>();
        String req ="select * from pharmacie";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
           
            ResultSet rs= ps.executeQuery(req);
            while(rs.next()){
            list.add(new Pharmacie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return list;
        
    }
    
     public List<Pharmacie> filtre(String nom) {
        List<Pharmacie> list = new ArrayList<>();

        
            
            try {
                String req = "SELECT ph.* FROM pharmacie ph "
                        + "JOIN inventaire inv  ON ph.id = inv.pharmacie_id   "
                        + "JOIN medicament med ON inv.medicaments_id  = med.id"
                        + " WHERE med.nomMed = ? ";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1, nom);
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    list.add(new Pharmacie(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
       return list;
     }
}
    

