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
@Table(name = "pagamento")
@NamedQueries({
    @NamedQuery(name = "Pagamento.findAll", query = "SELECT p FROM Pagamento p"),
    @NamedQuery(name = "Pagamento.findByCodpagamento", query = "SELECT p FROM Pagamento p WHERE p.codpagamento = :codpagamento"),
    @NamedQuery(name = "Pagamento.findByFormapgto", query = "SELECT p FROM Pagamento p WHERE p.formapgto = :formapgto")})
public class Pagamento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "CODPAGAMENTO")
    private Integer codpagamento;
    @Column(name = "FORMAPGTO")
    private String formapgto;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codpagamento")
    private Collection<Vendadiaria> vendadiariaCollection;

    public Pagamento() {
    }

    public Pagamento(Integer codpagamento) {
        this.codpagamento = codpagamento;
    }

    public Integer getCodpagamento() {
        return codpagamento;
    }

    public void setCodpagamento(Integer codpagamento) {
        this.codpagamento = codpagamento;
    }

    public String getFormapgto() {
        return formapgto;
    }

    public void setFormapgto(String formapgto) {
        this.formapgto = formapgto;
    }

    public Collection<Vendadiaria> getVendadiariaCollection() {
        return vendadiariaCollection;
    }

    public void setVendadiariaCollection(Collection<Vendadiaria> vendadiariaCollection) {
        this.vendadiariaCollection = vendadiariaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codpagamento != null ? codpagamento.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pagamento)) {
            return false;
        }
        Pagamento other = (Pagamento) object;
        if ((this.codpagamento == null && other.codpagamento != null) || (this.codpagamento != null && !this.codpagamento.equals(other.codpagamento))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.Pagamento[ codpagamento=" + codpagamento + " ]";
    }
    
}
