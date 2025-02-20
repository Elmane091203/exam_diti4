package sn.groupeisi.exam_diti4.utils;

import javafx.scene.control.Alert;
import sn.groupeisi.exam_diti4.Exception.ValidationInputionException;

import java.util.regex.Pattern;

public class ValidationUtils {

    /**
     * Vérifie si le champ de texte est rempli.
     * @param text la chaîne à vérifier
     * @return true si non null et non vide après trim, false sinon
     */
    public static boolean isTextFieldFilled(String text) {
        return text != null && !text.trim().isEmpty();
    }

    /**
     * Vérifie si l'adresse email est au bon format.
     * @param email l'email à vérifier
     * @return true si l'email correspond au pattern, false sinon
     */
    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        // Expression régulière simple pour l'email
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.matches(emailRegex, email);
    }

    /**
     * Vérifie si le numéro correspond à un numéro sénégalais.
     * Formats acceptés :
     * - Format local : "0XXXXXXXXX" (10 chiffres, le premier après le 0 entre 7 et 9)
     * - Format international : "+221XXXXXXXXX" ou "00221XXXXXXXXX" (le premier chiffre après le préfixe entre 7 et 9)
     * @param phoneNumber le numéro de téléphone à valider
     * @return true si le numéro est valide, false sinon
     */
    public static boolean isValidSenegalPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            return false;
        }
        // Regex qui accepte les formats locaux et internationaux
        String phoneRegex = "^([7-9]\\d{8}|\\+221[7-9]\\d{8}|00221[7-9]\\d{8})$";
        return Pattern.matches(phoneRegex, phoneNumber);
    }
    public static void showError(String title, Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }
    public static void showErrorInput(String title, ValidationInputionException e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(e.getMessage());
        alert.showAndWait();
    }

    public static void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Alert");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static void main(String[] args) {
        String text = "";
        String email = "";
        String phoneLocal = "";       // Exemple de numéro local
        String phoneInternational = "+221771234567"; // Exemple de numéro international

        System.out.println("Champ de texte rempli : " + isTextFieldFilled(text));
        System.out.println("Email valide : " + isValidEmail(email));
        System.out.println("Numéro sénégalais valide (local) : " + isValidSenegalPhoneNumber(phoneLocal));
        System.out.println("Numéro sénégalais valide (international) : " + isValidSenegalPhoneNumber(phoneInternational));
    }

}
