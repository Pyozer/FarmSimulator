package application.controlleurs;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.SwitchView;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Controlleur de la vue de connexion au logiciel
 */
public class HomeLoginController {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField user_login; // Champs identifiant
	@FXML private JFXPasswordField password_login; // Champs mot de passe
	@FXML private Label label_check;
	
    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());
	}

	@FXML private void btnLoginAction() {
		if (!user_login.getText().isEmpty() && !password_login.getText().isEmpty()) {
			login(user_login.getText(), password_login.getText());
		} else {
			AlertDialog alert = new AlertDialog("Erreur", null, "Veuillez saisir tous les champs de texte !", Alert.AlertType.ERROR);
			alert.show();
		}
	}

	private void login(String email, String password) {

		label_check.setTextFill(Constant.SUCCESS_COLOR);
		label_check.setText("Identification...");

        boolean login_ok = false;

        // TODO: Faire un UserSQL
		String request = "SELECT email, password FROM User WHERE email=:email AND password=:password LIMIT 1";
		try {
			NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
			stmt.setString("email", email);
			stmt.setString("password", password);
			// execute select SQL stetement
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
			    if(rs.getString("email").equals(email) && rs.getString("password").equals(password)) {
			        System.out.println(rs.getString("email"));
			        System.out.println(rs.getString("password"));
                    login_ok = true;
                }
			}

			stmt.close();
			rs.close();

			label_check.setText("");

			if(login_ok) {
				loadHome();
            } else {
                AlertDialog alert = new AlertDialog("Erreur", null, "Identifiants incorrectes !\nRéessayez.", Alert.AlertType.ERROR);
                alert.show();
            }

		} catch (SQLException e) {
			e.printStackTrace();
		}

    }

	private void loadHome() {
        SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE);
        switchView.showScene();
	}

}