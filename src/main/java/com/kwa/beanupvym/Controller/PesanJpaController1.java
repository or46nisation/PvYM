/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kwa.beanupvym.Controller;

import com.kwa.beanupvym.Controller.exceptions.NonexistentEntityException;
import com.kwa.beanupvym.Controller.exceptions.PreexistingEntityException;
import com.kwa.beanupvym.Pesan;
import com.kwa.beanupvym.PesanPK;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;

/**
 *
 * @author ibung
 */
public class PesanJpaController1 implements Serializable {

    public PesanJpaController1(UserTransaction utx, EntityManagerFactory emf) {
        this.utx = utx;
        this.emf = emf;
    }
    private UserTransaction utx = null;
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Pesan pesan) throws PreexistingEntityException, Exception {
        if (pesan.getPesanPK() == null) {
            pesan.setPesanPK(new PesanPK());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(pesan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPesan(pesan.getPesanPK()) != null) {
                throw new PreexistingEntityException("Pesan " + pesan + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Pesan pesan) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            pesan = em.merge(pesan);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PesanPK id = pesan.getPesanPK();
                if (findPesan(id) == null) {
                    throw new NonexistentEntityException("The pesan with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PesanPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Pesan pesan;
            try {
                pesan = em.getReference(Pesan.class, id);
                pesan.getPesanPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The pesan with id " + id + " no longer exists.", enfe);
            }
            em.remove(pesan);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Pesan> findPesanEntities() {
        return findPesanEntities(true, -1, -1);
    }

    public List<Pesan> findPesanEntities(int maxResults, int firstResult) {
        return findPesanEntities(false, maxResults, firstResult);
    }

    private List<Pesan> findPesanEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Pesan.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Pesan findPesan(PesanPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Pesan.class, id);
        } finally {
            em.close();
        }
    }

    public int getPesanCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Pesan> rt = cq.from(Pesan.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
