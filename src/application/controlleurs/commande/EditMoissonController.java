package application.controlleurs.commande;

import application.classes.AlertDialog;
import application.modeles.Commande;
import application.modeles.Moisson;
import application.modeles.MoissonSQL;
import application.modeles.Vehicule;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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

    private AffectationController affectationController;

    private boolean isEdit = false;
    private Moisson moissonToEdit;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        time_debut.setIs24HourView(true);
        time_fin.setIs24HourView(true);

        date_debut.setValue(LocalDate.now());
        date_fin.setValue(LocalDate.now());
        time_debut.setValue(LocalTime.now());
        time_fin.setValue(LocalTime.now());
    }


    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Moisson moisson) {
        if(isEdit) {

            moissonToEdit = moisson;

            poid_recolte.setText(moissonToEdit.getNbTonne().toString());
            nb_Kilo.setText(moissonToEdit.getNbKm().toString());

            LocalDateTime fin = moissonToEdit.getDatetimeFin();
            LocalDateTime debut = moissonToEdit.getDatetimeDebut();

            date_debut.setValue(debut.toLocalDate());
            time_debut.setValue(debut.toLocalTime());

            date_fin.setValue(fin.toLocalDate());
            time_fin.setValue(fin.toLocalTime());

        }
    }


    @FXML
    public void handleSaveMoisson() {

        try {
            String inputPoidRecolte= poid_recolte.getText().trim();
            String inputNbKilo = nb_Kilo.getText().trim();

            LocalDate inputDateDebut = date_debut.getValue();
            LocalTime inputTimeDebut = time_debut.getValue();

            LocalDate inputDateFin = date_fin.getValue();
            LocalTime inputTimeFin = time_fin.getValue();

            if (inputPoidRecolte.isEmpty() || inputNbKilo.isEmpty() || inputDateDebut.toString().isEmpty() || inputDateFin.toString().isEmpty() || inputTimeDebut.toString().isEmpty() || inputTimeFin.toString().isEmpty()) {
                AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
                alert.show();
            } else {
                Float poidRecolte = Float.parseFloat(inputPoidRecolte.replace(',', '.'));
                Float nbKilo = Float.parseFloat(inputNbKilo.replace(',', '.'));

                LocalDateTime tempInputDateTimeFin = LocalDateTime.of(inputDateFin, inputTimeFin);
                LocalDateTime tempInputDateTimeDebut = LocalDateTime.of(inputDateDebut, inputTimeDebut);

                MoissonSQL.editMoisson(selectedCommande, selectedVehicule, tempInputDateTimeDebut, tempInputDateTimeFin, nbKilo, poidRecolte);

                AlertDialog alert = new AlertDialog("Modification du rapport", "Succès de la modification", "Le rapport de moisson à bien été modifié !");
                alert.show();

                affectationController.defineVehiculeSelected(selectedVehicule);

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException  e){
            AlertDialog alert = new AlertDialog("Erreur", null, "Les champs de texte à chiffres doivent être un nombre !\nUtilisez un . ou , pour les nombres décimaux.", Alert.AlertType.ERROR);
            alert.show();
        }
    }

    public void defineVariableMoisson(Commande commande, Vehicule vehicule) {
        this.selectedCommande = commande;
        this.selectedVehicule = vehicule;
    }

    public void defineCommandeController(AffectationController affectationController) {
        this.affectationController = affectationController;
    }
}