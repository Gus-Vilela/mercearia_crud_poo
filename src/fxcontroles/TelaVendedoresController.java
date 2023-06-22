/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.VendedorDAO;
import entidades.Vendedor;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaVendedoresController implements Initializable {
    private VendedorDAO banco;
    private Vendedor vendedor;
    private Stage stage;
    private Scene scene;
    private Parent root;
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
    @FXML
    private Button TelaPrincipal;
    

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
        try{
        idCol.setCellValueFactory(new PropertyValueFactory<>("codvendedor"));
        nomeCol.setCellValueFactory(new PropertyValueFactory<>("nome"));
        salarioCol.setCellValueFactory(new PropertyValueFactory<>("salariobase"));
        comissaoCol.setCellValueFactory(new PropertyValueFactory<>("perccomissao"));
        
        tableVendedores.setItems(FXCollections.observableArrayList(banco.getAll()));
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
    public void editVendor(ActionEvent event){
        try{
            vendedor = tableVendedores.getSelectionModel().getSelectedItem();

            if(vendedor != null){    
                FXMLLoader carregador = new FXMLLoader(getClass().getClassLoader()
                        .getResource("./telas/TelaNovoVendedor.fxml"));
                root = carregador.load();        
                TelaNovoVendedorController controller = carregador.getController();
                controller.initializeWithData(vendedor);
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção");
                alert.setHeaderText("Nenhum vendedor selecionado!");
                alert.setContentText("Selecione o vendedor que deseja excluir.");
                alert.showAndWait();
            }
        }catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText(null);
            alert.setContentText("Erro: " + e.getMessage());
            alert.showAndWait();
        }
    }
    @FXML
    public void deleteVendor(){
        try{
            vendedor = tableVendedores.getSelectionModel().getSelectedItem();
            if(vendedor != null){
                Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
                alerta.setTitle("Atenção");
                alerta.setHeaderText("Confirmação de exclusão");
                alerta.setContentText("Tem certeza que deseja excluir o vendedor " + vendedor.getNome());
                Optional<ButtonType> escolha = alerta.showAndWait();

                if(escolha.isPresent() && escolha.get() == ButtonType.OK){
                    VendedorDAO vendedorDAO = new VendedorDAO();
                    vendedorDAO.delete(vendedor.getCodvendedor());
                    initializeTable();
                }
            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Atenção");
                alert.setHeaderText("Nenhum vendedor selecionado!");
                alert.setContentText("Selecione o vendedor que deseja excluir.");
                alert.showAndWait();
            }
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
