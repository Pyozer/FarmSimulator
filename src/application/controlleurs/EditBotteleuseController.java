package application.controlleurs;

import application.modeles.Botteleuse;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur pour ma modification d'une botteleuse
 */
public class EditBotteleuseController {

    /** Layout **/
    @FXML private BorderPane bpane;
    
    /** Elements **/
    @FXML private JFXTextField modele;
    @FXML private JFXTextField marque;
    @FXML private JFXComboBox<String> type;
    @FXML private JFXComboBox<String> liste_etat;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des ComboBox
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(0));

        type.getItems().addAll("Ronde", "Carré");
        type.setValue(type.getItems().get(0));
    }

    public void initTextFields(Botteleuse botteleuse) {
        modele.setText(botteleuse.getModele());
        marque.setText(botteleuse.getMarque());
        liste_etat.setValue(botteleuse.getEtat());
        type.setValue(botteleuse.isBotte_ronde() ? "Rond" : "Carré");
    }

    @FXML
    public void handleSaveBotteleuse() {
        // TODO : Modification d'une botteleuse
    }
}