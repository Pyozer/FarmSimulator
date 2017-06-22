package application.controlleurs.vehicule;

import application.classes.AlertDialog;
import application.modeles.Tracteur;
import application.modeles.VehiculeSQL;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controlleur pour ma modification d'un tracteur
 */
public class EditTracteurController {

    /** Layout **/
    @FXML private BorderPane bpane;
    /** Elements **/
    @FXML private JFXTextField modele;
    @FXML private JFXTextField marque;
    @FXML private JFXTextField cap_rem;
    @FXML private JFXComboBox<String> liste_etat;

    @FXML private Label title;

    private VehiculeController vehiculeController;
    private ChoixVehiculeController choixVehiculeController;
    private Tracteur tracteurToEdit;

    private boolean isEdit = false;

    /** Initializes the controller class. **/
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des ComboBox
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(0));
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Tracteur tracteur) {
        if(isEdit) {
            title.setText("Modifier le tracteur");

            tracteurToEdit = tracteur;

            modele.setText(tracteur.getModele());
            marque.setText(tracteur.getMarque());
            cap_rem.setText(String.valueOf(tracteur.getCapacite_remorque()));
            liste_etat.setValue(tracteur.getEtat());
        }
    }

    @FXML
    public void handleSaveTracteur() {
        String inputEtat = liste_etat.getValue();
        String inputMarque = marque.getText().trim();
        String inputModele = modele.getText().trim();
        String inputCapRem = cap_rem.getText().trim();

        try {
            if(inputMarque.isEmpty() || inputModele.isEmpty() || inputCapRem.isEmpty() || inputEtat.isEmpty()) {
                AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
                alert.show();
            } else {
                String message = "Le tracteur a bien été";

                int cap_rem = Integer.parseInt(inputCapRem.replace(',', '.'));

                if(isEdit) {
                    tracteurToEdit.setEtat(inputEtat);
                    tracteurToEdit.setMarque(inputMarque);
                    tracteurToEdit.setModele(inputModele);
                    tracteurToEdit.setCapacite_remorque(cap_rem);

                    VehiculeSQL.editTracteur(tracteurToEdit);

                    message += " modifié !";
                } else {
                    VehiculeSQL.addTracteur(inputModele, inputMarque, cap_rem, inputEtat);

                    message += " ajouté !";
                }

                AlertDialog alert = new AlertDialog("Succès", null, message, Alert.AlertType.INFORMATION);
                alert.show();

                vehiculeController.initData();
                vehiculeController.clearAllSelection();

                if(choixVehiculeController != null)
                    choixVehiculeController.closeWindow();

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException  e){
            AlertDialog alert = new AlertDialog("Erreur", null, "Les champs de texte à chiffres doivent être un nombre !\nUtilisez un . ou , pour les nombres décimaux.", Alert.AlertType.ERROR);
            alert.show();
        }
    }

    public void defineVehiculeController(VehiculeController vehiculeController, ChoixVehiculeController choixVehiculeController) {
        this.vehiculeController = vehiculeController;
        this.choixVehiculeController = choixVehiculeController;
    }
}