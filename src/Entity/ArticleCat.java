/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import colors.ConsoleColors;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.metamodel.SingularAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import utils.DataBase;
import utils.Connexion;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "article_cat")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ArticleCat.findAll", query = "SELECT a FROM ArticleCat a")
    , @NamedQuery(name = "ArticleCat.findById", query = "SELECT a FROM ArticleCat a WHERE a.id = :id")
    , @NamedQuery(name = "ArticleCat.findByCategorie", query = "SELECT a FROM ArticleCat a WHERE a.categorie = :categorie")
    , @NamedQuery(name = "ArticleCat.findByImage", query = "SELECT a FROM ArticleCat a WHERE a.image = :image")})
public class ArticleCat implements Serializable {

    @OneToMany(mappedBy = "idCatId")
    private Collection<Article> articleCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "categorie")
    private String categorie;
    @Basic(optional = false)
    @Column(name = "image")
    private String image;

    public ArticleCat() {
    }

    public ArticleCat(Integer id) {
        this.id = id;
    }

    public ArticleCat(Integer id, String categorie, String image) {
        this.id = id;
        this.categorie = categorie;
        this.image = image;
    }

 

    public ArticleCat(String categorie,String image) {
        this.categorie = categorie;
        this.image = image;
    }

    ArticleCat(int idCatId) {
 this.id = idCatId;
    }

    public ArticleCat(int id, String categorie, String image,java.sql.Date dateAjout,int etatAjout) {
        Article article = new Article();
        article.setDateAjout(dateAjout);
        article.setEtatAjout(etatAjout);
        this.id = id;
        this.categorie = categorie;
        this.image = image;
    }

    public ArticleCat(String string) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ArticleCat)) {
            return false;
        }
        ArticleCat other = (ArticleCat) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return categorie;
    }

    @XmlTransient
    public Collection<Article> getArticleCollection() {
        return articleCollection;
    }

    public void setArticleCollection(Collection<Article> articleCollection) {
        this.articleCollection = articleCollection;
    }
    
}
