package application.controlleurs.parametre;

import application.Constant;
import application.classes.*;
import application.modeles.EtaSettings;
import application.properties.SettingsProperties;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.Properties;

/**
 * Controlleur de la vue de paramétrage des infos de l'Eta
 */
public class ParamsInfosController implements Constant, APIGoogleMap {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField name_eta; // Nom de l'Eta
	@FXML private JFXTextField adr_eta; // Adresse postal de l'Eta
    @FXML private StackPane googleMaps;

    private GoogleMaps gMaps;

	private Point position_eta = null;

    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());

        gMaps = new GoogleMaps("parametre/maps_eta_position", this);
        gMaps.setParent(googleMaps);
	}

	@FXML
    private void btnNextAction() {
	    String nomInput = name_eta.getText().trim();
	    String adresseInput = adr_eta.getText().trim();

		if (!nomInput.isEmpty() && !adresseInput.isEmpty() && position_eta != null) {
		    if(!EtaSettings.checkIfExists(nomInput, adresseInput)) {
                EtaSettings.addEta(nomInput, adresseInput, position_eta);

                Properties prop = SettingsProperties.loadSettingsPropertiesFile();
                if (prop != null) {
                    prop.setProperty(PROP_ALREADY_RUN, "true");
                }

                SettingsProperties.makeSettingsProperties(prop);

                loadLogin();
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Un Eta possède déjà le même nom ou la même adresse !", Alert.AlertType.ERROR);
                alert.show();
            }
		} else {
			AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
			alert.show();
		}
	}

	private void loadLogin() {
        SwitchView switchView = new SwitchView("home_login", Constant.HOME_LOGIN_TITLE, bpane);
        switchView.showScene();
	}

	public void setMarkerEdited(String marker_pos) {
        System.out.println("ok");
        if(marker_pos.isEmpty()) {
            position_eta = null;
            System.out.println("ok null");
        }else {
            position_eta = JSONManager.readPoint(marker_pos);
            System.out.println("ok pt");
        }
        System.out.println("Point : " + position_eta);
    }

    public void log(String msg) {
        System.err.println(msg);
    }

}