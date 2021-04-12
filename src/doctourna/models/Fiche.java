/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "fiche")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Fiche.findAll", query = "SELECT f FROM Fiche f"),
    @NamedQuery(name = "Fiche.findById", query = "SELECT f FROM Fiche f WHERE f.id = :id"),
    @NamedQuery(name = "Fiche.findByNomCommerciale", query = "SELECT f FROM Fiche f WHERE f.nomCommerciale = :nomCommerciale"),
    @NamedQuery(name = "Fiche.findByDosage", query = "SELECT f FROM Fiche f WHERE f.dosage = :dosage"),
    @NamedQuery(name = "Fiche.findByUtilisation", query = "SELECT f FROM Fiche f WHERE f.utilisation = :utilisation")})
public class Fiche implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom_commerciale")
    private String nomCommerciale;
    @Column(name = "dosage")
    private Integer dosage;
    @Column(name = "utilisation")
    private String utilisation;
    @JoinColumn(name = "id_med_id", referencedColumnName = "id")
    @OneToOne
    private Medicament idMedId;

    public Fiche() {
    }

    public Fiche(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNomCommerciale() {
        return nomCommerciale;
    }

    public void setNomCommerciale(String nomCommerciale) {
        this.nomCommerciale = nomCommerciale;
    }

    public Integer getDosage() {
        return dosage;
    }

    public void setDosage(Integer dosage) {
        this.dosage = dosage;
    }

    public String getUtilisation() {
        return utilisation;
    }

    public void setUtilisation(String utilisation) {
        this.utilisation = utilisation;
    }

    public Medicament getIdMedId() {
        return idMedId;
    }

    public void setIdMedId(Medicament idMedId) {
        this.idMedId = idMedId;
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
        if (!(object instanceof Fiche)) {
            return false;
        }
        Fiche other = (Fiche) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Fiche[ id=" + id + " ]";
    }
    
}
