/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.Article;
import Entity.ArticleCat;
//import static Entity.Article_.dateAjout;
//import static Entity.Article_.idCatId;
import Entity.Rating;
import Entity.Reagit;
import Entity.Users;
import colors.ConsoleColors;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import utils.DataBase;

/**
 *
 * @author Lenovo
 */
public class ServiceArticle implements InterfaceService<Article>{
    Connection con = DataBase.getInstance().getConnetion();
    UserSession US = new UserSession();

    @Override
    public void ajouter(Article t) {
        try{
           String querry = "INSERT INTO article VALUES (null,?,?,?,?,?,?,?)";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setString(1, t.getTitre());
            stm.setString(2, t.getText());
            stm.setString(3, t.getImage());
            stm.setDate(4, (Date) t.getDateAjout());
            stm.setInt(5,t.getEtatAjout());
            stm.setInt(6,t.getIdUser());
            stm.setObject(7,t.getIdCatId());
            stm.executeUpdate();
            stm.executeUpdate(querry);
            System.out.println("L'article "+t.getTitre()+" est ajouter a votre liste d'attend !");
           
           

           

        }
        catch(SQLException ex){
          
        }
       
    }

    @Override
    public void supprimer(Article t) {
        try {
            String requete = "DELETE FROM article WHERE id=?";
            PreparedStatement stm = con.prepareStatement(requete);
            stm.setInt(1, t.getId());
            stm.executeUpdate();
            System.err.println(ConsoleColors.RED+"L'article avec l'id "+t.getId()+" à été supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Article t) {
        try{
            List<Article> list = new ArrayList<>();
            
//            String select = "SELECT * FROM article ";
//                PreparedStatement stms = con.prepareStatement(select);
//                ResultSet rslt = stms.executeQuery();
//                String titre=rslt.getString(2);
//                String image=rslt.getString(3);
//                String text=rslt.getString(4);
//                
//                if(t.getTitre()==null)
//            {
//                t.setTitre(titre);
//            }
//                if(t.getText()==null)
//                    t.setText(text);
//            {
//                t.setText(text);
//            }
//                if(t.getImage()==null)
//            {
//                t.setImage(image);
//            }
        String querry = "UPDATE article SET titre=?,image=?,text=? WHERE id=?";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setInt(4, t.getId());
            stm.setString(1, t.getTitre());
            stm.setString(2, t.getImage());
            stm.setString(3, t.getText());
            
            stm.executeUpdate();
            System.out.println("Votre Article "+t.getText()+" à été modifiée !");
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }       
    }

    @Override
    public void afficher(Article t) {
        
    }

    @Override
    public List<Article> afficherList() {
        List<Article> list = new ArrayList<>();
        try {
            String querry = "select u.nom,u.image,u.prenom,a.*,c.categorie FROM article a INNER JOIN user u ON u.id = a.id_user INNER JOIN article_cat c ON a.id_cat_id = c.id";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Article(rs.getInt("a.id"), rs.getString("a.titre"), rs.getString("a.text"), rs.getString("a.image"), rs.getDate("a.date_ajout"),rs.getInt("a.etat_ajout"),rs.getInt("id_user"),rs.getString("u.prenom")
                        ,rs.getString("u.nom"),rs.getString("u.image"),rs.getString("c.categorie")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        return list;
    }
    
    public void ValidArticle(Article t) {
    }
    
    public List<Article> MesArticle() {
        List<Article> listMesArticle = new ArrayList<>();
         try {
            String querry = "select u.nom,u.image,u.prenom,a.*,c.categorie FROM article a INNER JOIN user u ON u.id = a.id_user INNER JOIN article_cat c ON a.id_cat_id = c.id where a.id_user="+US.getIdUser();
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listMesArticle.add(new Article(rs.getInt("a.id"), rs.getString("a.titre"), rs.getString("a.text"), rs.getString("a.image"), rs.getDate("a.date_ajout"),rs.getInt("a.etat_ajout"),rs.getInt("id_user"),rs.getString("u.prenom")
                        ,rs.getString("u.nom"),rs.getString("u.image"),rs.getString("c.categorie")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
        return listMesArticle;
    }
    
    
  
    public List<Article> DetailsArticle() {
        List<Article> DetailsArticle = new ArrayList<>();
         try {
            String querry = "SELECT * FROM article";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                DetailsArticle.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),rs.getInt(6),rs.getInt("id_user"), rs.getInt(8)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
        return DetailsArticle;
    }
    
    public List<Article> ArticleRecom() {
        List<Article> ArticleRecom = new ArrayList<>();
         try {
            String querry = "SELECT u.nom,u.image as imageuser,u.prenom,a.*,c.categorie\n" +
"        FROM article a\n" +
"        INNER JOIN user u ON u.id = a.id_user\n" +
"        INNER JOIN article_cat c ON a.id_cat_id = c.id\n" +
"        where a.etat_ajout=1 AND a.id_cat_id=(SELECT MAX(`id_cat_id`) FROM article \n" +
"GROUP BY id_user\n" +
"HAVING id_user=1) AND a.id_user!=1";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                ArticleRecom.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),rs.getInt(6),rs.getInt("id_user"), rs.getInt(8)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
        return ArticleRecom;
    }
    
    public List<Article> AutreArticle() {
        List<Article> AutreArticle = new ArrayList<>();
         try {
            String querry = "SELECT u.nom,u.image as imageuser,u.prenom,a.*,c.categorie\n" +
"        FROM article a\n" +
"        INNER JOIN user u ON u.id = a.id_user\n" +
"        INNER JOIN article_cat c ON a.id_cat_id = c.id\n" +
"        where (a.id!='6') AND (a.id_user=(SELECT id_user FROM article WHERE id='6')) \n" +
"        LIMIT 2";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                AutreArticle.add(new Article(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getDate(5),rs.getInt(6),rs.getInt("i"), rs.getInt(8)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
        return AutreArticle;
    }
    
    public List<Reagit> ReagitList() {
        List<Reagit> Reagit = new ArrayList<>();
        try {
            String querry = "SELECT * FROM reagit";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Reagit.add(new Reagit(rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getInt(4)));
                
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return Reagit;
        
    }
    
    public void AjoutReajit(Reagit t) {
        try{
            List<Reagit> Reagit = new ArrayList<>();
            String querryreagit = "SELECT * FROM reagit WHERE id_user_id=? and id_art_id=?";
            PreparedStatement stms = con.prepareStatement(querryreagit);
            stms.setInt(1,1);
                stms.setInt(2, t.getIdArtId());
            ResultSet rs = stms.executeQuery();
            while (rs.next()) {
                Reagit.add(new Reagit(rs.getInt("id"),rs.getInt("id_user_id"),rs.getInt("id_art_id"),rs.getInt("type_react")));
                
            }
            
            // TEST AJOUT LIKE            
            if(Reagit.size()==0)
            {
                String querry = "INSERT INTO reagit VALUES (null,?,?,?)";
                PreparedStatement stm = con.prepareStatement(querry);
                stm.setInt(1, t.getIdUserId());
                stm.setInt(2, t.getIdArtId());
                stm.setInt(3, t.getTypeReact());
                stm.executeUpdate();
                stm.executeUpdate(querry);
                System.err.println("Votre réagit à été ajouter !");
            }
            
            // TEST MOD LIKE          
            if(Reagit.size()!=0)
            {
                int idUser=US.getIdUser();
                String querry = "UPDATE reagit SET type_react=? WHERE id_art_id=? and id_user_id=?";
                PreparedStatement stm = con.prepareStatement(querry);
                stm.setInt(1, t.getTypeReact());
                stm.setInt(2, t.getIdArtId());
                stm.setInt(3,idUser);
                
                stm.executeUpdate();
                stm.executeUpdate(querry);
                System.err.println("Votre réagit à été modifier !");
            }
            
            
           
           

           

        }
        catch(SQLException ex){
          
        }
       
    }
    
    public void supprimerReagit(Reagit t) {
       try {
            String requete = "DELETE FROM reagit WHERE id_art_id=? and id_user_id=?";
            PreparedStatement stm = con.prepareStatement(requete);
            stm.setInt(1, t.getId());
            stm.setInt(2, 1);
            stm.executeUpdate();
            System.err.println("Votre réagit à été supprimée !");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }    
    
       public List<Article> ArticlePrefere() {
        List<Article> listMesArticle = new ArrayList<>();
         try {
            int idUser=1;
            String querry = "SELECT DISTINCT u.nom,u.image as imageuser,u.prenom,u.nom,a.*,c.categorie\n" +
"        FROM reagit r,article a\n" +
"        INNER JOIN user u ON u.id = a.id_user \n" +
"        INNER JOIN article_cat c ON a.id_cat_id = c.id\n" +
"        where r.id_user_id = "+idUser+" AND r.id_art_id=a.id";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                listMesArticle.add(new Article(rs.getInt("a.id"), rs.getString("a.titre"), rs.getString("a.text"), rs.getString("a.image"), rs.getDate("a.date_ajout"),rs.getInt("a.etat_ajout"),rs.getInt("id_user"),rs.getString("u.prenom")
                        ,rs.getString("u.nom"),rs.getString("imageuser"),rs.getString("c.categorie")));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
        return listMesArticle;
    }
    
    public  void RecomArtice(Article t) {
        List<Article> list = new ArrayList<>();
        try {
            int idUser=1;
            String querry = "SELECT u.nom,u.image as imageuser,u.prenom,a.*,c.categorie\n" +
"        FROM article a \n" +
"        INNER JOIN user u ON u.id = a.id_user\n" +
"        INNER JOIN article_cat c ON a.id_cat_id = c.id\n" +
"        where a.id_cat_id=(SELECT id_cat_id FROM article WHERE id='?') AND etat_ajout=1   ";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setInt(1, t.getId());
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new Article(0, rs.getString("titre"), rs.getString("text"), rs.getString("image"),rs.getDate("a.date_ajout"), 0, 0, rs.getString("u.prenom"), rs.getString("u.nom"),rs.getString("u.image"), rs.getString("c.categorie")));
            }
            
            list.forEach((Article)->{
                System.out.println(list.get(2));
            });

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

        
    }  
    
      public List<Reagit> TopArticles() {
        List<Reagit> TopArticles = new ArrayList<>();
         try {
            String querry = "SELECT * from reagit ";
            PreparedStatement stm = con.prepareStatement(querry);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                TopArticles.add(new Reagit(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getInt(4)));
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
         
        return TopArticles;
    }
    
    
        public void VerifArticle(Article t) {
        try{
            
        String querry = "UPDATE article SET etat_ajout=1 WHERE id=?";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setInt(1, t.getId());
            
            stm.executeUpdate();
            System.out.println("Votre Articleà été modifiée !");
        }
        catch(SQLException ex){
            System.err.println(ex.getMessage());
        }       
    }
        
        public void ajouterRaitin(Rating t) {
        try{
            Number number=0;
            int idRating=0;
             List<Rating> RatingL = new ArrayList<>();
            String querryraiting = "SELECT * FROM rating WHERE id_user=? and id_article=?";
            PreparedStatement stms = con.prepareStatement(querryraiting);
            stms.setInt(1,t.getIdUser());
                stms.setInt(2, t.getIdArticle());
            ResultSet rs = stms.executeQuery();
            while (rs.next()) {
                number= (Number) rs.getInt("number");
                RatingL.add(new Rating(rs.getInt("id"),rs.getInt("id_user"),rs.getInt("id_article"),number));
                idRating =rs.getInt("id");
                
            }
            
            // TEST AJOUT LIKE            
            if(RatingL.size()==0)
            {
                String querry = "INSERT INTO rating VALUES (null,?,?,"+ t.getNumber()+")";
            PreparedStatement stm = con.prepareStatement(querry);
            stm.setInt(1, t.getIdUser());
            stm.setInt(2, t.getIdArticle());
            stm.executeUpdate();
            stm.executeUpdate(querry);
            }
            
            if (RatingL.size()>0){
                int idUser=US.getIdUser();
                Number nbrtest=(Number) t.getNumber();
              
                    System.out.println("---"+nbrtest+"-----"+number);
                String querry = "UPDATE rating SET number="+ t.getNumber()+" WHERE id=? ";
                PreparedStatement stm = con.prepareStatement(querry);
                stm.setInt(1,idRating);
                
           stm.executeUpdate();
                System.err.println("Votre réagit à été modifier !");
                 
               
            }
            
            } catch (SQLException ex) {
            Logger.getLogger(ServiceArticle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
            
            
           
    }
        
    
}
