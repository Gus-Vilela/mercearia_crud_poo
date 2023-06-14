package app;


import DAO.ProdutosDAO;
import DAO.ProdutosvendidosDAO;
import DAO.VendaDAO;
import DAO.VendedorDAO;
import entidades.Pagamento;
import entidades.Produtos;
import entidades.Produtosvendidos;
import entidades.Venda;
import entidades.Vendedor;
import java.time.LocalDate;
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
////////        
//        ProdutosDAO produtosDAO = new ProdutosDAO();
//        Produtos produto = new Produtos();
//        produto.setDescProduto("Miojo");
//        produto.setImagem("/pathToImage");
//        produto.setPreco(3.2);
//        produto.setUnidadeMedida("Unid.");
//        produtosDAO.add(produto);
//        
        VendaDAO vendaDAO = new VendaDAO();
//        Venda venda = new Venda();
//        venda.setCodvendedor(vendedor);
//        venda.setFormapagto(Pagamento.CARTAO_DE_CREDITO);
//        venda.setDatavenda(LocalDate.parse("2019-02-11"));
//        vendaDAO.add(venda);
    
        
//        ProdutosvendidosDAO produtosvendidosDAO = new ProdutosvendidosDAO();
//        Produtosvendidos pv = new Produtosvendidos();
//        pv.setProdutos(produtosDAO.getProdutos(1));
//        pv.setQuantidade(1);
//        pv.setVenda(vendaDAO.getVenda(1));
//        produtosvendidosDAO.add(pv);
        
        Venda venda = vendaDAO.getVenda(1);
        System.out.println(venda.getFormapagto());
        System.out.println(venda.getDatavenda());


//        List<Produtos> lista =  produtosDAO.getAll();
//        System.out.println(lista);

          
    }
}
