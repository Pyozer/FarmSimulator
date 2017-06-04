package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.Agriculteur;
import application.modeles.ClientSQL;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class EditClientController {

    /** Layout **/
    @FXML private BorderPane bpane;
    /** Elements **/
    @FXML private JFXTextField nom_client;
    @FXML private JFXTextField prenom_client;
    @FXML private JFXTextField tel_client;
    @FXML private JFXTextField adresse_client;
    @FXML private JFXTextField email_client;

    private Agriculteur agriculteurToEdit;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

    }

    public void initTextFields(Agriculteur agriculteur) {

        agriculteurToEdit = agriculteur;

        nom_client.setText(agriculteur.getNom());
        prenom_client.setText(agriculteur.getPrenom());
        tel_client.setText(agriculteur.getNum_tel());
        adresse_client.setText(agriculteur.getAdresse());
        email_client.setText(agriculteur.getEmail());
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
            agriculteurToEdit.setNom(inputNom);
            agriculteurToEdit.setPrenom(inputPrenom);
            agriculteurToEdit.setNum_tel(inputTel);
            agriculteurToEdit.setAdresse(inputAdresse);
            agriculteurToEdit.setEmail(inputEmail);

            ClientSQL.editClient(agriculteurToEdit);

            AlertDialog alert = new AlertDialog("Succès", null, "Le client à bien été ajouté !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }
    }


}
