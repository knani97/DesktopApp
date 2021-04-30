/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "disponibilite")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Disponibilite.findAll", query = "SELECT d FROM Disponibilite d"),
    @NamedQuery(name = "Disponibilite.findById", query = "SELECT d FROM Disponibilite d WHERE d.id = :id"),
    @NamedQuery(name = "Disponibilite.findByStartDate", query = "SELECT d FROM Disponibilite d WHERE d.startDate = :startDate"),
    @NamedQuery(name = "Disponibilite.findByEndDate", query = "SELECT d FROM Disponibilite d WHERE d.endDate = :endDate"),
    @NamedQuery(name = "Disponibilite.findByDureeRdv", query = "SELECT d FROM Disponibilite d WHERE d.dureeRdv = :dureeRdv"),
    @NamedQuery(name = "Disponibilite.findByDureePause", query = "SELECT d FROM Disponibilite d WHERE d.dureePause = :dureePause")})
public class Disponibilite implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "start_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp startDate;
    @Basic(optional = false)
    @Column(name = "end_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp endDate;
    @Basic(optional = false)
    @Column(name = "duree_rdv")
    @Temporal(TemporalType.TIME)
    private Time dureeRdv;
    @Column(name = "duree_pause")
    @Temporal(TemporalType.TIME)
    private Time dureePause;

    public Disponibilite() {
    }

    public Disponibilite(Integer id) {
        this.id = id;
    }

    public Disponibilite(Integer id, Timestamp startDate, Timestamp endDate, Time dureeRdv, Time dureePause) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.dureeRdv = dureeRdv;
        this.dureePause = dureePause;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Time getDureeRdv() {
        return dureeRdv;
    }

    public void setDureeRdv(Time dureeRdv) {
        this.dureeRdv = dureeRdv;
    }

    public Time getDureePause() {
        return dureePause;
    }

    public void setDureePause(Time dureePause) {
        this.dureePause = dureePause;
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
        if (!(object instanceof Disponibilite)) {
            return false;
        }
        Disponibilite other = (Disponibilite) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Disponibilite[ id=" + id + " ]";
    }
    
}
