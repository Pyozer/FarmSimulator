package application.controlleurs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue pour ajouter une Botteleuse
 */
public class AddBotteleuseController {

    /** Layout **/
    @FXML private BorderPane bpane;
    
    /** Elements **/
    @FXML private JFXTextField modele;
    @FXML private JFXTextField marque;
    @FXML private JFXComboBox<String> type;
    @FXML private JFXComboBox<String> liste_etat;

    private VehiculeController vehiculeController;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initialisation des ComboxBox
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(0));

        type.getItems().setAll("Ronde", "Carré" );
        type.setValue(type.getItems().get(0));
    }

    @FXML
    public void handleSaveBotteleuse() {
        // TODO : Faire la sauvegarde de la botteleuse
    }

    public void defineVehiculeController(VehiculeController vehiculeController) {
        this.vehiculeController = vehiculeController;
    }
}