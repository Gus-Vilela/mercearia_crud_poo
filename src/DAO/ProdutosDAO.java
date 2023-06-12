/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Produtos;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import jpacontroles.ProdutosJpaController;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class ProdutosDAO {
    private ProdutosJpaController objetoJPA;
    private EntityManagerFactory emf;

    public ProdutosDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new ProdutosJpaController(emf);
    }
    
    public void add(Produtos objeto){
        try {
            objetoJPA.create(objeto);
        } catch (PersistenceException ex) {
            System.out.println("PersistenceException: " + ex.getMessage());  
        }
    }

    public void edit(Produtos objeto) throws Exception{
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Produto " + objeto + " não existe.", ex);
        }
    }

    public void delete(Integer id) throws Exception{
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Produto " + id + " não existe.", ex);
        }
    }

    public List<Produtos> getAll(){
        return objetoJPA.findProdutosEntities();
    }
    
    public Produtos getProdutos(Integer id){
        return objetoJPA.findProdutos(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public ProdutosJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
}
