package application.controlleurs.parametre;

import application.classes.AlertDialog;
import application.database.DBConnection;
import application.properties.DBProperties;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Properties;

import static application.Constant.*;

/**
 * Controlleur de la vue de paramétrage de la BDD
 */
public class ParamsBddController {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField hote_bdd; // Champs hote bdd
	@FXML private JFXTextField port_bdd; // Champs hote bdd
	@FXML private JFXTextField dbname_bdd; // Champs hote bdd
	@FXML private JFXTextField identifiant_bdd; // Champs utilisateur bdd
	@FXML private JFXPasswordField password_bdd; // Champs mot de passe bdd

    private boolean isEdition = false;

    /**
     * Initializes the controller class.
     */
	public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        Properties properties = new DBProperties().loadPropertiesFile();

        hote_bdd.setText(properties.getProperty(PROP_HOST));
        port_bdd.setText(properties.getProperty(PROP_PORT));
        dbname_bdd.setText(properties.getProperty(PROP_DB));
        identifiant_bdd.setText(properties.getProperty(PROP_USER));
        password_bdd.setText(properties.getProperty(PROP_PASS));
	}

	@FXML
    private void btnCheckAction() {
        DBProperties properties = new DBProperties();

        properties.makeDbProperties(hote_bdd.getText().trim(), port_bdd.getText().trim(), dbname_bdd.getText().trim(), identifiant_bdd.getText().trim(), password_bdd.getText().trim());


        if (DBConnection.checkConnection()) {

            AlertDialog alert = new AlertDialog("Information", null, "Connexion à la base de donnée réussi !");
            alert.show();

            disableFields();

            if(!isEdition)
                exitWindow();
        } else {
            enableFields();

            AlertDialog alert = new AlertDialog("Erreur", null, "Connexion à la base de donnée impossible !\nVérifiez les données saisies et réessayez.", Alert.AlertType.ERROR);
            alert.show();
        }
	}
	
	private void enableFields() {
        hote_bdd.setDisable(false);
        port_bdd.setDisable(false);
        dbname_bdd.setDisable(false);
        identifiant_bdd.setDisable(false);
        password_bdd.setDisable(false);
    }

    private void disableFields() {
        hote_bdd.setDisable(true);
        port_bdd.setDisable(true);
        dbname_bdd.setDisable(true);
        identifiant_bdd.setDisable(true);
        password_bdd.setDisable(true);
    }

    public void setToEditionMode() {
	    isEdition = true;
    }

	private void exitWindow() {
        Stage stage = (Stage) bpane.getScene().getWindow();
        stage.close();
	}
}
