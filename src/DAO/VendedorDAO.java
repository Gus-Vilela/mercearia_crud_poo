/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Vendedor;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import jpacontroles.VendedorJpaController;
import jpacontroles.exceptions.NonexistentEntityException;
import javax.persistence.PersistenceException;

/**
 *
 * @author gusta
 */
public class VendedorDAO {
    private VendedorJpaController objetoJPA;
    private EntityManagerFactory emf;

    public VendedorDAO() {
        emf = Persistence.createEntityManagerFactory("MerceariaPU");
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
    
}
