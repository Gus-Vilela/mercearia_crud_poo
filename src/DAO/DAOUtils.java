/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author gusta
 */
public class DAOUtils {
    private static EntityManagerFactory ENTITY_MANAGER_FACTORY = null;
    
    public static EntityManagerFactory getEntityManagerFac(){
        if(ENTITY_MANAGER_FACTORY == null){
            ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("MerceariaPU");
           
        }
        return ENTITY_MANAGER_FACTORY;
    }
}
