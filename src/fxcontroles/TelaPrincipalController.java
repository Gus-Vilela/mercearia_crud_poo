/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.xml.transform.Source;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaPrincipalController implements Initializable {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private Button TelaVendedores;
    @FXML
    private Button TelaProdutos;
    @FXML
    private Button TelaVendas;
    @FXML
    private Button TelaRelatorioDiario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    public void switchScene(ActionEvent event)  {
        try{
            Button btn = (Button)event.getSource();
            String nomeTela = btn.getId();
            String caminhoTela = "./telas/" + nomeTela + ".fxml";     
            FXMLLoader carregador = new FXMLLoader(getClass().getClassLoader().getResource(caminhoTela));
            root = carregador.load();

            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        
        
        }catch(IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
            
        }
    }
    
}
