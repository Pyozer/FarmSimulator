package application.controlleurs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AddBotteleuseController {

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
        // Titre de la vue

        // Initalise la combobox avec une view
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(0));

        type.getItems().setAll("Ronde", "Carré" );
        type.setValue(type.getItems().get(0));
    }

    @FXML
    public void handleSaveBotteleuse() {
        System.out.println("BOUTON Sauvegarde CLIQUÉ");
        System.out.println("Créer une Botteleuse : " );
        System.out.println("         -Marque  : " + marque.getText());
        System.out.println("         -Modèle  : " + modele.getText());
        System.out.println("         -Etat  : " + liste_etat.getValue());
        System.out.println("         -Type  : " + type.getValue());
    }
}