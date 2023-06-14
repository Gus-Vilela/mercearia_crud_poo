/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Venda;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import jpacontroles.VendaJpaController;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class VendaDAO {
    private VendaJpaController objetoJPA;
    private EntityManagerFactory emf;

    public VendaDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new VendaJpaController(emf);
    }
    
    public void add(Venda objeto){
        try {
            objetoJPA.create(objeto);
        } catch (PersistenceException ex) {
            System.out.println("PersistenceException: " + ex.getMessage());  
        }
    }

    public void edit(Venda objeto) throws Exception{
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Venda diária " + objeto + " não existe.", ex);
        }
    }

    public void delete(Integer id) throws Exception{
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Venda diária " + id + " não existe.", ex);
        }
    }

    public List<Venda> getAll(){
        return objetoJPA.findVendaEntities();
    }
    
    public Venda getVenda(Integer id){
        return objetoJPA.findVenda(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public VendaJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
}
