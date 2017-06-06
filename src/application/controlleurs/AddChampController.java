package application.controlleurs;

import application.modeles.Agriculteur;
import application.modeles.ChampSQL;
import application.modeles.ClientSQL;
import application.modeles.Culture;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

import java.util.List;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class AddChampController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<Culture> liste_type;
    @FXML private JFXComboBox<Agriculteur> liste_proprio;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        liste_type.getItems().setAll(ChampSQL.getTypeChampList());
        liste_type.setValue(liste_type.getItems().get(0));

        liste_proprio.getItems().setAll(ClientSQL.getClientsList());
        liste_proprio.setValue(liste_proprio.getItems().get(0));

    }

    @FXML
    public void onAddPoint() {
        System.out.println("BOUTON AJOUTER POINT CLIQUÉ");
    }

    public void onSubmit() {
        System.out.println("BOUTON SUBMIT CLIQUÉ");
    }


}
