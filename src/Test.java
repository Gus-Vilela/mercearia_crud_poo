
import DAO.VendedorDAO;
import entidades.Vendedor;
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
        Vendedor vendedor = new Vendedor();
        vendedor.setNome("Jao");
        vendedor.setPerccomissao(10.00);
        vendedor.setSalariobase(1200.00);
        vendedorDAO.add(vendedor);
    }
}
