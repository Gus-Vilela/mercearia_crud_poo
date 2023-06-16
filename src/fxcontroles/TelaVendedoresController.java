/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import entidades.Vendedor;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaVendedoresController implements Initializable {

    @FXML
    private TableView<Vendedor> tableVendedores;
    @FXML
    private TableColumn<Vendedor, Integer> idCol;
    @FXML
    private TableColumn<Vendedor, String> nomeCol;
    @FXML
    private TableColumn<Vendedor, Double> salarioCol;
    @FXML
    private TableColumn<Vendedor, Double> comissaoCol;
    @FXML
    private TextField inputId;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField salarioInput;
    @FXML
    private TextField comissaoInput;
    @FXML
    private Button adicionar;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
