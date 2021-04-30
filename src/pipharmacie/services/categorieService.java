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
import pipharmacie.connexion.connexionBD;
import pipharmacie.entities.Categorie;
import pipharmacie.entities.Fiche;


public class categorieService {
              private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
    public categorieService() {
    cnx = connexionBD.getInstance().getCnx();
  }
    
     
      public boolean ajoutCategorie(Categorie m)
    {
          try {
            String req = "INSERT INTO categorie_med (nom,description) VALUES "
                    + "('" + m.getNom() + "', '" +m.getDescription() +  "')";

            st = cnx.createStatement();

            st.executeUpdate(req);

            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
    }
            
      public boolean modifierCategorie(Categorie m)
    {
      try {
            String req = "update categorie_med set  nom=?,description=? where id = ?";

            pre = cnx.prepareStatement(req);
                      
            pre.setString(1,m.getNom());
            pre.setString(2,m.getDescription());

            pre.setInt(3,m.getId());



            
            pre.executeUpdate();

            System.out.println("Update 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
    
        public boolean deleteCategorie(int id)
    {
           try {
            String req = "delete from categorie_med where id = ?";

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
public List<Categorie> afficherCategorie()
    {
         List<Categorie> listP = new ArrayList<>();

        try {
            String req = "SELECT * from categorie_med ";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Categorie p = new Categorie();
                p.setId(res.getInt("id"));
             
                            p.setDescription(res.getString("description"));

               p.setNom(res.getString("nom"));
                listP.add(p);
            }
            
            System.out.println(listP);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }
       public int recupererID(String nom)
    {
int id =0;
        try {
            String req = "SELECT id FROM categorie_med where nom='"+nom+"'";;

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
}
