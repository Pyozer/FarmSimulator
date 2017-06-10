package application.controlleurs.client;

import application.classes.AlertDialog;
import application.modeles.Agriculteur;
import application.modeles.ClientSQL;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Controlleur de la vue de la gestion des clients (Ajouté/Modifier) de l'ETA
 */
public class EditClientController {

    /** Layout **/
    @FXML private BorderPane bpane;
    /** Elements **/
    @FXML private Label title;

    @FXML private JFXTextField nom_client;
    @FXML private JFXTextField prenom_client;
    @FXML private JFXTextField tel_client;
    @FXML private JFXTextField adresse_client;
    @FXML private JFXTextField email_client;
    @FXML private JFXColorPicker couleur_client;

    private Agriculteur agriculteurToEdit;
    private ClientController clientController;

    private boolean isEdit = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        couleur_client.setValue(Color.rgb(68, 68, 68));
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Agriculteur agriculteur) {
        if(isEdit) {
            title.setText("Modification du client");

            agriculteurToEdit = agriculteur;

            nom_client.setText(agriculteur.getNom());
            prenom_client.setText(agriculteur.getPrenom());
            tel_client.setText(agriculteur.getNum_tel());
            adresse_client.setText(agriculteur.getAdresse());
            email_client.setText(agriculteur.getEmail());
            couleur_client.setValue(agriculteur.getCouleur());
        }
    }

    @FXML
    public void handleSubmitClient() {
        String inputNom = nom_client.getText();
        String inputPrenom = prenom_client.getText();
        String inputTel = tel_client.getText();
        String inputAdresse = adresse_client.getText();
        String inputEmail = email_client.getText();
        Color inputCouleur = couleur_client.getValue();

        if(inputNom.isEmpty() || inputPrenom.isEmpty() || inputTel.isEmpty() || inputAdresse.isEmpty() || inputEmail.isEmpty() || inputCouleur.toString().isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            String message = "Le client a bien été";

            if(isEdit) {
                agriculteurToEdit.setNom(inputNom);
                agriculteurToEdit.setPrenom(inputPrenom);
                agriculteurToEdit.setNum_tel(inputTel);
                agriculteurToEdit.setAdresse(inputAdresse);
                agriculteurToEdit.setEmail(inputEmail);
                agriculteurToEdit.setCouleur(inputCouleur);

                ClientSQL.editClient(agriculteurToEdit);

                message += " modifié !";
            } else {
                ClientSQL.addClient(inputNom, inputPrenom, inputTel, inputAdresse, inputEmail, inputCouleur);

                message += " ajouté !";
            }

            AlertDialog alert = new AlertDialog("Succès", null, message, Alert.AlertType.INFORMATION);
            alert.show();

            clientController.initData();
            clientController.clearAllSelection();

            Stage stage = (Stage) bpane.getScene().getWindow();
            stage.close();
        }
    }

    public void defineClientController(ClientController clientController) {
        this.clientController = clientController;
    }
}
