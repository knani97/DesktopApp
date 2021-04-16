/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.services;

import doctourna.models.Calendrier;
import doctourna.models.Disponibilite;
import doctourna.models.Tache;
import doctourna.utils.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import static org.eclipse.persistence.jpa.jpql.parser.Expression.SQL;

/**
 *
 * @author mouhe
 */
public class ServiceTache implements IService<Tache> {

    Connection connection = DataSource.getInstance().getConnection();
    ServiceCalendrier sc = new ServiceCalendrier();

    @Override
    public void ajouter(Tache tache) {
        String query = "INSERT INTO tache(calendrier,libelle,description,type,couleur,date,duree) "
                + "VALUES(?,?,?,?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, tache.getCalendrier().getId());
            ps.setString(2, tache.getLibelle());
            ps.setString(3, tache.getDescription());
            ps.setString(4, tache.getType());
            ps.setString(5, tache.getCouleur());
            ps.setObject(6, tache.getDate());
            ps.setObject(7, tache.getDuree());
            ps.executeUpdate();
            System.out.println("Tâche ajoutée avec succés.");

            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    tache.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Création de tâche échoué, pas de ID obtenu.");
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void supprimer(Tache tache) {
        String query = "DELETE FROM tache "
                + "WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tache.getId());
            ps.executeUpdate();
            System.out.println("Tâche supprimée avec succés.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Tache tache) {
        String query = "UPDATE tache SET "
                + "calendrier=?,libelle=?,description=?,type=?,couleur=?,date=?,duree=? WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, tache.getCalendrier().getId());
            ps.setString(2, tache.getLibelle());
            ps.setString(3, tache.getDescription());
            ps.setString(4, tache.getType());
            ps.setString(5, tache.getCouleur());
            ps.setObject(6, tache.getDate());
            ps.setObject(7, tache.getDuree());
            ps.setObject(8, tache.getId());
            ps.executeUpdate();
            System.out.println("Tâche modifiée avec succés.");
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public List<Tache> afficher() {
        List<Tache> list = new ArrayList<Tache>();
        String query = "SELECT * FROM tache";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Tache(
                        rs.getInt("id"),
                        sc.find(rs.getInt("calendrier")),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("couleur"),
                        rs.getObject("date", Timestamp.class),
                        rs.getObject("duree", Time.class)
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public Tache find(int id) {
        List<Tache> list = new ArrayList<Tache>();
        String query = "SELECT * FROM tache WHERE id=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new Tache(
                        rs.getInt("id"),
                        sc.find(rs.getInt("calendrier")),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("couleur"),
                        rs.getObject("date", Timestamp.class),
                        rs.getObject("duree", Time.class)
                );
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public List<Tache> findByCalendrier(int calendrier) {
        List<Tache> list = new ArrayList<Tache>();
        String query = "SELECT * FROM tache WHERE calendrier=?";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, calendrier);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Tache(
                        rs.getInt("id"),
                        sc.find(rs.getInt("calendrier")),
                        rs.getString("libelle"),
                        rs.getString("description"),
                        rs.getString("type"),
                        rs.getString("couleur"),
                        rs.getObject("date", Timestamp.class),
                        rs.getObject("duree", Time.class)
                ));
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return list;
    }

    public List<Tache> findDispos(int calendrier) {
        return findByCalendrier(calendrier).stream().filter(t -> t.getType().contains("4")).collect(Collectors.toList());
    }

    public List<Tache> findByType(int calendrier, Integer type) {
        return findByCalendrier(calendrier).stream().filter(t -> t.getType().contains(type.toString())).collect(Collectors.toList());
    }

    public void ajouterDispos(Disponibilite dispo, int calendrier) {
        LocalDateTime t = dispo.getStartDate().toLocalDateTime();
        int i = 0;

        while (t.isBefore(dispo.getEndDate().toLocalDateTime())) {
            ajouter(new Tache(
                    null,
                    sc.find(calendrier),
                    "Disponibilité",
                    "Disponibilité",
                    "4",
                    "#00FF00",
                    Timestamp.valueOf(t),
                    dispo.getDureeRdv()
            ));
            t = t.plusHours(dispo.getDureeRdv().toLocalTime().getHour());
            t = t.plusMinutes(dispo.getDureeRdv().toLocalTime().getMinute());
            t = t.plusHours(dispo.getDureePause().toLocalTime().getHour());
            t = t.plusMinutes(dispo.getDureePause().toLocalTime().getMinute());
        }
        
        System.out.println("Disponibilités ajoutés.");
    }
}
