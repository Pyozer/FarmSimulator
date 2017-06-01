package application.controlleurs;

import application.modeles.Agriculteur;
import application.modeles.ChampSQL;
import application.modeles.ClientSQL;
import application.modeles.Culture;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class AddChampController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<String> liste_type;
    @FXML private JFXComboBox<String> liste_proprio;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        ChampSQL champSQL = new ChampSQL();
        List<Culture> listCulture = champSQL.getTypeChampList();
        for(Culture culture : listCulture)
            liste_type.getItems().add(culture.toString());
        liste_type.setValue(listCulture.get(0).toString());

        ClientSQL clientSQL = new ClientSQL();
        List<Agriculteur> listClient = clientSQL.getClientsList();
        for(Agriculteur agri : listClient)
            liste_proprio.getItems().add(agri.toString());
        liste_proprio.setValue(listClient.get(0).toString());

    }

    @FXML
    public void onAddPoint() {
        System.out.println("BOUTON AJOUTER POINT CLIQUÉ");
    }

    public void onSubmit() {
        System.out.println("BOUTON SUBMIT CLIQUÉ");
    }


}
