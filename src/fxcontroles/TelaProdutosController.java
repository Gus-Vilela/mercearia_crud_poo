/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.ProdutosDAO;
import entidades.Produtos;
import entidades.ProdutosImg;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaProdutosController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ProdutosDAO banco;
    @FXML
    private TableView<ProdutosImg> tableProdutos;
    @FXML
    private TableColumn<ProdutosImg, String> descCol;
    @FXML
    private TableColumn<ProdutosImg, Double> precoCol;
    @FXML
    private TableColumn<ProdutosImg, ImageView> imgCol;
    @FXML
    private TableColumn<ProdutosImg, String> medidaCol;
    @FXML
    private TableColumn<ProdutosImg, Integer> idCol;
    @FXML
    private Button TelaPrincipal;
    private ImageView ovo;
    @FXML
    private Button TelaNovoProduto;

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
        initializeTable();
        
        
    }    
    
     private void initializeTable(){
        try{
            List<Produtos> lista= banco.getAll();
            List<ProdutosImg> listaImg = new ArrayList<ProdutosImg>();
            for(Produtos produto : lista){
                listaImg.add(new ProdutosImg(produto.getCodproduto(),
                        produto.getDescProduto(),produto.getPreco(),
                        produto.getImagem(),produto.getUnidadeMedida())); 
            }

            idCol.setCellValueFactory(new PropertyValueFactory<>("codproduto"));
            descCol.setCellValueFactory(new PropertyValueFactory<>("descProduto"));
            precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
            imgCol.setCellValueFactory(new PropertyValueFactory<ProdutosImg, ImageView>("imagem"));
            medidaCol.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));

            tableProdutos.setItems(FXCollections.observableArrayList(listaImg));
        }catch(Exception e){
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
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
