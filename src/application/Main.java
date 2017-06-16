package application;

import application.classes.SHA1;
import application.classes.SwitchView;
import application.database.DBConnection;
import application.properties.SettingsProperties;
import javafx.application.Application;
import javafx.stage.Stage;

import java.security.NoSuchAlgorithmException;
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

        SwitchView switchView = new SwitchView("parametre/params_home", Constant.PARAMS_APP_TITLE);

        Properties properties = SettingsProperties.loadSettingsPropertiesFile();
        if(properties != null && properties.getProperty(PROP_ALREADY_RUN).equals("true"))
            switchView = new SwitchView("home_login", Constant.ACCUEIL_APP_TITLE);

        switchView.showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}