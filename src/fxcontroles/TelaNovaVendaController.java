/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.ProdutosDAO;
import DAO.ProdutosvendidosDAO;
import DAO.VendaDAO;
import DAO.VendedorDAO;
import entidades.Pagamento;
import entidades.Produtos;
import entidades.ProdutosVenda;
import entidades.Produtosvendidos;
import entidades.Venda;
import entidades.Vendedor;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaNovaVendaController implements Initializable {
    private Stage stage;
    private Scene scene;
    private Parent root;
    private short flag;
    private VendedorDAO vendedorDAO;
    private VendaDAO vendaDAO;
    private Venda venda;
    private ProdutosDAO produtosDAO;
    private ProdutosvendidosDAO produtosvendidosDAO;
    private List<ProdutosVenda> listaVenda;
    @FXML
    private ComboBox<Vendedor> vendedorBox;
    @FXML
    private DatePicker dataVenda;
    @FXML
    private ComboBox<Pagamento> pagamentoBox;
    @FXML
    private TableView<ProdutosVenda> tableProdutos;
    @FXML
    private TableColumn<ProdutosVenda, String> prodCol;
    @FXML
    private TableColumn<ProdutosVenda, Double> precoCol;
    @FXML
    private TableColumn<ProdutosVenda, ComboBox> quantCol;
    @FXML
    private TableColumn<ProdutosVenda, CheckBox> addCol;
    @FXML
    private Button TelaRelatorio;
    @FXML
    private TableColumn<ProdutosVenda, String> medidaCol;
    @FXML
    private TableColumn<ProdutosVenda, ImageView> imgCol;
    @FXML
    private Text campoObri;
    @FXML
    private Button TelaVendas;
    

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

            pagamentoBox.getItems().addAll(Pagamento.values());

            if (!pagamentoBox.getItems().isEmpty()) {
                pagamentoBox.setValue(pagamentoBox.getItems().get(0));
            }

            dataVenda.setValue(LocalDate.now());

            initializeTable();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e.getMessage(), "Erro na inicialização do banco",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
  
    }    
    
    private void initializeTable(){
        try{
        produtosDAO = new ProdutosDAO();
        List<Produtos> lista= produtosDAO.getAll();
        listaVenda = new ArrayList<ProdutosVenda>();
        for(Produtos produto : lista){
            listaVenda.add(new ProdutosVenda(produto.getCodproduto(),
                    produto.getDescProduto(),produto.getPreco(),produto.getImagem(),
                    produto.getUnidadeMedida())); 
        }
        
        imgCol.setCellValueFactory(new PropertyValueFactory<ProdutosVenda, ImageView>("imagem"));
        prodCol.setCellValueFactory(new PropertyValueFactory<>("descProduto"));
        precoCol.setCellValueFactory(new PropertyValueFactory<>("preco"));
        medidaCol.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantidade"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("check"));
        
        tableProdutos.setItems(FXCollections.observableArrayList(listaVenda));
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
    private void addSale(ActionEvent event) {
        flag = 0;
        for(ProdutosVenda produto : listaVenda){
            if(produto.getCheck().isSelected() == true){
               flag = 1;
               break;
            }
        }
        if (dataVenda.getValue() == null){
            campoObri.setOpacity(1);
            dataVenda.setStyle("-fx-border-color:red;");
        }else if(flag == 1){dataVenda.setStyle("-fx-border-color:grey;");
            try{
                vendaDAO = new VendaDAO();
                venda = new Venda();
                venda.setCodvendedor(vendedorDAO.getVendedor(vendedorBox.getValue().getCodvendedor()));
                venda.setFormapagto(pagamentoBox.getValue());
                venda.setDatavenda(dataVenda.getValue());
//              vendaDAO.add(venda);
                
                produtosvendidosDAO = new ProdutosvendidosDAO();
                for(ProdutosVenda produto : listaVenda){
                    if(produto.getCheck().isSelected() == true){
                       Produtosvendidos pv = new Produtosvendidos();
                       pv.setProdutos(produtosDAO.getProdutos(produto.getCodproduto()));
                       pv.setQuantidade(produto.getQuantidade().getValue());
                       pv.setVenda(venda);
//                     produtosvendidosDAO.add(pv);
                    }                          
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Cadastro Realizado");
                alert.setHeaderText(null);
                alert.setContentText("Venda Cadastrada!");
                alert.showAndWait();
                
                switchScene(event);
                
            }catch(Exception e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erro");
                alert.setHeaderText(null);
                alert.setContentText("Erro: " + e.getMessage());
                alert.showAndWait();
            }
        }else{dataVenda.setStyle("-fx-border-color:grey;");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Atenção");
            alert.setHeaderText(null);
            alert.setContentText("Selecione pelo menos 1 produto");
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
        
        if (nomeTela.equals("TelaRelatorioVenda")) {
            TelaRelatorioVendaController controller = carregador.getController();
            controller.initializeWithData(vendedorBox.getValue(), dataVenda.getValue(),
                    pagamentoBox.getValue(), listaVenda);
        }
        
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
