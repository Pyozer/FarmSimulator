package application.controlleurs;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class AddMoissonneuseController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXTextField taille_tremis;
    @FXML private JFXTextField taille_reservoir;
    @FXML private JFXTextField largeur_route;
    @FXML private JFXTextField largeur_coupe;
    @FXML private JFXTextField hauteur;
    @FXML private JFXTextField conso_fonctionnement;
    @FXML private JFXTextField conso_route;
    @FXML private JFXTextField poids;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

    }

    @FXML
    public void onSubmit() {
        System.out.println("BOUTON SUBMIT CLIQUÉ");
    }


}
