package application.controlleurs;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.MenuApp;
import application.classes.SwitchView;
import application.modeles.*;
import com.jfoenix.controls.JFXButton;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

import java.util.Optional;

/**
 * Controlleur de la vue de la gestion des affectations de l'ETA
 */
public class AddAffectationController {

    /** Layout **/
    @FXML private BorderPane bpane;

    /** Element **/
    @FXML private Label titleCommandeSelected;
    @FXML private ComboBox<Vehicule> liste_vehicule;
    @FXML private TextField commande;

    private Vehicule selectedVehicule = null;
    private Commande selectedCommande = null;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        liste_vehicule.getItems().setAll(VehiculeSQL.getVehiculeList());
        liste_vehicule.setValue(liste_vehicule.getItems().get(0));

    }

    @FXML
    public void handleSubmitAffect() {
        selectedVehicule = liste_vehicule.getValue();
        if(selectedVehicule == null){
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
            alert.show();
        }
        else {
            AffectationSQL.addAffect(selectedCommande,selectedVehicule);
            AlertDialog alert = new AlertDialog("Succès", null, "Le rapport de moisson à bien été modifié !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }
    }


    public void defineCommandeSelected(Commande commande) {
        selectedCommande = commande;
        titleCommandeSelected.setText(selectedCommande.toString());

    }

}