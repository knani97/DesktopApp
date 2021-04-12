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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "pharmacie")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pharmacie.findAll", query = "SELECT p FROM Pharmacie p"),
    @NamedQuery(name = "Pharmacie.findById", query = "SELECT p FROM Pharmacie p WHERE p.id = :id"),
    @NamedQuery(name = "Pharmacie.findByNom", query = "SELECT p FROM Pharmacie p WHERE p.nom = :nom"),
    @NamedQuery(name = "Pharmacie.findByAdr", query = "SELECT p FROM Pharmacie p WHERE p.adr = :adr"),
    @NamedQuery(name = "Pharmacie.findByGouv", query = "SELECT p FROM Pharmacie p WHERE p.gouv = :gouv"),
    @NamedQuery(name = "Pharmacie.findByImgPat", query = "SELECT p FROM Pharmacie p WHERE p.imgPat = :imgPat"),
    @NamedQuery(name = "Pharmacie.findByNote", query = "SELECT p FROM Pharmacie p WHERE p.note = :note")})
public class Pharmacie implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "adr")
    private String adr;
    @Column(name = "gouv")
    private String gouv;
    @Column(name = "img_pat")
    private String imgPat;
    @Column(name = "note")
    private Integer note;

    public Pharmacie() {
    }

    public Pharmacie(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdr() {
        return adr;
    }

    public void setAdr(String adr) {
        this.adr = adr;
    }

    public String getGouv() {
        return gouv;
    }

    public void setGouv(String gouv) {
        this.gouv = gouv;
    }

    public String getImgPat() {
        return imgPat;
    }

    public void setImgPat(String imgPat) {
        this.imgPat = imgPat;
    }

    public Integer getNote() {
        return note;
    }

    public void setNote(Integer note) {
        this.note = note;
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
        if (!(object instanceof Pharmacie)) {
            return false;
        }
        Pharmacie other = (Pharmacie) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Pharmacie[ id=" + id + " ]";
    }
    
}
