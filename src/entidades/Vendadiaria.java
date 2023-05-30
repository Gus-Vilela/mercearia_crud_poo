/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gusta
 */
@Entity
@Table(name = "vendadiaria")
@NamedQueries({
    @NamedQuery(name = "Vendadiaria.findAll", query = "SELECT v FROM Vendadiaria v"),
    @NamedQuery(name = "Vendadiaria.findByCodvenda", query = "SELECT v FROM Vendadiaria v WHERE v.codvenda = :codvenda"),
    @NamedQuery(name = "Vendadiaria.findByValortotal", query = "SELECT v FROM Vendadiaria v WHERE v.valortotal = :valortotal"),
    @NamedQuery(name = "Vendadiaria.findByCodproduto", query = "SELECT v FROM Vendadiaria v WHERE v.codproduto = :codproduto")})
public class Vendadiaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODVENDA")
    private Integer codvenda;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "VALORTOTAL")
    private Double valortotal;
    @Basic(optional = false)
    @Column(name = "CODPRODUTO")
    private int codproduto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vendadiaria")
    private Collection<Produtosvendidos> produtosvendidosCollection;
    @JoinColumn(name = "CODPAGAMENTO", referencedColumnName = "CODPAGAMENTO")
    @ManyToOne(optional = false)
    private Pagamento codpagamento;
    @JoinColumn(name = "CODVENDEDOR", referencedColumnName = "CODVENDEDOR")
    @ManyToOne(optional = false)
    private Vendedor codvendedor;

    public Vendadiaria() {
    }

    public Vendadiaria(Integer codvenda) {
        this.codvenda = codvenda;
    }

    public Vendadiaria(Integer codvenda, int codproduto) {
        this.codvenda = codvenda;
        this.codproduto = codproduto;
    }

    public Integer getCodvenda() {
        return codvenda;
    }

    public void setCodvenda(Integer codvenda) {
        this.codvenda = codvenda;
    }

    public Double getValortotal() {
        return valortotal;
    }

    public void setValortotal(Double valortotal) {
        this.valortotal = valortotal;
    }

    public int getCodproduto() {
        return codproduto;
    }

    public void setCodproduto(int codproduto) {
        this.codproduto = codproduto;
    }

    public Collection<Produtosvendidos> getProdutosvendidosCollection() {
        return produtosvendidosCollection;
    }

    public void setProdutosvendidosCollection(Collection<Produtosvendidos> produtosvendidosCollection) {
        this.produtosvendidosCollection = produtosvendidosCollection;
    }

    public Pagamento getCodpagamento() {
        return codpagamento;
    }

    public void setCodpagamento(Pagamento codpagamento) {
        this.codpagamento = codpagamento;
    }

    public Vendedor getCodvendedor() {
        return codvendedor;
    }

    public void setCodvendedor(Vendedor codvendedor) {
        this.codvendedor = codvendedor;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codvenda != null ? codvenda.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendadiaria)) {
            return false;
        }
        Vendadiaria other = (Vendadiaria) object;
        if ((this.codvenda == null && other.codvenda != null) || (this.codvenda != null && !this.codvenda.equals(other.codvenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Vendadiaria[ codvenda=" + codvenda + " ]";
    }
    
}
