package application.controlleurs.parametre;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.Point;
import application.classes.SwitchView;
import application.modeles.EtaSQL;
import application.properties.SettingsProperties;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.util.Properties;

/**
 * Controlleur de la vue de paramétrage des infos de l'ETA
 */
public class ParamsInfosController implements Constant {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField name_eta; // Nom de l'ETA
	@FXML private JFXTextField adr_eta; // Adresse postal de l'ETA
	@FXML private JFXTextField posLat_eta; // Position - Latitude de l'ETA
	@FXML private JFXTextField posLong_eta; // Position - Longitude de l'ETA

    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());
	}

	@FXML
    private void btnNextAction() {
	    String nomInput = name_eta.getText().trim();
	    String adresseInput = adr_eta.getText().trim();
	    String posLatInput = posLat_eta.getText().trim();
	    String posLongInput = posLong_eta.getText().trim();

		if (!nomInput.isEmpty() && !adresseInput.isEmpty() && !posLatInput.isEmpty() && !posLongInput.isEmpty()) {
		    if(!EtaSQL.checkIfExists(nomInput, adresseInput)) {
		        Point position = new Point(Double.parseDouble(posLatInput), Double.parseDouble(posLongInput));
                EtaSQL.addEta(nomInput, adresseInput, position);

                Properties prop = SettingsProperties.loadPropertiesFile();
                if (prop != null) {
                    prop.setProperty(PROP_ALREADY_RUN, "true");
                }

                SettingsProperties.makeSettingsProperties(prop);

                loadLogin();
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Un ETA possède déjà le même nom ou la même adresse !", Alert.AlertType.ERROR);
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

}