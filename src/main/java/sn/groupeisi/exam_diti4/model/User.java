package sn.groupeisi.exam_diti4.model;


import jakarta.persistence.*;
import lombok.Setter;
import lombok.Getter;


@Getter
@Setter
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30)
    private String nom;
    @Column(length = 20)
    private String prenom;
    @Column(length = 14)
    private String telephone;
    @Column(length = 50)
    private String email;
    @Column(length = 20)
    private String login;
    private String password;

    public User() {}

    public User(String nom, String prenom, String telephone, String email, String login, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.telephone = telephone;
        this.email = email;
        this.login = login;
        this.password = password;
    }

}
