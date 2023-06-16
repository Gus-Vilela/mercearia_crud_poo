/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "venda")
@NamedQueries({
    @NamedQuery(name = "Venda.findAll", query = "SELECT v FROM Venda v"),
    @NamedQuery(name = "Venda.findByCodvenda", query = "SELECT v FROM Venda v WHERE v.codvenda = :codvenda"),
    @NamedQuery(name = "Venda.findByFormapagto", query = "SELECT v FROM Venda v WHERE v.formapagto = :formapagto"),
    @NamedQuery(name = "Venda.findByDatavenda", query = "SELECT v FROM Venda v WHERE v.datavenda = :datavenda")})
public class Venda implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODVENDA")
    private Integer codvenda;
    @Enumerated(EnumType.ORDINAL)
    @Basic(optional = false)
    @Column(name = "FORMAPAGTO")
    private Pagamento formapagto;
    @Basic(optional = false)
    @Column(name = "DATAVENDA", columnDefinition = "DATE")
    private LocalDate datavenda;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "venda")
    private Collection<Produtosvendidos> produtosvendidosCollection;
    @JoinColumn(name = "CODVENDEDOR", referencedColumnName = "CODVENDEDOR")
    @ManyToOne(optional = false)
    private Vendedor codvendedor;

    public Venda() {
    }

    public Venda(Integer codvenda) {
        this.codvenda = codvenda;
    }

    public Venda(Integer codvenda, Pagamento formapagto, LocalDate datavenda) {
        this.codvenda = codvenda;
        this.formapagto = formapagto;
        this.datavenda = datavenda;
    }

    public Integer getCodvenda() {
        return codvenda;
    }

    public void setCodvenda(Integer codvenda) {
        this.codvenda = codvenda;
    }

    public Pagamento getFormapagto() {
        return formapagto;
    }

    public void setFormapagto(Pagamento formapagto) {
        this.formapagto = formapagto;
    }

    public LocalDate getDatavenda() {
        return datavenda;
    }

    public void setDatavenda(LocalDate datavenda) {
        this.datavenda = datavenda;
    }

    public Collection<Produtosvendidos> getProdutosvendidosCollection() {
        return produtosvendidosCollection;
    }

    public void setProdutosvendidosCollection(Collection<Produtosvendidos> produtosvendidosCollection) {
        this.produtosvendidosCollection = produtosvendidosCollection;
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
        if (!(object instanceof Venda)) {
            return false;
        }
        Venda other = (Venda) object;
        if ((this.codvenda == null && other.codvenda != null) || (this.codvenda != null && !this.codvenda.equals(other.codvenda))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Venda[ codvenda=" + codvenda + " ]";
    }
    
}
