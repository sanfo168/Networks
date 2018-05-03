/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package networkproject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author alexsanford, Justin Malmstedt
 */
public class Browser extends Application {
    
    private TextField website;
    private TextField file;
    private Socket socket = null;
    private DataOutputStream output = null;
    private DataInputStream input = null;
    private String display = "";
    
    @Override
    public void start(Stage primaryStage) {
        
        website = new TextField();
        website.setPrefWidth(675);
        
        Button btn = new Button();
        btn.setPrefSize(120, 20);
        
        file = new TextField();
        file.setPrefSize(600, 715);
        
        btn.setText("Search");
        //creates an Hbox to store the textbox and website.
        HBox hb = new HBox();
        hb.getChildren().addAll(website, btn);
        hb.setSpacing(10);
        
        //creates a VBox to store the hbox and the file to display
        VBox vb = new VBox();
        vb.getChildren().addAll(hb, file);
        vb.setSpacing(10);
        
        
        
        //Search button on click listner
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                try {
                    file.setText("About to retrieve data");
                    //holds the URL of the website that we want to go to.
                    String URL = website.getText();                    
                    socket = new Socket("localhost", 8008);
                    output = new DataOutputStream(socket.getOutputStream());
                    input = new DataInputStream(socket.getInputStream());
                    
                    if(socket != null && output != null && input != null){
                        output.writeUTF(URL);
                        //output.flush(); This was invalidating the incoming stream on the DNS
                        
                        display = input.readUTF();
                        file.setText(display);
                        
                    }
                    
                } catch (IOException ex) {
                    Logger.getLogger(Browser.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        //adds all of the properties to the scene
        StackPane root = new StackPane();
        root.getChildren().add(vb);
        
        Scene scene = new Scene(root, 800, 750);
        
        primaryStage.setTitle("The Ultimate Web Browser!!!!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}