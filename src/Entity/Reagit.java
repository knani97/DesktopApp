/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entity;

import java.io.Serializable;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Lenovo
 */
@Entity
@Table(name = "reagit")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Reagit.findAll", query = "SELECT r FROM Reagit r")
    , @NamedQuery(name = "Reagit.findById", query = "SELECT r FROM Reagit r WHERE r.id = :id")
    , @NamedQuery(name = "Reagit.findByTypeReact", query = "SELECT r FROM Reagit r WHERE r.typeReact = :typeReact")})
public class Reagit implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    private int typeReact;
    private int idUserId;
    private int idArtId;

    public Reagit() {
    }

    public Reagit(Integer id) {
        this.id = id;
    }

    public Reagit(Integer id, int typeReact) {
        this.id = id;
        this.typeReact = typeReact;
    }

    public Reagit(int id, int idUserId, int idArtId,int typeReact) {
        this.id = id;
        this.idUserId = idUserId;
        this.idArtId = idArtId;
        this.typeReact = typeReact;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTypeReact() {
        return typeReact;
    }

    public void setTypeReact(int typeReact) {
        this.typeReact = typeReact;
    }

    public int getIdUserId() {
        return idUserId;
    }

    public void setIdUserId(int idUserId) {
        this.idUserId = idUserId;
    }

    public int getIdArtId() {
        return idArtId;
    }

    public void setIdArtId(int idArtId) {
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
        if (!(object instanceof Reagit)) {
            return false;
        }
        Reagit other = (Reagit) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "- id=" + id + "-";
    }
    
}
