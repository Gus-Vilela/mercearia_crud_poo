/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacontroles;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Vendadiaria;
import entidades.Vendedor;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontroles.exceptions.IllegalOrphanException;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class VendedorJpaController implements Serializable {

    public VendedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendedor vendedor) {
        if (vendedor.getVendadiariaCollection() == null) {
            vendedor.setVendadiariaCollection(new ArrayList<Vendadiaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vendadiaria> attachedVendadiariaCollection = new ArrayList<Vendadiaria>();
            for (Vendadiaria vendadiariaCollectionVendadiariaToAttach : vendedor.getVendadiariaCollection()) {
                vendadiariaCollectionVendadiariaToAttach = em.getReference(vendadiariaCollectionVendadiariaToAttach.getClass(), vendadiariaCollectionVendadiariaToAttach.getCodvenda());
                attachedVendadiariaCollection.add(vendadiariaCollectionVendadiariaToAttach);
            }
            vendedor.setVendadiariaCollection(attachedVendadiariaCollection);
            em.persist(vendedor);
            for (Vendadiaria vendadiariaCollectionVendadiaria : vendedor.getVendadiariaCollection()) {
                Vendedor oldCodvendedorOfVendadiariaCollectionVendadiaria = vendadiariaCollectionVendadiaria.getCodvendedor();
                vendadiariaCollectionVendadiaria.setCodvendedor(vendedor);
                vendadiariaCollectionVendadiaria = em.merge(vendadiariaCollectionVendadiaria);
                if (oldCodvendedorOfVendadiariaCollectionVendadiaria != null) {
                    oldCodvendedorOfVendadiariaCollectionVendadiaria.getVendadiariaCollection().remove(vendadiariaCollectionVendadiaria);
                    oldCodvendedorOfVendadiariaCollectionVendadiaria = em.merge(oldCodvendedorOfVendadiariaCollectionVendadiaria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendedor vendedor) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor persistentVendedor = em.find(Vendedor.class, vendedor.getCodvendedor());
            Collection<Vendadiaria> vendadiariaCollectionOld = persistentVendedor.getVendadiariaCollection();
            Collection<Vendadiaria> vendadiariaCollectionNew = vendedor.getVendadiariaCollection();
            List<String> illegalOrphanMessages = null;
            for (Vendadiaria vendadiariaCollectionOldVendadiaria : vendadiariaCollectionOld) {
                if (!vendadiariaCollectionNew.contains(vendadiariaCollectionOldVendadiaria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vendadiaria " + vendadiariaCollectionOldVendadiaria + " since its codvendedor field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Vendadiaria> attachedVendadiariaCollectionNew = new ArrayList<Vendadiaria>();
            for (Vendadiaria vendadiariaCollectionNewVendadiariaToAttach : vendadiariaCollectionNew) {
                vendadiariaCollectionNewVendadiariaToAttach = em.getReference(vendadiariaCollectionNewVendadiariaToAttach.getClass(), vendadiariaCollectionNewVendadiariaToAttach.getCodvenda());
                attachedVendadiariaCollectionNew.add(vendadiariaCollectionNewVendadiariaToAttach);
            }
            vendadiariaCollectionNew = attachedVendadiariaCollectionNew;
            vendedor.setVendadiariaCollection(vendadiariaCollectionNew);
            vendedor = em.merge(vendedor);
            for (Vendadiaria vendadiariaCollectionNewVendadiaria : vendadiariaCollectionNew) {
                if (!vendadiariaCollectionOld.contains(vendadiariaCollectionNewVendadiaria)) {
                    Vendedor oldCodvendedorOfVendadiariaCollectionNewVendadiaria = vendadiariaCollectionNewVendadiaria.getCodvendedor();
                    vendadiariaCollectionNewVendadiaria.setCodvendedor(vendedor);
                    vendadiariaCollectionNewVendadiaria = em.merge(vendadiariaCollectionNewVendadiaria);
                    if (oldCodvendedorOfVendadiariaCollectionNewVendadiaria != null && !oldCodvendedorOfVendadiariaCollectionNewVendadiaria.equals(vendedor)) {
                        oldCodvendedorOfVendadiariaCollectionNewVendadiaria.getVendadiariaCollection().remove(vendadiariaCollectionNewVendadiaria);
                        oldCodvendedorOfVendadiariaCollectionNewVendadiaria = em.merge(oldCodvendedorOfVendadiariaCollectionNewVendadiaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendedor.getCodvendedor();
                if (findVendedor(id) == null) {
                    throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor vendedor;
            try {
                vendedor = em.getReference(Vendedor.class, id);
                vendedor.getCodvendedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendedor with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vendadiaria> vendadiariaCollectionOrphanCheck = vendedor.getVendadiariaCollection();
            for (Vendadiaria vendadiariaCollectionOrphanCheckVendadiaria : vendadiariaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vendedor (" + vendedor + ") cannot be destroyed since the Vendadiaria " + vendadiariaCollectionOrphanCheckVendadiaria + " in its vendadiariaCollection field has a non-nullable codvendedor field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(vendedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendedor> findVendedorEntities() {
        return findVendedorEntities(true, -1, -1);
    }

    public List<Vendedor> findVendedorEntities(int maxResults, int firstResult) {
        return findVendedorEntities(false, maxResults, firstResult);
    }

    private List<Vendedor> findVendedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendedor.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Vendedor findVendedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendedor> rt = cq.from(Vendedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
