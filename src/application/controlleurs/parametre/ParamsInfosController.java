package application.controlleurs.parametre;

import java.sql.ResultSet;
import java.sql.SQLException;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.Settings;
import application.classes.SwitchView;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue de paramétrage des infos de l'ETA
 */
public class ParamsInfosController {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField name_eta; // Champs identifiant
	@FXML private JFXTextField adr_eta; // Champs mot de passe
	
    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());

	}

	@FXML
    private void btnNextAction(ActionEvent event) {
		if (!name_eta.getText().isEmpty() && !adr_eta.getText().isEmpty()) {
		    if(!alreadyEtaExists(name_eta.getText(), adr_eta.getText())) {
                saveEtaInBDD(name_eta.getText(), adr_eta.getText());
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Un ETA possède déjà le même nom ou la même adresse !", Alert.AlertType.ERROR);
                alert.show();
            }
		} else {
			AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
			alert.show();
		}
	}

    /**
     * Vérifie si il n'y a pas déjà un ETA ayant le même nom ou adresse
     * @param name_eta String
     * @param adresse_eta String
     * @return boolean
     */
    private boolean alreadyEtaExists(String name_eta, String adresse_eta) {
        // TODO: Faire un modele SQL
        String request = "SELECT COUNT(*) as rowCount FROM Eta WHERE nom=:nom OR adresse=:adresse";
        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setString("nom", name_eta);
            stmt.setString("adresse", adresse_eta);
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
     * Sauvegarde les informations de l'ETA dans la BDD
     * @param nom_eta String
     * @param adresse_eta String
     */
    private void saveEtaInBDD(String nom_eta, String adresse_eta) {
        // TODO: Faire un modele SQL
        String request = "INSERT INTO Eta(nom, adresse) VALUES(:nom, :adresse)";
        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setString("nom", nom_eta);
            stmt.setString("adresse", adresse_eta);
            stmt.executeUpdate();

            stmt.close();

            Settings settings = new Settings();
            settings.setParams("already_boot", "true");

            loadLogin();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

	private void loadLogin() {
        SwitchView switchView = new SwitchView("parametre/home_login", Constant.HOME_LOGIN);
        switchView.showScene();
	}

}