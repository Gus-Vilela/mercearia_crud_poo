/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.ProdutosDAO;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaRelatorioDiarioController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private ProdutosDAO produtosDAO;
    
    @FXML
    private TableView<ProdutosDAO.ProdutoVendido> produtosVendidosTable;
    @FXML
    private TableColumn<ProdutosDAO.ProdutoVendido, String> descCol;
    @FXML
    private TableColumn<ProdutosDAO.ProdutoVendido, Integer> quantCol;
    @FXML
    private TableColumn<ProdutosDAO.ProdutoVendido, Double> totalProdCol;
    @FXML
    private Label dataLab;
    @FXML
    private Button TelaPrincipal;
    @FXML
    private Label totalLab;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            produtosDAO = new ProdutosDAO();
            descCol.setCellValueFactory(new PropertyValueFactory<>("descProduto"));
            quantCol.setCellValueFactory(new PropertyValueFactory<>("totalQuantidade"));
            totalProdCol.setCellValueFactory(new PropertyValueFactory<>("totalValor"));

            LocalDate specificDate = LocalDate.now(); 

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataLab.setText(specificDate.format(dateFormatter));

            List<ProdutosDAO.ProdutoVendido> produtosVendidos = produtosDAO.getProdutosVendidosNaData(specificDate);
            produtosVendidosTable.getItems().addAll(produtosVendidos);

            double totalValor = produtosDAO.getTotalValorVendidoNaData(specificDate);

            
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            totalLab.setText(decimalFormat.format(totalValor));
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
