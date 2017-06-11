package application.classes;

import application.Constant;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
        MenuItem accueil_item = new MenuItem("Accéder");
        accueil.getItems().add(accueil_item);

        Menu clients = new Menu(CLIENTS);
        MenuItem client_item = new MenuItem("Accéder");
        clients.getItems().add(client_item);

        Menu vehicules = new Menu(VEHICULES);
        MenuItem vehicules_item = new MenuItem("Accéder");
        vehicules.getItems().add(vehicules_item);

        Menu champs = new Menu(CHAMPS);
        MenuItem champs_item = new MenuItem("Accéder");
        champs.getItems().add(champs_item);

        Menu global = new Menu(GLOBAL);
        MenuItem global_item = new MenuItem("Accéder");
        MenuItem global_item2 = new MenuItem("Voir les commandes");
        global.getItems().addAll(global_item, global_item2);

        Menu parametres = new Menu(PARAMETRES);
        MenuItem parametres_item = new MenuItem("Accéder");
        parametres.getItems().add(parametres_item);

        accueil_item.setOnAction(actionEvent -> {
            SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE);
            switchView.showScene();
        });
        client_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("client/client_app", Constant.CLIENT_APP_TITLE);
            switchView.showScene();
        });
        vehicules_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("vehicule/vehicule_app", Constant.VEHICULE_APP_TITLE);
            switchView.showScene();
        });
        champs_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("champ/champ_app", Constant.CHAMP_APP_TITLE);
            switchView.showScene();
        });
        global_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("global_app", Constant.GLOBAL_APP_TITLE);
            switchView.showScene();
        });
        global_item2.setOnAction(event -> {
            SwitchView switchView = new SwitchView("commande/commande_app", Constant.GLOBAL_APP_TITLE);
            switchView.showScene();
        });
        parametres.setOnAction(event -> {
            SwitchView switchView = new SwitchView("parametre/params_app", Constant.PARAMS_APP_TITLE);
            switchView.showScene();
        });

        menuBar.getMenus().setAll(accueil, clients, vehicules, champs, global, parametres);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }
}
