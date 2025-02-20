package sn.groupeisi.exam_diti4.controller;


import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import sn.groupeisi.exam_diti4.Exception.ValidationInputionException;
import sn.groupeisi.exam_diti4.model.Categorie;
import sn.groupeisi.exam_diti4.services.CategorieDAO;
import sn.groupeisi.exam_diti4.utils.ValidationUtils;

import java.util.List;

public class CategorieController {

    private final CategorieDAO categorieDAO = new CategorieDAO();
    @FXML
    private TableView<Categorie> tableCategories;
    @FXML
    private Button btnSupprimer, btnAjouter, btnModifier, btnEffacer;

    @FXML
    private TextField txtId, txtLibelle;

    @FXML
    private TableColumn<Categorie, Integer> idColonne;

    @FXML
    private TableColumn<Categorie, String> libelleColonne;


    @FXML
    public void initialize() {
        loadCategories();
    }

    private void loadCategories() {
        clearFields();
        defineColonne();
        List<Categorie> categories = categorieDAO.findAll();
        tableCategories.setItems(FXCollections.observableArrayList(categories));
    }


    @FXML
    void Ajouter(ActionEvent event) {
        String libelle = txtLibelle.getText();
        if (ValidationUtils.isTextFieldFilled(libelle)) {
            Categorie newCategorie = new Categorie(libelle);
            try {
                categorieDAO.create(newCategorie);
                ValidationUtils.showInfo("Categorie ajoute!");
                loadCategories();
            } catch (Exception e) {
                ValidationUtils.showError("Ajout Categorie", e);
            }
        } else {
            ValidationUtils.showErrorInput("Champs non saisi!", new ValidationInputionException("Veuillez remplir tous les champs!"));
        }
    }

    @FXML
    void Effacer(ActionEvent event) {
        clearFields();
    }

    @FXML
    void Modifier(ActionEvent event) {
        try {
            Categorie c = categorieDAO.find(Integer.parseInt(txtId.getText()));
            c.setLibelle(txtLibelle.getText());
            categorieDAO.update(c);
            loadCategories();
        } catch (Exception e) {
            ValidationUtils.showError("Erreur de mise à jour", e);
        }
    }


    @FXML
    void Supprimer(ActionEvent event) {
        try {
            int id = Integer.parseInt(txtId.getText());
            categorieDAO.delete(categorieDAO.find(id));
            loadCategories();
        } catch (Exception e) {
            ValidationUtils.showError("Erreur de suppression", e);
        }
    }


    @FXML
    void chargementChamps(MouseEvent event) {
        Categorie c = tableCategories.getSelectionModel().getSelectedItem();
        if (c != null) {
            txtId.setText(String.valueOf(c.getId()));
            txtLibelle.setText(c.getLibelle());
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
        }

    }

    private void defineColonne() {

        idColonne.setCellValueFactory(new PropertyValueFactory<>("id"));
        libelleColonne.setCellValueFactory(new PropertyValueFactory<>("libelle"));
    }

    private void clearFields() {
        txtId.clear();
        txtLibelle.clear();
        txtLibelle.requestFocus();
        btnAjouter.setDisable(true);
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    @FXML
    void saisi(KeyEvent event) {
        if (ValidationUtils.isTextFieldFilled(txtId.getText())) {
            btnAjouter.setDisable(true);
        } else {
            btnAjouter.setDisable(!ValidationUtils.isTextFieldFilled(txtLibelle.getText()));
        }
    }
}
