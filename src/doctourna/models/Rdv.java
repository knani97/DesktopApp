/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import java.sql.Timestamp;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "rdv")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rdv.findAll", query = "SELECT r FROM Rdv r"),
    @NamedQuery(name = "Rdv.findById", query = "SELECT r FROM Rdv r WHERE r.id = :id"),
    @NamedQuery(name = "Rdv.findByDate", query = "SELECT r FROM Rdv r WHERE r.date = :date"),
    @NamedQuery(name = "Rdv.findByEtat", query = "SELECT r FROM Rdv r WHERE r.etat = :etat"),
    @NamedQuery(name = "Rdv.findByDescription", query = "SELECT r FROM Rdv r WHERE r.description = :description"),
    @NamedQuery(name = "Rdv.findByJointure", query = "SELECT r FROM Rdv r WHERE r.jointure = :jointure")})
public class Rdv implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp date;
    @Basic(optional = false)
    @Column(name = "etat")
    private int etat;
    @Column(name = "description")
    private String description;
    @Column(name = "jointure")
    private String jointure;
    @JoinColumn(name = "tache_dispo_id", referencedColumnName = "id")
    @OneToOne(optional = false)
    private Tache tacheDispoId;
    @JoinColumn(name = "medecin_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private User medecinId;
    @JoinColumn(name = "patient_id", referencedColumnName = "id")
    @ManyToOne
    private User patientId;
    @JoinColumn(name = "tache_user_id", referencedColumnName = "id")
    @OneToOne
    private Tache tacheUserId;

    public Rdv() {
    }

    public Rdv(Integer id) {
        this.id = id;
    }

    public Rdv(Integer id, Timestamp date, int etat) {
        this.id = id;
        this.date = date;
        this.etat = etat;
    }

    public Rdv(Integer id, Timestamp date, int etat, String description, String jointure, Tache tacheDispoId, User medecinId, User patientId, Tache tacheUserId) {
        this.id = id;
        this.date = date;
        this.etat = etat;
        this.description = description;
        this.jointure = jointure;
        this.tacheDispoId = tacheDispoId;
        this.medecinId = medecinId;
        this.patientId = patientId;
        this.tacheUserId = tacheUserId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJointure() {
        return jointure;
    }

    public void setJointure(String jointure) {
        this.jointure = jointure;
    }

    public Tache getTacheDispoId() {
        return tacheDispoId;
    }

    public void setTacheDispoId(Tache tacheDispoId) {
        this.tacheDispoId = tacheDispoId;
    }

    public User getMedecinId() {
        return medecinId;
    }

    public void setMedecinId(User medecinId) {
        this.medecinId = medecinId;
    }

    public User getPatientId() {
        return patientId;
    }

    public void setPatientId(User patientId) {
        this.patientId = patientId;
    }

    public Tache getTacheUserId() {
        return tacheUserId;
    }

    public void setTacheUserId(Tache tacheUserId) {
        this.tacheUserId = tacheUserId;
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
        if (!(object instanceof Rdv)) {
            return false;
        }
        Rdv other = (Rdv) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Rdv[ id=" + id + " ]";
    }
    
}
