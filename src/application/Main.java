package application;

import application.classes.SwitchView;
import application.database.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;
import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Connexion à la bdd
        DBConnection dbCon = new DBConnection();
        try {
            dbCon.makeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Settings settings = new Settings();
        //settings.setParams("already_boot", null);

        SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE);

        //if(settings.getParams("already_boot") == null) {
        	//switchView = n  ew SwitchView("params_home", "styles.css", Constant.FIRST_PARAMS_HOME_TITLE);
        //}

        switchView.showScene();

    }

    public static void main(String[] args) {
        launch(args);
    }
}