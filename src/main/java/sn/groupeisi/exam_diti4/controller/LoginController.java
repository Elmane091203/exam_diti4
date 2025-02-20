package sn.groupeisi.exam_diti4.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sn.groupeisi.exam_diti4.model.Produit;
import sn.groupeisi.exam_diti4.model.User;
import sn.groupeisi.exam_diti4.services.ProduitDAO;
import sn.groupeisi.exam_diti4.services.UserDAO;
import sn.groupeisi.exam_diti4.utils.ValidationUtils;

import java.io.IOException;
import java.util.List;

public class LoginController {

    private final UserDAO UserDAO = new UserDAO();
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button btnSeConnecter, btnSInscrire;

    static void chargerFenetre(FXMLLoader loader, Scene scene) throws IOException {
        Parent root = loader.load();
        Stage stage = (Stage) scene.getWindow();
        stage.setScene(new Scene(root));
        stage.setX((javafx.stage.Screen.getPrimary().getVisualBounds().getWidth() - stage.getWidth()) / 2);
        stage.setY((javafx.stage.Screen.getPrimary().getVisualBounds().getHeight() - stage.getHeight()) / 2);
    }

    @FXML
    private void handleSeConnecter(ActionEvent event) {
        String login = loginField.getText();
        String password = passwordField.getText();

        User user = UserDAO.findByLoginAndPassword(login, password);
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Login ou mot de passe incorrect");
            alert.setTitle("Gestion produit");
            alert.show();
        } else {
            ProduitDAO produitDAO = new ProduitDAO();
            List<Produit> lowStockProducts = produitDAO.qteProduitBas(5);
            if (!lowStockProducts.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Attention : certains produits ont une quantité inférieure à 5 !");
                alert.setTitle("Gestion produit");
                alert.show();
            }
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Acceuil.fxml"));
                chargerFenetre(loader, btnSeConnecter.getScene());
            } catch (IOException e) {
                ValidationUtils.showError("Page non trouve!",e);
            }
        }
    }

    @FXML
    private void handleSInscrire(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Inscription.fxml"));
            chargerFenetre(loader, btnSeConnecter.getScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
