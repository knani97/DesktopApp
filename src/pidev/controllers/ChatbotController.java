/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pidev.controllers;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import java.net.URL;
import static java.util.Collections.list;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 *
 * @author Meriem
 */
public class ChatbotController implements Initializable {
    
    @FXML
    TextField text ; 
     @FXML
     Label label ; 
    @FXML
	private VBox vb ;
   
     
    
    
    
    
    
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    
    @FXML
    
    public void envoyer()
    {
        
         try {
            
            String ch = text.getText();
           String  ch2=ch.replace(" ","%20");
            System.out.println(ch2);
            HttpResponse<String> response = Unirest.get("https://acobot-brainshop-ai-v1.p.rapidapi.com/get?bid=155900&key=CZainXOvM6BlGVtY&uid=pidev&msg="+ch2)
                    .header("x-rapidapi-key", "acd422d7b6msh682a6b9121a4397p1f6684jsnfd86ac745d6b")
                    .header("x-rapidapi-host", "acobot-brainshop-ai-v1.p.rapidapi.com")
                    .asString();
            System.out.println(response.getBody());
             
           // label.setText(response.getBody());
           // newmsg();
           ObservableList<String> list =  FXCollections.observableArrayList();
           ObservableList<Label> list2 =  FXCollections.observableArrayList();
            list.add(ch);    
            list.add(response.getBody().replace("cnt","bot"));
            for(String s : list)
            {Label l =new Label(s);
            l.setText(s);
            list2.add(l);
            }
            
          for (Label l : list2) {
              l.setFont(Font.font("Cambria", 15));
             
               l.setWrapText(true);
                l.setTextFill(Color.web("#1E9789"));
			vb.getChildren().add(l);
                        
		}
        } catch (UnirestException ex) {
            Logger.getLogger(ChatbotController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  /*  public void newmsg (){
        ObservableList<Label> list =  FXCollections.observableArrayList();
        while (text.getText() != ""){
             Label l1 = new Label();
             list.add(l1);
             
		}
        for (Label l : list) {
			vb.getChildren().add(l);
            
        }
    }*/
    
}
