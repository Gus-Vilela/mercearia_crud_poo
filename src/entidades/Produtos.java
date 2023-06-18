/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;


import java.io.Serializable;
import java.util.Collection;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gusta
 */
@Entity
@Table(name = "produtos")
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p"),
    @NamedQuery(name = "Produtos.findByCodproduto", query = "SELECT p FROM Produtos p WHERE p.codproduto = :codproduto"),
    @NamedQuery(name = "Produtos.findByDescProduto", query = "SELECT p FROM Produtos p WHERE p.descProduto = :descProduto"),
    @NamedQuery(name = "Produtos.findByPreco", query = "SELECT p FROM Produtos p WHERE p.preco = :preco"),
    @NamedQuery(name = "Produtos.findByImagem", query = "SELECT p FROM Produtos p WHERE p.imagem = :imagem"),
    @NamedQuery(name = "Produtos.findByUnidadeMedida", query = "SELECT p FROM Produtos p WHERE p.unidadeMedida = :unidadeMedida")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODPRODUTO")
    private Integer codproduto;
    @Column(name = "DESC_PRODUTO")
    private String descProduto;
    @Basic(optional = false)
    @Column(name = "PRECO")
    private double preco;
    @Column(name = "IMAGEM")
    private String imagem;
    @Basic(optional = false)
    @Column(name = "UNIDADE_MEDIDA")
    private String unidadeMedida;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtos")
    private Collection<Produtosvendidos> produtosvendidosCollection;
    
    
    public Produtos() {
    }

    public Produtos(Integer codproduto) {
        this.codproduto = codproduto;
    }

    public Produtos(Integer codproduto, double preco, String unidadeMedida) {
        this.codproduto = codproduto;
        this.preco = preco;
        this.unidadeMedida = unidadeMedida;
    }

    public Integer getCodproduto() {
        return codproduto;
    }

    public void setCodproduto(Integer codproduto) {
        this.codproduto = codproduto;
    }

    public String getDescProduto() {
        return descProduto;
    }

    public void setDescProduto(String descProduto) {
        this.descProduto = descProduto;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getUnidadeMedida() {
        return unidadeMedida;
    }

    public void setUnidadeMedida(String unidadeMedida) {
        this.unidadeMedida = unidadeMedida;
    }

    public Collection<Produtosvendidos> getProdutosvendidosCollection() {
        return produtosvendidosCollection;
    }

    public void setProdutosvendidosCollection(Collection<Produtosvendidos> produtosvendidosCollection) {
        this.produtosvendidosCollection = produtosvendidosCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codproduto != null ? codproduto.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.codproduto == null && other.codproduto != null) || (this.codproduto != null && !this.codproduto.equals(other.codproduto))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Produtos[ codproduto=" + codproduto + " ]";
    }
    
}
