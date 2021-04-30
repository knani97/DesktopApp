///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.doctourna.tests;
//
//
//import com.doctounra.service.panier_Service;
//import com.doctounra.service.video_Service;
//import com.doctourna.models.Video;
//
//
//
//import java.util.Scanner;
//
///**
// *
// * @author yessi
// */
//public class MainVideo {
//
//    public static void main(String[] args) {
//
////        //DataSource ds1= DataSource.getInstance();
////        Scanner scanner = new Scanner(System.in);
////
////        int input = scanner.nextInt();
////
//       video_Service sv = new video_Service();
//      panier_Service sp = new panier_Service();
////      sv.ajouter(new Video("video10","aaaaaaaaaaaaa",false,1000d,4,sp.find(1)));
//   
////        sv.modifier(new Video(input,(String)scanner.next(),scanner.nextDouble()));
//     
//     //  sv.supprimer(new Video(input));
//    
// //     sv.afficher().forEach(System.out::println);
//
//
////do{
////    System.out.println("Select options form below menu :");
////     System.out.println("Press 1 to select data\nPress 2 to i   nsert data\nPress 3 to delete  :");
////}
//
//int choice;
//
//Scanner sc=new Scanner(System.in);
//Scanner test=new Scanner(System.in);
//        System.out.println("select options form below menu");
//        System.out.println("1. Ajouter");
//        System.out.println("2. modifier");
//        System.out.println("3. supprimer");
//        System.out.println("4. afficher");
//        System.out.println("5. rechercher");
//        
//        System.out.println("Enter choice (1-5):");
//        
//        choice=sc.nextInt();
//
//        
//        //logic
//        switch(choice)
//        {
//            case 1 : 
//            System.out.print("Donner un titre: ");
//            String titre = test.nextLine();
//            titre = test.nextLine();             
//            System.out.println("Donner Source: ");
//            String sour = sc.nextLine();
//            System.out.print("Gratuit:'oui' & 'non' ");
//            Boolean gra = Boolean.parseBoolean(sc.nextLine());
//            System.out.print("Donner Prix: ");
//            Double prix = sc.nextDouble();
//            System.out.print("Donner Note: ");
//            Integer note = sc.nextInt();
//            System.out.print("Donner PanierId: ");
//            Integer panier = sc.nextInt();
//
//           // sv.ajouter(new Video(titre,sour,gra,prix,note,sp.find(panier)));
//            break;
//            case 2: 
//            System.out.print("Entrer id de video pour modifier : ");
//            Integer id = sc.nextInt();
//            System.out.print("Donner nouveau titre : ");
//            sour = sc.next();
//            System.out.print("donner nouveau prix ");
//            gra = sc.nextBoolean();
//            //sv.modifier(new Video(input,(String)scanner.next(),scanner.nextDouble()));
//            break;
//            case 3 : 
//            System.out.print("Entrer id de video pour supprimer : ");
//            id = sc.nextInt();
//            System.out.print("Voulez-vous supprimer cette video : ");
//            String input = sc.next();
//            if (input.contains("yes")) {
//           // sv.supprimer(new Video(id));
//            }
//            else if (input.contains("no")) {
//            System.out.print("Aucun suppression !: ");
//            }
//           
//            break;
//            case 4 :
//            System.out.print("Liste des vidéos : ");
//           // sv.afficher().forEach(System.out::println);
//           
//            break;
//            case 5 : 
//            System.out.print("Rechercher un video avec Titre : ");
//            
//            input = sc.next();
//           // sv.findByTitre(input).forEach(System.out::println);
//
//            break;
//
////            case 6 : 
////            System.out.print("Rechercher un video avec Id : ");
////            
////            input = sc.next();
////            sv.findById(input).forEach(System.out::println);
////
////            break;
//            default:
//             System.out.print("Not in menu : ");
//              
//        
//        }
//
//
//    }
//
//}
