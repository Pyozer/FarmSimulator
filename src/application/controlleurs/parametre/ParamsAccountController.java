package application.controlleurs.parametre;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.SwitchView;
import application.modeles.EtaSettings;
import application.modeles.UserSQL;
import application.properties.SettingsProperties;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

import java.security.NoSuchAlgorithmException;
import java.util.Properties;

import static application.Constant.PROP_ALREADY_RUN;

/**
 * Controlleur de la vue de création d'un compte admin
 */
public class ParamsAccountController {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField nom; // Nom de la personne
	@FXML private JFXTextField prenom; // Prénom de la personne
	@FXML private JFXTextField email; // Adresse mail
	@FXML private JFXPasswordField password; // Mot de passe
	@FXML private JFXPasswordField passwordconfirm; // Confirmation du mot de passe
	
    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());
	}

	@FXML
	private void btnNextAction() throws NoSuchAlgorithmException {
		String nomInput = nom.getText().trim();
		String prenomInput = prenom.getText().trim();
		String emailInput = email.getText().trim();
		String passwordInput = password.getText().trim();
		String passwordConfInput = passwordconfirm.getText().trim();

		if (!nomInput.isEmpty() && !prenomInput.isEmpty()&& !emailInput.isEmpty() && !passwordInput.isEmpty() && !passwordConfInput.isEmpty()) {
		    if(passwordInput.equals(passwordConfInput)) {
		    	if(!UserSQL.checkIfExists(email.getText())) {
					UserSQL.addAccount(nomInput, prenomInput, emailInput, passwordInput);

					AlertDialog alert = new AlertDialog("Information", null, "Compte administrateur enregistré.\nDès à présent vous vous connecterez avec ces identifiants.");
					alert.show();

					loadParamsInfos();
				} else {
					AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échoué.\nL'adresse mail existe déjà !", Alert.AlertType.ERROR);
					alert.show();
				}
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échoué.\nLa confirmation du mot de passe est incorrecte.", Alert.AlertType.ERROR);
                alert.show();
            }
		} else {
            AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échoué !\nVeuillez saisir tous les champs de texte.", Alert.AlertType.ERROR);
            alert.show();
		}
	}

	/**
	 * Charge la fenetre pour les paramètres de l'Eta
	 */
	private void loadParamsInfos() {
		if(!EtaSettings.isAlreadyETA()) {
			SwitchView switchView = new SwitchView("parametre/params_infos", Constant.FIRST_PARAMS_INFOS_TITLE, bpane);
			switchView.showScene();
		} else {
            Properties prop = SettingsProperties.loadSettingsPropertiesFile();
            if (prop != null) {
                prop.setProperty(PROP_ALREADY_RUN, "true");
            }

            SettingsProperties.makeSettingsProperties(prop);

			SwitchView switchView = new SwitchView("home_login", Constant.HOME_LOGIN_TITLE, bpane);
			switchView.showScene();
		}
	}

}