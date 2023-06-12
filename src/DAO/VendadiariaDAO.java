/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Vendadiaria;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import jpacontroles.VendadiariaJpaController;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class VendadiariaDAO {
    private VendadiariaJpaController objetoJPA;
    private EntityManagerFactory emf;

    public VendadiariaDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new VendadiariaJpaController(emf);
    }
    
    public void add(Vendadiaria objeto){
        try {
            objetoJPA.create(objeto);
        } catch (PersistenceException ex) {
            System.out.println("PersistenceException: " + ex.getMessage());  
        }
    }

    public void edit(Vendadiaria objeto) throws Exception{
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

    public List<Vendadiaria> getAll(){
        return objetoJPA.findVendadiariaEntities();
    }
    
    public Vendadiaria getVendadiaria(Integer id){
        return objetoJPA.findVendadiaria(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public VendadiariaJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
}
