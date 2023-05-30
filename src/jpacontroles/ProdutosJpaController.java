/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jpacontroles;

import entidades.Produtos;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import entidades.Produtosvendidos;
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
public class ProdutosJpaController implements Serializable {

    public ProdutosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produtos produtos) {
        if (produtos.getProdutosvendidosCollection() == null) {
            produtos.setProdutosvendidosCollection(new ArrayList<Produtosvendidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Produtosvendidos> attachedProdutosvendidosCollection = new ArrayList<Produtosvendidos>();
            for (Produtosvendidos produtosvendidosCollectionProdutosvendidosToAttach : produtos.getProdutosvendidosCollection()) {
                produtosvendidosCollectionProdutosvendidosToAttach = em.getReference(produtosvendidosCollectionProdutosvendidosToAttach.getClass(), produtosvendidosCollectionProdutosvendidosToAttach.getProdutosvendidosPK());
                attachedProdutosvendidosCollection.add(produtosvendidosCollectionProdutosvendidosToAttach);
            }
            produtos.setProdutosvendidosCollection(attachedProdutosvendidosCollection);
            em.persist(produtos);
            for (Produtosvendidos produtosvendidosCollectionProdutosvendidos : produtos.getProdutosvendidosCollection()) {
                Produtos oldProdutosOfProdutosvendidosCollectionProdutosvendidos = produtosvendidosCollectionProdutosvendidos.getProdutos();
                produtosvendidosCollectionProdutosvendidos.setProdutos(produtos);
                produtosvendidosCollectionProdutosvendidos = em.merge(produtosvendidosCollectionProdutosvendidos);
                if (oldProdutosOfProdutosvendidosCollectionProdutosvendidos != null) {
                    oldProdutosOfProdutosvendidosCollectionProdutosvendidos.getProdutosvendidosCollection().remove(produtosvendidosCollectionProdutosvendidos);
                    oldProdutosOfProdutosvendidosCollectionProdutosvendidos = em.merge(oldProdutosOfProdutosvendidosCollectionProdutosvendidos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produtos produtos) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtos persistentProdutos = em.find(Produtos.class, produtos.getCodproduto());
            Collection<Produtosvendidos> produtosvendidosCollectionOld = persistentProdutos.getProdutosvendidosCollection();
            Collection<Produtosvendidos> produtosvendidosCollectionNew = produtos.getProdutosvendidosCollection();
            List<String> illegalOrphanMessages = null;
            for (Produtosvendidos produtosvendidosCollectionOldProdutosvendidos : produtosvendidosCollectionOld) {
                if (!produtosvendidosCollectionNew.contains(produtosvendidosCollectionOldProdutosvendidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produtosvendidos " + produtosvendidosCollectionOldProdutosvendidos + " since its produtos field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Produtosvendidos> attachedProdutosvendidosCollectionNew = new ArrayList<Produtosvendidos>();
            for (Produtosvendidos produtosvendidosCollectionNewProdutosvendidosToAttach : produtosvendidosCollectionNew) {
                produtosvendidosCollectionNewProdutosvendidosToAttach = em.getReference(produtosvendidosCollectionNewProdutosvendidosToAttach.getClass(), produtosvendidosCollectionNewProdutosvendidosToAttach.getProdutosvendidosPK());
                attachedProdutosvendidosCollectionNew.add(produtosvendidosCollectionNewProdutosvendidosToAttach);
            }
            produtosvendidosCollectionNew = attachedProdutosvendidosCollectionNew;
            produtos.setProdutosvendidosCollection(produtosvendidosCollectionNew);
            produtos = em.merge(produtos);
            for (Produtosvendidos produtosvendidosCollectionNewProdutosvendidos : produtosvendidosCollectionNew) {
                if (!produtosvendidosCollectionOld.contains(produtosvendidosCollectionNewProdutosvendidos)) {
                    Produtos oldProdutosOfProdutosvendidosCollectionNewProdutosvendidos = produtosvendidosCollectionNewProdutosvendidos.getProdutos();
                    produtosvendidosCollectionNewProdutosvendidos.setProdutos(produtos);
                    produtosvendidosCollectionNewProdutosvendidos = em.merge(produtosvendidosCollectionNewProdutosvendidos);
                    if (oldProdutosOfProdutosvendidosCollectionNewProdutosvendidos != null && !oldProdutosOfProdutosvendidosCollectionNewProdutosvendidos.equals(produtos)) {
                        oldProdutosOfProdutosvendidosCollectionNewProdutosvendidos.getProdutosvendidosCollection().remove(produtosvendidosCollectionNewProdutosvendidos);
                        oldProdutosOfProdutosvendidosCollectionNewProdutosvendidos = em.merge(oldProdutosOfProdutosvendidosCollectionNewProdutosvendidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = produtos.getCodproduto();
                if (findProdutos(id) == null) {
                    throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.");
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
            Produtos produtos;
            try {
                produtos = em.getReference(Produtos.class, id);
                produtos.getCodproduto();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtos with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Produtosvendidos> produtosvendidosCollectionOrphanCheck = produtos.getProdutosvendidosCollection();
            for (Produtosvendidos produtosvendidosCollectionOrphanCheckProdutosvendidos : produtosvendidosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Produtos (" + produtos + ") cannot be destroyed since the Produtosvendidos " + produtosvendidosCollectionOrphanCheckProdutosvendidos + " in its produtosvendidosCollection field has a non-nullable produtos field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(produtos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produtos> findProdutosEntities() {
        return findProdutosEntities(true, -1, -1);
    }

    public List<Produtos> findProdutosEntities(int maxResults, int firstResult) {
        return findProdutosEntities(false, maxResults, firstResult);
    }

    private List<Produtos> findProdutosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produtos.class));
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

    public Produtos findProdutos(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produtos> rt = cq.from(Produtos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
