package sn.groupeisi.exam_diti4.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import sn.groupeisi.exam_diti4.model.Categorie;
import sn.groupeisi.exam_diti4.model.Produit;
import sn.groupeisi.exam_diti4.services.CategorieDAO;
import sn.groupeisi.exam_diti4.services.ProduitDAO;
import sn.groupeisi.exam_diti4.utils.ValidationUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class DocumentController {

    @FXML
    private Button btnExcel, btnPdf;
    ProduitDAO produitDAO = new ProduitDAO();
    CategorieDAO categorieDAO = new CategorieDAO();

    @FXML
    void genererCategorieProduitsNbExcel(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Excel Files", "*.xlsx"));
        fileChooser.setInitialFileName("produits_par_categorie.xlsx");

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (Workbook workbook = new XSSFWorkbook();
                 FileOutputStream fileOut = new FileOutputStream(file)) {

                Sheet sheet = workbook.createSheet("Produits par Catégorie");
                Row headerRow = sheet.createRow(0);

                headerRow.createCell(0).setCellValue("Catégorie");
                headerRow.createCell(1).setCellValue("Nombre de Produits");

                // Exemple de données (Remplacez avec une récupération depuis la BDD)
                List<Categorie> categories = categorieDAO.findAll();

                int rowNum = 1;
                for (Categorie cat : categories) {
                    Row row = sheet.createRow(rowNum++);
                    row.createCell(0).setCellValue(cat.getLibelle());
                    row.createCell(1).setCellValue(cat.getProduits().size());
                }

                workbook.write(fileOut);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void genererProduitsPDF(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));
        fileChooser.setInitialFileName("liste_produits.pdf");

        File file = fileChooser.showSaveDialog(new Stage());
        if (file != null) {
            try (PdfWriter writer = new PdfWriter(file);
                 PdfDocument pdf = new PdfDocument(writer);
                 Document document = new Document(pdf)) {

                document.add(new Paragraph("Liste des Produits").setFontSize(14).setUnderline());

                List<Produit> products = produitDAO.findAll();

                for (Produit product : products) {
                    document.add(new Paragraph(product.toString()));
                }

            } catch (IOException e) {
                ValidationUtils.showError("Erreur d'exportation",e);
            }
        }
    }

}

