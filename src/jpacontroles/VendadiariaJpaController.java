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
import entidades.Pagamento;
import entidades.Vendedor;
import entidades.Produtosvendidos;
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
public class VendadiariaJpaController implements Serializable {

    public VendadiariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Vendadiaria vendadiaria) {
        if (vendadiaria.getProdutosvendidosCollection() == null) {
            vendadiaria.setProdutosvendidosCollection(new ArrayList<Produtosvendidos>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pagamento codpagamento = vendadiaria.getCodpagamento();
            if (codpagamento != null) {
                codpagamento = em.getReference(codpagamento.getClass(), codpagamento.getCodpagamento());
                vendadiaria.setCodpagamento(codpagamento);
            }
            Vendedor codvendedor = vendadiaria.getCodvendedor();
            if (codvendedor != null) {
                codvendedor = em.getReference(codvendedor.getClass(), codvendedor.getCodvendedor());
                vendadiaria.setCodvendedor(codvendedor);
            }
            Collection<Produtosvendidos> attachedProdutosvendidosCollection = new ArrayList<Produtosvendidos>();
            for (Produtosvendidos produtosvendidosCollectionProdutosvendidosToAttach : vendadiaria.getProdutosvendidosCollection()) {
                produtosvendidosCollectionProdutosvendidosToAttach = em.getReference(produtosvendidosCollectionProdutosvendidosToAttach.getClass(), produtosvendidosCollectionProdutosvendidosToAttach.getProdutosvendidosPK());
                attachedProdutosvendidosCollection.add(produtosvendidosCollectionProdutosvendidosToAttach);
            }
            vendadiaria.setProdutosvendidosCollection(attachedProdutosvendidosCollection);
            em.persist(vendadiaria);
            if (codpagamento != null) {
                codpagamento.getVendadiariaCollection().add(vendadiaria);
                codpagamento = em.merge(codpagamento);
            }
            if (codvendedor != null) {
                codvendedor.getVendadiariaCollection().add(vendadiaria);
                codvendedor = em.merge(codvendedor);
            }
            for (Produtosvendidos produtosvendidosCollectionProdutosvendidos : vendadiaria.getProdutosvendidosCollection()) {
                Vendadiaria oldVendadiariaOfProdutosvendidosCollectionProdutosvendidos = produtosvendidosCollectionProdutosvendidos.getVendadiaria();
                produtosvendidosCollectionProdutosvendidos.setVendadiaria(vendadiaria);
                produtosvendidosCollectionProdutosvendidos = em.merge(produtosvendidosCollectionProdutosvendidos);
                if (oldVendadiariaOfProdutosvendidosCollectionProdutosvendidos != null) {
                    oldVendadiariaOfProdutosvendidosCollectionProdutosvendidos.getProdutosvendidosCollection().remove(produtosvendidosCollectionProdutosvendidos);
                    oldVendadiariaOfProdutosvendidosCollectionProdutosvendidos = em.merge(oldVendadiariaOfProdutosvendidosCollectionProdutosvendidos);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Vendadiaria vendadiaria) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Vendadiaria persistentVendadiaria = em.find(Vendadiaria.class, vendadiaria.getCodvenda());
            Pagamento codpagamentoOld = persistentVendadiaria.getCodpagamento();
            Pagamento codpagamentoNew = vendadiaria.getCodpagamento();
            Vendedor codvendedorOld = persistentVendadiaria.getCodvendedor();
            Vendedor codvendedorNew = vendadiaria.getCodvendedor();
            Collection<Produtosvendidos> produtosvendidosCollectionOld = persistentVendadiaria.getProdutosvendidosCollection();
            Collection<Produtosvendidos> produtosvendidosCollectionNew = vendadiaria.getProdutosvendidosCollection();
            List<String> illegalOrphanMessages = null;
            for (Produtosvendidos produtosvendidosCollectionOldProdutosvendidos : produtosvendidosCollectionOld) {
                if (!produtosvendidosCollectionNew.contains(produtosvendidosCollectionOldProdutosvendidos)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Produtosvendidos " + produtosvendidosCollectionOldProdutosvendidos + " since its vendadiaria field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (codpagamentoNew != null) {
                codpagamentoNew = em.getReference(codpagamentoNew.getClass(), codpagamentoNew.getCodpagamento());
                vendadiaria.setCodpagamento(codpagamentoNew);
            }
            if (codvendedorNew != null) {
                codvendedorNew = em.getReference(codvendedorNew.getClass(), codvendedorNew.getCodvendedor());
                vendadiaria.setCodvendedor(codvendedorNew);
            }
            Collection<Produtosvendidos> attachedProdutosvendidosCollectionNew = new ArrayList<Produtosvendidos>();
            for (Produtosvendidos produtosvendidosCollectionNewProdutosvendidosToAttach : produtosvendidosCollectionNew) {
                produtosvendidosCollectionNewProdutosvendidosToAttach = em.getReference(produtosvendidosCollectionNewProdutosvendidosToAttach.getClass(), produtosvendidosCollectionNewProdutosvendidosToAttach.getProdutosvendidosPK());
                attachedProdutosvendidosCollectionNew.add(produtosvendidosCollectionNewProdutosvendidosToAttach);
            }
            produtosvendidosCollectionNew = attachedProdutosvendidosCollectionNew;
            vendadiaria.setProdutosvendidosCollection(produtosvendidosCollectionNew);
            vendadiaria = em.merge(vendadiaria);
            if (codpagamentoOld != null && !codpagamentoOld.equals(codpagamentoNew)) {
                codpagamentoOld.getVendadiariaCollection().remove(vendadiaria);
                codpagamentoOld = em.merge(codpagamentoOld);
            }
            if (codpagamentoNew != null && !codpagamentoNew.equals(codpagamentoOld)) {
                codpagamentoNew.getVendadiariaCollection().add(vendadiaria);
                codpagamentoNew = em.merge(codpagamentoNew);
            }
            if (codvendedorOld != null && !codvendedorOld.equals(codvendedorNew)) {
                codvendedorOld.getVendadiariaCollection().remove(vendadiaria);
                codvendedorOld = em.merge(codvendedorOld);
            }
            if (codvendedorNew != null && !codvendedorNew.equals(codvendedorOld)) {
                codvendedorNew.getVendadiariaCollection().add(vendadiaria);
                codvendedorNew = em.merge(codvendedorNew);
            }
            for (Produtosvendidos produtosvendidosCollectionNewProdutosvendidos : produtosvendidosCollectionNew) {
                if (!produtosvendidosCollectionOld.contains(produtosvendidosCollectionNewProdutosvendidos)) {
                    Vendadiaria oldVendadiariaOfProdutosvendidosCollectionNewProdutosvendidos = produtosvendidosCollectionNewProdutosvendidos.getVendadiaria();
                    produtosvendidosCollectionNewProdutosvendidos.setVendadiaria(vendadiaria);
                    produtosvendidosCollectionNewProdutosvendidos = em.merge(produtosvendidosCollectionNewProdutosvendidos);
                    if (oldVendadiariaOfProdutosvendidosCollectionNewProdutosvendidos != null && !oldVendadiariaOfProdutosvendidosCollectionNewProdutosvendidos.equals(vendadiaria)) {
                        oldVendadiariaOfProdutosvendidosCollectionNewProdutosvendidos.getProdutosvendidosCollection().remove(produtosvendidosCollectionNewProdutosvendidos);
                        oldVendadiariaOfProdutosvendidosCollectionNewProdutosvendidos = em.merge(oldVendadiariaOfProdutosvendidosCollectionNewProdutosvendidos);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = vendadiaria.getCodvenda();
                if (findVendadiaria(id) == null) {
                    throw new NonexistentEntityException("The vendadiaria with id " + id + " no longer exists.");
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
            Vendadiaria vendadiaria;
            try {
                vendadiaria = em.getReference(Vendadiaria.class, id);
                vendadiaria.getCodvenda();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The vendadiaria with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Produtosvendidos> produtosvendidosCollectionOrphanCheck = vendadiaria.getProdutosvendidosCollection();
            for (Produtosvendidos produtosvendidosCollectionOrphanCheckProdutosvendidos : produtosvendidosCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Vendadiaria (" + vendadiaria + ") cannot be destroyed since the Produtosvendidos " + produtosvendidosCollectionOrphanCheckProdutosvendidos + " in its produtosvendidosCollection field has a non-nullable vendadiaria field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Pagamento codpagamento = vendadiaria.getCodpagamento();
            if (codpagamento != null) {
                codpagamento.getVendadiariaCollection().remove(vendadiaria);
                codpagamento = em.merge(codpagamento);
            }
            Vendedor codvendedor = vendadiaria.getCodvendedor();
            if (codvendedor != null) {
                codvendedor.getVendadiariaCollection().remove(vendadiaria);
                codvendedor = em.merge(codvendedor);
            }
            em.remove(vendadiaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Vendadiaria> findVendadiariaEntities() {
        return findVendadiariaEntities(true, -1, -1);
    }

    public List<Vendadiaria> findVendadiariaEntities(int maxResults, int firstResult) {
        return findVendadiariaEntities(false, maxResults, firstResult);
    }

    private List<Vendadiaria> findVendadiariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Vendadiaria.class));
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

    public Vendadiaria findVendadiaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Vendadiaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getVendadiariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Vendadiaria> rt = cq.from(Vendadiaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
