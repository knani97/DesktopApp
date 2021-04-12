/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "user")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
    @NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
    @NamedQuery(name = "User.findByImage", query = "SELECT u FROM User u WHERE u.image = :image"),
    @NamedQuery(name = "User.findByType", query = "SELECT u FROM User u WHERE u.type = :type"),
    @NamedQuery(name = "User.findByNom", query = "SELECT u FROM User u WHERE u.nom = :nom"),
    @NamedQuery(name = "User.findByPrenom", query = "SELECT u FROM User u WHERE u.prenom = :prenom"),
    @NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
    @NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
    @NamedQuery(name = "User.findByActivationToken", query = "SELECT u FROM User u WHERE u.activationToken = :activationToken"),
    @NamedQuery(name = "User.findByResetToken", query = "SELECT u FROM User u WHERE u.resetToken = :resetToken"),
    @NamedQuery(name = "User.findByArticles", query = "SELECT u FROM User u WHERE u.articles = :articles")})
public class User implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idUserId")
    private Collection<Reagit> reagitCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Post> postCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId")
    private Collection<Comment> commentCollection;
    @OneToMany(mappedBy = "idUserId")
    private Collection<Commentaires> commentairesCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "image")
    private String image;
    @Column(name = "type")
    private Integer type;
    @Basic(optional = false)
    @Column(name = "nom")
    private String nom;
    @Basic(optional = false)
    @Column(name = "prenom")
    private String prenom;
    @Column(name = "email")
    private String email;
    @Basic(optional = false)
    @Lob
    @Column(name = "roles")
    private String roles;
    @Basic(optional = false)
    @Column(name = "password")
    private String password;
    @Column(name = "activation_token")
    private String activationToken;
    @Column(name = "reset_token")
    private String resetToken;
    @Column(name = "articles")
    private String articles;
    @OneToMany(mappedBy = "uidId")
    private Collection<Calendrier> calendrierCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "medecinId")
    private Collection<Rdv> rdvCollection;
    @OneToMany(mappedBy = "patientId")
    private Collection<Rdv> rdvCollection1;
    @JoinColumn(name = "cv_id", referencedColumnName = "id")
    @OneToOne
    private Cv cvId;

    public User() {
    }

    public User(Integer id) {
        this.id = id;
    }

    public User(Integer id, String nom, String prenom, String roles, String password) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.roles = roles;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getActivationToken() {
        return activationToken;
    }

    public void setActivationToken(String activationToken) {
        this.activationToken = activationToken;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public String getArticles() {
        return articles;
    }

    public void setArticles(String articles) {
        this.articles = articles;
    }

    @XmlTransient
    public Collection<Calendrier> getCalendrierCollection() {
        return calendrierCollection;
    }

    public void setCalendrierCollection(Collection<Calendrier> calendrierCollection) {
        this.calendrierCollection = calendrierCollection;
    }

    @XmlTransient
    public Collection<Rdv> getRdvCollection() {
        return rdvCollection;
    }

    public void setRdvCollection(Collection<Rdv> rdvCollection) {
        this.rdvCollection = rdvCollection;
    }

    @XmlTransient
    public Collection<Rdv> getRdvCollection1() {
        return rdvCollection1;
    }

    public void setRdvCollection1(Collection<Rdv> rdvCollection1) {
        this.rdvCollection1 = rdvCollection1;
    }

    public Cv getCvId() {
        return cvId;
    }

    public void setCvId(Cv cvId) {
        this.cvId = cvId;
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
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.User[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<Reagit> getReagitCollection() {
        return reagitCollection;
    }

    public void setReagitCollection(Collection<Reagit> reagitCollection) {
        this.reagitCollection = reagitCollection;
    }

    @XmlTransient
    public Collection<Post> getPostCollection() {
        return postCollection;
    }

    public void setPostCollection(Collection<Post> postCollection) {
        this.postCollection = postCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @XmlTransient
    public Collection<Commentaires> getCommentairesCollection() {
        return commentairesCollection;
    }

    public void setCommentairesCollection(Collection<Commentaires> commentairesCollection) {
        this.commentairesCollection = commentairesCollection;
    }
    
}
