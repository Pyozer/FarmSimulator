package application.controlleurs;

import application.modeles.Agriculteur;
import application.modeles.ChampSQL;
import application.modeles.ClientSQL;
import application.modeles.Culture;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur pour l'ajout d'un champ
 */
public class AddChampController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<Culture> liste_type;
    @FXML private JFXComboBox<Agriculteur> liste_proprio;

    private ChampController champController;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initialisation des ComboxBox
        liste_type.getItems().setAll(ChampSQL.getTypeChampList());
        liste_type.setValue(liste_type.getItems().get(0));

        liste_proprio.getItems().setAll(ClientSQL.getClientsList());
        liste_proprio.setValue(liste_proprio.getItems().get(0));
    }

    @FXML
    public void onAddPoint() {
        //TODO: Faire l'ajout de nouveau champs de textes
    }

    public void onSubmit() {
        // TODO: Faire l'ajout du champs à la BDD
    }

    public void defineChampController(ChampController champController) {
        this.champController = champController;
    }
}