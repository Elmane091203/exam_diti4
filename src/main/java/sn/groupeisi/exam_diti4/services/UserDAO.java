package sn.groupeisi.exam_diti4.services;

import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import sn.groupeisi.exam_diti4.model.User;

public class UserDAO extends DAO<User> {

    public UserDAO() {
        super(User.class);
    }

    public User findByLoginAndPassword(String login, String password) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password");
        query.setParameter("login", login);
        query.setParameter("password", password);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public User findLogin(String login) {
        Query query = em.createQuery("SELECT u FROM User u WHERE u.login = :login");
        query.setParameter("login", login);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
}
