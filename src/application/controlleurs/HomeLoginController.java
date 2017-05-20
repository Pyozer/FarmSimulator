package application.controlleurs;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import application.Constant;
import application.classes.AlertDialog;
import application.classes.SwitchView;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

/**
 * Controlleur de la vue de connexion au logiciel
 */
public class HomeLoginController  implements Initializable {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private JFXTextField user_login; // Champs identifiant
	@FXML private JFXPasswordField password_login; // Champs mot de passe
	@FXML private Label label_check;
	
    /**
     * Initializes the controller class.
     */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());
	}

	@FXML private void btnLoginAction(ActionEvent event) {
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

		Connection dbCon = new DBConnection().getConnection();
		String request = "SELECT email, password FROM User WHERE email=:email AND password=:password LIMIT 1";
		try {
			NamedParameterStatement stmt = new NamedParameterStatement(dbCon, request);
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

			dbCon.close();
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

        //new SendEmail("jeancharles.msse@gmail.com", "Connexion effectué", "Un connexion a été réalisé sur votre logiciel.").send();

    }

	private void loadHome() {
	    // TODO: TEMPORAIRE !
        SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE, bpane);
        switchView.showScene();
	}

}