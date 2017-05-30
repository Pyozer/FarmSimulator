package application;

import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.classes.SwitchView;
import application.database.DBConnection;
import application.modeles.Agriculteur;
import application.modeles.Champ;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


      /*  String request = "SELECT id_vehi, position_vehi FROM vehicule";

        try {
            PreparedStatement preparedStatement = dbCon.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String position = rs.getString("position_vehi");
                position = "[" + position.split(",")[0].trim() + "," + position.split(",")[1].trim() + "]";

                String update = "UPDATE vehicule SET position_vehi='" + position + "' WHERE id_vehi=" + rs.getInt("id_vehi");
                preparedStatement = dbCon.getConnection().prepareStatement(update);
                // Execute SQL statement
                preparedStatement.executeUpdate();
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }*/

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