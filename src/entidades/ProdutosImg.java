/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author gusta
 */
public class ProdutosImg {
    private Integer codproduto;
    
    private String descProduto;
    
    private double preco;
   
    private ImageView imagem;
   
    private String unidadeMedida;
 

    

    public ProdutosImg(Integer codproduto, String descProduto, double preco, String imagem, String unidadeMedida) {
        this.codproduto = codproduto;
        this.descProduto = descProduto;
        this.preco = preco;
        this.imagem = new ImageView(new Image("file:build/classes/telas/media/"+imagem,50,50,true,true));
        this.unidadeMedida = unidadeMedida;
 
    }

    public Integer getCodproduto() {
        return codproduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public double getPreco() {
        return preco;
    }

    public ImageView getImagem() {
        return imagem;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setCodproduto(Integer codproduto) {
        this.codproduto = codproduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public void setImagem(ImageView imagem) {
        this.imagem = imagem;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }
    
}
