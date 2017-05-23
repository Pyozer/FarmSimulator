package application.controlleurs;

import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;


import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class AddClientController implements Initializable {

    /** Layout **/
    @FXML private BorderPane bpane;
    /** Elements **/
    @FXML private JFXTextField nom_client;
    @FXML private JFXTextField prenom_client;
    @FXML private JFXTextField tel_client;
    @FXML private JFXTextField adresse_client;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

    }

    @FXML
    public void handleSubmitClient() {
        System.out.println("BOUTON VALIDER CLIQUÉ");
    }


}
