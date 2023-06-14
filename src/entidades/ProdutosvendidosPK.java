/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidades;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author gusta
 */
@Embeddable
public class ProdutosvendidosPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "CODPRODUTO")
    private int codproduto;
    @Basic(optional = false)
    @Column(name = "CODVENDA")
    private int codvenda;

    public ProdutosvendidosPK() {
    }

    public ProdutosvendidosPK(int codproduto, int codvenda) {
        this.codproduto = codproduto;
        this.codvenda = codvenda;
    }

    public int getCodproduto() {
        return codproduto;
    }

    public void setCodproduto(int codproduto) {
        this.codproduto = codproduto;
    }

    public int getCodvenda() {
        return codvenda;
    }

    public void setCodvenda(int codvenda) {
        this.codvenda = codvenda;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codproduto;
        hash += (int) codvenda;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdutosvendidosPK)) {
            return false;
        }
        ProdutosvendidosPK other = (ProdutosvendidosPK) object;
        if (this.codproduto != other.codproduto) {
            return false;
        }
        if (this.codvenda != other.codvenda) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.ProdutosvendidosPK[ codproduto=" + codproduto + ", codvenda=" + codvenda + " ]";
    }
    
}
