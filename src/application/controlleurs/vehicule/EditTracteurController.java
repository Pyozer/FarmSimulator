package application.controlleurs.vehicule;

import application.modeles.Tracteur;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

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
    private Tracteur selectedTracteur;

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

            selectedTracteur = tracteur;

            modele.setText(tracteur.getModele());
            marque.setText(tracteur.getMarque());
            cap_rem.setText(String.valueOf(tracteur.getCapacite_remorque()));
            liste_etat.setValue(tracteur.getEtat());
        }
    }

    @FXML
    public void handleSaveTracteur() {
        // TODO: Modification d'un tracteur
    }

    public void defineVehiculeController(VehiculeController vehiculeController) {
        this.vehiculeController = vehiculeController;
    }
}