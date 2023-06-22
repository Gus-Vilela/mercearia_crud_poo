/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package fxcontroles;

import DAO.VendedorDAO;
import entidades.Vendedor;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author gusta
 */
public class TelaNovoVendedorController implements Initializable {
    private VendedorDAO banco;
    private Vendedor vendedor;
    private short flag;
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField inputNome;
    @FXML
    private TextField inputSalario;
    @FXML
    private TextField inputComissao;
    @FXML
    private Text campoObri;
    @FXML
    private Text reqSalario;
    @FXML
    private Text reqSalario1;
    @FXML
    private Text reqSalario2;
    @FXML
    private Button addBtn;
    private Text titleText;
    @FXML
    private Button TelaVendedores;
    @FXML
    private Label idLab;
    @FXML
    private TextField inputId;
    @FXML
    private Text titleText1;
    @FXML
    private Text titleText2;
    

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
    }    
    
    public void initializeWithData(Vendedor vendedor){
       this.vendedor = vendedor;
       titleText1.setOpacity(0);
       titleText2.setOpacity(1);
       idLab.setOpacity(1);
       inputId.setOpacity(1);
       inputId.setText(""+vendedor.getCodvendedor());
       inputNome.setText(vendedor.getNome());
       inputSalario.setText(vendedor.getSalariobase()+"");
       inputComissao.setText(vendedor.getPerccomissao()+"");
       addBtn.setText("Editar");
    };

    @FXML
    private void addVendor(ActionEvent event){
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
                if(vendedor == null){
                    vendedor = new Vendedor();
                    vendedor.setNome(inputNome.getText());
                    vendedor.setSalariobase(parseDouble(inputSalario.getText()));
                    vendedor.setPerccomissao(parseDouble(inputComissao.getText()));

                    banco.add(vendedor);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Cadastro Realizado");
                    alert.setHeaderText(null);
                    alert.setContentText("Vendedor Cadastrado!");
                    alert.showAndWait();
                }else{
                    vendedor.setNome(inputNome.getText());
                    vendedor.setSalariobase(parseDouble(inputSalario.getText()));
                    vendedor.setPerccomissao(parseDouble(inputComissao.getText()));
                    
                    banco.edit(vendedor);
                    
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Atualização Realizada");
                    alert.setHeaderText(null);
                    alert.setContentText("Vendedor Editado!");
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
