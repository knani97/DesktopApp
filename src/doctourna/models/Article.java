/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "article")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Article.findAll", query = "SELECT a FROM Article a"),
    @NamedQuery(name = "Article.findById", query = "SELECT a FROM Article a WHERE a.id = :id"),
    @NamedQuery(name = "Article.findByTitre", query = "SELECT a FROM Article a WHERE a.titre = :titre"),
    @NamedQuery(name = "Article.findByText", query = "SELECT a FROM Article a WHERE a.text = :text"),
    @NamedQuery(name = "Article.findByImage", query = "SELECT a FROM Article a WHERE a.image = :image"),
    @NamedQuery(name = "Article.findByDateAjout", query = "SELECT a FROM Article a WHERE a.dateAjout = :dateAjout"),
    @NamedQuery(name = "Article.findByEtatAjout", query = "SELECT a FROM Article a WHERE a.etatAjout = :etatAjout"),
    @NamedQuery(name = "Article.findByIdUser", query = "SELECT a FROM Article a WHERE a.idUser = :idUser")})
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "titre")
    private String titre;
    @Basic(optional = false)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @Column(name = "image")
    private String image;
    @Basic(optional = false)
    @Column(name = "date_ajout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAjout;
    @Basic(optional = false)
    @Column(name = "etat_ajout")
    private int etatAjout;
    @Basic(optional = false)
    @Column(name = "id_user")
    private int idUser;
    @OneToMany(mappedBy = "idArtId")
    private Collection<Reagit> reagitCollection;
    @JoinColumn(name = "id_cat_id", referencedColumnName = "id")
    @ManyToOne
    private ArticleCat idCatId;
    @OneToMany(mappedBy = "idArtId")
    private Collection<Commentaires> commentairesCollection;

    public Article() {
    }

    public Article(Integer id) {
        this.id = id;
    }

    public Article(Integer id, String titre, String text, String image, Date dateAjout, int etatAjout, int idUser) {
        this.id = id;
        this.titre = titre;
        this.text = text;
        this.image = image;
        this.dateAjout = dateAjout;
        this.etatAjout = etatAjout;
        this.idUser = idUser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public int getEtatAjout() {
        return etatAjout;
    }

    public void setEtatAjout(int etatAjout) {
        this.etatAjout = etatAjout;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    @XmlTransient
    public Collection<Reagit> getReagitCollection() {
        return reagitCollection;
    }

    public void setReagitCollection(Collection<Reagit> reagitCollection) {
        this.reagitCollection = reagitCollection;
    }

    public ArticleCat getIdCatId() {
        return idCatId;
    }

    public void setIdCatId(ArticleCat idCatId) {
        this.idCatId = idCatId;
    }

    @XmlTransient
    public Collection<Commentaires> getCommentairesCollection() {
        return commentairesCollection;
    }

    public void setCommentairesCollection(Collection<Commentaires> commentairesCollection) {
        this.commentairesCollection = commentairesCollection;
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
        if (!(object instanceof Article)) {
            return false;
        }
        Article other = (Article) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Article[ id=" + id + " ]";
    }
    
}
