/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author gusta
 */
@Entity
@Table(name = "produtosvendidos")
@NamedQueries({
    @NamedQuery(name = "Produtosvendidos.findAll", query = "SELECT p FROM Produtosvendidos p"),
    @NamedQuery(name = "Produtosvendidos.findByCodproduto", query = "SELECT p FROM Produtosvendidos p WHERE p.produtosvendidosPK.codproduto = :codproduto"),
    @NamedQuery(name = "Produtosvendidos.findByCodvenda", query = "SELECT p FROM Produtosvendidos p WHERE p.produtosvendidosPK.codvenda = :codvenda"),
    @NamedQuery(name = "Produtosvendidos.findByQuantidade", query = "SELECT p FROM Produtosvendidos p WHERE p.quantidade = :quantidade")})
public class Produtosvendidos implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ProdutosvendidosPK produtosvendidosPK;
    @Basic(optional = false)
    @Column(name = "QUANTIDADE")
    private int quantidade;
    @JoinColumn(name = "CODPRODUTO", referencedColumnName = "CODPRODUTO", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Produtos produtos;
    @JoinColumn(name = "CODVENDA", referencedColumnName = "CODVENDA", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Venda venda;

    public Produtosvendidos() {
    }

    public Produtosvendidos(ProdutosvendidosPK produtosvendidosPK) {
        this.produtosvendidosPK = produtosvendidosPK;
    }

    public Produtosvendidos(ProdutosvendidosPK produtosvendidosPK, int quantidade) {
        this.produtosvendidosPK = produtosvendidosPK;
        this.quantidade = quantidade;
    }

    public Produtosvendidos(int codproduto, int codvenda) {
        this.produtosvendidosPK = new ProdutosvendidosPK(codproduto, codvenda);
    }

    public ProdutosvendidosPK getProdutosvendidosPK() {
        return produtosvendidosPK;
    }

    public void setProdutosvendidosPK(ProdutosvendidosPK produtosvendidosPK) {
        this.produtosvendidosPK = produtosvendidosPK;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public Produtos getProdutos() {
        return produtos;
    }

    public void setProdutos(Produtos produtos) {
        this.produtos = produtos;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (produtosvendidosPK != null ? produtosvendidosPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtosvendidos)) {
            return false;
        }
        Produtosvendidos other = (Produtosvendidos) object;
        if ((this.produtosvendidosPK == null && other.produtosvendidosPK != null) || (this.produtosvendidosPK != null && !this.produtosvendidosPK.equals(other.produtosvendidosPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Produtosvendidos[ produtosvendidosPK=" + produtosvendidosPK + " ]";
    }
    
}
