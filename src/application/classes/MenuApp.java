package application.classes;

import application.Constant;
import javafx.scene.Parent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MenuApp {

    private MenuBar menuBar;

    public MenuApp(BorderPane parent) {

        menuBar = new MenuBar();

        Menu accueil = new Menu("Accueil");
        MenuItem accueil_item = new MenuItem("Retourner à l'accueil");
        accueil.getItems().add(accueil_item);

        Menu clients = new Menu("Clients");
        MenuItem client_item = new MenuItem("Voir les clients");
        MenuItem add_client_item = new MenuItem("Ajouter un client");
        clients.getItems().addAll(client_item, add_client_item);

        Menu vehicules = new Menu("Véhicules");
        MenuItem vehicules_item = new MenuItem("Voir les véhicules");
        MenuItem add_vehicules_item = new MenuItem("Ajouter un véhicule");
        vehicules.getItems().addAll(vehicules_item, add_vehicules_item);

        Menu champs = new Menu("Champs");
        MenuItem champs_item = new MenuItem("Voir les champs");
        MenuItem add_champs_item = new MenuItem("Ajouter un champ");
        champs.getItems().addAll(champs_item, add_champs_item);

        Menu global = new Menu("Global");
        MenuItem global_item = new MenuItem("Voir la vue globale");
        MenuItem commande_item = new MenuItem("Voir les commandes");
        MenuItem add_commande_item = new MenuItem("Ajouter un commande");
        global.getItems().addAll(global_item, commande_item, add_commande_item);

        Menu parametres = new Menu("Paramètres");
        MenuItem parametres_item = new MenuItem("Accéder");
        parametres.getItems().add(parametres_item);

        accueil_item.setOnAction(actionEvent -> {
            SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE, parent);
            switchView.showScene();
        });
        client_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("client/client_app", Constant.CLIENT_APP_TITLE, parent);
            switchView.showScene();
        });

        add_client_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("client/client_app", Constant.CLIENT_APP_TITLE, parent);
            switchView.showScene();

            SwitchView switchViewAdd = new SwitchView("client/edit_client_app", Constant.ADD_CLIENT_APP_TITLE);
            switchViewAdd.setPopUp();
            switchViewAdd.showScene();
        });
        vehicules_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("vehicule/vehicule_app", Constant.VEHICULE_APP_TITLE, parent);
            switchView.showScene();
        });
        add_vehicules_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("vehicule/vehicule_app", Constant.VEHICULE_APP_TITLE, parent);
            switchView.showScene();

            SwitchView switchViewAdd = new SwitchView("vehicule/choix_vehicule_app", Constant.ADD_VEHICULE_APP_TITLE);
            switchViewAdd.setPopUp();
            switchViewAdd.showScene();
        });
        champs_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("champ/champ_app", Constant.CHAMP_APP_TITLE, parent);
            switchView.showScene();
        });
        add_champs_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("champ/champ_app", Constant.CHAMP_APP_TITLE, parent);
            switchView.showScene();

            SwitchView switchViewAdd = new SwitchView("champ/edit_champ_app", Constant.ADD_CHAMP_APP_TITLE);
            switchViewAdd.setPopUp();
            switchViewAdd.showScene();

            closeStage(parent);
        });
        global_item.setOnAction(event -> {
            SwitchView switchView = new SwitchView("global/global_app", Constant.GLOBAL_APP_TITLE, parent);
            switchView.showScene();
        });
        commande_item.setOnAction(event -> {
            SwitchView switchViewCom = new SwitchView("commande/commande_app", Constant.GLOBAL_APP_TITLE, parent);
            switchViewCom.showScene();
        });
        add_commande_item.setOnAction(event -> {
            SwitchView switchViewCom = new SwitchView("commande/commande_app", Constant.GLOBAL_APP_TITLE, parent);
            switchViewCom.showScene();

            SwitchView switchViewAddCom = new SwitchView("commande/edit_commande_app", Constant.ADD_COMMANDE_APP_TITLE);
            switchViewAddCom.setPopUp();
            switchViewAddCom.showScene();
        });
        parametres.setOnAction(event -> {
            SwitchView switchView = new SwitchView("parametre/params_app", Constant.PARAMS_APP_TITLE, parent);
            switchView.showScene();
        });

        menuBar.getMenus().setAll(accueil, clients, vehicules, champs, global, parametres);
    }

    public MenuBar getMenuBar() {
        return menuBar;
    }

    private void closeStage(Parent parent) {
        Stage stage = (Stage) parent.getScene().getWindow();
        stage.close();
    }
}