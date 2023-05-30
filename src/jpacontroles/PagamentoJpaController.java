/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacontroles;

import entidades.Pagamento;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Vendadiaria;
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
public class PagamentoJpaController implements Serializable {

    public PagamentoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pagamento pagamento) {
        if (pagamento.getVendadiariaCollection() == null) {
            pagamento.setVendadiariaCollection(new ArrayList<Vendadiaria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Vendadiaria> attachedVendadiariaCollection = new ArrayList<Vendadiaria>();
            for (Vendadiaria vendadiariaCollectionVendadiariaToAttach : pagamento.getVendadiariaCollection()) {
                vendadiariaCollectionVendadiariaToAttach = em.getReference(vendadiariaCollectionVendadiariaToAttach.getClass(), vendadiariaCollectionVendadiariaToAttach.getCodvenda());
                attachedVendadiariaCollection.add(vendadiariaCollectionVendadiariaToAttach);
            }
            pagamento.setVendadiariaCollection(attachedVendadiariaCollection);
            em.persist(pagamento);
            for (Vendadiaria vendadiariaCollectionVendadiaria : pagamento.getVendadiariaCollection()) {
                Pagamento oldCodpagamentoOfVendadiariaCollectionVendadiaria = vendadiariaCollectionVendadiaria.getCodpagamento();
                vendadiariaCollectionVendadiaria.setCodpagamento(pagamento);
                vendadiariaCollectionVendadiaria = em.merge(vendadiariaCollectionVendadiaria);
                if (oldCodpagamentoOfVendadiariaCollectionVendadiaria != null) {
                    oldCodpagamentoOfVendadiariaCollectionVendadiaria.getVendadiariaCollection().remove(vendadiariaCollectionVendadiaria);
                    oldCodpagamentoOfVendadiariaCollectionVendadiaria = em.merge(oldCodpagamentoOfVendadiariaCollectionVendadiaria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pagamento pagamento) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento persistentPagamento = em.find(Pagamento.class, pagamento.getCodpagamento());
            Collection<Vendadiaria> vendadiariaCollectionOld = persistentPagamento.getVendadiariaCollection();
            Collection<Vendadiaria> vendadiariaCollectionNew = pagamento.getVendadiariaCollection();
            List<String> illegalOrphanMessages = null;
            for (Vendadiaria vendadiariaCollectionOldVendadiaria : vendadiariaCollectionOld) {
                if (!vendadiariaCollectionNew.contains(vendadiariaCollectionOldVendadiaria)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Vendadiaria " + vendadiariaCollectionOldVendadiaria + " since its codpagamento field is not nullable.");
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
            pagamento.setVendadiariaCollection(vendadiariaCollectionNew);
            pagamento = em.merge(pagamento);
            for (Vendadiaria vendadiariaCollectionNewVendadiaria : vendadiariaCollectionNew) {
                if (!vendadiariaCollectionOld.contains(vendadiariaCollectionNewVendadiaria)) {
                    Pagamento oldCodpagamentoOfVendadiariaCollectionNewVendadiaria = vendadiariaCollectionNewVendadiaria.getCodpagamento();
                    vendadiariaCollectionNewVendadiaria.setCodpagamento(pagamento);
                    vendadiariaCollectionNewVendadiaria = em.merge(vendadiariaCollectionNewVendadiaria);
                    if (oldCodpagamentoOfVendadiariaCollectionNewVendadiaria != null && !oldCodpagamentoOfVendadiariaCollectionNewVendadiaria.equals(pagamento)) {
                        oldCodpagamentoOfVendadiariaCollectionNewVendadiaria.getVendadiariaCollection().remove(vendadiariaCollectionNewVendadiaria);
                        oldCodpagamentoOfVendadiariaCollectionNewVendadiaria = em.merge(oldCodpagamentoOfVendadiariaCollectionNewVendadiaria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = pagamento.getCodpagamento();
                if (findPagamento(id) == null) {
                    throw new NonexistentEntityException("The pagamento with id " + id + " no longer exists.");
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
            Pagamento pagamento;
            try {
                pagamento = em.getReference(Pagamento.class, id);
                pagamento.getCodpagamento();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pagamento with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Vendadiaria> vendadiariaCollectionOrphanCheck = pagamento.getVendadiariaCollection();
            for (Vendadiaria vendadiariaCollectionOrphanCheckVendadiaria : vendadiariaCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Pagamento (" + pagamento + ") cannot be destroyed since the Vendadiaria " + vendadiariaCollectionOrphanCheckVendadiaria + " in its vendadiariaCollection field has a non-nullable codpagamento field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(pagamento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pagamento> findPagamentoEntities() {
        return findPagamentoEntities(true, -1, -1);
    }

    public List<Pagamento> findPagamentoEntities(int maxResults, int firstResult) {
        return findPagamentoEntities(false, maxResults, firstResult);
    }

    private List<Pagamento> findPagamentoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pagamento.class));
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

    public Pagamento findPagamento(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pagamento.class, id);
        } finally {
            em.close();
        }
    }

    public int getPagamentoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pagamento> rt = cq.from(Pagamento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
