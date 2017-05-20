package application.database;

import application.Constant;
import application.classes.SwitchView;

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

    private Connection con;
    private String url;
    private String urlForCreate;
    private String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
    private String user;
    private String pass;


    /**
     * Défini les variables de connexion
     */
    public void defineProperties() {
        Properties properties = new DBProperties().loadPropertiesFile();
        url = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/" + properties.getProperty("db");
        urlForCreate = "jdbc:mysql://" + properties.getProperty("host") + ":" + properties.getProperty("port") + "/";
        user = properties.getProperty("user");
        pass = properties.getProperty("password");
    }

    /**
     * Permet la liaison lors de commandes de création en SQL
     * CREATE DATABASE, CREATE TABLE
     * @return
     */
    public Connection makeDataBase() throws SQLException {
        defineProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(urlForCreate, user, pass);

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
     * @return
     */
    public Connection getConnection() {
        defineProperties();
        try {
            Class.forName("com.mysql.jdbc.Driver");
            String unicode = "?useUnicode=yes&characterEncoding=UTF-8";
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
     * @return
     */
    public boolean checkConnection() {
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
