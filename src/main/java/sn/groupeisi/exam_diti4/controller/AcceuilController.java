package sn.groupeisi.exam_diti4.controller;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

import java.io.IOException;

public class AcceuilController {

    @FXML
    private BorderPane mainPane;

    @FXML
    private Button btnProduit, btnCategorie, btnStatistique, btnExtraireDoc,btnSeDeconnecter;

    @FXML
    private void initialize() {
    }

    @FXML
    private void showProduit() {
        loadPage("/fxml/Produit.fxml");
    }

    @FXML
    private void showCategorie() {
        loadPage("/fxml/Categorie.fxml");
    }

    @FXML
    private void showStatistique() {
        loadPage("/fxml/Statistique.fxml");
    }

    @FXML
    private void showExtraireDoc() {
        loadPage("/fxml/Document.fxml");
    }

    private void loadPage(String fxmlPath) {
        try {
            Parent page = FXMLLoader.load(getClass().getResource(fxmlPath));
            mainPane.setCenter(page);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleDeconnexion(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Login.fxml"));
            LoginController.chargerFenetre(loader, btnSeDeconnecter.getScene());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
