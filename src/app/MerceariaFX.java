/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package app;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author gusta
 */
public class MerceariaFX extends Application {
    
    @Override
    public void start(Stage primaryStage) throws IOException{
        FXMLLoader carregador = new FXMLLoader();
        String nomeTela = "./telas/FXML.fxml";

        AnchorPane root = (AnchorPane) carregador.load(getClass().getClassLoader()
                .getResource(nomeTela));
        
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("Hello World!");
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
