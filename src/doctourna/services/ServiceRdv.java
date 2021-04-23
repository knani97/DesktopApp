/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.services;

import doctourna.models.Calendrier;
import doctourna.models.Rdv;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author mouhe
 */
public class ServiceRdv implements IService<Rdv> {
    Connection connection = DataSource.getInstance().getConnection();
    ServiceUser su = new ServiceUser();
    ServiceCalendrier sc = new ServiceCalendrier();
    ServiceTache st = new ServiceTache();

    @Override
    public void ajouter(Rdv t) {
        String query = "INSERT INTO rdv(tache_dispo_id,patient_id,medecin_id,tache_user_id,date,etat,description,jointure) "
                    + "VALUES(?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getTacheDispoId().getId());
            ps.setInt(2, t.getPatientId().getId());
            ps.setInt(3, t.getMedecinId().getId());
            ps.setInt(4, t.getTacheUserId().getId());
            ps.setString(5, t.getDate().toString());
            ps.setInt(6, t.getEtat());
            ps.setString(7, t.getDescription());
            ps.setString(8, t.getJointure());
            ps.executeUpdate();
            System.out.println("RDV ajouté avec succés.");
            
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    t.setId(generatedKeys.getInt(1));
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Rdv t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void modifier(Rdv t) {
        String query = "UPDATE rdv SET "
                    + "tache_dispo_id=?,patient_id=?,medecin_id=?,tache_user_id=?,date=?,etat=?,description=?,jointure=? WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, t.getTacheDispoId().getId());
            ps.setInt(2, t.getPatientId().getId());
            ps.setInt(3, t.getMedecinId().getId());
            ps.setInt(4, t.getTacheUserId().getId());
            ps.setString(5, t.getDate().toString());
            ps.setInt(6, t.getEtat());
            ps.setString(7, t.getDescription());
            ps.setString(8, t.getJointure());
            ps.setInt(9, t.getId());
            ps.executeUpdate();
            System.out.println("Tâche modifiée avec succés.");
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Rdv> afficher() {
        List<Rdv> list = new ArrayList<Rdv>();
        String query = "SELECT * FROM rdv";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                list.add(new Rdv(
                        rs.getInt("id"),
                        rs.getObject("date", Timestamp.class),
                        rs.getInt("etat"),
                        rs.getString("description"),
                        rs.getString("jointure"),
                        st.find(rs.getInt("tache_dispo_id")),
                        su.find(rs.getInt("medecin_id")),
                        su.find(rs.getInt("patient_id")),
                        st.find(rs.getInt("tache_user_id"))
                        ));
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }
    
    public void prendre(Rdv rdv) {
        Tache tache = new Tache(
                null, 
                sc.findByUid(rdv.getPatientId().getId()), 
                "RDV", 
                "RDV avec Dr. " + rdv.getMedecinId().getNom() + " " + rdv.getMedecinId().getPrenom(), 
                "1", 
                "#FF0000", 
                rdv.getDate(), 
                rdv.getTacheDispoId().getDuree()
        );
        st.ajouter(tache);
        rdv.setTacheUserId(tache);
        rdv.getTacheDispoId().setCouleur("#FF0000");
        rdv.getTacheDispoId().setType("1");
        rdv.getTacheDispoId().setLibelle("RDV");
        rdv.getTacheDispoId().setDescription("RDV avec " + rdv.getPatientId().getNom() + " " + rdv.getPatientId().getPrenom());
        st.modifier(rdv.getTacheDispoId());
        ajouter(rdv);
        System.out.println("RDV pris.");
    }
    
    public void annuler(Rdv rdv) {
        rdv.setEtat(3);
        modifier(rdv);
        rdv.getTacheDispoId().setType("4");
        rdv.getTacheDispoId().setCouleur("#00FF00");
        rdv.getTacheUserId().setType("4");
        rdv.getTacheUserId().setCouleur("#00FF00");
        st.modifier(rdv.getTacheDispoId());
        st.modifier(rdv.getTacheUserId());
        System.out.println("RDV annulé.");
    }
    
    public void reporter(Rdv rdv, Timestamp date) {
        rdv.setEtat(2);
        rdv.setDate(date);
        rdv.getTacheDispoId().setType("4");
        Tache rdv2 = new Tache(
                null, 
                sc.findByUid(rdv.getMedecinId().getId()), 
                "RDV", 
                "RDV avec " + rdv.getPatientId().getNom() + " " + rdv.getPatientId().getPrenom(), 
                "1",
                "#FF0000",
                date,
                rdv.getTacheDispoId().getDuree()
        );
        st.ajouter(rdv2);
        rdv.setTacheDispoId(rdv2);
        st.modifier(rdv.getTacheDispoId());
        modifier(rdv);
        System.out.println("RDV reporté.");
    }
    
    public void terminer(Rdv rdv) {
        rdv.setEtat(4);
        modifier(rdv);
        System.out.println("RDV terminé.");
    }
    
    public Rdv find(int id) {
        List<Rdv> list = new ArrayList<Rdv>();
        String query = "SELECT * FROM rdv WHERE id=?";
        
        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                return new Rdv(
                        rs.getInt("id"),
                        rs.getObject("date", Timestamp.class),
                        rs.getInt("etat"),
                        rs.getString("description"),
                        rs.getString("jointure"),
                        st.find(rs.getInt("tache_dispo_id")),
                        su.find(rs.getInt("medecin_id")),
                        su.find(rs.getInt("patient_id")),
                        st.find(rs.getInt("tache_user_id"))
                        );
            }
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
    
    public List<Rdv> findByUid(int uid) {
        return afficher().stream().filter(r -> r.getPatientId().getId() == uid || r.getMedecinId().getId() == uid).collect(Collectors.toList());
    }
    
    public List<Rdv> rechercher(int patient_id, int medecin_id) {
        return afficher().stream().filter(r -> r.getPatientId().getId() == patient_id && r.getMedecinId().getId() == medecin_id).collect(Collectors.toList());
    }
    
    public List<Rdv> rechercherNom(String nom) {
        return afficher().stream().filter(r -> r.getPatientId().getNom().contains(nom) || r.getMedecinId().getNom().contains(nom)).collect(Collectors.toList());
    }
    
    public List<Rdv> triDate(int uid) {
        return findByUid(uid).stream().sorted((r1, r2) -> r1.getDate().before(r2.getDate()) ? 1 : -1).collect(Collectors.toList());
    }
}
