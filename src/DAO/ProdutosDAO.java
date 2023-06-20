/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import entidades.Produtos;
import entidades.Produtosvendidos;
import entidades.Venda;
import java.time.LocalDate;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;
import jpacontroles.ProdutosJpaController;
import jpacontroles.exceptions.NonexistentEntityException;

/**
 *
 * @author gusta
 */
public class ProdutosDAO {
    private ProdutosJpaController objetoJPA;
    private EntityManagerFactory emf;

    public ProdutosDAO() {
        emf = DAOUtils.getEntityManagerFac();
        objetoJPA = new ProdutosJpaController(emf);
    }
    
    public void add(Produtos objeto){
        try {
            objetoJPA.create(objeto);
        } catch (PersistenceException ex) {
            System.out.println("PersistenceException: " + ex.getMessage());  
        }
    }

    public void edit(Produtos objeto) throws Exception{
        try {
            objetoJPA.edit(objeto);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Produto " + objeto + " não existe.", ex);
        }
    }

    public void delete(Integer id) throws Exception{
        try {
            objetoJPA.destroy(id);
        } catch (NonexistentEntityException ex) {
            throw new Exception("Produto " + id + " não existe.", ex);
        }
    }

    public List<Produtos> getAll(){
        return objetoJPA.findProdutosEntities();
    }
    
    public Produtos getProdutos(Integer id){
        return objetoJPA.findProdutos(id);
    }
    
    /**
     * @return the objetoJPA
     */
    public ProdutosJpaController getObjetoJPA() {
        return objetoJPA;
    }

    /**
     * @return the emf
     */
    public EntityManagerFactory getEmf() {
        return emf;
    }
    
    public List<ProdutoVendido> getProdutosVendidosNaData(LocalDate dataEspecifica) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<ProdutoVendido> query = cb.createQuery(ProdutoVendido.class);
            Root<Produtosvendidos> root = query.from(Produtosvendidos.class);

            Join<Produtosvendidos, Produtos> produtoJoin = root.join("produtos", JoinType.INNER);
            Join<Produtosvendidos, Venda> vendaJoin = root.join("venda", JoinType.INNER);

            Expression<Double> totalValor = cb.prod(produtoJoin.get("preco"), cb.sum(root.get("quantidade")));
            Expression<Double> roundedTotalValor = (Expression<Double>) cb.function("ROUND", Double.class, totalValor, cb.literal(2)).alias("totalValor");

            query.multiselect(
                produtoJoin.get("codproduto"),
                produtoJoin.get("descProduto"),
                cb.sum(root.get("quantidade")).alias("totalQuantidade"),
                roundedTotalValor
            );

            query.where(cb.equal(vendaJoin.get("datavenda"), dataEspecifica));
            query.groupBy(produtoJoin.get("codproduto"), produtoJoin.get("descProduto"));

            TypedQuery<ProdutoVendido> typedQuery = em.createQuery(query);
            return typedQuery.getResultList();
        } finally {
            em.close();
        }
    }

    public double getTotalValorVendidoNaData(LocalDate dataEspecifica) {
        EntityManager em = emf.createEntityManager();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Double> query = cb.createQuery(Double.class);
            Root<Produtosvendidos> root = query.from(Produtosvendidos.class);

            Join<Produtosvendidos, Produtos> produtoJoin = root.join("produtos", JoinType.INNER);
            Join<Produtosvendidos, Venda> vendaJoin = root.join("venda", JoinType.INNER);

            query.select(cb.sum(cb.prod(produtoJoin.get("preco"), root.get("quantidade"))));
            query.where(cb.equal(vendaJoin.get("datavenda"), dataEspecifica));

            TypedQuery<Double> typedQuery = em.createQuery(query);
            Double resultado = typedQuery.getSingleResult();
            return resultado != null ? resultado : 0.0;
        } finally {
            em.close();
        }
    }

    public static class ProdutoVendido {
        private Integer codProduto;
        private String descProduto;
        private int totalQuantidade;
        private double totalValor;

        public ProdutoVendido(Integer codProduto, String descProduto, int totalQuantidade, double totalValor) {
            this.codProduto = codProduto;
            this.descProduto = descProduto;
            this.totalQuantidade = totalQuantidade;
            this.totalValor = totalValor;
        }

        public Integer getCodProduto() {
            return codProduto;
        }

        public String getDescProduto() {
            return descProduto;
        }

        public int getTotalQuantidade() {
            return totalQuantidade;
        }

        public double getTotalValor() {
            return totalValor;
        }
    }
}
