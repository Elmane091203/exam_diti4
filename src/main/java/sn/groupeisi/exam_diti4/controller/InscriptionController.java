package sn.groupeisi.exam_diti4.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sn.groupeisi.exam_diti4.Exception.ValidationInputionException;
import sn.groupeisi.exam_diti4.model.User;
import sn.groupeisi.exam_diti4.services.UserDAO;
import sn.groupeisi.exam_diti4.utils.ValidationUtils;

import java.io.IOException;

public class InscriptionController {

    private final UserDAO userDAO = new UserDAO();
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField telephoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btnAnnuler, btnIncription;

    @FXML
    private void handleInscription(ActionEvent event) {
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String telephone = telephoneField.getText();
        String email = emailField.getText();
        String login = loginField.getText();
        String password = passwordField.getText();
        if (ValidationUtils.isTextFieldFilled(nom) || ValidationUtils.isTextFieldFilled(prenom) ||
                ValidationUtils.isTextFieldFilled(telephone) || ValidationUtils.isTextFieldFilled(email) ||
                ValidationUtils.isTextFieldFilled(login) || ValidationUtils.isTextFieldFilled(password)) {
            if (ValidationUtils.isValidEmail(email) && ValidationUtils.isValidSenegalPhoneNumber(telephone)) {
                if (userDAO.findLogin(login) == null) {
                    User newUser = new User(nom, prenom, telephone, email, login, password);
                    userDAO.create(newUser);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Inscription réussie!");
                    alert.show();
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
                        LoginController.chargerFenetre(loader, nomField.getScene());
                    } catch (IOException e) {
                        ValidationUtils.showError("Page non trouve!", e);
                    }
                } else {
                    ValidationUtils.showWarning("Login incorrect");
                }
            } else {
                ValidationUtils.showErrorInput("Champs saisi incorret", new ValidationInputionException("Verifiez l'email ou le telephone"));
            }
        } else {
            ValidationUtils.showErrorInput("Champs non remplis", new ValidationInputionException("Veuillez remplir tous les champs!"));

        }
    }

    public void handleAnnuler(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            LoginController.chargerFenetre(loader, btnAnnuler.getScene());
        } catch (IOException e) {
            ValidationUtils.showError("Page non trouve!", e);
        }
    }

}
