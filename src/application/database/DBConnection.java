package application.database;

import application.Constant;
import application.classes.SwitchView;
import application.properties.DBProperties;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour gérer la connexion à la base de donnée
 */
public class DBConnection {

    private static Connection con;
    private static String url;
    private static String user;
    private static String pass;
    private static String unicode = "?useUnicode=yes&characterEncoding=UTF-8";


    /**
     * Défini les variables de connexion
     */
    private static void defineProperties() {
        Properties properties = new DBProperties().loadPropertiesFile();
        url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/" + properties.getProperty("db");
        user = properties.getProperty("user");
        pass = properties.getProperty("password");
    }

    /**
     * Permet la liaison lors de commandes de création en SQL
     * CREATE DATABASE, CREATE TABLE
     * @return Connection
     */
    public static Connection makeDataBase() throws SQLException {
        defineProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("Driver O.K.");
            System.out.println(url);
            System.out.println(user);
            System.out.println(pass);
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Connexion effective !");

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            SwitchView switchView = new SwitchView("parametre/params_bdd", Constant.FIRST_PARAMS_BDD_TITLE, true);
            switchView.showScene();
        }
        return con;
    }

    /**
     * Permet la liaison lors de commandes d'opération en SQL
     * SELECT, INSERT INTO, UPDATE, DELETE FROM
     * @return Connection
     */
    public static Connection getConnection() {
        defineProperties();

        try {
            if(!con.isClosed()) {
                return con;
            }
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + unicode, user, pass);

        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            SwitchView switchView = new SwitchView("params_bdd", Constant.FIRST_PARAMS_BDD_TITLE, true);
            switchView.showScene();
        }
        return con;
    }

    /**
     * Permet la liaison lors de commandes d'opération en SQL
     * SELECT, INSERT INTO, UPDATE, DELETE FROM
     * @return boolean
     */
    public static boolean checkConnection() {
        defineProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url + unicode, user, pass);
            con.close();
            return true;
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
