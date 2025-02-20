package sn.groupeisi.exam_diti4.controller;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sn.groupeisi.exam_diti4.Exception.ValidationInputionException;
import sn.groupeisi.exam_diti4.model.Categorie;
import sn.groupeisi.exam_diti4.model.Produit;
import sn.groupeisi.exam_diti4.services.CategorieDAO;
import sn.groupeisi.exam_diti4.services.ProduitDAO;
import sn.groupeisi.exam_diti4.utils.ValidationUtils;

import java.time.LocalDate;
import java.util.List;

public class ProduitController {

    private final ProduitDAO produitDAO = new ProduitDAO();
    private final CategorieDAO categorieDAO = new CategorieDAO();
    @FXML
    private Button btnAjouter, btnEffacer, btnModifier, btnSupprimer;
    @FXML
    private TableColumn<Produit, Categorie> categorieColonne;
    @FXML
    private ComboBox<Categorie> cbbCategorie;
    @FXML
    private TableColumn<Produit, Integer> idColonne;
    @FXML
    private TableColumn<Produit, String> libelleColonne;
    @FXML
    private TableColumn<Produit, Double> prixColonne;
    @FXML
    private TableColumn<Produit, Integer> qteColonne;
    @FXML
    private TextField txtId, txtLibelle, txtPrix, txtQte, txtRechercher;
    @FXML
    private TableView<Produit> tableProduits;

    @FXML
    void Ajouter(ActionEvent event) {
        String libelle = txtLibelle.getText();
        String prix = (txtPrix.getText());
        String qte = (txtQte.getText());
        Categorie catego = cbbCategorie.getValue();
        if (ValidationUtils.isTextFieldFilled(libelle) || ValidationUtils.isTextFieldFilled(prix) ||
                ValidationUtils.isTextFieldFilled(qte) || catego != null) {
            Produit p = new Produit(libelle, Integer.parseInt(qte), Double.parseDouble(prix), catego, LocalDate.now());
            produitDAO.create(p);
            clearFields();
            ValidationUtils.showInfo("Produit Ajoute!");


        } else {
            ValidationUtils.showErrorInput("Champs non remplis", new ValidationInputionException("Veuillez remplir tous les champs!"));

        }
    }

    @FXML
    void Modifier(ActionEvent event) {
        String libelle = txtLibelle.getText();
        String prix = (txtPrix.getText());
        String qte = (txtQte.getText());
        Categorie catego = cbbCategorie.getValue();
        if (ValidationUtils.isTextFieldFilled(libelle) || ValidationUtils.isTextFieldFilled(prix) ||
                ValidationUtils.isTextFieldFilled(qte) || catego != null) {
            Produit p = produitDAO.find(Integer.parseInt(txtId.getText()));
            p.setCategorie(catego);
            p.setLibelle(libelle);
            p.setQuantite(Integer.parseInt(qte));
            p.setPrixUnitaire(Double.parseDouble(prix));
//            p.setDateAjout(LocalDate.now());
            produitDAO.update(p);
            clearFields();
            ValidationUtils.showInfo("Produit Modifie!");


        } else {
            ValidationUtils.showErrorInput("Champs non remplis", new ValidationInputionException("Veuillez remplir tous les champs!"));

        }
    }

    @FXML
    void Supprimer(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            produitDAO.delete(produitDAO.find(id));
            clearFields();
        } catch (Exception e) {
            ValidationUtils.showError("Erreur de suppression", e);
        }
    }

    @FXML
    void rechercher(KeyEvent event) {
        rechercherProduits(txtRechercher.getText());

    }


    @FXML
    public void initialize() {
        clearFields();
        loadCategories();
        txtLibelle.textProperty().addListener((observable, oldValue, newValue) -> {
            saisi();
        });
        txtQte.textProperty().addListener((observable, oldValue, newValue) -> {
            saisi();
        });
        txtPrix.textProperty().addListener((observable, oldValue, newValue) -> {
            saisi();
        });
        cbbCategorie.valueProperty().addListener((observable, oldValue, newValue) -> {
            saisi();
        });
    }

    private void loadProduits() {
        defineColonne();
        List<Produit> produits = produitDAO.findAll();
        tableProduits.setItems(FXCollections.observableArrayList(produits));
    }

    private void rechercherProduits(String keyword) {
        defineColonne();
        List<Produit> produits = produitDAO.findByLibelle(keyword);
        tableProduits.setItems(FXCollections.observableArrayList(produits));
    }

    @FXML
    void chargementChamps(MouseEvent event) {
        Produit p = tableProduits.getSelectionModel().getSelectedItem();
        if (p != null) {
            txtId.setText(String.valueOf(p.getId()));
            txtPrix.setText(String.valueOf(p.getPrixUnitaire()));
            txtQte.setText(String.valueOf(p.getQuantite()));
            txtLibelle.setText(p.getLibelle());
            cbbCategorie.setValue(p.getCategorie());
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
        }
    }

    @FXML
    void Effacer(ActionEvent event) {
        clearFields();
    }

    private void clearFields() {
        txtId.clear();
        txtLibelle.clear();
        txtPrix.clear();
        txtQte.clear();
        txtRechercher.clear();
//        cbbCategorie.setValue(new Categorie("Categorie"));
        cbbCategorie.setValue(null);
        txtLibelle.requestFocus();
        btnAjouter.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
        loadProduits();
    }

    private void defineColonne() {

        idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelleColonne.setCellValueFactory(new PropertyValueFactory<>("libelle"));
        prixColonne.setCellValueFactory(new PropertyValueFactory<>("prixUnitaire"));
        qteColonne.setCellValueFactory(new PropertyValueFactory<>("quantite"));
        categorieColonne.setCellValueFactory(new PropertyValueFactory<>("categorie"));
    }

    private void loadCategories() {
        try {
            cbbCategorie.setItems(FXCollections.observableArrayList(categorieDAO.findAll()));
        } catch (Exception e) {
            ValidationUtils.showError("Erreur lors du chargement des animaux", e);
        }
    }

    private void saisi() {
        if (ValidationUtils.isTextFieldFilled(txtId.getText())) {
            btnAjouter.setDisable(true);
        } else {
            btnAjouter.setDisable(!(ValidationUtils.isTextFieldFilled(txtLibelle.getText()) &&
                    ValidationUtils.isTextFieldFilled(txtPrix.getText()) &&
                    ValidationUtils.isTextFieldFilled(txtQte.getText()) &&
                    cbbCategorie.getValue() != null));
        }
    }

}
