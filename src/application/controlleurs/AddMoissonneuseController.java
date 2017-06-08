package application.controlleurs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de l'ajout d'une moissonneuse
 */
public class AddMoissonneuseController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<String> liste_etat;
    @FXML private JFXTextField modele;
    @FXML private JFXTextField marque;
    @FXML private JFXTextField taille_tremis;
    @FXML private JFXTextField taille_reservoir;
    @FXML private JFXTextField largeur_route;
    @FXML private JFXTextField largeur_coupe;
    @FXML private JFXTextField hauteur;
    @FXML private JFXTextField conso_fonctionnement;
    @FXML private JFXTextField conso_route;
    @FXML private JFXTextField poids;

    private VehiculeController vehiculeController;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
    }

    @FXML
    public void onSubmit() {
        // TODO: FAIRE AJOUT DE LA MOISSONEUSE
    }

    public void defineVehiculeController(VehiculeController vehiculeController) {
        this.vehiculeController = vehiculeController;
    }
}