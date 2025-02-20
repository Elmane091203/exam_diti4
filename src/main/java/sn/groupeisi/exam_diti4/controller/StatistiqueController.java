package sn.groupeisi.exam_diti4.controller;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import sn.groupeisi.exam_diti4.model.Categorie;
import sn.groupeisi.exam_diti4.services.CategorieDAO;
import sn.groupeisi.exam_diti4.services.ProduitDAO;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class StatistiqueController {

    private final ProduitDAO produitDAO = new ProduitDAO();
    private final CategorieDAO categorieDAO = new CategorieDAO();
    @FXML
    private PieChart pieChart;
    @FXML
    private ComboBox<Integer> cbbAnnee;
    @FXML
    private BarChart<String, Number> barChart;
    @FXML
    private CategoryAxis labelMoisAnnee;

    @FXML
    public void initialize() {
        // Diagramme en camembert : nombre de produits par catégorie
        List<Categorie> categories = categorieDAO.findAll();
        pieChart.setData(FXCollections.observableArrayList());
        for (Categorie cat : categories) {
            long count = cat.getProduits().size();
            pieChart.getData().add(new PieChart.Data(cat.getLibelle(), count));
        }

        // Diagramme en barre : nombre de produits ajoutés par mois en 2024

        barChart.getData().add(produitsAnne(2024));
        cbbAnnee.setItems(FXCollections.observableArrayList(
                2022,
                2023,
                2024,
                2025,
                2026
        ));
        cbbAnnee.valueProperty().addListener(observable -> {
            barChart.setData(FXCollections.observableArrayList());
            barChart.getData().add(produitsAnne(cbbAnnee.getValue()));
            labelMoisAnnee.setLabel("Mois en "+cbbAnnee.getValue());
        });

    }

    private XYChart.Series<String, Number> produitsAnne(int ann){
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        Month[] listeDesMois = Month.values();
        for (Month month : listeDesMois) {
            long count = produitDAO.findAll().stream()
                    .filter(p -> p.getDateAjout() != null &&
                            p.getDateAjout().getYear() == ann &&
                            p.getDateAjout().getMonth() == month)
                    .count();
            // On ajoute la donnée au graphique, ici on affiche par exemple le nom abrégé du mois en français
            String monthName = month.getDisplayName(TextStyle.SHORT, Locale.FRENCH);
            series.getData().add(new XYChart.Data<>(monthName, count));
        }
        return series;
    }
}
