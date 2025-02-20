package sn.groupeisi.exam_diti4.model;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "Produits")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String libelle;
    private int quantite;
    private double prixUnitaire;

    private LocalDate dateAjout;

    @ManyToOne
    @JoinColumn(name = "idCategorie")
    private Categorie categorie;

    public Produit() {}

    public Produit(String libelle, int quantite, double prixUnitaire, Categorie categorie, LocalDate dateAjout) {
        this.libelle = libelle;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.categorie = categorie;
        this.dateAjout = dateAjout;
    }

    @Override
    public String toString() {
        return "Produit{" +
                "id=" + id +
                ", libelle='" + libelle + '\'' +
                ", quantite=" + quantite +
                ", prixUnitaire=" + prixUnitaire +
                ", dateAjout=" + dateAjout +
                ", categorie=" + categorie +
                '}';
    }
}
