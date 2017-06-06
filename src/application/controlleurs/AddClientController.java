package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.ClientSQL;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur pour l'ajout d'un client
 */
public class AddClientController {

    /** Layout **/
    @FXML private BorderPane bpane;
    /** Elements **/
    @FXML private JFXTextField nom_client;
    @FXML private JFXTextField prenom_client;
    @FXML private JFXTextField tel_client;
    @FXML private JFXTextField adresse_client;
    @FXML private JFXTextField email_client;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
    }

    @FXML
    public void handleSubmitClient() {
        String inputNom = nom_client.getText();
        String inputPrenom = prenom_client.getText();
        String inputTel = tel_client.getText();
        String inputAdresse = adresse_client.getText();
        String inputEmail = email_client.getText();

        if(inputNom.isEmpty() || inputPrenom.isEmpty() || inputTel.isEmpty() || inputAdresse.isEmpty() || inputEmail.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            ClientSQL.addClient(inputNom, inputPrenom, inputTel, inputAdresse, inputEmail);

            AlertDialog alert = new AlertDialog("Succès", null, "Le client à bien été ajouté !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }
    }


}
