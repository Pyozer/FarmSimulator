package application.controlleurs.commande;

import application.Constant;
import application.classes.AlertDialog;
import application.modeles.*;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class EditMoissonController implements Constant {

    /** Layout **/
    @FXML private BorderPane bpane;

    /** Elements **/
    @FXML private JFXTextField poid_recolte;
    @FXML private JFXTextField nb_Kilo;
    @FXML private JFXDatePicker date_debut;
    @FXML private JFXDatePicker date_fin;
    @FXML private JFXTimePicker time_debut;
    @FXML private JFXTimePicker time_fin;

    @FXML private Label title;

    private Commande selectedCommande;
    private Vehicule selectedVehicule;

    private AffectationController affectationController;

    private boolean isEdit = false;
    private Moisson moissoneToEdit;

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
            title.setText("Modifier le rapport");

            moissoneToEdit = moisson;

            poid_recolte.setText("" +  moissoneToEdit.getNbTonne());
            nb_Kilo.setText("" + moissoneToEdit.getNbKilo());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime fin = LocalDateTime.parse(moissoneToEdit.getH_fin(), formatter);
            LocalDateTime debut = LocalDateTime.parse(moissoneToEdit.getH_debut(), formatter);

            LocalDate d_fin = fin.toLocalDate();
            LocalTime h_fin = fin.toLocalTime();

            LocalDate d_debut = debut.toLocalDate();
            LocalTime h_debut = debut.toLocalTime();

            date_debut.setValue(d_fin);
            time_debut.setValue(h_debut);

            date_fin.setValue(d_fin);
            time_fin.setValue(h_fin);

        }
    }


    @FXML
    public void handleSaveMoisson() {

        Float inputPoidRecolte= Float.parseFloat(poid_recolte.getText());
        Float inputNbKilo = Float.parseFloat(nb_Kilo.getText());

        LocalDate inputDateDebut = date_debut.getValue();
        LocalTime inputTimeDebut = time_debut.getValue();

        LocalDate inputDateFin = date_fin.getValue();
        LocalTime inputTimeFin = time_fin.getValue();

        if (inputDateDebut.toString().isEmpty() || inputDateFin.toString().isEmpty() || inputTimeDebut.toString().isEmpty() || inputTimeFin.toString().isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            Float inputDuree = getDuree(inputDateDebut, inputTimeDebut, inputDateFin, inputTimeFin);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            LocalDateTime tempInputDateTimeFin = LocalDateTime.of(inputDateFin, inputTimeFin);
            LocalDateTime tempInputDateTimeDebut = LocalDateTime.of(inputDateDebut, inputTimeDebut);

            String inputDateTimeFin = tempInputDateTimeFin.format(formatter);
            String inputDateTimeDebut = tempInputDateTimeDebut.format(formatter);

            MoissonSQL.editMoisson(selectedCommande, selectedVehicule, inputDuree, inputDateTimeFin, inputDateTimeDebut, inputNbKilo, inputPoidRecolte);

            AlertDialog alert = new AlertDialog("Succès", null, "Le rapport de moisson à bien été modifié !", Alert.AlertType.INFORMATION);
            alert.show();
        }
    }

    private Float getDuree(LocalDate inputDateDebut, LocalTime inputTimeDebut, LocalDate inputDateFin, LocalTime inputTimeFin) {
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

        Double inputDuree = (years*Constant.HEURE_PAR_AN + months*Constant.HEURE_PAR_MOIS + days*Constant.HEURE_PAR_JOUR + minutes*Constant.HEURE_PAR_MINUTE + seconds*Constant.HEURE_PAR_SECONDE);

        return inputDuree.floatValue();
    }

    public void defineVariableMoisson(Commande commande, Vehicule vehicule) {
        this.selectedCommande = commande;
        this.selectedVehicule = vehicule;
    }

    public void defineCommandeController(AffectationController affectationController) {
        this.affectationController = affectationController;
    }
}