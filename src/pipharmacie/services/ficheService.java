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
import javafx.scene.control.Alert;
import pipharmacie.connexion.connexionBD;
import pipharmacie.entities.Fiche;
import pipharmacie.entities.Medicament;


public class ficheService {
           private Connection cnx;
    private Statement st;
    private PreparedStatement pre;
    public ficheService() {
    cnx = connexionBD.getInstance().getCnx();
  }
    
      public boolean ajoutFiche(Fiche m)
    {
         try {
            String req = "INSERT INTO fiche ( idMed,quantite,prix_vente,utilisation) VALUES "
                    + "('" + m.getIdMed() + "', '" +m.getQuantite() + "', '" +m.getPrix_vente() +
                    "', '" +m.getUtilisation()+"')";

            st = cnx.createStatement();

            st.executeUpdate(req);

            return true;

        } catch (SQLException ex) {
            Error("la fiche existe déjà");
            System.out.println(ex);
            return false;
        }
    }
            
      private void Error(String msg) {
      Alert alert = new Alert(Alert.AlertType.ERROR);
alert.setTitle("Error Dialog");
alert.setHeaderText("Look, an Error Dialog");
alert.setContentText(msg);
alert.showAndWait();
    }
      
       public List<Fiche> afficherFiche()
    {
         List<Fiche> listP = new ArrayList<>();

        try {
            String req = "SELECT f.*,m.nomMed FROM fiche f inner join medicament m on f.idMed= m.id ";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                Fiche p = new Fiche();
                p.setId(res.getInt("id"));
             
                p.setIdMed(res.getInt("idMed"));
                p.setQuantite(res.getInt("quantite"));
               p.setPrix_vente(res.getFloat("prix_vente"));
               p.setUtilisation(res.getString("utilisation"));
               p.setNomMed(res.getString("nomMed"));
                listP.add(p);
            }
            
            System.out.println(listP);

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return listP;
    }
      
         
       public Fiche afficherUneFiche(int idMed)
    {                Fiche p = new Fiche();


        try {
            String req = "SELECT f.*,m.nomMed FROM fiche f inner join medicament m on f.idMed= m.id where f.idMed='"+idMed+"'";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
                p.setId(res.getInt("id"));
             
                p.setIdMed(res.getInt("idMed"));
                p.setQuantite(res.getInt("quantite"));
               p.setPrix_vente(res.getFloat("prix_vente"));
               p.setUtilisation(res.getString("utilisation"));
               p.setNomMed(res.getString("nomMed"));
            }
            

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return p;
    }
            
            public boolean modifierQuantite(Fiche m)
    {
      try {
            String req = "update fiche set  quantite=? where id = ?";

            pre = cnx.prepareStatement(req);
            pre.setInt(2,m.getId());
            pre.setInt(1,m.getQuantite());
           
          


            
            pre.executeUpdate();

            System.out.println("Update 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
       
       
            
       public int ficheexist(int idMed)
    {              int id=0;


        try {
            String req = "SELECT idMed from fiche where idMed='"+idMed+"'";

            st = cnx.createStatement();
            ResultSet res = st.executeQuery(req);

            while (res.next()) {
             
              id=res.getInt("idMed");
             
            }
            

        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return id;
    }
       
       
       
       
       
       
       
       
       
       
       
         public boolean modifierFiche(Fiche m)
    {
      try {
            String req = "update fiche set idMed=?, quantite=?,prix_vente=?, utilisation=? where id = ?";

            pre = cnx.prepareStatement(req);
            pre.setInt(5,m.getId());
                        pre.setInt(1,m.getIdMed());
            pre.setInt(2,m.getQuantite());

            pre.setFloat(3,m.getPrix_vente());
  pre.setString(4,m.getUtilisation());


            
            pre.executeUpdate();

            System.out.println("Update 2 Reussie!");
            return true;

        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }

    }
    
        public boolean deleteFiche(int id)
    {
           try {
            String req = "delete from fiche where id = ?";

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
}
