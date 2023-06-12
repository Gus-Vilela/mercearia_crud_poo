/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Pagamento;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import jpacontroles.PagamentoJpaController;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class PagamentoDAO {
    private PagamentoJpaController objetoJPA;
    private EntityManagerFactory emf;

    public PagamentoDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new PagamentoJpaController(emf);
    }
    
    public void add(Pagamento objeto){
        try {
            objetoJPA.create(objeto);
        } catch (PersistenceException ex) {
            System.out.println("PersistenceException: " + ex.getMessage());  
        }
    }

    public void edit(Pagamento objeto) throws Exception{
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Pagamento " + objeto + " não existe.", ex);
        }
    }

    public void delete(Integer id) throws Exception{
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Pagamento " + id + " não existe.", ex);
        }
    }

    public List<Pagamento> getAll(){
        return objetoJPA.findPagamentoEntities();
    }
    
    public Pagamento getPagamento(Integer id){
        return objetoJPA.findPagamento(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public PagamentoJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
}
