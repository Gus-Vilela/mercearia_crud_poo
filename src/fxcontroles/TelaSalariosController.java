/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.VendedorDAO;
import entidades.Vendedor;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.List;
import java.util.ResourceBundle;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaSalariosController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private VendedorDAO vendedorDAO;
    @FXML
    private Button calculaSalario;
    @FXML
    private Button TelaVendedores;
    @FXML
    private Label salarioTotal;
    @FXML
    private ComboBox<Vendedor> vendedorBox;
    @FXML
    private ComboBox<Integer> mesBox;
    @FXML
    private ComboBox<Integer> anoBox;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            
        
        vendedorDAO = new VendedorDAO();
        List<Vendedor> allVendors = vendedorDAO.getAll();
        vendedorBox.getItems().addAll(allVendors);

        if (!allVendors.isEmpty()) {
            vendedorBox.setValue(allVendors.get(0));
        }
        initializeMesBox();
        initializeAnoBox();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na inicialização do banco",
   JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void initializeMesBox() {
        ObservableList<Integer> meses = FXCollections.observableArrayList();
        for (int i = 1; i <= 12; i++) {
            meses.add(i);
        }
        mesBox.setItems(meses);
        mesBox.setValue(1);
    }
    
    private void initializeAnoBox() {
        ObservableList<Integer> anos = FXCollections.observableArrayList();
        for (int i = 2023; i >= 2000; i--) {
            anos.add(i);
        }
        anoBox.setItems(anos);
        anoBox.setValue(2023);
    }
    
    @FXML 
    public void calcularSalario(){
        try{
            Double total = vendedorDAO.calcularSalario(vendedorBox.getValue(), anoBox.getValue(), mesBox.getValue());
            DecimalFormat decimalFormat = new DecimalFormat("#0.00");
            salarioTotal.setText("R$"+decimalFormat.format(total));
            
        }catch(Exception e){
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
