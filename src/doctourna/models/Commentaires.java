/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "commentaires")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Commentaires.findAll", query = "SELECT c FROM Commentaires c"),
    @NamedQuery(name = "Commentaires.findById", query = "SELECT c FROM Commentaires c WHERE c.id = :id"),
    @NamedQuery(name = "Commentaires.findByText", query = "SELECT c FROM Commentaires c WHERE c.text = :text"),
    @NamedQuery(name = "Commentaires.findByDateAjout", query = "SELECT c FROM Commentaires c WHERE c.dateAjout = :dateAjout")})
public class Commentaires implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "text")
    private String text;
    @Basic(optional = false)
    @Column(name = "date_ajout")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateAjout;
    @JoinColumn(name = "id_user_id", referencedColumnName = "id")
    @ManyToOne
    private User idUserId;
    @JoinColumn(name = "id_art_id", referencedColumnName = "id")
    @ManyToOne
    private Article idArtId;

    public Commentaires() {
    }

    public Commentaires(Integer id) {
        this.id = id;
    }

    public Commentaires(Integer id, String text, Date dateAjout) {
        this.id = id;
        this.text = text;
        this.dateAjout = dateAjout;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(Date dateAjout) {
        this.dateAjout = dateAjout;
    }

    public User getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(User idUserId) {
        this.idUserId = idUserId;
    }

    public Article getIdArtId() {
        return idArtId;
    }

    public void setIdArtId(Article idArtId) {
        this.idArtId = idArtId;
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
        if (!(object instanceof Commentaires)) {
            return false;
        }
        Commentaires other = (Commentaires) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Commentaires[ id=" + id + " ]";
    }
    
}
