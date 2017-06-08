package application.controlleurs;

import application.classes.AlertDialog;
import application.modeles.Champ;
import application.modeles.ChampSQL;
import application.modeles.Commande;
import application.modeles.CommandeSQL;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

/**
 * Controlleur pour la modification d'une commande
 */
public class EditCommandeController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXDatePicker date_commande;
    @FXML private JFXComboBox<Champ> liste_champs;
    @FXML private JFXComboBox<String> liste_transport;
    @FXML private JFXComboBox<String> liste_type_bott;
    @FXML private JFXTextField tMaxTransp;

    private Commande commandeToEdit;
    private List<Champ> listChamps;

    private CommandeController commandeController;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des ComboBox
        listChamps = ChampSQL.getChampsList();
        liste_champs.getItems().setAll(listChamps);
        liste_champs.setValue(liste_champs.getItems().get(0));

        liste_transport.getItems().setAll("Le client", "ETA", "Négociant");
        liste_transport.setValue(liste_transport.getItems().get(0));

        liste_type_bott.getItems().setAll("Ronde", "Carré", "Pas Demandé");
        liste_type_bott.setValue(liste_type_bott.getItems().get(0));

        date_commande.setValue(LocalDate.now());
    }

    public void initTextFields(Commande commande) {
        commandeToEdit = commande;

        date_commande.setValue(commande.getDate());

        Champ champCommand = commande.getChampCommande();
        for(Champ champ : listChamps)
            if (champ.equals(champCommand))
                liste_champs.getItems().remove(champ);

        liste_champs.getItems().add(commande.getChampCommande());
        liste_champs.setValue(commande.getChampCommande());

        liste_transport.setValue(commande.getTransport());
        liste_type_bott.setValue(commande.getTypebott());
        tMaxTransp.setText(String.valueOf(commande.getTaillemax()));
    }

    @FXML
    public void onSubmit() {
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
                commandeToEdit.setDate(inputDate);
                commandeToEdit.setChampCommande(inputChamp);
                commandeToEdit.setTransport(inputTransport);
                commandeToEdit.setTypebott(inputTypeBott);
                commandeToEdit.setTailleMax(Float.parseFloat(inputTMaxTranspString));

                CommandeSQL.editCommande(commandeToEdit);

                AlertDialog alert = new AlertDialog("Succès", null, "La commande à bien été modifié !", Alert.AlertType.INFORMATION);
                alert.show();

                commandeController.initData();

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException  e){
                AlertDialog alert = new AlertDialog("Erreur", null, "Le format de la taille maximale doit être un nombre !\nUtilisez un . pour les nombres décimaux.", Alert.AlertType.ERROR);
                alert.show();
            }
        }
    }

    public void defineCommandeController(CommandeController commandeController) {
        this.commandeController = commandeController;
    }
}