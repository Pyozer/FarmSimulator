package application.controlleurs.commande;

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
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

import static application.Constant.TYPE_BOTT_CARRE;
import static application.Constant.TYPE_BOTT_ROND;

/**
 * Controlleur pour la gestion d'une commande (Ajout/Modification) d'une commande
 */
public class EditCommandeController {

    /** Layout **/
    @FXML private BorderPane bpane;

    @FXML private JFXDatePicker date_commande;
    @FXML private JFXComboBox<Champ> liste_champs;
    @FXML private JFXComboBox<String> liste_transport;
    @FXML private JFXComboBox<String> liste_type_bott;
    @FXML private JFXTextField tMaxTransp;
    @FXML private JFXTextField coutCom;

    @FXML private Label title;

    private Commande commandeToEdit;
    private List<Champ> listChamps;

    private CommandeController commandeController;

    private boolean isEdit = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        // Initalisation des ComboBox
        listChamps = ChampSQL.getChampsList();
        liste_champs.getItems().setAll(listChamps);
        liste_champs.setValue(liste_champs.getItems().get(0));

        liste_transport.getItems().setAll("Le client", "Eta", "Négociant");
        liste_transport.setValue(liste_transport.getItems().get(0));

        liste_type_bott.getItems().setAll(TYPE_BOTT_ROND, TYPE_BOTT_CARRE, "Pas Demandé");
        liste_type_bott.setValue(liste_type_bott.getItems().get(0));

        date_commande.setValue(LocalDate.now());
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Commande commande) {
        if(isEdit) {
            title.setText("Modifier une commande");

            commandeToEdit = commande;

            date_commande.setValue(commande.getDate());

            Champ champCommand = commande.getChampCommande();
            for (Champ champ : listChamps)
                if (champ.equals(champCommand))
                    liste_champs.getItems().remove(champ);

            liste_champs.getItems().add(commande.getChampCommande());
            liste_champs.setValue(commande.getChampCommande());

            liste_transport.setValue(commande.getTransport());
            liste_type_bott.setValue(commande.getTypeBott());
            tMaxTransp.setText(String.valueOf(commande.getTailleMax()));
            coutCom.setText(String.valueOf(commande.getCout()));
        }
    }

    @FXML
    public void onSubmit() {
        LocalDate inputDate = date_commande.getValue();
        Champ inputChamp = liste_champs.getValue();
        String inputTransport = liste_transport.getValue();
        String inputTypeBott = liste_type_bott.getValue();
        String inputTMaxTranspString = tMaxTransp.getText().trim();
        String inputCoutComString = coutCom.getText().trim();

        if(inputDate.toString().isEmpty() || inputTransport.isEmpty() || inputTypeBott.isEmpty() || inputChamp == null || inputTMaxTranspString.isEmpty() || inputCoutComString.isEmpty()) {
            AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
            alert.show();
        } else {
            try {

                float inputTMaxTransp = Float.parseFloat(inputTMaxTranspString.replace(',', '.'));
                float inputCoutCom = Float.parseFloat(inputCoutComString.replace(',', '.'));

                String message = "La commande a bien été";
                if(isEdit) {
                    commandeToEdit.setDate(inputDate);
                    commandeToEdit.setChampCommande(inputChamp);
                    commandeToEdit.setTransport(inputTransport);
                    commandeToEdit.setTypeBott(inputTypeBott);
                    commandeToEdit.setTailleMax(inputTMaxTransp);
                    commandeToEdit.setCout(inputCoutCom);

                    CommandeSQL.editCommande(commandeToEdit);

                    message += " modifié !";
                } else {
                    CommandeSQL.addCommande(new Commande(0, inputTransport, inputTypeBott, inputTMaxTransp, inputDate.toString(), 0, 0, inputChamp, false)); //0 = T max

                    message += " ajouté !";
                }

                AlertDialog alert = new AlertDialog("Succès", null, message, Alert.AlertType.INFORMATION);
                alert.show();

                commandeController.initData();

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();

            } catch (NumberFormatException  e){
                AlertDialog alert = new AlertDialog("Erreur", null, "Les champs de texte à chiffres doivent être un nombre !\nUtilisez un . ou , pour les nombres décimaux.", Alert.AlertType.ERROR);
                alert.show();
            }
        }
    }

    public void defineCommandeController(CommandeController commandeController) {
        this.commandeController = commandeController;
    }
}