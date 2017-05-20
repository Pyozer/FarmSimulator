package application.classes;

import application.Constant;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;

public class MenuApp {

    private MenuBar menuBar;
    private BorderPane parent;
    private final static String ACCUEIL = "Accueil";
    private final static String CLIENTS = "Clients";
    private final static String VEHICULES = "Véhicules";
    private final static String CHAMPS = "Champs";
    private final static String GLOBAL = "Résumé";
    private final static String PARAMETRES = "Paramètres";

    public MenuApp(BorderPane parent) {

        menuBar = new MenuBar();

        Menu accueil = new Menu(ACCUEIL);
        Menu clients = new Menu(CLIENTS);
        Menu vehicules = new Menu(VEHICULES);
        Menu champs = new Menu(CHAMPS);
        Menu global = new Menu(GLOBAL);
        Menu parametres = new Menu(PARAMETRES);

        accueil.showingProperty().addListener(
                (observableValue, oldValue, newValue) -> {
                    System.out.println("lol");
                    SwitchView switchView = new SwitchView("accueil_app", Constant.CLIENT_APP_TITLE, parent);
                    switchView.showScene();
                }
        );

        accueil.setOnAction(actionEvent -> {
            System.out.println("lol");
            SwitchView switchView = new SwitchView("accueil_app", Constant.CLIENT_APP_TITLE, parent);
            switchView.showScene();
        });
        clients.setOnAction(event -> {
            SwitchView switchView = new SwitchView("client_app", Constant.CLIENT_APP_TITLE, parent);
            switchView.showScene();
        });
        vehicules.setOnAction(event -> {
            SwitchView switchView = new SwitchView("vehicule_app", Constant.CLIENT_APP_TITLE, parent);
            switchView.showScene();
        });
        champs.setOnAction(event -> {
            SwitchView switchView = new SwitchView("champ_app", Constant.CLIENT_APP_TITLE, parent);
            switchView.showScene();
        });
        global.setOnAction(event -> {
            //SwitchView switchView = new SwitchView("global_app", Constant.CLIENT_APP_TITLE, parent);
            //switchView.showScene();
        });
        parametres.setOnAction(event -> {
            //SwitchView switchView = new SwitchView("parametres_app", Constant.CLIENT_APP_TITLE, parent);
            //switchView.showScene();
        });

        menuBar.getMenus().setAll(accueil, clients, vehicules, champs, global, parametres);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
