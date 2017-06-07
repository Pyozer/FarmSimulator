package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.ClientSQL;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

public class AddMoissonController {

    /** Layout **/
    @FXML private BorderPane bpane;

    /** Elements **/
    @FXML private JFXTextField poid_recolte;
    @FXML private JFXTextField nb_Kilo;
    @FXML private JFXDatePicker date_debut;
    @FXML private JFXDatePicker date_fin;
    @FXML private JFXTimePicker time_debut;
    @FXML private JFXTimePicker time_fin;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
    }

    @FXML
    public void handleSubmitCommande() {

        /*String inputPoidRecolte= poid_recolte.getText();
        String inputNbKilo = nb_Kilo.getText();
        String inputDateDebut = date_debut.getValue().toString();
        String inputDateFin = date_fin.getValue().toString();
        String inputTimeDebut = time_debut.getValue().toString();
        String inputTimeFin = time_fin.getValue().toString();

        if (inputNom.isEmpty() || inputPrenom.isEmpty() || inputTel.isEmpty() || inputAdresse.isEmpty() || inputEmail.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            ClientSQL.addClient(inputNom, inputPrenom, inputTel, inputAdresse, inputEmail);

            AlertDialog alert = new AlertDialog("Succès", null, "Le client à bien été ajouté !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }*/
    }

}