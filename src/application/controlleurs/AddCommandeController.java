package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Controlleur de la vue de la gestion des clients de l'ETA
 */
public class AddCommandeController {

    /** Layout **/
    @FXML private BorderPane bpane;
    /** Elements **/
    @FXML private JFXDatePicker date_commande;
    @FXML private JFXComboBox<Champ> liste_champs;
    @FXML private JFXComboBox<String> liste_transport;
    @FXML private JFXComboBox<String> liste_type_bott;
    @FXML private JFXTextField tMaxTransp;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        ChampSQL champSQL = new ChampSQL();
        liste_champs.getItems().setAll(champSQL.getChampsList());
        liste_champs.setValue(liste_champs.getItems().get(0));

        liste_transport.getItems().setAll("Le client", "ETA", "Négociant");
        liste_transport.setValue(liste_transport.getItems().get(0));

        liste_type_bott.getItems().setAll("Ronde", "Carré", "Non Demandé");
        liste_type_bott.setValue(liste_type_bott.getItems().get(0));

        date_commande.setValue(LocalDate.now());
    }

    @FXML
    public void handleSubmitCommande() {
        LocalDate inputDate = date_commande.getValue();

        Champ inputChamp = liste_champs.getValue();

        String inputTransport = liste_transport.getValue();
        String inputTypeBott = liste_type_bott.getValue();
        String inputTMaxTranspString = tMaxTransp.getText();

        if(inputDate.toString().isEmpty() || inputTransport.isEmpty() || inputTypeBott.isEmpty() || inputChamp == null || inputTMaxTranspString.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            try{
                float inputTmaxTransp = Float.parseFloat(inputTMaxTranspString);
                CommandeSQL commandeSQL = new CommandeSQL();
                commandeSQL.addCommande(inputDate.toString(), inputTypeBott, inputTransport, inputTmaxTransp, inputChamp); //0 = T max

                AlertDialog alert = new AlertDialog("Succès", null, "La commande à bien été ajoutée !", Alert.AlertType.CONFIRMATION);
                alert.show();

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException  e){
                AlertDialog alert = new AlertDialog("Erreur", null, "Le format de la taille maximale doit être un nombre !\nUtilisez un . pour les nombres décimaux.", Alert.AlertType.ERROR);
                alert.show();
            }
        }
    }


}
