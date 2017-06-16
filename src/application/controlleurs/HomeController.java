package application.controlleurs;

import application.Constant;
import application.classes.SwitchView;
import application.modeles.EtaSettings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import javafx.scene.image.ImageView;

/**
 * Controlleur de la vue de l'accueil
 */
public class HomeController {

    /** Layout **/
    @FXML private BorderPane bpane;
    @FXML private ImageView params;
    @FXML private ImageView exit;
    @FXML private Label title;

    /**
     * Initializes the controller class.
     */
    public void initialize() {
        bpane.setOnMouseClicked(e -> bpane.requestFocus());

        title.setText(EtaSettings.getInfosEta().toString());

        params.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            handleParams();
        });

        exit.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            handleExit();
        });
    }

    @FXML
    public void handleClient() {
        SwitchView switchView = new SwitchView("client/client_app", Constant.CLIENT_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleVehicule() {
        SwitchView switchView = new SwitchView("vehicule/vehicule_app", Constant.VEHICULE_APP_TITLE, bpane);
        switchView.showScene();
    }

    @FXML
    public void handleChamps() {
        SwitchView switchView = new SwitchView("champ/champ_app", Constant.CHAMP_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void handleGlobal() {
        SwitchView switchView = new SwitchView("global/global_app", Constant.GLOBAL_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void handleParams() {
        SwitchView switchView = new SwitchView("parametre/params_app", Constant.PARAMS_APP_TITLE, bpane);
        switchView.showScene();
    }

    public void handleExit() {
        SwitchView switchView = new SwitchView("home_login", Constant.HOME_LOGIN_TITLE, bpane);
        switchView.showScene();
    }
}
