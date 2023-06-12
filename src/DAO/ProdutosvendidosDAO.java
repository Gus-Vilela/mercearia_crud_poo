/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Produtosvendidos;
import entidades.ProdutosvendidosPK;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import jpacontroles.ProdutosvendidosJpaController;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class ProdutosvendidosDAO {
    private ProdutosvendidosJpaController objetoJPA;
    private EntityManagerFactory emf;

    public ProdutosvendidosDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new ProdutosvendidosJpaController(emf);
    }
    
    public void add(Produtosvendidos objeto) {
        try {
            objetoJPA.create(objeto);
        } catch (Exception e){
            System.out.println(e);
        } 
    }

    public void edit(Produtosvendidos objeto) throws Exception{
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Produto vendido " + objeto + " não existe.", ex);
        }
    }

    public void delete(ProdutosvendidosPK id) throws Exception{
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Produto vendido " + id + " não existe.", ex);
        }
    }

    public List<Produtosvendidos> getAll(){
        return objetoJPA.findProdutosvendidosEntities();
    }
    
    public Produtosvendidos getProdutosvendidos(ProdutosvendidosPK id){
        return objetoJPA.findProdutosvendidos(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public ProdutosvendidosJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
}
