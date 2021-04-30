/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.models;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
public class Tache {
    
    private Integer id;

    private String libelle;

    private String description;

    private String type;

    private String couleur;

    private Timestamp date;

    private Time duree;

    private Calendrier calendrier;

    private Rdv rdv;

    private Rdv rdv1;

    public Tache() {
    }

    public Tache(Integer id) {
        this.id = id;
    }

    public Tache(Integer id, String libelle, String description, String type, String couleur, Timestamp date, Time duree) {
        this.id = id;
        this.libelle = libelle;
        this.description = description;
        this.type = type;
        this.couleur = couleur;
        this.date = date;
        this.duree = duree;
    }
    
    public Tache(Integer id, Calendrier calendrier, String libelle, String description, String type, String couleur, Timestamp date, Time duree) {
        this.id = id;
        this.calendrier = calendrier;
        this.libelle = libelle;
        this.description = description;
        this.type = type;
        this.couleur = couleur;
        this.date = date;
        this.duree = duree;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public Time getDuree() {
        return duree;
    }

    public void setDuree(Time duree) {
        this.duree = duree;
    }

    public Calendrier getCalendrier() {
        return calendrier;
    }

    public void setCalendrier(Calendrier calendrier) {
        this.calendrier = calendrier;
    }

    public Rdv getRdv() {
        return rdv;
    }

    public void setRdv(Rdv rdv) {
        this.rdv = rdv;
    }

    public Rdv getRdv1() {
        return rdv1;
    }

    public void setRdv1(Rdv rdv1) {
        this.rdv1 = rdv1;
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
        if (!(object instanceof Tache)) {
            return false;
        }
        Tache other = (Tache) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return date.toString() + " " + libelle + " (" + type + ")";
    }
    
}
