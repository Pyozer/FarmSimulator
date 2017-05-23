package application.controlleurs;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBotteleuseController implements Initializable {

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
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
        // Titre de la vue

        // Initalise la combobox avec une view
        liste_etat.getItems().addAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue("Non utilisé");

        type.getItems().addAll("Ronde", "Carré" );
        type.setValue("Ronde");
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