/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Services;

import Entity.ArticleCat;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.DeviceNColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javafx.scene.control.Cell;
import javax.crypto.SealedObject;
import javax.persistence.Table;

/**
 *
 * @author Lenovo
 */
public class PdfExport {
    
    public void PdfExportTest(){
        
        
        Font titreFont = new Font(Font.FontFamily.TIMES_ROMAN,16,Font.NORMAL,BaseColor.WHITE);
        ServiceArticleCat SAC = new ServiceArticleCat();
        String file_name="C:\\Users\\mouhe\\OneDrive\\Bureau\\pidev\\srcDoctourna.pdf";
        Document document = new Document();
        try{
           PdfWriter.getInstance(document,new FileOutputStream(file_name));
           document.setPageSize(PageSize.A4);
            document.open();
           
           
           Image image = Image.getInstance("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/logodoc.png");
           image.scaleAbsolute(200,150);
           image .setAlignment(Image.MIDDLE);
           document.add(image);
           
           SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss ");
           Date dateAjout = new Date(System.currentTimeMillis());
           
           System.out.println(formatter.format(dateAjout));
           Paragraph DateAjout = new Paragraph(formatter.format(dateAjout));
           DateAjout.setAlignment(Paragraph.ALIGN_RIGHT);
          document.add(DateAjout);

////           Paragraph para = new Paragraph("weeey ya lawska");
//           document.add(para);

//   ------------ CATEGORIE DU MOIS  -----------------------

PdfPTable moisCatTable = new PdfPTable(3);
            PdfPCell cellArt = new PdfPCell (new Paragraph("Categorie du mois",titreFont));
            
            cellArt.setColspan(3);
            cellArt.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellArt.setBackgroundColor(BaseColor.CYAN);
            moisCatTable.addCell(cellArt);
            
            Color color = Color.getColor("#92489B");
            
            SAC.TopArticleCat().stream()
                                .limit(1);
            
            for (int i=0;i<1;i++){
                String nbr= SAC.TopArticleCat().get(1);
                String Categorie= SAC.TopArticleCat().get(0);
                
                String imageCat=SAC.TopArticleCat().get(2);
                System.out.println(imageCat);
                
                Rectangle taille=new Rectangle(300,300);
                Image imageCaT = Image.getInstance("C:\\Users\\mouhe\\OneDrive\\Documents\\NetBeansProjects\\DOCTOURNA\\src\\images\\"+imageCat);
                imageCaT.scaleAbsolute(10,10);
                
                moisCatTable.addCell(imageCaT);  
                moisCatTable.addCell(Categorie); 
                moisCatTable.addCell(nbr+"/Mois"); 
            }

//   ------------ TOP 3 categories  -----------------------


PdfPTable Top3CatTable = new PdfPTable(3);
            PdfPCell cellArtTop3CatTable = new PdfPCell (new Paragraph("CATEGORIE DU MOIS"));
            
            cellArtTop3CatTable.setColspan(3);
            cellArtTop3CatTable.setHorizontalAlignment(Element.ALIGN_CENTER);
            cellArtTop3CatTable.setBackgroundColor(BaseColor.CYAN);
            moisCatTable.addCell(cellArt);
            
            
            
            for (int i=0;i<SAC.top3ArticleCat().size();i++){
                int nbr=SAC.top3ArticleCat().get(i).getId();
                String Categorie= SAC.top3ArticleCat().get(i).getCategorie();
                
                
                String imageCat=SAC.top3ArticleCat().get(i).getImage();
                System.out.println(imageCat);
                
                
                Image imageCaT = Image.getInstance("C:\\Users\\mouhe\\OneDrive\\Documents\\NetBeansProjects\\DOCTOURNA\\src\\images\\"+SAC.top3ArticleCat().get(i).getImage());
                
                imageCaT.scaleToFit(5,5);
                moisCatTable.addCell(imageCaT);  
                moisCatTable.addCell(Categorie); 
                moisCatTable.addCell(nbr+"/Mois"); 
            }
            
            
//   ------------ tous les categories  -----------------------
            PdfPTable table = new PdfPTable(2);
            PdfPCell cell = new PdfPCell (new Paragraph("LISTE DES CATEGORIES",titreFont));
            
            cell.setColspan(2);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setBackgroundColor(BaseColor.CYAN);
            table.addCell(cell);
            
            
            for (int i=0;i<SAC.afficherList().size();i++){
                String Categorie=SAC.afficherList().get(i).getCategorie().toString();
                
                String imageCat=SAC.afficherList().get(i).getImage();
                System.out.println(imageCat);
                
                Rectangle taille=new Rectangle(300,300);
                Image imageCaT = Image.getInstance("C:\\Users\\mouhe\\OneDrive\\Documents\\NetBeansProjects\\DOCTOURNA\\src\\images\\"+SAC.afficherList().get(i).getImage());
                imageCaT.scaleToFit(5,5);
                
                table.addCell(imageCaT);  
                table.addCell(Categorie);  
            }
            document.add(moisCatTable);
            document.add(Top3CatTable);           
            document.add(table);
            // QR CODE
            
            qrCodeImage qrCode = new qrCodeImage();
            qrCode.qrCodeTest();
            Image imageQrCode = Image.getInstance("file:///C:/Users/mouhe/OneDrive/Documents/NetBeansProjects/DOCTOURNA/src/images/QrCodeDoctourna.png");
            
            imageQrCode .setAlignment(Image.MIDDLE);
            document.add(imageQrCode);
            
           document.close();
            System.out.println("pidev pdf");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PdfExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfExport.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PdfExport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
