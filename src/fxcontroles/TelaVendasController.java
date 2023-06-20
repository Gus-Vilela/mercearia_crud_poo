/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.VendaDAO;
import entidades.Venda;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaVendasController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private VendaDAO banco;
    @FXML
    private TableColumn<Venda, Integer> idCol;
    @FXML
    private TableColumn<Venda, String> pagCol;
    @FXML
    private TableColumn<Venda, LocalDate> dataCol;
    @FXML
    private TableColumn<Venda, String> vendedorCol;
    @FXML
    private TableView<Venda> tableVendas;
    @FXML
    private Button TelaNovaVenda;
    @FXML
    private Button TelaPrincipal;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            banco = new VendaDAO();    // TODO
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na inicialização do banco",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        initializeTable();
    }    
    
    private void initializeTable(){
        try{
        idCol.setCellValueFactory(new PropertyValueFactory<>("codvenda"));
        pagCol.setCellValueFactory(new PropertyValueFactory<>("formapagto"));
        dataCol.setCellValueFactory(new PropertyValueFactory<>("datavenda"));
        vendedorCol.setCellValueFactory(cellData -> {
            Venda venda = cellData.getValue();
            String nomeVendedor = venda.getCodvendedor().getNome();
            return new SimpleStringProperty(nomeVendedor);
        });
        
        tableVendas.setItems(FXCollections.observableArrayList(banco.getAll()));
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
