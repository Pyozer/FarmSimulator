package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

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
    @FXML private JFXComboBox liste_transport;
    @FXML private JFXComboBox liste_type_bott;
    @FXML private JFXTextField tMaxTransp;


    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        ChampSQL champSQL = new ChampSQL();
        List<Champ> listChamp = champSQL.getChampsList();
        for(Champ champ : listChamp)
            liste_champs.getItems().add(champ);

        liste_transport.getItems().addAll("Le client", "ETA", "Le négociant");

        liste_type_bott.getItems().addAll("Non demandé", "Ronde", "Carré");
    }

    @FXML
    public void handleSubmitCommande() {
        LocalDate inputDateBadFormat = date_commande.getValue();
        DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String inputDate = inputDateBadFormat.format(formatDate);

        Champ inputChamp = liste_champs.getValue();

        String inputTransport = "" + liste_transport.getValue();
        String inputTypeBott = "" + liste_type_bott.getValue();
        String inputTMaxTranspString = tMaxTransp.getText();


        System.out.println(inputDate);
        if(inputDate.isEmpty() || inputTransport.isEmpty() || inputTypeBott.isEmpty() || inputChamp == null || inputTMaxTransp.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        }else {
            CommandeSQL commandeSQL = new CommandeSQL();
            commandeSQL.addCommande(inputDate, inputTypeBott, inputTransport, inputTMaxTransp, inputChamp); //0 = T max

            AlertDialog alert = new AlertDialog("Succès", null, "La commande à bien été ajoutée !", Alert.AlertType.CONFIRMATION);
            alert.show();
        }
    }


}
