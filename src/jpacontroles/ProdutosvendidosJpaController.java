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
import entidades.Produtos;
import entidades.Produtosvendidos;
import entidades.ProdutosvendidosPK;
import entidades.Vendadiaria;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import jpacontroles.exceptions.NonexistentEntityException;
import jpacontroles.exceptions.PreexistingEntityException;

/**
 *
 * @author gusta
 */
public class ProdutosvendidosJpaController implements Serializable {

    public ProdutosvendidosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produtosvendidos produtosvendidos) throws PreexistingEntityException, Exception {
        if (produtosvendidos.getProdutosvendidosPK() == null) {
            produtosvendidos.setProdutosvendidosPK(new ProdutosvendidosPK());
        }
        produtosvendidos.getProdutosvendidosPK().setCodvenda(produtosvendidos.getVendadiaria().getCodvenda());
        produtosvendidos.getProdutosvendidosPK().setCodproduto(produtosvendidos.getProdutos().getCodproduto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtos produtos = produtosvendidos.getProdutos();
            if (produtos != null) {
                produtos = em.getReference(produtos.getClass(), produtos.getCodproduto());
                produtosvendidos.setProdutos(produtos);
            }
            Vendadiaria vendadiaria = produtosvendidos.getVendadiaria();
            if (vendadiaria != null) {
                vendadiaria = em.getReference(vendadiaria.getClass(), vendadiaria.getCodvenda());
                produtosvendidos.setVendadiaria(vendadiaria);
            }
            em.persist(produtosvendidos);
            if (produtos != null) {
                produtos.getProdutosvendidosCollection().add(produtosvendidos);
                produtos = em.merge(produtos);
            }
            if (vendadiaria != null) {
                vendadiaria.getProdutosvendidosCollection().add(produtosvendidos);
                vendadiaria = em.merge(vendadiaria);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProdutosvendidos(produtosvendidos.getProdutosvendidosPK()) != null) {
                throw new PreexistingEntityException("Produtosvendidos " + produtosvendidos + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Produtosvendidos produtosvendidos) throws NonexistentEntityException, Exception {
        produtosvendidos.getProdutosvendidosPK().setCodvenda(produtosvendidos.getVendadiaria().getCodvenda());
        produtosvendidos.getProdutosvendidosPK().setCodproduto(produtosvendidos.getProdutos().getCodproduto());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtosvendidos persistentProdutosvendidos = em.find(Produtosvendidos.class, produtosvendidos.getProdutosvendidosPK());
            Produtos produtosOld = persistentProdutosvendidos.getProdutos();
            Produtos produtosNew = produtosvendidos.getProdutos();
            Vendadiaria vendadiariaOld = persistentProdutosvendidos.getVendadiaria();
            Vendadiaria vendadiariaNew = produtosvendidos.getVendadiaria();
            if (produtosNew != null) {
                produtosNew = em.getReference(produtosNew.getClass(), produtosNew.getCodproduto());
                produtosvendidos.setProdutos(produtosNew);
            }
            if (vendadiariaNew != null) {
                vendadiariaNew = em.getReference(vendadiariaNew.getClass(), vendadiariaNew.getCodvenda());
                produtosvendidos.setVendadiaria(vendadiariaNew);
            }
            produtosvendidos = em.merge(produtosvendidos);
            if (produtosOld != null && !produtosOld.equals(produtosNew)) {
                produtosOld.getProdutosvendidosCollection().remove(produtosvendidos);
                produtosOld = em.merge(produtosOld);
            }
            if (produtosNew != null && !produtosNew.equals(produtosOld)) {
                produtosNew.getProdutosvendidosCollection().add(produtosvendidos);
                produtosNew = em.merge(produtosNew);
            }
            if (vendadiariaOld != null && !vendadiariaOld.equals(vendadiariaNew)) {
                vendadiariaOld.getProdutosvendidosCollection().remove(produtosvendidos);
                vendadiariaOld = em.merge(vendadiariaOld);
            }
            if (vendadiariaNew != null && !vendadiariaNew.equals(vendadiariaOld)) {
                vendadiariaNew.getProdutosvendidosCollection().add(produtosvendidos);
                vendadiariaNew = em.merge(vendadiariaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ProdutosvendidosPK id = produtosvendidos.getProdutosvendidosPK();
                if (findProdutosvendidos(id) == null) {
                    throw new NonexistentEntityException("The produtosvendidos with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProdutosvendidosPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Produtosvendidos produtosvendidos;
            try {
                produtosvendidos = em.getReference(Produtosvendidos.class, id);
                produtosvendidos.getProdutosvendidosPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The produtosvendidos with id " + id + " no longer exists.", enfe);
            }
            Produtos produtos = produtosvendidos.getProdutos();
            if (produtos != null) {
                produtos.getProdutosvendidosCollection().remove(produtosvendidos);
                produtos = em.merge(produtos);
            }
            Vendadiaria vendadiaria = produtosvendidos.getVendadiaria();
            if (vendadiaria != null) {
                vendadiaria.getProdutosvendidosCollection().remove(produtosvendidos);
                vendadiaria = em.merge(vendadiaria);
            }
            em.remove(produtosvendidos);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Produtosvendidos> findProdutosvendidosEntities() {
        return findProdutosvendidosEntities(true, -1, -1);
    }

    public List<Produtosvendidos> findProdutosvendidosEntities(int maxResults, int firstResult) {
        return findProdutosvendidosEntities(false, maxResults, firstResult);
    }

    private List<Produtosvendidos> findProdutosvendidosEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Produtosvendidos.class));
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

    public Produtosvendidos findProdutosvendidos(ProdutosvendidosPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtosvendidos.class, id);
        } finally {
            em.close();
        }
    }

    public int getProdutosvendidosCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Produtosvendidos> rt = cq.from(Produtosvendidos.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
