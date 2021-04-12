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
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

/**
 *
 * @author mouhe
 */
public class Console {

    public static void clear() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        }
        catch (Exception e) {
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
        return true;
    }
    
    public static boolean isTimezone(String string) {
        return true;
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
    
    public static void showColors() {
        
    }
    
    public static void showTimezones() {
        
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
        
        System.out.print("Veuillez indiquez le couleur de votre calenrier (RGB our CSS color) : ");
        do {
            input = scanner.next();
            if (isColor(input)) {
                calendrier.setCouleur(input);
                pass = true;
            }
            else {
                System.out.println("Couleur invalide !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez le TimeZone de votre calenrier (Entrez ? pour afficher tous les Timezones): ");
        do {
            input = scanner.next();
            if (isTimezone(input)) {
                calendrier.setTimezone(input);
                pass = true;
            }
            else if (input == "?") {
                showTimezones();
                pass =false;
            }
            else {
                System.out.println("Timezone invalide !");
                pass =false;
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
            }
            else {
                System.out.println("Syntaxe incorrect !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez une description (Max 255 caractères) : ");
        do {
            input = scanner.next();
            if (input.length() <= 255) {
                tache.setDescription(input);
                pass = true;
            }
            else {
                System.out.println("Syntaxe incorrect !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez le couleur de votre tâche (RGB our CSS color) : ");
        do {
            input = scanner.next();
            if (isColor(input)) {
                tache.setCouleur(input);
                pass = true;
            }
            else {
                System.out.println("Couleur invalide !");
                pass =false;
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
                tache.setDate(Timestamp.from(LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toInstant(ZoneOffset.UTC)));
                pass = true;
            }
            else {
                System.out.println("Syntaxe invalide !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez la durée de votre tâche (Syntaxe: h:i) : ");
        do {
            input = scanner.next();
            if (isTime(input)) {
                tache.setDuree(Time.valueOf(LocalTime.parse(input)));
                pass = true;
            }
            else {
                System.out.println("Syntaxe invalide !");
                pass =false;
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
            input = scanner.next();
            if (isDatetime(input)) {
                dispo.setStartDate(Timestamp.valueOf(input));
                pass = true;
            }
            else {
                System.out.println("Syntaxe invalide !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez la date de fin de votre disponibilité (Syntaxe: yyyy/mm/dd h:i) : ");
        do {
            input = scanner.next();
            if (isDatetime(input)) {
                dispo.setEndDate(Timestamp.valueOf(input));
                pass = true;
            }
            else {
                System.out.println("Syntaxe invalide !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez la durée de vos RDVs (Syntaxe: h:i) : ");
        do {
            input = scanner.next();
            if (isTime(input)) {
                dispo.setDureeRdv(Time.valueOf(input));
                pass = true;
            }
            else {
                System.out.println("Syntaxe invalide !");
                pass =false;
            }
        } while (!pass);
        
        System.out.print("Veuillez indiquez la durée de vos pauses (Syntaxe: h:i) : ");
        do {
            input = scanner.next();
            if (isTime(input)) {
                dispo.setDureePause(Time.valueOf(input));
                pass = true;
            }
            else {
                System.out.println("Syntaxe invalide !");
                pass =false;
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
        if (calendrier.getEmail())
            System.out.println("Activée");
        else
            System.out.println("Désactivée");
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
                        }
                        else {
                            System.out.println("Couleur invalide !");
                            pass =false;
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
                        }
                        else if (input == "?") {
                            showTimezones();
                            pass =false;
                        }
                        else {
                            System.out.println("Timezone invalide !");
                            pass =false;
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
                        }
                        else {
                            System.out.println("Syntaxe incorrect !");
                            pass =false;
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
                        }
                        else {
                            System.out.println("Syntaxe incorrect !");
                            pass =false;
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
                        }
                        else {
                            System.out.println("Couleur invalide !");
                            pass =false;
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
                            tache.setDate(Timestamp.from(LocalDateTime.parse(input, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")).toInstant(ZoneOffset.UTC)));
                            pass = true;
                        }
                        else {
                            System.out.println("Syntaxe invalide !");
                            pass =false;
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
                        }
                        else {
                            System.out.println("Syntaxe invalide !");
                            pass =false;
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
}
