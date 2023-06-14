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
import entidades.Vendedor;
import entidades.Produtosvendidos;
import entidades.Venda;
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
public class VendaJpaController implements Serializable {

    public VendaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Venda venda) {
        if (venda.getProdutosvendidosCollection() == null) {
            venda.setProdutosvendidosCollection(new ArrayList<Produtosvendidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendedor codvendedor = venda.getCodvendedor();
            if (codvendedor != null) {
                codvendedor = em.getReference(codvendedor.getClass(), codvendedor.getCodvendedor());
                venda.setCodvendedor(codvendedor);
            }
            Collection<Produtosvendidos> attachedProdutosvendidosCollection = new ArrayList<Produtosvendidos>();
            for (Produtosvendidos produtosvendidosCollectionProdutosvendidosToAttach : venda.getProdutosvendidosCollection()) {
                produtosvendidosCollectionProdutosvendidosToAttach = em.getReference(produtosvendidosCollectionProdutosvendidosToAttach.getClass(), produtosvendidosCollectionProdutosvendidosToAttach.getProdutosvendidosPK());
                attachedProdutosvendidosCollection.add(produtosvendidosCollectionProdutosvendidosToAttach);
            }
            venda.setProdutosvendidosCollection(attachedProdutosvendidosCollection);
            em.persist(venda);
            if (codvendedor != null) {
                codvendedor.getVendaCollection().add(venda);
                codvendedor = em.merge(codvendedor);
            }
            for (Produtosvendidos produtosvendidosCollectionProdutosvendidos : venda.getProdutosvendidosCollection()) {
                Venda oldVendaOfProdutosvendidosCollectionProdutosvendidos = produtosvendidosCollectionProdutosvendidos.getVenda();
                produtosvendidosCollectionProdutosvendidos.setVenda(venda);
                produtosvendidosCollectionProdutosvendidos = em.merge(produtosvendidosCollectionProdutosvendidos);
                if (oldVendaOfProdutosvendidosCollectionProdutosvendidos != null) {
                    oldVendaOfProdutosvendidosCollectionProdutosvendidos.getProdutosvendidosCollection().remove(produtosvendidosCollectionProdutosvendidos);
                    oldVendaOfProdutosvendidosCollectionProdutosvendidos = em.merge(oldVendaOfProdutosvendidosCollectionProdutosvendidos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Venda venda) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Venda persistentVenda = em.find(Venda.class, venda.getCodvenda());
            Vendedor codvendedorOld = persistentVenda.getCodvendedor();
            Vendedor codvendedorNew = venda.getCodvendedor();
            Collection<Produtosvendidos> produtosvendidosCollectionOld = persistentVenda.getProdutosvendidosCollection();
            Collection<Produtosvendidos> produtosvendidosCollectionNew = venda.getProdutosvendidosCollection();
            List<String> illegalOrphanMessages = null;
            for (Produtosvendidos produtosvendidosCollectionOldProdutosvendidos : produtosvendidosCollectionOld) {
                if (!produtosvendidosCollectionNew.contains(produtosvendidosCollectionOldProdutosvendidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produtosvendidos " + produtosvendidosCollectionOldProdutosvendidos + " since its venda field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codvendedorNew != null) {
                codvendedorNew = em.getReference(codvendedorNew.getClass(), codvendedorNew.getCodvendedor());
                venda.setCodvendedor(codvendedorNew);
            }
            Collection<Produtosvendidos> attachedProdutosvendidosCollectionNew = new ArrayList<Produtosvendidos>();
            for (Produtosvendidos produtosvendidosCollectionNewProdutosvendidosToAttach : produtosvendidosCollectionNew) {
                produtosvendidosCollectionNewProdutosvendidosToAttach = em.getReference(produtosvendidosCollectionNewProdutosvendidosToAttach.getClass(), produtosvendidosCollectionNewProdutosvendidosToAttach.getProdutosvendidosPK());
                attachedProdutosvendidosCollectionNew.add(produtosvendidosCollectionNewProdutosvendidosToAttach);
            }
            produtosvendidosCollectionNew = attachedProdutosvendidosCollectionNew;
            venda.setProdutosvendidosCollection(produtosvendidosCollectionNew);
            venda = em.merge(venda);
            if (codvendedorOld != null && !codvendedorOld.equals(codvendedorNew)) {
                codvendedorOld.getVendaCollection().remove(venda);
                codvendedorOld = em.merge(codvendedorOld);
            }
            if (codvendedorNew != null && !codvendedorNew.equals(codvendedorOld)) {
                codvendedorNew.getVendaCollection().add(venda);
                codvendedorNew = em.merge(codvendedorNew);
            }
            for (Produtosvendidos produtosvendidosCollectionNewProdutosvendidos : produtosvendidosCollectionNew) {
                if (!produtosvendidosCollectionOld.contains(produtosvendidosCollectionNewProdutosvendidos)) {
                    Venda oldVendaOfProdutosvendidosCollectionNewProdutosvendidos = produtosvendidosCollectionNewProdutosvendidos.getVenda();
                    produtosvendidosCollectionNewProdutosvendidos.setVenda(venda);
                    produtosvendidosCollectionNewProdutosvendidos = em.merge(produtosvendidosCollectionNewProdutosvendidos);
                    if (oldVendaOfProdutosvendidosCollectionNewProdutosvendidos != null && !oldVendaOfProdutosvendidosCollectionNewProdutosvendidos.equals(venda)) {
                        oldVendaOfProdutosvendidosCollectionNewProdutosvendidos.getProdutosvendidosCollection().remove(produtosvendidosCollectionNewProdutosvendidos);
                        oldVendaOfProdutosvendidosCollectionNewProdutosvendidos = em.merge(oldVendaOfProdutosvendidosCollectionNewProdutosvendidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = venda.getCodvenda();
                if (findVenda(id) == null) {
                    throw new NonexistentEntityException("The venda with id " + id + " no longer exists.");
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
            Venda venda;
            try {
                venda = em.getReference(Venda.class, id);
                venda.getCodvenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The venda with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Produtosvendidos> produtosvendidosCollectionOrphanCheck = venda.getProdutosvendidosCollection();
            for (Produtosvendidos produtosvendidosCollectionOrphanCheckProdutosvendidos : produtosvendidosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Venda (" + venda + ") cannot be destroyed since the Produtosvendidos " + produtosvendidosCollectionOrphanCheckProdutosvendidos + " in its produtosvendidosCollection field has a non-nullable venda field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Vendedor codvendedor = venda.getCodvendedor();
            if (codvendedor != null) {
                codvendedor.getVendaCollection().remove(venda);
                codvendedor = em.merge(codvendedor);
            }
            em.remove(venda);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Venda> findVendaEntities() {
        return findVendaEntities(true, -1, -1);
    }

    public List<Venda> findVendaEntities(int maxResults, int firstResult) {
        return findVendaEntities(false, maxResults, firstResult);
    }

    private List<Venda> findVendaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Venda.class));
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

    public Venda findVenda(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Venda.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Venda> rt = cq.from(Venda.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
