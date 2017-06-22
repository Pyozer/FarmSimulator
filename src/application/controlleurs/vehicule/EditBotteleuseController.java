package application.controlleurs.vehicule;

import application.classes.AlertDialog;
import application.modeles.Botteleuse;
import application.modeles.VehiculeSQL;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

import static application.Constant.TYPE_BOTT_CARRE;
import static application.Constant.TYPE_BOTT_ROND;

/**
 * Controlleur pour gestion (Ajout/Modification) d'une botteleuse
 */
public class EditBotteleuseController {

    /** Layout **/
    @FXML private BorderPane bpane;
    
    /** Elements **/
    @FXML private JFXTextField modele;
    @FXML private JFXTextField marque;
    @FXML private JFXComboBox<String> type;
    @FXML private JFXComboBox<String> liste_etat;

    @FXML private Label title;

    private VehiculeController vehiculeController;
    private ChoixVehiculeController choixVehiculeController;
    private Botteleuse botteleuseToEdit;

    private boolean isEdit = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des ComboBox
        type.getItems().addAll(TYPE_BOTT_ROND, TYPE_BOTT_CARRE);
        type.setValue(type.getItems().get(0));
        liste_etat.getItems().setAll("En maitenance", "Utilisé", "Non utilisé");
        liste_etat.setValue(liste_etat.getItems().get(2));
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Botteleuse botteleuse) {
        if(isEdit) {
            title.setText("Modifier la botteleuse");

            botteleuseToEdit = botteleuse;

            modele.setText(botteleuse.getModele());
            marque.setText(botteleuse.getMarque());
            liste_etat.setValue(botteleuse.getEtat());
            type.setValue(botteleuse.isBotte_ronde() ? TYPE_BOTT_ROND : TYPE_BOTT_CARRE);
        }
        else{
            title.setText("Ajouter une botteleuse");
        }
    }

    @FXML
    public void handleSaveBotteleuse() {
        String inputEtat = liste_etat.getValue();
        String inputMarque = marque.getText();
        String inputModele = modele.getText();
        String inputType = type.getValue();

        if(inputMarque.isEmpty() || inputModele.isEmpty() || inputType.isEmpty() || inputEtat.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            String message = "La botteleuse a bien été";

            if(isEdit) {
                botteleuseToEdit.setEtat(inputEtat);
                botteleuseToEdit.setMarque(inputMarque);
                botteleuseToEdit.setModele(inputModele);

                if(inputType == TYPE_BOTT_ROND) botteleuseToEdit.setBotte_ronde(true);
                else botteleuseToEdit.setBotte_ronde(false);

                VehiculeSQL.editBotteleuse(botteleuseToEdit);

                message += " modifié !";
            } else {
                boolean bottRonde = inputType.equals(TYPE_BOTT_ROND);
                VehiculeSQL.addBotteleuse(new Botteleuse(0, inputMarque, inputModele, inputEtat, null, bottRonde));

                message += " ajouté !";
            }

            AlertDialog alert = new AlertDialog("Succès", null, message, Alert.AlertType.INFORMATION);
            alert.show();

            vehiculeController.initData();
            vehiculeController.clearAllSelection();

            if(choixVehiculeController != null)
                choixVehiculeController.closeWindow();

            Stage stage = (Stage) bpane.getScene().getWindow();
            stage.close();
        }
    }

    public void defineVehiculeController(VehiculeController vehiculeController, ChoixVehiculeController choixVehiculeController) {
        this.vehiculeController = vehiculeController;
        this.choixVehiculeController = choixVehiculeController;
    }
}