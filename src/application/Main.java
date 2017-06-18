package application;

import application.classes.SwitchView;
import application.database.DBConnection;
import application.modeles.EtaSettings;
import application.modeles.UserSQL;
import application.properties.SettingsProperties;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;
import java.util.Properties;

public class Main extends Application implements Constant {

    @Override
    public void start(Stage primaryStage) {

        /*
        System.setProperty("http.proxyHost", "proxy.univ-lemans.fr");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("https.proxyHost", "proxy.univ-lemans.fr");
        System.setProperty("https.proxyPort", "3128");
        */

        // Connexion et création de la bdd
        try {
            DBConnection.makeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SwitchView switchView = new SwitchView("parametre/params_home", Constant.FIRST_PARAMS_HOME_TITLE);

        Properties properties = SettingsProperties.loadSettingsPropertiesFile();
        if(properties != null && properties.getProperty(PROP_ALREADY_RUN).equals("true") && UserSQL.getNbAccount() > 0 && EtaSettings.isAlreadyETA())
            switchView = new SwitchView("home_login", Constant.ACCUEIL_APP_TITLE);

        switchView.showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}