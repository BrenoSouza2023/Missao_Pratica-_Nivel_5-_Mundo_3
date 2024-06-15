/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author HAF
 */
@Entity
@Table(name = "produtos")
@NamedQueries({
    @NamedQuery(name = "Produtos.findAll", query = "SELECT p FROM Produtos p"),
    @NamedQuery(name = "Produtos.findByIdprodutos", query = "SELECT p FROM Produtos p WHERE p.idprodutos = :idprodutos"),
    @NamedQuery(name = "Produtos.findByPdtNome", query = "SELECT p FROM Produtos p WHERE p.pdtNome = :pdtNome"),
    @NamedQuery(name = "Produtos.findByPdtQuantidade", query = "SELECT p FROM Produtos p WHERE p.pdtQuantidade = :pdtQuantidade"),
    @NamedQuery(name = "Produtos.findByPdtPreco", query = "SELECT p FROM Produtos p WHERE p.pdtPreco = :pdtPreco")})
public class Produtos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "idprodutos")
    private Integer idprodutos;
    @Column(name = "pdt_nome")
    private String pdtNome;
    @Column(name = "pdt_quantidade")
    private Integer pdtQuantidade;
    @Column(name = "pdt_preco")
    private Long pdtPreco;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "produtosIdprodutos")
    private Collection<Movimento> movimentoCollection;

    public Produtos() {
    }

    public Produtos(Integer idprodutos) {
        this.idprodutos = idprodutos;
    }

    public Integer getIdprodutos() {
        return idprodutos;
    }

    public void setIdprodutos(Integer idprodutos) {
        this.idprodutos = idprodutos;
    }

    public String getPdtNome() {
        return pdtNome;
    }

    public void setPdtNome(String pdtNome) {
        this.pdtNome = pdtNome;
    }

    public Integer getPdtQuantidade() {
        return pdtQuantidade;
    }

    public void setPdtQuantidade(Integer pdtQuantidade) {
        this.pdtQuantidade = pdtQuantidade;
    }

    public Long getPdtPreco() {
        return pdtPreco;
    }

    public void setPdtPreco(Long pdtPreco) {
        this.pdtPreco = pdtPreco;
    }

    public Collection<Movimento> getMovimentoCollection() {
        return movimentoCollection;
    }

    public void setMovimentoCollection(Collection<Movimento> movimentoCollection) {
        this.movimentoCollection = movimentoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodutos != null ? idprodutos.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Produtos)) {
            return false;
        }
        Produtos other = (Produtos) object;
        if ((this.idprodutos == null && other.idprodutos != null) || (this.idprodutos != null && !this.idprodutos.equals(other.idprodutos))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Produtos[ idprodutos=" + idprodutos + " ]";
    }

    public void getId() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public boolean getNome() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
