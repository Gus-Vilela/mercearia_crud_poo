/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.VendedorDAO;
import entidades.Vendedor;
import static java.lang.Double.parseDouble;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeListener;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaVendedoresController implements Initializable {
    private VendedorDAO banco;
    private short flag;
    private Vendedor vendedor;
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
    private TextField inputNome;
    @FXML
    private TextField inputSalario;
    @FXML
    private TextField inputComissao;
    @FXML
    private Button adicionar;
    
    @FXML
    private Text reqNome;
    @FXML
    private Text reqComissao;
    @FXML
    private Text reqSalario;
    @FXML
    private Text campoObri;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         try {
            banco = new VendedorDAO();    // TODO
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na inicialização do banco",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        initializeTable();
    }    
    
    
    private void initializeTable(){
        idCol.setCellValueFactory(new PropertyValueFactory<>("codvendedor"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        salarioCol.setCellValueFactory(new PropertyValueFactory<>("salariobase"));
        comissaoCol.setCellValueFactory(new PropertyValueFactory<>("perccomissao"));
        
        tableVendedores.setItems(FXCollections.observableArrayList(banco.getAll()));
    }
    
    
    
    @FXML
    private void adicionarVendedor(){
        flag = 0;
        if ("".equals(inputNome.getText())){
            campoObri.setOpacity(1);
            inputNome.setStyle("-fx-border-color:red;");
            flag = 1;
        }else{inputNome.setStyle("-fx-border-color:grey;");}
        if ("".equals(inputSalario.getText())){
            campoObri.setOpacity(1);
            inputSalario.setStyle("-fx-border-color:red;");
            flag = 1;
        }else{inputSalario.setStyle("-fx-border-color:grey;");}
        if ("".equals(inputComissao.getText())){
            campoObri.setOpacity(1);
            inputComissao.setStyle("-fx-border-color:red;");
            flag = 1;
        }else{inputComissao.setStyle("-fx-border-color:grey;");}
        
        if(flag == 0){
            try{
                vendedor = new Vendedor();
                vendedor.setNome(inputNome.getText());
                vendedor.setSalariobase(parseDouble(inputSalario.getText()));
                vendedor.setPerccomissao(parseDouble(inputComissao.getText()));
                
                banco.add(vendedor);
                
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Cadastro Realizado");
                alert.setHeaderText(null);
                alert.setContentText("Vendedor Cadastrado!");
                alert.showAndWait();
                initializeTable();
                
            }catch(Exception e){
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
            }
        }
        
    }
}
