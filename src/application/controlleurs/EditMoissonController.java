package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.ClientSQL;
import application.modeles.Commande;
import application.modeles.MoissonSQL;
import application.modeles.Vehicule;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

public class EditMoissonController {

    /** Layout **/
    @FXML private BorderPane bpane;

    /** Elements **/
    @FXML private JFXTextField poid_recolte;
    @FXML private JFXTextField nb_Kilo;
    @FXML private JFXDatePicker date_debut;
    @FXML private JFXDatePicker date_fin;
    @FXML private JFXTimePicker time_debut;
    @FXML private JFXTimePicker time_fin;

    private Commande selectedCommande;
    private Vehicule selectedVehicule;
    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());
    }


    @FXML
    public void handleSubmitCommande() {

        Float inputPoidRecolte= Float.parseFloat(poid_recolte.getText());
        Float inputNbKilo = Float.parseFloat(nb_Kilo.getText());
        String inputDateDebut = date_debut.getValue().toString();
        String inputDateFin = date_fin.getValue().toString();
        String inputTimeDebut = time_debut.getValue().toString();
        String inputTimeFin = time_fin.getValue().toString();

        if (inputNbKilo.isEmpty() || inputPoidRecolte.isEmpty() || inputDateDebut.isEmpty() || inputDateFin.isEmpty() || inputTimeDebut.isEmpty() || inputTimeFin.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            MoissonSQL.editMoisson(selectedCommande, selectedVehicule, inputDateDebut, inputTimeDebut, inputDateFin, inputTimeFin, inputNbKilo, inputPoidRecolte);

            AlertDialog alert = new AlertDialog("Succès", null, "Le client à bien été ajouté !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }
    }

    public void defineVariableMoisson(Commande commande, Vehicule vehicule) {
        this.selectedCommande = commande;
        this.selectedVehicule = vehicule;
    }

}