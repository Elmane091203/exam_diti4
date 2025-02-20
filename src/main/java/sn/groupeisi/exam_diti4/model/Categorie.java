package sn.groupeisi.exam_diti4.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Categories")
public class Categorie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String libelle;

    @OneToMany(mappedBy = "categorie",orphanRemoval = true, cascade = CascadeType.ALL)
    private List<Produit> produits;

    public Categorie() {}

    public Categorie(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
