/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.console;

import doctourna.models.Calendrier;
import doctourna.models.Tache;
import doctourna.utils.DataSource;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceTache;
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
    public static void main(String[] args) {
        DataSource dataSource = DataSource.getInstance();
        ServiceCalendrier sc = new ServiceCalendrier();
        ServiceTache st = new ServiceTache();
        Scanner scanner= new Scanner(System.in);  
        boolean pass = false;
        int uid;
        int type;
        
        System.out.println("Bienvenu dans DOCTOURNA !");
        System.out.println("Vous êtes?");
        System.out.println("1 - Patient");
        System.out.println("2 - Médecin");
        do {
            switch (scanner.nextInt()) {
                case 1:
                    uid = 1;
                    type = 1;
                    System.out.println("Bienvenu Mr. le patient !");
                    System.out.println("Veuillez choisir une option:");
                    System.out.println("1 - Consulter Calendrier");
                    System.out.println("2 - Prendre RDV");
                    do {
                        switch (scanner.nextInt()) {
                            case 1:
                                if (sc.findByUid(uid) != null) {
                                    System.out.println("Veuillez choisir une option:");
                                    System.out.println("1 - Consulter les evenements de votre calendrier");
                                    System.out.println("2 - Modifier la calendrier");
                                    System.out.println("3 - Supprimer la calendrier");
                                    do {
                                        switch (scanner.nextInt()) {
                                            case 1:
                                                System.out.println("Vos tâches:");
                                                st.findByCalendrier(sc.findByUid(uid).getId()).stream().forEach(System.out::println);
                                                System.out.println("\nVeuillez choisir une option:");
                                                System.out.println("1 - Afficher la description complête d'une tâche");
                                                System.out.println("2 - Ajouter une tâche");
                                                System.out.println("3 - Modifier une tâche");
                                                System.out.println("4 - Supprimer une tâche");
                                                do {
                                                    switch (scanner.nextInt()) {
                                                        case 1:
                                                            System.out.print("Veuille indiquer l'id du tâche à consulter: ");
                                                            do {
                                                                Tache tache = st.find(scanner.nextInt());
                                                                if (tache != null) {
                                                                    Console.showTache(tache);
                                                                    pass = true;
                                                                }
                                                                else {
                                                                    System.out.println("Id invalide !");
                                                                    pass = false;
                                                                }
                                                            } while (!pass);
                                                            pass = true;
                                                            break;
                                                        case 2:
                                                            st.ajouter(Console.getTache(sc.findByUid(uid).getId(), scanner));
                                                            pass = true;
                                                            break;
                                                        case 3:
                                                            System.out.print("Veuille indiquer l'id du tâche à modifier: ");
                                                            do {
                                                                Tache tache = st.find(scanner.nextInt());
                                                                if (tache != null) {
                                                                    Console.showTache(tache);
                                                                    System.out.print("Veuillez choisir une caractéristique à modifier: ");
                                                                    st.modifier(Console.getModifTache(tache, scanner));
                                                                    pass = true;
                                                                }
                                                                else {
                                                                    System.out.println("Id invalide !");
                                                                    pass = false;
                                                                }
                                                            } while (!pass);
                                                            pass = true;
                                                            break;
                                                        case 4:
                                                            System.out.print("Veuille indiquer l'id du tâche à supprimer: ");
                                                            do {
                                                                Tache tache = st.find(scanner.nextInt());
                                                                if (tache != null) {
                                                                    st.supprimer(tache);
                                                                    pass = true;
                                                                }
                                                                else {
                                                                    System.out.println("Id invalide !");
                                                                    pass = false;
                                                                }
                                                            } while (!pass);
                                                            pass = true;
                                                            break;
                                                        default:
                                                            pass = false;
                                                            break;
                                                    }
                                                } while (!pass);
                                                pass = true;
                                                break;
                                            case 2:
                                                Console.showCalendrier(sc.findByUid(uid));
                                                System.out.print("Veuillez choisir une caractéristique à modifier: ");
                                                sc.modifier(Console.getModifCalendrier(sc.findByUid(uid), scanner));
                                                pass = true;
                                                break;
                                            case 3:
                                                if (Console.warning("Voulez vous vraiment supprimer votre calendrier et tous ses tâches ?", scanner))
                                                    sc.supprimer(sc.findByUid(uid));
                                                pass = true;
                                                break;
                                            default:
                                                System.out.println("Option invalide !");
                                                pass = false;
                                                break;
                                        }
                                    } while (!pass);
                                }
                                else {
                                    System.out.println("Vous n'avez pas encore ajouté une calendrier !");
                                    System.out.println("1 - Ajouter une calendrier");
                                    do {
                                        switch (scanner.nextInt()) {
                                            case 1:
                                                sc.ajouter(Console.getCalendrier(uid, type, scanner));
                                                pass = true;
                                                break;
                                            default:
                                                System.out.println("Option invalide !");
                                                pass = false;
                                                break;
                                        }
                                    } while (!pass);
                                }
                                pass = true;
                                break;
                            case 2:
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
                    uid = 2;
                    type = 2;
                    System.out.println("Bienvenu docteur !");
                    System.out.println("Veuillez choisir une option:");
                    System.out.println("1 - Consulter Calendrier");
                    System.out.println("2 - Configurer vos disponibilités");
                    do {
                        switch (scanner.nextInt()) {
                            case 1:
                                pass = true;
                                break;
                            case 2:
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
