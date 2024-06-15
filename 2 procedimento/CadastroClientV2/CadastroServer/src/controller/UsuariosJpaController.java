/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import model.Usuarios;

/**
 *
 * @author HAF
 */
public class UsuariosJpaController {
    
    private EntityManagerFactory emf = null;

    public UsuariosJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Usuarios findUsuario(String login, String senha) {
        EntityManager em = getEntityManager();
        try {
            Query query = em.createQuery("SELECT u FROM Usuarios u WHERE u.login = :login AND u.senha = :senha");
            query.setParameter("login", login);
            query.setParameter("senha", senha);
            try {
                return (Usuarios) query.getSingleResult();
            } catch (NoResultException e) {
                return null;
            }
        } finally {
            em.close();
        }
}

    private static class UsuariosJpaControllerEntityManagerFactory {

        public UsuariosJpaControllerEntityManagerFactory() {
        }
    }
}
