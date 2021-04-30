/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.console;

import static doctourna.console.Console.isDatetime;
import static doctourna.console.Console.toDate;
import doctourna.models.Calendrier;
import doctourna.models.Disponibilite;
import doctourna.models.Rdv;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.utils.DataSource;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import doctourna.utils.Emailer;
import doctourna.utils.Session;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

import static javafx.application.Application.launch;

/**
 *
 * @author mouhe
 */
public class MainProg {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException {
        DataSource dataSource = DataSource.getInstance();
        ServiceCalendrier sc = new ServiceCalendrier();
        ServiceTache st = new ServiceTache();
        ServiceRdv sr = new ServiceRdv();
        ServiceUser su = new ServiceUser();
        Scanner scanner = new Scanner(System.in);
        boolean pass = false;
        /*new ServiceRdv().prendre(new Rdv(
                null, 
                st.find(1).getDate(), 
                1, 
                "test", 
                "test2", 
                st.find(1), 
                new ServiceUser().find(2), 
                new ServiceUser().find(1), 
                null
        ));*/

 /*DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy H:m");
        Date date = dateFormat.parse("23/09/2007 10:10");
        long time = date.getTime();
        Date date2 = dateFormat.parse("24/09/2007 10:10");
        long time2 = date2.getTime();
        Timestamp a = new Timestamp(time);
        Timestamp b = new Timestamp(time2);
        Time rdv = Time.valueOf("01:00:00");
        Time duree = Time.valueOf("00:00:00");
        st.ajouterDispos(new Disponibilite(1, a, b, rdv, duree), sc.findByUid(uid).getId());*/
        System.out.println("Bienvenu dans DOCTOURNA !");
        System.out.println("Vous êtes?");
        System.out.println(Console.ANSI_GREEN + "1 - Patient");
        System.out.println(Console.ANSI_BLUE + "2 - Médecin");
        System.out.println(Console.ANSI_CYAN + "3 - Administrateur" + Console.ANSI_BLACK);
        do {
            switch (scanner.nextInt()) {
                case 1:
                    Session.setId(1);
                    Session.setType(1);
                    Session.setEmail("mouheb.benabdallah@esprit.tn");
                    System.out.println("Bienvenu Mr. le patient !");
                    System.out.println(Console.ANSI_PURPLE + "Veuillez choisir une option:" + Console.ANSI_BLACK);
                    System.out.println("1 - Consulter Calendrier");
                    System.out.println("2 - Prendre RDV");
                    do {
                        switch (scanner.nextInt()) {
                            case 1:
                                Console.consulterCalendrier(sc, st, Session.getId(), Session.getType(), scanner);
                                pass = true;
                                break;
                            case 2:
                                Console.getRdv(scanner, sr, su, Session.getId(), Session.getType());
                                pass = true;
                                break;
                            default:
                                System.out.println("Option invalide !");
                                pass = false;
                                break;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case 2:
                    Session.setId(2);
                    Session.setType(2);
                    Session.setEmail("mouhebbenabdallah@outlook.fr");
                    System.out.println("Bienvenu docteur !");
                    System.out.println(Console.ANSI_PURPLE + "Veuillez choisir une option:" + Console.ANSI_BLACK);
                    System.out.println("1 - Consulter Calendrier");
                    System.out.println("2 - Configurer vos disponibilités");
                    do {
                        switch (scanner.nextInt()) {
                            case 1:
                                Console.consulterCalendrier(sc, st, Session.getId(), Session.getType(), scanner);
                                pass = true;
                                break;
                            case 2:
                                System.out.println("Veuillez saisir les paramétres de votre disponibilité : ");
                                st.ajouterDispos(Console.getDisponibilite(scanner), sc.findByUid(Session.getId()).getId());
                                pass = true;
                                break;
                            default:
                                System.out.println("Option invalide !");
                                pass = false;
                                break;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case 3:
                    System.out.println("Bienvenu dans le dashboard !");
                    System.out.println(Console.ANSI_PURPLE + "Veuillez choisir une option:" + Console.ANSI_BLACK);
                    System.out.println("1 - Consulter les calendriers");
                    System.out.println("2 - Consulter les RDVs");
                    do {
                        switch (scanner.nextInt()) {
                            case 1:
                                int calId = 0;
                                sc.afficher().forEach(c -> System.out.println(c.getId() + " - " + c.getUidId().getNom() + " " + c.getUidId().getPrenom() + " - " + st.findByCalendrier(c.getId()).size() + " tâches."));
                                System.out.println("\nVeuillez choisir une option:");
                                System.out.println("1 - Afficher les tâches d'un calendrier");
                                System.out.println("2 - Supprimer un calendrier");
                                System.out.println("3 - Rechercher un calendrier selon nom/prenom");
                                do {
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            System.out.println("Veuillez saisir l'id du calendrier : ");
                                            calId = scanner.nextInt();
                                            while (sc.find(calId) == null) {
                                                System.out.println("Id invalide.");
                                                scanner.nextInt();
                                            }
                                            System.out.println(st.findByCalendrier(calId));
                                            pass = true;
                                            break;
                                        case 2:
                                            System.out.println("Veuillez saisir l'id du calendrier : ");
                                            calId = scanner.nextInt();
                                            while (sc.find(calId) == null) {
                                                System.out.println("Id invalide.");
                                                scanner.nextInt();
                                            }
                                            if (Console.warning("Voulez-vraiment supprimer le calendrier de " + sc.find(calId).getUidId() + " et tous ses tâches ?", scanner)) {
                                                sc.supprimer(sc.find(calId));
                                            }
                                            pass = true;
                                            break;
                                        case 3:
                                            System.out.println("Entrer un mot : ");
                                            scanner.nextLine();
                                            String mot = scanner.nextLine();
                                            System.out.println("Résultats : ");
                                            sc.findByUsername(mot).stream().forEach(c -> System.out.println(c.getUidId().getId() + " " + c.getUidId().getNom() + " " + c.getUidId().getPrenom() + " - " + st.findByCalendrier(c.getId()).size() + " tâches"));
                                            pass = true;
                                            break;
                                        default:
                                            System.out.println("Option invalide !");
                                            pass = false;
                                            break;
                                    }
                                } while (!pass);
                                pass = true;
                                break;
                            case 2:
                                int rdvId = 0;
                                sr.afficher().forEach(r -> System.out.println(r.getId() + " - " + r.getPatientId() + " avec Dr." + r.getMedecinId() + " le " + r.getDate() + " ETAT: " + Console.getEtat(r.getEtat())));
                                System.out.println("\nVeuillez choisir une option:");
                                System.out.println("1 - Annuler un RDV");
                                System.out.println("2 - Reporter un RDV");
                                System.out.println("3 - Afficher description d'un RDV");
                                do {
                                    switch (scanner.nextInt()) {
                                        case 1:
                                            System.out.println("Veuillez saisir l'id du RDV : ");
                                            rdvId = scanner.nextInt();
                                            while (sr.find(rdvId) == null) {
                                                System.out.println("Id invalide.");
                                                scanner.nextInt();
                                            }
                                            if (Console.warning("Voulez-vraiment annuler le RDV de " + sr.find(rdvId).getPatientId() + " avec Dr." + sr.find(rdvId).getMedecinId() + "?", scanner)) {
                                                sr.annuler(sr.find(rdvId));
                                            }
                                            pass = true;
                                            break;
                                        case 2:
                                            System.out.println("Veuillez saisir l'id du RDV : ");
                                            rdvId = scanner.nextInt();
                                            while (sr.find(rdvId) == null) {
                                                System.out.println("Id invalide.");
                                                scanner.nextInt();
                                            }
                                            System.out.print("Veuillez indiquez la date de votre tâche (Syntaxe: yyyy/mm/dd h:i) : ");
                                            String input = "";
                                            do {
                                                input = scanner.nextLine();
                                                input = scanner.nextLine();
                                                if (isDatetime(input)) {
                                                    sr.reporter(sr.find(rdvId), toDate(input));
                                                    pass = true;
                                                } else {
                                                    System.out.println("Syntaxe invalide !");
                                                    pass = false;
                                                }
                                            } while (!pass);
                                            pass = true;
                                            break;
                                        case 3:
                                            System.out.println("Veuillez saisir l'ID du RDV : ");
                                            rdvId = scanner.nextInt();
                                            while (sr.find(rdvId) == null) {
                                                System.out.println("ID invalide.");
                                                scanner.nextInt();
                                            }
                                            System.out.println(sr.find(rdvId).getDescription());
                                            pass = true;
                                            break;
                                        default:
                                            System.out.println("Option invalide !");
                                            pass = false;
                                            break;
                                    }
                                } while (!pass);
                                pass = true;
                                break;
                            default:
                                System.out.println("Option invalide !");
                                pass = false;
                                break;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                default:
                    System.out.println("Option invalide !");
                    pass = false;
                    break;
            }
        } while (!pass);
    }
}
