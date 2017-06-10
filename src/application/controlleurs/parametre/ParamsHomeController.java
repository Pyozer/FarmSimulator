package application.controlleurs.parametre;

import application.Constant;
import application.classes.SwitchView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Controlleur de la vue de d'accueil de la configuration du logiciel
 */
public class ParamsHomeController {

	/** Layout **/
	@FXML private BorderPane bpane;

	/** Elements **/
	@FXML private Text text_home; // Texte présentation
	
    /**
     * Initializes the controller class.
     */
	public void initialize() {
		bpane.setOnMouseClicked(e -> bpane.requestFocus());

		text_home.setText("Ce logiciel vous permet de gérer votre ETA de manière simple et intuitive.\n"
				+ "Avant tout, vous devez le configurer pour que celui-ci fonctionne.\n"
				+ "Suivez étapes par étapes le processus.");
		text_home.setFont(Font.font ("Raleway", 20));

	}

	@FXML
	private void btnNextAction(ActionEvent event) {
		SwitchView switchView = new SwitchView("parametre/params_account", Constant.PARAMS_ACCOUNT_TITLE);
		switchView.showScene();
	}

}