package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.AffectationSQL;
import application.modeles.Commande;
import application.modeles.Vehicule;
import application.modeles.VehiculeSQL;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * Controlleur de la vue de l'ajout d'une affectation à une commande
 */
public class AddAffectationController {

    /** Layout **/
    @FXML private BorderPane bpane;

    /** Element **/
    @FXML private Label titleCommandeSelected;
    @FXML private ComboBox<Vehicule> liste_vehicule;

    private Commande selectedCommande = null;
    private AffectationController affectationController;

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
        Vehicule vehiculeSelected = liste_vehicule.getValue();
        if(vehiculeSelected == null){
            AlertDialog alert = new AlertDialog("Erreur", null, "Aucun véhicule n'a été selectionné !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            AffectationSQL.addAffect(selectedCommande, vehiculeSelected);

            AlertDialog alert = new AlertDialog("Succès", null, "Le véhicule\n" + vehiculeSelected + " a bien été affecté à la commande\n" + selectedCommande, Alert.AlertType.INFORMATION);
            alert.show();

            affectationController.defineCommandeSelected(selectedCommande);

            Stage stage = (Stage) bpane.getScene().getWindow();
            stage.close();
        }
    }

    public void defineCommandeSelected(Commande commande) {
        selectedCommande = commande;
        titleCommandeSelected.setText(selectedCommande.toString());
    }

    public void defineAffectController(AffectationController affectationController) {
        this.affectationController = affectationController;
    }
}