package application.controlleurs.champ;

import application.modeles.*;
import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur pour l'ajout d'un champ
 */
public class EditChampController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXComboBox<Culture> liste_type;
    @FXML private JFXComboBox<Agriculteur> liste_proprio;

    @FXML private Label title;

    private ChampController champController;
    private Champ selectedChamp;

    private boolean isEdit = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Champ champ) {
        if(isEdit) {
            title.setText("Modifier le champ");

            liste_type.getItems().setAll(ChampSQL.getTypeChampList());
            liste_type.setValue(liste_type.getItems().get(0));

            liste_proprio.getItems().setAll(ClientSQL.getClientsList());
            liste_proprio.setValue(liste_proprio.getItems().get(0));

            selectedChamp = champ;
        }
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