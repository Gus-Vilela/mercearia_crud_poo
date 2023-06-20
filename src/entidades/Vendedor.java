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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author gusta
 */
@Entity
@Table(name = "vendedor")
@NamedQueries({
    @NamedQuery(name = "Vendedor.findAll", query = "SELECT v FROM Vendedor v"),
    @NamedQuery(name = "Vendedor.findByCodvendedor", query = "SELECT v FROM Vendedor v WHERE v.codvendedor = :codvendedor"),
    @NamedQuery(name = "Vendedor.findByNome", query = "SELECT v FROM Vendedor v WHERE v.nome = :nome"),
    @NamedQuery(name = "Vendedor.findBySalariobase", query = "SELECT v FROM Vendedor v WHERE v.salariobase = :salariobase"),
    @NamedQuery(name = "Vendedor.findByPerccomissao", query = "SELECT v FROM Vendedor v WHERE v.perccomissao = :perccomissao")})
public class Vendedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODVENDEDOR")
    private Integer codvendedor;
    @Basic(optional = false)
    @Column(name = "NOME")
    private String nome;
    @Basic(optional = false)
    @Column(name = "SALARIOBASE")
    private double salariobase;
    @Basic(optional = false)
    @Column(name = "PERCCOMISSAO")
    private double perccomissao;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codvendedor")
    private Collection<Venda> vendaCollection;

    public Vendedor() {
    }

    public Vendedor(Integer codvendedor) {
        this.codvendedor = codvendedor;
    }

    public Vendedor(Integer codvendedor, String nome, double salariobase, double perccomissao) {
        this.codvendedor = codvendedor;
        this.nome = nome;
        this.salariobase = salariobase;
        this.perccomissao = perccomissao;
    }

    public Integer getCodvendedor() {
        return codvendedor;
    }

    public void setCodvendedor(Integer codvendedor) {
        this.codvendedor = codvendedor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getSalariobase() {
        return salariobase;
    }

    public void setSalariobase(double salariobase) {
        this.salariobase = salariobase;
    }

    public double getPerccomissao() {
        return perccomissao;
    }

    public void setPerccomissao(double perccomissao) {
        this.perccomissao = perccomissao;
    }

    public Collection<Venda> getVendaCollection() {
        return vendaCollection;
    }

    public void setVendaCollection(Collection<Venda> vendaCollection) {
        this.vendaCollection = vendaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codvendedor != null ? codvendedor.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vendedor)) {
            return false;
        }
        Vendedor other = (Vendedor) object;
        if ((this.codvendedor == null && other.codvendedor != null) || (this.codvendedor != null && !this.codvendedor.equals(other.codvendedor))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nome + ", Código: " +this.codvendedor;
    }
    
}
