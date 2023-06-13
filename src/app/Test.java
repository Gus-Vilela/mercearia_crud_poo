package app;


import DAO.ProdutosDAO;
import DAO.VendedorDAO;
import entidades.Produtos;
import entidades.Vendedor;
import java.util.List;
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author gusta
 */
public class Test {
    public static void main(String[] args) {
//        VendedorDAO vendedorDAO = new VendedorDAO();
//        Vendedor vendedor = new Vendedor();
//        vendedor.setNome("Gus");
//        vendedor.setPerccomissao(15.00);
//        vendedor.setSalariobase(1500.00);
//        vendedorDAO.add(vendedor);
//        
        ProdutosDAO produtosDAO = new ProdutosDAO();
        Produtos produto = new Produtos();
        produto.setDescProduto("Banana");
        produto.setImagem("/pathToImage");
        produto.setPreco(1.8);
        produto.setUnidadeMedida("Kg");
        produtosDAO.add(produto);
        List<Produtos> lista =  produtosDAO.getAll();
        System.out.println(lista);
    }
}
