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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mouhe
 */
@Entity
@Table(name = "medicament")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Medicament.findAll", query = "SELECT m FROM Medicament m"),
    @NamedQuery(name = "Medicament.findById", query = "SELECT m FROM Medicament m WHERE m.id = :id"),
    @NamedQuery(name = "Medicament.findByNom", query = "SELECT m FROM Medicament m WHERE m.nom = :nom"),
    @NamedQuery(name = "Medicament.findByFournisseur", query = "SELECT m FROM Medicament m WHERE m.fournisseur = :fournisseur"),
    @NamedQuery(name = "Medicament.findByPrixAchat", query = "SELECT m FROM Medicament m WHERE m.prixAchat = :prixAchat"),
    @NamedQuery(name = "Medicament.findByPoid", query = "SELECT m FROM Medicament m WHERE m.poid = :poid"),
    @NamedQuery(name = "Medicament.findByFicheExist", query = "SELECT m FROM Medicament m WHERE m.ficheExist = :ficheExist"),
    @NamedQuery(name = "Medicament.findByImg", query = "SELECT m FROM Medicament m WHERE m.img = :img")})
public class Medicament implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nom")
    private String nom;
    @Column(name = "fournisseur")
    private String fournisseur;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "prix_achat")
    private Double prixAchat;
    @Column(name = "poid")
    private Double poid;
    @Column(name = "fiche_exist")
    private Boolean ficheExist;
    @Column(name = "img")
    private String img;
    @OneToOne(mappedBy = "idMedId")
    private Fiche fiche;

    public Medicament() {
    }

    public Medicament(Integer id) {
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

    public String getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(String fournisseur) {
        this.fournisseur = fournisseur;
    }

    public Double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(Double prixAchat) {
        this.prixAchat = prixAchat;
    }

    public Double getPoid() {
        return poid;
    }

    public void setPoid(Double poid) {
        this.poid = poid;
    }

    public Boolean getFicheExist() {
        return ficheExist;
    }

    public void setFicheExist(Boolean ficheExist) {
        this.ficheExist = ficheExist;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public Fiche getFiche() {
        return fiche;
    }

    public void setFiche(Fiche fiche) {
        this.fiche = fiche;
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
        if (!(object instanceof Medicament)) {
            return false;
        }
        Medicament other = (Medicament) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "doctourna.models.Medicament[ id=" + id + " ]";
    }
    
}
