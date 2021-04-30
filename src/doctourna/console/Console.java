/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package doctourna.console;

import doctourna.models.Calendrier;
import doctourna.models.Disponibilite;
import doctourna.models.Tache;
import doctourna.models.User;
import doctourna.services.ServiceCalendrier;
import doctourna.services.ServiceRdv;
import doctourna.services.ServiceTache;
import doctourna.services.ServiceUser;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Scanner;
import javafx.beans.property.SimpleStringProperty;
import javax.swing.SpringLayout;

/**
 *
 * @author mouhe
 */
public class Console {

    // Colors
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    // Background colors
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    public static void changeColor(String color) {
        System.out.println(color);
    }

    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static boolean warning(String message, Scanner scanner) {
        String input = "";

        System.out.println("!!! " + message + " !!!");
        do {
            input = scanner.next();

            switch (input) {
                case "oui":
                    return true;
                case "non":
                    return false;
                default:
                    System.out.println("Choix invalide !");
            }
        } while (input != "oui" && input != "non");

        return false;
    }

    public static boolean isColor(String string) {
        return (string.startsWith("#") && string.length() == 7) || string.toLowerCase().contains("rouge") || string.toLowerCase().contains("bleu") || string.toLowerCase().contains("vert") || string.toLowerCase().contains("jaune");
    }

    public static boolean isTimezone(String string) {
        return string.contains("UTC");
    }

    public static boolean isDatetime(String datetime) {
        return true;
    }

    public static boolean isDate(String date) {
        return true;
    }

    public static boolean isTime(String time) {
        return true;
    }
    
    public static boolean isNumber(String str) {
        return str.matches("[0-9,+,(,)]+");
    }

    public static void showColors() {
        System.out.println(Console.ANSI_RED + "Rouge");
        System.out.println(Console.ANSI_BLUE + "Bleu");
        System.out.println(Console.ANSI_YELLOW + "Jaune");
        System.out.println(Console.ANSI_GREEN + "Vert" + Console.ANSI_BLACK);
    }

    public static void showTimezones() {
        for (Integer i = 14; i > 0; i--) {
            System.out.println("UTC-" + i.toString());
        }
        System.out.println("UTC");
        for (Integer i = 1; i < 15; i++) {
            System.out.println("UTC+" + i.toString());
        }
    }

    public static String getEtat(int etat) {
        switch (etat) {
            case 1:
                return "Disponible";
            case 2:
                return "Reporté";
            case 3:
                return "Annulé";
            case 4:
                return "Terminé";
            default:
                return "Erreur";
        }
    }

    public static Integer getType(String etat) {
        switch (etat) {
            case "1":
            case "RDV":
                return 1;
            case "2":
            case "Prise Médicament":
                return 2;
            case "3":
            case "Personnelle":
                return 3;
            case "4":
            case "Disponibilité":
                return 4;
            case "5":
            case "RDV Perso":
                return 5;
            default:
                return -1;
        }
    }

    public static Timestamp toDate(String str) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd H:m");
        try {
            Date date = dateFormat.parse(str);
            long time = date.getTime();
            return new Timestamp(time);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static boolean secsDiff(LocalDateTime fromDateTime, LocalDateTime toDateTime, int mins) {
        LocalDateTime tempDateTime = LocalDateTime.from(fromDateTime);

        long years = tempDateTime.until(toDateTime, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears(years);

        long months = tempDateTime.until(toDateTime, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths(months);

        long days = tempDateTime.until(toDateTime, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays(days);

        long hours = tempDateTime.until(toDateTime, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours(hours);

        long minutes = tempDateTime.until(toDateTime, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes(minutes);

        long seconds = tempDateTime.until(toDateTime, ChronoUnit.SECONDS);
        
        return years == 0 && months == 0 && days == 0 && hours == 0 && minutes <= mins;
    }

    public static Calendrier getCalendrier(int uid, int type, Scanner scanner) {
        Calendrier calendrier = new Calendrier();
        boolean pass = true;
        String input = "";

        System.out.print("Veuillez indiquer la format de votre calendrier (Plage Horaire (1) | Calendrier Stanard (2)): ");
        do {
            switch (scanner.next()) {
                case "1":
                case "Plage Horaire":
                    calendrier.setFormat(1);
                    pass = true;
                    break;
                case "2":
                case "Calendrier Standard":
                    calendrier.setFormat(2);
                    pass = true;
                    break;
                default:
                    System.out.println("Option invalide !");
                    pass = false;
                    break;
            }
        } while (!pass);

        System.out.print("Voulez vous activer l'option Rappel-Email dans votre calendrier ? : ");
        do {
            switch (scanner.next().toLowerCase()) {
                case "non":
                    calendrier.setEmail(false);
                    pass = true;
                    break;
                case "oui":
                    calendrier.setEmail(true);
                    pass = true;
                    break;
                default:
                    System.out.println("Option invalide !");
                    pass = false;
                    break;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez le couleur de votre calenrier (RGB our CSS color | ? pour afficher les couleurs) : ");
        do {
            input = scanner.next();
            if (input.contains("?")) {
                showColors();
                pass = false;
            } else if (isColor(input)) {
                calendrier.setCouleur(input);
                pass = true;
            } else {
                System.out.println("Couleur invalide !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez le TimeZone de votre calenrier (Entrez ? pour afficher tous les Timezones) : ");
        do {
            input = scanner.next();
            if (input.contains("?")) {
                showTimezones();
                pass = false;
            } else if (isTimezone(input)) {
                calendrier.setTimezone(input);
                pass = true;
            } else {
                System.out.println("Timezone invalide !");
                pass = false;
            }
        } while (!pass);

        calendrier.setUidId(new User(uid));
        calendrier.setType(type);

        return calendrier;
    }

    public static Tache getTache(int calendrier, Scanner scanner) {
        Tache tache = new Tache();
        boolean pass = true;
        String input = "";

        System.out.print("Veuillez indiquez une libellé (Min 3 caractères | Max 25 caractères) : ");
        do {
            input = scanner.next();
            if (input.length() >= 3 && input.length() <= 25) {
                tache.setLibelle(input);
                pass = true;
            } else {
                System.out.println("Syntaxe incorrect !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez une description (Max 255 caractères) : ");
        do {
            input = scanner.next();
            if (input.length() <= 255) {
                tache.setDescription(input);
                pass = true;
            } else {
                System.out.println("Syntaxe incorrect !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez le couleur de votre tâche (RGB our CSS color | ? pour afficher les couleurs) : ");
        do {
            input = scanner.next();
            if (input.contains("?")) {
                showColors();
                pass = false;
            } else if (isColor(input)) {
                tache.setCouleur(input);
                pass = true;
            } else {
                System.out.println("Couleur invalide !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquer le type de votre tâche (RDV Perso (1) | Prise Médicament (2) | Personnelle (3) | Disponibilité (4)): ");
        do {
            switch (scanner.next()) {
                case "1":
                case "RDV Perso":
                    tache.setType("1");
                    pass = true;
                    break;
                case "2":
                case "Prise Médicament":
                    tache.setType("2");
                    pass = true;
                    break;
                case "3":
                case "Personnelle":
                    tache.setType("3");
                    pass = true;
                    break;
                case "4":
                case "Disponibilité":
                    tache.setType("4");
                    pass = true;
                    break;
                default:
                    System.out.println("Option invalide !");
                    pass = false;
                    break;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez la date de votre tâche (Syntaxe: yyyy/mm/dd h:i) : ");
        do {
            input = scanner.nextLine();
            input = scanner.nextLine();
            if (isDatetime(input)) {
                tache.setDate(toDate(input));
                pass = true;
            } else {
                System.out.println("Syntaxe invalide !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez la durée de votre tâche (Syntaxe: h:i) : ");
        do {
            input = scanner.next();
            if (isTime(input)) {
                tache.setDuree(Time.valueOf(LocalTime.parse(input)));
                pass = true;
            } else {
                System.out.println("Syntaxe invalide !");
                pass = false;
            }
        } while (!pass);

        tache.setCalendrier(new Calendrier(calendrier));

        return tache;
    }

    public static Disponibilite getDisponibilite(Scanner scanner) {
        Disponibilite dispo = new Disponibilite();
        boolean pass = true;
        String input = "";

        System.out.print("Veuillez indiquez la date de debut de votre disponibilité (Syntaxe: yyyy/mm/dd h:i) : ");
        do {
            input = scanner.nextLine();
            input = scanner.nextLine();
            if (isDatetime(input)) {
                dispo.setStartDate(toDate(input));
                pass = true;
            } else {
                System.out.println("Syntaxe invalide !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez la date de fin de votre disponibilité (Syntaxe: yyyy/mm/dd h:i) : ");
        do {
            input = scanner.nextLine();
            if (isDatetime(input)) {
                dispo.setEndDate(toDate(input));
                pass = true;
            } else {
                System.out.println("Syntaxe invalide !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez la durée de vos RDVs (Syntaxe: h:i) : ");
        do {
            input = scanner.next();
            if (isTime(input)) {
                dispo.setDureeRdv(Time.valueOf(input));
                pass = true;
            } else {
                System.out.println("Syntaxe invalide !");
                pass = false;
            }
        } while (!pass);

        System.out.print("Veuillez indiquez la durée de vos pauses (Syntaxe: h:i) : ");
        do {
            input = scanner.next();
            if (isTime(input)) {
                dispo.setDureePause(Time.valueOf(input));
                pass = true;
            } else {
                System.out.println("Syntaxe invalide !");
                pass = false;
            }
        } while (!pass);

        return dispo;
    }

    public static void showCalendrier(Calendrier calendrier) {
        System.out.print("Format: ");
        switch (calendrier.getFormat()) {
            case 1:
                System.out.println("Plage Horaire");
                break;
            case 2:
                System.out.println("Calendrier Standard");
                break;
            default:
                System.out.println("Erreur");
                break;
        }
        System.out.print("Option Reminder-Email: ");
        if (calendrier.getEmail()) {
            System.out.println("Activée");
        } else {
            System.out.println("Désactivée");
        }
        System.out.println("Couleur: " + calendrier.getCouleur());
        System.out.println("Timezone: " + calendrier.getTimezone());
    }

    public static void showTache(Tache tache) {
        System.out.println("Libelle: " + tache.getLibelle());
        System.out.println("Description: " + tache.getDescription());
        System.out.println("Couleur: " + tache.getCouleur());
        System.out.print("Type: ");
        switch (tache.getType()) {
            case "1":
                System.out.println("RDV");
                break;
            case "2":
                System.out.println("Prise Médicament");
                break;
            case "3":
                System.out.println("Personnelle");
                break;
            case "4":
                System.out.println("Disponibilité");
                break;
            case "5":
                System.out.println("RDV Perso");
                break;
            default:
                System.out.println("Erreur");
                break;
        }
        System.out.println("Date: " + tache.getDate());
        System.out.println("Durée: " + tache.getDuree());
    }

    public static Calendrier getModifCalendrier(Calendrier calendrier, Scanner scanner) {
        boolean pass = true;
        String input = "";

        do {
            switch (scanner.next()) {
                case "1":
                    System.out.print("Veuillez indiquer la format de votre calendrier (Plage Horaire (1) | Calendrier Stanard (2)): ");
                    do {
                        switch (scanner.next()) {
                            case "1":
                            case "Plage Horaire":
                                calendrier.setFormat(1);
                                pass = true;
                                break;
                            case "2":
                            case "Calendrier Standard":
                                calendrier.setFormat(2);
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
                case "2":
                    System.out.print("Voulez vous activer l'option Rappel-Email dans votre calendrier ? : ");
                    do {
                        switch (scanner.next()) {
                            case "non":
                                calendrier.setEmail(false);
                                pass = true;
                                break;
                            case "oui":
                                calendrier.setEmail(true);
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
                case "3":
                    System.out.print("Veuillez indiquez le couleur de votre calenrier (RGB our CSS color) : ");
                    do {
                        input = scanner.next();
                        if (isColor(input)) {
                            calendrier.setCouleur(input);
                            pass = true;
                        } else {
                            System.out.println("Couleur invalide !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case "4":
                    System.out.print("Veuillez indiquez le TimeZone de votre calenrier (Entrez ? pour afficher tous les Timezones): ");
                    do {
                        input = scanner.next();
                        if (isTimezone(input)) {
                            calendrier.setTimezone(input);
                            pass = true;
                        } else if (input == "?") {
                            showTimezones();
                            pass = false;
                        } else {
                            System.out.println("Timezone invalide !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                default:
                    System.out.println("Paramétre invalide !");
                    pass = true;
                    break;
            }
        } while (!pass);

        return calendrier;
    }

    public static Tache getModifTache(Tache tache, Scanner scanner) {
        boolean pass = true;
        String input = "";

        do {
            switch (scanner.next()) {
                case "1":
                    System.out.print("Veuillez indiquez une libellé (Min 3 caractères | Max 25 caractères) : ");
                    do {
                        input = scanner.next();
                        if (input.length() >= 3 && input.length() <= 25) {
                            tache.setLibelle(input);
                            pass = true;
                        } else {
                            System.out.println("Syntaxe incorrect !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case "2":
                    System.out.print("Veuillez indiquez une description (Max 255 caractères) : ");
                    do {
                        input = scanner.next();
                        if (input.length() <= 255) {
                            tache.setDescription(input);
                            pass = true;
                        } else {
                            System.out.println("Syntaxe incorrect !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case "3":
                    System.out.print("Veuillez indiquez le couleur de votre tâche (RGB our CSS color) : ");
                    do {
                        input = scanner.next();
                        if (isColor(input)) {
                            tache.setCouleur(input);
                            pass = true;
                        } else {
                            System.out.println("Couleur invalide !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case "4":
                    System.out.print("Veuillez indiquer le type de votre tâche (RDV Perso (1) | Prise Médicament (2) | Personnelle (3) | Disponibilité (4)): ");
                    do {
                        switch (scanner.next()) {
                            case "1":
                            case "RDV Perso":
                                tache.setType("1");
                                pass = true;
                                break;
                            case "2":
                            case "Prise Médicament":
                                tache.setType("2");
                                pass = true;
                                break;
                            case "3":
                            case "Personnelle":
                                tache.setType("3");
                                pass = true;
                                break;
                            case "4":
                            case "Disponibilité":
                                tache.setType("4");
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
                case "5":
                    System.out.print("Veuillez indiquez la date de votre tâche (Syntaxe: yyyy/mm/dd h:i) : ");
                    do {
                        input = scanner.nextLine();
                        input = scanner.nextLine();
                        if (isDatetime(input)) {
                            tache.setDate(toDate(input));
                            pass = true;
                        } else {
                            System.out.println("Syntaxe invalide !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                case "6":
                    System.out.print("Veuillez indiquez la durée de votre tâche (Syntaxe: h:i) : ");
                    do {
                        input = scanner.next();
                        if (isTime(input)) {
                            tache.setDuree(Time.valueOf(LocalTime.parse(input)));
                            pass = true;
                        } else {
                            System.out.println("Syntaxe invalide !");
                            pass = false;
                        }
                    } while (!pass);
                    pass = true;
                    break;
                default:
                    System.out.println("Paramétre invalide !");
                    pass = true;
                    break;
            }
        } while (!pass);

        return tache;
    }

    public static void consulterCalendrier(ServiceCalendrier sc, ServiceTache st, int uid, int type, Scanner scanner) {
        Boolean pass = false;
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
                        System.out.println("5 - Filtrer selon type");
                        do {
                            switch (scanner.nextInt()) {
                                case 1:
                                    System.out.print("Veuille indiquer l'id du tâche à consulter: ");
                                    do {
                                        Tache tache = st.find(scanner.nextInt());
                                        if (tache != null) {
                                            Console.showTache(tache);
                                            pass = true;
                                        } else {
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
                                        } else {
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
                                        } else {
                                            System.out.println("Id invalide !");
                                            pass = false;
                                        }
                                    } while (!pass);
                                    pass = true;
                                    break;
                                case 5:
                                    System.out.println("Veuille donner le type de tâches à afficher: ");
                                    System.out.println("RDVs (1) | Prise Médicaments (2) | Personnelles (3) | Disponibilités (4) | RDVs Persos (5)");
                                    do {
                                        Integer typeT = scanner.nextInt();
                                        if (typeT < 6 && typeT > 0) {
                                            st.findByCalendrier(sc.findByUid(uid).getId()).stream().filter(t -> t.getType().contains(typeT.toString())).forEach(t -> System.out.println(t));
                                            pass = true;
                                        } else {
                                            System.out.println("Type invalide !");
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
                        if (Console.warning("Voulez vous vraiment supprimer votre calendrier et tous ses tâches ?", scanner)) {
                            sc.supprimer(sc.findByUid(uid));
                        }
                        pass = true;
                        break;
                    default:
                        System.out.println("Option invalide !");
                        pass = false;
                        break;
                }
            } while (!pass);
        } else {
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
    }

    static void getRdv(Scanner scanner, ServiceRdv sr, ServiceUser su, int uid, int type) {
        Boolean pass = false;

        System.out.println("Liste de vos RDVs: ");
        if (type == 1) {
            sr.findByUid(uid).stream().forEach(r -> System.out.println("RDV avec Dr. "
                    + r.getMedecinId().getNom() + " " + r.getMedecinId().getPrenom()
                    + " le " + r.getDate().toString() + " ETAT: " + Console.getEtat(r.getEtat())));
        } else {
            sr.findByUid(uid).stream().forEach(r -> System.out.println("RDV avec le patient "
                    + r.getPatientId().getNom() + " " + r.getMedecinId().getPrenom()
                    + " le " + r.getDate().toString() + " ETAT: " + Console.getEtat(r.getEtat())));
        }
        System.out.println("\nVeuillez choisir une option :");
        if (type == 1) {
            System.out.println("1 - Prendre un RDV");
        } else {
            System.out.println("1 - Reporter un RDV");
        }
        System.out.println("2 - Annuler un RDV");
        do {
            switch (scanner.nextInt()) {
                case 1:
                    int choix;
                    System.out.println("Liste des médecins :");
                    su.rechercherMedecins().stream().forEach(m -> System.out.println(m.getId() + " - " + m));
                    System.out.println("Veuillez selectionner un médecin : ");
                    choix = scanner.nextInt();
                    while (!su.rechercherMedecins().contains(su.find(choix))) {
                        System.out.println("ID invalide.");
                        choix = scanner.nextInt();
                    }
                    System.out.println("Liste des dispos :");
                    su.rechercherMedecins().stream().forEach(m -> System.out.println(m.getId() + " - " + m));
                    System.out.println("Veuillez selectionner une date : ");
                    choix = scanner.nextInt();
                    while (!su.rechercherMedecins().contains(su.find(choix))) {
                        System.out.println("ID invalide.");
                        choix = scanner.nextInt();
                    }
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
