package application.controlleurs.champ;

import application.classes.AlertDialog;
import application.classes.GoogleMaps;
import application.classes.JSONManager;
import application.classes.Polygon;
import application.controlleurs.CarteController;
import application.modeles.*;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Controlleur pour l'ajout d'un champ
 */
public class EditChampController extends CarteController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private StackPane googleMaps;

    @FXML private JFXComboBox<Culture> liste_type;
    @FXML private JFXComboBox<Agriculteur> liste_proprio;
    @FXML private JFXTextField surface;
    @FXML private JFXTextField adresse;

    @FXML private Label title;

    private GoogleMaps gMaps;
    private ChampController champController;
    private Champ selectedChamp;

    private String coords_edited;

    private boolean isEdit = false;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        gMaps = new GoogleMaps("champ/maps_add_champ", this);
        gMaps.setParent(googleMaps);

        liste_type.getItems().setAll(ChampSQL.getTypeChampList());
        liste_type.setValue(liste_type.getItems().get(0));

        liste_proprio.getItems().setAll(ClientSQL.getClientsList());
        liste_proprio.setValue(liste_proprio.getItems().get(0));
    }

    public void setEditionMode(boolean state) {
        isEdit = state;
    }

    public void initView(Champ champ) {
        if(isEdit) {
            title.setText("Modifier le champ");

            selectedChamp = champ;

            adresse.setText(champ.getAdresse());
            surface.setText(String.valueOf(champ.getSurface()));

            for (int i = 0;i < liste_type.getItems().size();i++)
                if (liste_type.getItems().get(i).equals(champ.getTypeCulture()))
                    liste_type.getItems().remove(liste_type.getItems().get(i));
            liste_type.getItems().add(champ.getTypeCulture());
            liste_type.setValue(champ.getTypeCulture());

            for (int i = 0;i < liste_proprio.getItems().size();i++)
                if (liste_proprio.getItems().get(i).equals(champ.getProprietaire()))
                    liste_proprio.getItems().remove(liste_proprio.getItems().get(i));
            liste_proprio.getItems().add(champ.getProprietaire());
            liste_proprio.setValue(champ.getProprietaire());
        }
    }

    public void askToLoadChamps() {
        gMaps.addChamp(selectedChamp);
    }

    public void onSubmit() {
        Agriculteur inputProprio = liste_proprio.getValue();
        Culture inputCulture = liste_type.getValue();
        String inputAdresse = adresse.getText();
        String inputSurface = surface.getText();

        try {
            if (inputProprio == null || inputCulture == null || coords_edited.isEmpty() || inputAdresse.isEmpty() || inputSurface.isEmpty()) {
                AlertDialog alert = new AlertDialog("Erreur", null, "Vous devez remplir tous les champs de texte !", Alert.AlertType.ERROR);
                alert.show();
            } else {
                String message = "Le champ a bien été";

                Polygon champ = JSONManager.readPolygon(coords_edited);
                System.out.println(champ);
                Float surface = Float.parseFloat(inputSurface.replace(',', '.'));

                if (isEdit) {
                    selectedChamp.setProprietaire(inputProprio);
                    selectedChamp.setTypeCulture(inputCulture);
                    selectedChamp.setCoordChamp(champ);
                    selectedChamp.setCoordCenter(champ.getCenter());
                    selectedChamp.setSurface(surface);
                    selectedChamp.setAdresse(inputAdresse);

                    ChampSQL.editChamp(selectedChamp);

                    message += " modifié !";
                } else {
                    ChampSQL.addChamp(new Champ(0, surface, inputAdresse, champ.getCenter(), champ, inputCulture, inputProprio));

                    message += " ajouté !";
                }

                AlertDialog alert = new AlertDialog("Succès", null, message, Alert.AlertType.INFORMATION);
                alert.show();

                champController.initData();
                champController.clearAllSelection();

                Stage stage = (Stage) bpane.getScene().getWindow();
                stage.close();
            }
        } catch (NumberFormatException  e){
            AlertDialog alert = new AlertDialog("Erreur", null, "Les champs de texte à chiffres doivent être un nombre !\nUtilisez un . ou , pour les nombres décimaux.", Alert.AlertType.ERROR);
            alert.show();
        }
    }

    public void defineChampController(ChampController champController) {
        this.champController = champController;
    }

    public void setPolygonEdited(String polygon) {
        coords_edited = polygon;
    }

}