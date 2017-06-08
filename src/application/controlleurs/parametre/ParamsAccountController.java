package application.controlleurs.parametre;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.SwitchView;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	private void btnNextAction() {
		if (!nom.getText().isEmpty() && !prenom.getText().isEmpty()&& !email.getText().isEmpty() && !password.getText().isEmpty() && !passwordconfirm.getText().isEmpty()) {
		    if(password.getText().equals(passwordconfirm.getText())) {
		    	if(!alreadyAccountExists(email.getText())) {
					saveAccountToBDD(nom.getText(), prenom.getText(), email.getText(), password.getText());
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
	 * Vérifie si l'adresse mail n'a pas déjà été utilisé
	 * @param email String
	 * @return boolean
	 */
	private boolean alreadyAccountExists(String email) {
		// TODO: Faire un UserSQL

		String request = "SELECT COUNT(*) as rowCount FROM User WHERE email=:email";
		try {
			NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
			stmt.setString("email", email);
			ResultSet res = stmt.executeQuery();

			res.next();
			int count = res.getInt("rowCount");

			res.close();
			stmt.close();

			return count >= 1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Sauvegarde le compte dans la base de donnée
	 * @param nom String
	 * @param prenom String
	 * @param email String
	 * @param password String
	 */
	private void saveAccountToBDD(String nom, String prenom, String email, String password) {
		// TODO: Faire un UserSQL
		String request = "INSERT INTO User(nom, prenom, email, password) VALUES(:nom, :prenom, :email, :password)";
		try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setString("nom", nom);
            stmt.setString("prenom", prenom);
            stmt.setString("email", email);
            stmt.setString("password", password);
            stmt.executeUpdate();

			stmt.close();

            AlertDialog alert = new AlertDialog("Information", null, "Compte administrateur enregistré.\nDès à présent vous vous connecterez avec ces identifiants.");
            alert.show();

            loadParamsInfos();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void loadParamsInfos() {
        SwitchView switchView = new SwitchView("parametre/params_infos", Constant.FIRST_PARAMS_INFOS_TITLE);
        switchView.showScene();
	}

}