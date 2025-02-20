package sn.groupeisi.exam_diti4.services;

import jakarta.persistence.Query;
import sn.groupeisi.exam_diti4.model.Produit;

import java.util.List;

public class ProduitDAO extends DAO<Produit> {

    public ProduitDAO() {
        super(Produit.class);
    }

    public List<Produit> qteProduitBas(int seuil) {
        Query query = em.createQuery("SELECT p FROM Produit p WHERE p.quantite < :seuil");
        query.setParameter("seuil", seuil);
        try {
            return query.getResultList();

        }catch (Exception e){
            return  null;
        }
    }

    public List<Produit> findByLibelle(String mot) {
        Query query = em.createQuery("SELECT p FROM Produit p WHERE p.libelle LIKE :libelle");
        query.setParameter("libelle", "%" + mot + "%");
        try {
            return query.getResultList();

        }catch (Exception e){
            return  null;
        }
    }

}
