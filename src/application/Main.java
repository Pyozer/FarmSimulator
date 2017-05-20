package application;

import application.classes.Settings;
import application.classes.SwitchView;
import application.database.DBModel;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Création de la base de données si absente
        /*DBModel model = new DBModel();
        model.createDataBase();

        Settings settings = new Settings();
        settings.setParams("already_boot", null);

        SwitchView switchView = new SwitchView("accueil_app", "styles.css", Constant.ACCUEIL_APP_TITLE);

        if(settings.getParams("already_boot") == null) {
        	switchView = new SwitchView("params_home", "styles.css", Constant.FIRST_PARAMS_HOME_TITLE);
        }
        switchView.showScene();*/

        SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE);
        switchView.showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}