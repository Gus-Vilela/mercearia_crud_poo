/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Vendedor;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontroles.VendedorJpaController;
import jpacontroles.exceptions.NonexistentEntityException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import static org.eclipse.persistence.internal.sessions.coordination.corba.sun.SunCORBAConnectionHelper.id;

/**
 *
 * @author gusta
 */
public class VendedorDAO {
    private VendedorJpaController objetoJPA;
    private EntityManagerFactory emf;

    public VendedorDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new VendedorJpaController(emf);
    }
    
    public void add(Vendedor objeto){
        try {
            objetoJPA.create(objeto);
        } catch (PersistenceException ex) {
            System.out.println("PersistenceException: " + ex.getMessage());  
        }
    }

    public void edit(Vendedor objeto) throws Exception{
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Vendedor " + objeto + " não existe.", ex);
        }
    }

    public void delete(Integer id) throws Exception{
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Vendedor " + id + " não existe.", ex);
        }
    }

    public List<Vendedor> getAll(){
        return objetoJPA.findVendedorEntities();
    }
    
    public Vendedor getVendedor(Integer id){
        return objetoJPA.findVendedor(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public VendedorJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
    public double calcularSalario(Vendedor vendedor, int year, int month) {
        EntityManager em = getEmf().createEntityManager();
        // Create a native SQL query that joins the tables and calculates the sum
        String sql = "SELECT SUM(p.PRECO * pv.QUANTIDADE) FROM PRODUTOS p " +
                     "JOIN PRODUTOSVENDIDOS pv ON p.CODPRODUTO = pv.CODPRODUTO " +
                     "JOIN VENDA v ON pv.CODVENDA = v.CODVENDA " +
                     "JOIN VENDEDOR ve ON v.CODVENDEDOR = ve.CODVENDEDOR " +
                     "WHERE ve.CODVENDEDOR = ? AND MONTH(v.DATAVENDA) = ? AND YEAR(v.DATAVENDA) = ?";
        // Create a Query object with the SQL query and set the parameters
        Query query = em.createNativeQuery(sql);
        query.setParameter(1, vendedor.getCodvendedor());
        query.setParameter(2, month);
        query.setParameter(3, year);
        // Execute the query and get the result as a Double object
        Double result = (Double) query.getSingleResult();
        // Close the EntityManager
        em.close();
        // If the result is null, return zero
        if (result == null) {
            result = 0.0;
        }
        double salaryBase = vendedor.getSalariobase();
        double commission = vendedor.getPerccomissao();
        return salaryBase + (result * commission);
        
    }

}
