package app;


import DAO.ProdutosDAO;
import DAO.ProdutosvendidosDAO;
import DAO.VendaDAO;
import DAO.VendedorDAO;
import entidades.Produtos;
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
        VendedorDAO vendedorDAO = new VendedorDAO();
//        Vendedor vendedor = new Vendedor();
//        vendedor.setNome("Joe");
//        vendedor.setPerccomissao(12.00);
//        vendedor.setSalariobase(1300.00);
//        vendedorDAO.add(vendedor);
       
        ProdutosDAO produtosDAO = new ProdutosDAO();
//        Produtos produto = new Produtos();
//        produto.setDescProduto("Ovo");
//        produto.setImagem("/pathToImage");
//        produto.setPreco(0.5);
//        produto.setUnidadeMedida("Unid.");
//        produtosDAO.add(produto);
       
        VendaDAO vendaDAO = new VendaDAO();
//        Venda venda = new Venda();
//        venda.setCodvendedor(vendedorDAO.getVendedor(1));
//        venda.setFormapagto(Pagamento.DEPOSITO);
//        venda.setDatavenda(LocalDate.of(2023, 5, 19));
//        vendaDAO.add(venda);

        ProdutosvendidosDAO produtosvendidosDAO = new ProdutosvendidosDAO();
//        Produtosvendidos pv = new Produtosvendidos();
//        pv.setProdutos(produtosDAO.getProdutos(3));
//        pv.setQuantidade(12);
//        pv.setVenda(vendaDAO.getVenda(3));
//        produtosvendidosDAO.add(pv);
        
//        Venda venda = vendaDAO.getVenda(1);
//        System.out.println(venda.getFormapagto());
//        System.out.println(venda.getDatavenda());


        List<Produtos> lista =  produtosDAO.getAll();
        System.out.println(lista);

          
    }
}
