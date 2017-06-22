package application.controlleurs.parametre;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.SwitchView;
import application.modeles.EtaSettings;
import application.modeles.User;
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

	private User userDefine;
	private boolean isEdition = false;

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

		boolean isOk = false;

		if(!isEdition) {
            if (!nomInput.isEmpty() && !prenomInput.isEmpty()&& !emailInput.isEmpty() && !passwordInput.isEmpty() && !passwordConfInput.isEmpty()) {
                if(!UserSQL.checkIfExists(email.getText())) {
                    isOk = true;
                } else {
                    AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échouée.\nLa confirmation du mot de passe est incorrecte.", Alert.AlertType.ERROR);
                    alert.show();
                }
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échouée !\nVeuillez saisir tous les champs de texte.", Alert.AlertType.ERROR);
                alert.show();
            }
        } else {
            if (!nomInput.isEmpty() && !prenomInput.isEmpty()&& !emailInput.isEmpty() && ((passwordInput.isEmpty() && passwordConfInput.isEmpty()) || (!passwordInput.isEmpty() && !passwordConfInput.isEmpty()))) {
                isOk = true;
            } else{
                AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échouée !\nVeuillez saisir tous les champs de texte.", Alert.AlertType.ERROR);
                alert.show();
            }
        }

        if(isOk) {
            if (passwordInput.equals(passwordConfInput)) {
                if (!isEdition) {
                    userDefine = new User(0, nomInput, prenomInput, emailInput, passwordInput);

                    UserSQL.addAccount(userDefine);
                } else {
                    userDefine.setNom(nomInput);
                    userDefine.setPrenom(prenomInput);
                    userDefine.setEmail(emailInput);
                    if (!passwordInput.isEmpty())
                        userDefine.setPassword(passwordInput);

                    UserSQL.editAccount(userDefine);
                }

                AlertDialog alert = new AlertDialog("Information", null, "Compte administrateur enregistré.\nDès à présent vous pourrez vous connectez avec ces identifiants.");
                alert.show();

                if (!isEdition)
                    loadParamsInfos();
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Création du compte échouée.\nLa confirmation du mot de passe est incorrecte.", Alert.AlertType.ERROR);
                alert.show();
            }
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

	public void initView(User user) {
        userDefine = user;
        nom.setText(userDefine.getNom());
        prenom.setText(userDefine.getPrenom());
        email.setText(userDefine.getEmail());
    }

	public void setToEditionMode() {
		isEdition = true;
	}
}