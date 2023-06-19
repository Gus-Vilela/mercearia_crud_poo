/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.ProdutosDAO;
import entidades.Produtos;
import java.io.IOException;
import static java.lang.Double.parseDouble;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaNovoProdutoController implements Initializable {
    
    private ProdutosDAO banco;
    private short flag;
    private Produtos Produtos;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField inputDesc;
    @FXML
    private TextField inputPreco;
    @FXML
    private TextField inputImg;
    @FXML
    private TextField InputMedida;
    @FXML
    private Button adicionar;
    @FXML
    private Text campoObri;
    
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            banco = new ProdutosDAO();    // TODO
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na inicialização do banco",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
    }    
    
    @FXML
    private void addProduct(ActionEvent event){
        flag = 0;
        if ("".equals(inputDesc.getText())){
            campoObri.setOpacity(1);
            inputDesc.setStyle("-fx-border-color:red;");
            flag = 1;
        }else{inputDesc.setStyle("-fx-border-color:grey;");}
        if ("".equals(inputPreco.getText())){
            campoObri.setOpacity(1);
            inputPreco.setStyle("-fx-border-color:red;");
            flag = 1;
        }else{inputPreco.setStyle("-fx-border-color:grey;");}
        if ("".equals(InputMedida.getText())){
            campoObri.setOpacity(1);
            InputMedida.setStyle("-fx-border-color:red;");
            flag = 1;
        }else{InputMedida.setStyle("-fx-border-color:grey;");}
        
        if(flag == 0){
            try{
                Produtos = new Produtos();
                Produtos.setDescProduto(inputDesc.getText());
                Produtos.setPreco(parseDouble(inputPreco.getText()));
                Produtos.setImagem(inputImg.getText());
                Produtos.setUnidadeMedida(InputMedida.getText());
                
                banco.add(Produtos);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Cadastro Realizado");
                alert.setHeaderText(null);
                alert.setContentText("Produtos Cadastrado!");
                alert.showAndWait();
                
                
            }catch(Exception e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
            }
        }
        
    }    
    @FXML
    public void switchScene(ActionEvent event)  {
        try{
        Button btn = (Button)event.getSource();
        String nomeTela = btn.getId();
        
        FXMLLoader carregador = new FXMLLoader();
        String caminhoTela = "./telas/" + nomeTela
                + ".fxml";     
        
        Parent root = carregador.load(getClass().getClassLoader()
                .getResource(caminhoTela));
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
