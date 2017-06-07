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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ChronoUnit;import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;

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
    public void handleSaveMoisson() {

        Float inputPoidRecolte= Float.parseFloat(poid_recolte.getText());
        Float inputNbKilo = Float.parseFloat(nb_Kilo.getText());

        LocalDate inputDateDebut = date_debut.getValue();
        LocalTime inputTimeDebut = time_debut.getValue();

        LocalDate inputDateFin = date_fin.getValue();
        LocalTime inputTimeFin = time_fin.getValue();

        LocalDateTime inputDateTimeFin = LocalDateTime.of(inputDateFin, inputTimeFin);

        LocalDateTime inputDateTimeDebut = LocalDateTime.of(inputDateDebut, inputTimeDebut);

        LocalDateTime tempDateTime = LocalDateTime.from( inputDateTimeDebut );

        long years = tempDateTime.until( inputDateTimeFin, ChronoUnit.YEARS);
        tempDateTime = tempDateTime.plusYears( years );

        long months = tempDateTime.until( inputDateTimeFin, ChronoUnit.MONTHS);
        tempDateTime = tempDateTime.plusMonths( months );

        long days = tempDateTime.until( inputDateTimeFin, ChronoUnit.DAYS);
        tempDateTime = tempDateTime.plusDays( days );


        long hours = tempDateTime.until( inputDateTimeFin, ChronoUnit.HOURS);
        tempDateTime = tempDateTime.plusHours( hours );

        long minutes = tempDateTime.until( inputDateTimeFin, ChronoUnit.MINUTES);
        tempDateTime = tempDateTime.plusMinutes( minutes );

        long seconds = tempDateTime.until( inputDateTimeFin, ChronoUnit.SECONDS);

        Float inputDuree = years*


     /*   if (inputDateDebut.toString().isEmpty() || inputDateFin.isEmpty() || inputTimeDebut.isEmpty() || inputTimeFin.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            MoissonSQL.editMoisson(selectedCommande, selectedVehicule, inputDuree, inputDateFin, inputTimeFin, inputNbKilo, inputPoidRecolte);

            AlertDialog alert = new AlertDialog("Succès", null, "Le rapport de moisson à bien été modifié !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }*/
    }

    public void defineVariableMoisson(Commande commande, Vehicule vehicule) {
        this.selectedCommande = commande;
        this.selectedVehicule = vehicule;
    }

}