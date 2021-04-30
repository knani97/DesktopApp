/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.services;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.imageio.ImageIO;
import pidev.interfaces.Initializable;
import pidev.models.Inventaire;
import pidev.models.Pharmacie;
import pidev.utils.DbConnection;

/**
 *
 * @author Meriem
 */
public class InventaireService implements Initializable<Inventaire>{
        Connection cnx = DbConnection.getInstance().getCnx();

   
    public boolean add(Inventaire i, Pharmacie ph) {
        
        
        
        
         
            
            
            
            
            
            
            
            String code = "image"+Calendar.getInstance().getTimeInMillis();

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
     
        String data = "id_medicament : "+ i.getMedicaments_id()+"Quantite: "+i.getQuantite()+"id_pharmacie: "+ph.getId();
        int width = 300;
        int height = 300;

        BufferedImage bufferedImage = null;
        try {
            BitMatrix byteMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);
            bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            bufferedImage.createGraphics();

            Graphics2D image = (Graphics2D) bufferedImage.getGraphics();
            image.setColor(Color.WHITE);
            image.fillRect(0, 0, width, height);
            image.setColor(Color.BLACK);

            for (int iq = 0; iq < height; iq++) {
                for (int j = 0; j < width; j++) {
                    if (byteMatrix.get(iq, j)) {
                        image.fillRect(iq, j, 1, 1);
                    }
                }
            }
            if (ImageIO.write(bufferedImage, "png", new File("C:/xampp/htdocs/images/" + code + ".png"))) {
                System.out.println("-- saved");
            }
            System.out.println("QR created successfully....");

        } catch (WriterException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
            
        
        
        
        
        
        
        
        
             try {
            String req = "INSERT INTO inventaire ( pharmacie_id,medicaments_id,quantite,qr) VALUES "
                    + "(?,?,?,?)";

            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ph.getId());
            ps.setInt(2, i.getMedicaments_id());
            ps.setInt(3, i.getQuantite());
            ps.setString(4, code + ".png");
            ps.executeUpdate();
            System.out.println("inventaire ajoutée");
return true ;


        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false ;
        }

    
    }

    
    public void delete(Inventaire i,Pharmacie ph) {
         String req ="delete from inventaire where (pharmacie_id = ? and medicaments_id=?) ";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1, ph.getId());
            ps.setInt(2, i.getMedicaments_id());
            ps.executeUpdate();
            System.out.println("inventaire supprimée");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
    }

    public void update(Inventaire i, Pharmacie ph) {
        try {
            String req = "update inventaire set quantite=?  where (pharmacie_id = ? and medicaments_id=?)  ";
           
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setInt(1,i.getQuantite());
             ps.setInt(2, ph.getId());
            ps.setInt(3, i.getMedicaments_id());
           
            ps.executeUpdate();

            System.out.println("inventaire modifiée!");
            

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            
        }
    }

    @Override
    public List<Inventaire> read() {
         List<Inventaire> list= new ArrayList<>();
        String req ="select * from inventaire";

        try {
            PreparedStatement ps = cnx.prepareStatement(req);
           
            ResultSet rs= ps.executeQuery(req);
            while(rs.next()){
            list.add(new Inventaire(rs.getInt(1), rs.getInt(2), rs.getInt(3),rs.getString(4)));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());

        }
        return list;
        
    
    }

    @Override
    public void add(Inventaire c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete(Inventaire c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void update(Inventaire c) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
