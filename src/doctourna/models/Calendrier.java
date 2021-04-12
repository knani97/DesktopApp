/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import java.util.Collection;
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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "calendrier")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Calendrier_1.findAll", query = "SELECT c FROM Calendrier_1 c"),
    @NamedQuery(name = "Calendrier_1.findById", query = "SELECT c FROM Calendrier_1 c WHERE c.id = :id"),
    @NamedQuery(name = "Calendrier_1.findByType", query = "SELECT c FROM Calendrier_1 c WHERE c.type = :type"),
    @NamedQuery(name = "Calendrier_1.findByEmail", query = "SELECT c FROM Calendrier_1 c WHERE c.email = :email"),
    @NamedQuery(name = "Calendrier_1.findByCouleur", query = "SELECT c FROM Calendrier_1 c WHERE c.couleur = :couleur"),
    @NamedQuery(name = "Calendrier_1.findByTimezone", query = "SELECT c FROM Calendrier_1 c WHERE c.timezone = :timezone"),
    @NamedQuery(name = "Calendrier_1.findByFormat", query = "SELECT c FROM Calendrier_1 c WHERE c.format = :format")})
public class Calendrier implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "type")
    private int type;
    @Basic(optional = false)
    @Column(name = "email")
    private boolean email;
    @Basic(optional = false)
    @Column(name = "couleur")
    private String couleur;
    @Basic(optional = false)
    @Column(name = "timezone")
    private String timezone;
    @Basic(optional = false)
    @Column(name = "format")
    private int format;
    @OneToMany(mappedBy = "calendrier")
    private Collection<Tache> tacheCollection;
    @JoinColumn(name = "uid_id", referencedColumnName = "id")
    @ManyToOne
    private User uidId;

    public Calendrier() {
    }

    public Calendrier(Integer id) {
        this.id = id;
    }

    public Calendrier(Integer id, int type, boolean email, String couleur, String timezone, int format) {
        this.id = id;
        this.type = type;
        this.email = email;
        this.couleur = couleur;
        this.timezone = timezone;
        this.format = format;
    }
    
    public Calendrier(Integer id, User uidId, int type, boolean email, String couleur, String timezone, int format) {
        this.id = id;
        this.uidId = uidId;
        this.type = type;
        this.email = email;
        this.couleur = couleur;
        this.timezone = timezone;
        this.format = format;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean getEmail() {
        return email;
    }

    public void setEmail(boolean email) {
        this.email = email;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public int getFormat() {
        return format;
    }

    public void setFormat(int format) {
        this.format = format;
    }

    @XmlTransient
    public Collection<Tache> getTacheCollection() {
        return tacheCollection;
    }

    public void setTacheCollection(Collection<Tache> tacheCollection) {
        this.tacheCollection = tacheCollection;
    }

    public User getUidId() {
        return uidId;
    }

    public void setUidId(User uidId) {
        this.uidId = uidId;
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
        if (!(object instanceof Calendrier)) {
            return false;
        }
        Calendrier other = (Calendrier) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Calendrier_1[ id=" + id + " ]";
    }
    
}
