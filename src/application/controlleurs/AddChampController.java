package application.controlleurs;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class AddChampController implements Initializable {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<String> liste_type;
    @FXML private JFXComboBox<String> liste_proprio;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        liste_type.getItems().addAll("Blé", "Colza", "Orge");
        liste_type.setValue("Blé");

        liste_proprio.getItems().addAll("Robert Downey Jr", "Chris Evans", "Soprano", "Jean-Claude Van Damme");
        liste_proprio.setValue("Chris Evans");

    }

    @FXML
    public void onAddPoint() {
        System.out.println("BOUTON AJOUTER POINT CLIQUÉ");
    }

    public void onSubmit() {
        System.out.println("BOUTON SUBMIT CLIQUÉ");
    }


}
