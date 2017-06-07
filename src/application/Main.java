package application;

import application.classes.SwitchView;
import application.database.DBConnection;
import javafx.application.Application;
import javafx.stage.Stage;

import java.sql.SQLException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Connexion et création de la bdd

        /*System.setProperty("http.proxyHost", "proxy.univ-lemans.fr");
        System.setProperty("http.proxyPort", "3128");
        System.setProperty("https.proxyHost", "proxy.univ-lemans.fr");
        System.setProperty("https.proxyPort", "3128");*/

        try {
            DBConnection.makeDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        SwitchView switchView = new SwitchView("accueil_app", Constant.ACCUEIL_APP_TITLE);
        switchView.showScene();
    }

    public static void main(String[] args) {
        launch(args);
    }
}