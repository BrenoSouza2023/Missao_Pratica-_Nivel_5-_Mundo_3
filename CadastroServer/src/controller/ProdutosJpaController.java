package controller;

import model.Produtos;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class ProdutosJpaController {
    private EntityManagerFactory emf;

    public ProdutosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Produtos produto) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(produto);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
        }
    }

    public Produtos findProduto(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Produtos.class, id);
        } finally {
            em.close();
        }
    }

    public List<Produtos> findProdutoEntities() {
        EntityManager em = getEntityManager();
        try {
            TypedQuery<Produtos> query = em.createQuery("SELECT p FROM Produto p", Produtos.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public void edit(Produtos produto) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(produto);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
        }
    }

    public void destroy(Long id) {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Produtos produto;
            try {
                produto = em.getReference(Produtos.class, id);
                produto.getId(); // Ensure the entity is loaded
            } catch (Exception ex) {
                throw new RuntimeException("Produto with id " + id + " not found.", ex);
            }
            em.remove(produto);
            tx.commit();
        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            em.close();
        }
    }

    public Object findAllProdutos() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
