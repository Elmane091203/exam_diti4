package sn.groupeisi.exam_diti4.services;


import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import sn.groupeisi.exam_diti4.utils.JPAUtil;

import java.util.List;

public class DAO<T> {
    private final Class<T> entityClass;
    protected EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();

    public DAO(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    public void create(T entity) {
        em.getTransaction().begin();
        em.persist(entity);
        em.getTransaction().commit();
    }

    public T find(Object id) {
        return em.find(entityClass, id);
    }

    public void update(T entity) {
        em.getTransaction().begin();
        em.merge(entity);
        em.getTransaction().commit();
    }

    public void delete(T entity) {
        em.getTransaction().begin();
        em.remove(entity);
        em.getTransaction().commit();
    }

    public List<T> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return em.createQuery(cq).getResultList();
    }
}
