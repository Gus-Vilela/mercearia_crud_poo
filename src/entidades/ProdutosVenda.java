/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;

/**
 *
 * @author gusta
 */
public class ProdutosVenda {
    private ProdutosImg produtosImg;
    private ComboBox<Integer> quantidade;
    private CheckBox check;

    public ProdutosVenda(Integer codproduto, String descProduto, double preco, String imagem, String unidadeMedida) {
        this.produtosImg = new ProdutosImg(codproduto, descProduto, preco, imagem, unidadeMedida);
        this.quantidade = new ComboBox<>();

        // Initialize ComboBox with values 1-20
        ObservableList<Integer> quantidadeValues = FXCollections.observableArrayList();
        for (int i = 1; i <= 20; i++) {
            quantidadeValues.add(i);
        }
        quantidade.setItems(quantidadeValues);

        // Set default value to 1
        quantidade.setValue(1);

        // Set CheckBox initial value to false
        this.check = new CheckBox();
        check.setSelected(false);
    }

    public Integer getCodproduto() {
        return produtosImg.getCodproduto();
    }

    public String getDescProduto() {
        return produtosImg.getDescProduto();
    }

    public double getPreco() {
        return produtosImg.getPreco();
    }

    public String getUnidadeMedida() {
        return produtosImg.getUnidadeMedida();
    }

    public ComboBox<Integer> getQuantidade() {
        return quantidade;
    }

    public CheckBox getCheck() {
        return check;
    }

    public ImageView getImagem() {
        return produtosImg.getImagem();
    }

    @Override
    public String toString() {
        return "ProdutosVenda{" +
                "codproduto=" + getCodproduto() +
                ", descProduto=" + getDescProduto() +
                ", preco=" + getPreco() +
                ", unidadeMedida=" + getUnidadeMedida() +
                ", quantidade=" + quantidade +
                ", check=" + check +
                '}';
    }
}







