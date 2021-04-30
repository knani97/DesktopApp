/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;
import Entity.Article;
import Entity.ArticleCat;
import Entity.Users;
import colors.ConsoleColors;
import java.sql.Connection;
import static java.sql.JDBCType.DATE;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import utils.DataBase;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Observable;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import static javax.persistence.TemporalType.DATE;
import utils.DataBase;
/**
 *
 * @author Lenovo
 */
public class ServiceArticleCat implements InterfaceService<ArticleCat>{

    Connection con = DataBase.getInstance().getConnetion();

     //   ********************************  CRUD  ********************************
    @Override
    public void ajouter(ArticleCat t) {
        try{
           String querry = "INSERT INTO article_cat VALUES (null,?,?)";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setString(1, t.getCategorie());
            stm.setString(2, t.getImage());
            stm.executeUpdate();
            stm.executeUpdate(querry);
            System.out.println("Categorie"+t.getCategorie()+" ajoutée !");
           

           

        }
        catch(SQLException ex){
          
        }
    }
    

    //   ********************************  CRUD  ********************************
    @Override
    public void supprimer(ArticleCat t) {
        try {
            String requete = "DELETE FROM article_cat WHERE id=?";
            PreparedStatement stm = con.prepareStatement(requete);
            stm.setInt(1, t.getId());
            stm.executeUpdate();
            System.err.println("Votre catégorie à été supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
    
    //   ********************************  CRUD  ********************************
    @Override
    public void modifier(ArticleCat t) {
        try{
        String querry = "UPDATE article_cat SET categorie=?,image=? WHERE id=?";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setString(1, t.getCategorie());
            stm.setString(2, t.getImage());
            stm.setInt(3, t.getId());
            stm.executeUpdate();
            System.out.println("Catégorie modifiée !");
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }
    }
    
    
    
    //   ********************************  CRUD  ********************************
    @Override
    public List<ArticleCat> afficherList() {
        List<ArticleCat> list = new ArrayList<>();
        try {
            String querry = "SELECT * FROM article_cat";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ArticleCat(rs.getInt("id"), rs.getString("categorie"), rs.getString("image")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }

    @Override
    public void afficher(ArticleCat t) {
       System.err.println("Votre requete pour "+t.toString()+" a été bien executer");
    }

    /**
     *
     * @return
     */
    
    //   ********************************  METIER  ********************************
    public List<Article> notification() {
        List <Article> list = new ArrayList<>();
        try {
            String querry = "SELECT u.nom,u.image as imageuser,u.prenom,a.*,c.categorie\n" +
"        FROM article a\n" +
"        INNER JOIN user u ON u.id = a.id_user\n" +
"        INNER JOIN article_cat c ON a.id_cat_id = c.id\n";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
//                boolean add = list.add(new Article(rs.getInt("id"),rs.getString("titre"),rs.getString("text"),rs.getString("image"),rs.getDate("date_ajout"),rs.getInt("etat_ajout"),rs.getInt("id_user"),rs.getInt(8)));
                  
                  Integer id = rs.getInt("id");
                  String titre=rs.getString("titre").toUpperCase();
                  String categorie=rs.getString("categorie");
                  String nom=rs.getString("prenom");
                  String prenom=rs.getString("nom");
                  Date dateAjout=rs.getDate("date_ajout");
                  Integer etatAjout=rs.getInt("etat_ajout");
                  Integer idUser=rs.getInt("id_user");
                  String idCatId=rs.getString("categorie");
                  
                  
                  list.add(new Article(rs.getInt("a.id"), rs.getString("a.titre"), rs.getString("a.text"), rs.getString("a.image"), rs.getDate("a.date_ajout"),rs.getInt("a.etat_ajout"),rs.getInt("id_user"),rs.getString("u.prenom")
                        ,rs.getString("u.nom"),rs.getString("imageuser"),rs.getString("c.categorie")));
                  

                  
            
            }
            

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list.stream()
                .filter(a -> a.getEtatAjout()== 0)
                .sorted((a1, a2) -> a1.getDateAjout().before(a2.getDateAjout())? 1 : -1)
                .collect(Collectors.toList());
    
    
}
    
    //   ********************************  METIER  ********************************
    public List<String> TopArticleCat() {
        List<String> list = new ArrayList<>();
        try {
            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
             int currentMounth = currentdate.getMonthValue();
            String querry = "SELECT  COUNT(a.id_cat_id) AS nbr,cat.categorie,cat.image \n" +
"FROM article_cat cat \n" +
"INNER JOIN article a ON a.id_cat_id = cat.id\n" +
"WHERE(Month(a.date_ajout)="+currentMounth+" AND Year(a.date_ajout)="+currentYear+" ) AND a.etat_Ajout=1\n" +
"GROUP BY a.id_cat_id \n" +
"ORDER BY nbr DESC limit 1\n";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
//                boolean add = list.add(new Article(rs.getInt("id"),rs.getString("titre"),rs.getString("text"),rs.getString("image"),rs.getDate("date_ajout"),rs.getInt("etat_ajout"),rs.getInt("id_user"),rs.getInt(8)));
                  
                  Integer nbr = rs.getInt("nbr");
                  String categorie=rs.getString("categorie");
                  String image=rs.getString("cat.image"); 
                  
                  
                  
                  list.add(categorie);
                  list.add(nbr+"");
                  list.add(image);

                  

                  
            
            }
            System.err.println("La catégorie la plus utilisé dans ce mois est : ");
            

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    //   ********************************  METIER  ********************************
    public List<ArticleCat> top3ArticleCat() {
        List<ArticleCat> list = new ArrayList<>();
        try {
            LocalDate currentdate = LocalDate.now();
            int currentYear = currentdate.getYear();
            int currentMounth = currentdate.getMonthValue();
            String querry = "SELECT  COUNT(a.id_cat_id) AS nbr,cat.categorie,cat.image \n" +
"FROM article_cat cat "+
                    "INNER JOIN article a ON a.id_cat_id = cat.id\n" +
                    "WHERE(Month(a.date_ajout)="+currentMounth+" AND Year(a.date_ajout)="+currentYear+" ) AND a.etat_Ajout=1\n"+
                    "GROUP BY a.id_cat_id \n" ;
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
            boolean add = list.add(new ArticleCat(rs.getInt("nbr"),rs.getString("cat.categorie"),rs.getString("cat.image")));
                  
                  String categorie=rs.getString("cat.categorie");
                  String image=rs.getString("cat.image");
                  String nbr=rs.getString(rs.getInt("nbr")+" ");
                  
                  

                  
            
            }
            System.err.println("Les catégories les plus utilisé dans ce mois sont : ");
            

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    
     
    
    public List<String> afficherCatName() throws SQLException {
        ObservableList <String> list = FXCollections.observableArrayList();
        
            String querry = "SELECT categorie FROM article_cat";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                String categorie = rs.getString("categorie");
                list.add(categorie);

            }

        return list;
    }
    
    
    
    
}

        