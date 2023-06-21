/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import entidades.Pagamento;
import entidades.ProdutosVenda;
import entidades.Vendedor;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
public class TelaRelatorioVendaController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    DecimalFormat decimalFormat;
    @FXML
    private Label vendedorLab;
    @FXML
    private Label dataLab;
    @FXML
    private Label pagamentoLab;
    @FXML
    private TableView<ProdutosVenda> produtosTable;
    @FXML
    private TableColumn<ProdutosVenda, String> descCol;
    @FXML
    private TableColumn<ProdutosVenda, Double> precoUniCol;
    @FXML
    private TableColumn<ProdutosVenda, Integer> quantCol;
    @FXML
    private TableColumn<ProdutosVenda, Double> totalProdCol;
    @FXML
    private Label totalLab;
    
    private ObservableList<ProdutosVenda> produtosVendidos;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        decimalFormat = new DecimalFormat("#0.00");
    }    
    
    public void initializeWithData(Vendedor seller, LocalDate date, Pagamento payment, List<ProdutosVenda> products) {
        vendedorLab.setText(seller.getNome());
        dataLab.setText(date.toString());
        pagamentoLab.setText(payment.toString());

        // Set up the table view with the products data
        produtosVendidos = FXCollections.observableArrayList(products);
        produtosTable.setItems(produtosVendidos);

        // Set up the table columns
        descCol.setCellValueFactory(new PropertyValueFactory<>("descProduto"));
        precoUniCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
        quantCol.setCellValueFactory(cellData -> {
            ProdutosVenda product = cellData.getValue();
            int quant = product.getQuantidade().getValue();
            return new SimpleIntegerProperty(quant).asObject();
        });
        totalProdCol.setCellValueFactory(cellData -> {
            ProdutosVenda product = cellData.getValue();
            double totalPrice = Double.parseDouble(decimalFormat.format(product.getPreco() * product.getQuantidade().getValue()));
            return new SimpleDoubleProperty(totalPrice).asObject();
        });

        
        double total = 0.0;
        for (ProdutosVenda product : products) {
            total += product.getPreco() * product.getQuantidade().getValue();
        }

        
        totalLab.setText(decimalFormat.format( total));
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
