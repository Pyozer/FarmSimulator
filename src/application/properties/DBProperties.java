package application.properties;

import application.Constant;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour la gestion des paramètres de connexion à la base de données.
 * Utilisation d'un fichier "database.properties".
 */
public class DBProperties implements Constant {

    private Properties properties = new Properties();
    private final static String FILE_CONFIG = "database.properties";
    private final static String HOTE = "localhost";
    private final static String PORT = "3306";
    private final static String DBNAME = "pts2";
    private final static String USER = "root";
    private final static String PASSWORD = "";

    /**
     * Création du fichier "database.properties"
     */
    public void makeDbProperties(String hote, String port, String dbname, String user, String password) {

        try {
            OutputStream output = new FileOutputStream(FILE_CONFIG);
            properties.setProperty(PROP_HOST, hote);
            properties.setProperty(PROP_PORT, port);
            properties.setProperty(PROP_PORT, dbname);
            properties.setProperty(PROP_USER, user);
            properties.setProperty(PROP_PASS, password);
            properties.store(output, null);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeDefaultDbProperties() {
        makeDbProperties(HOTE, PORT, DBNAME, USER, PASSWORD);
    }

    /**
     * On charge le fichier "database.properties" en type Properties
     * @return
     */
    public Properties loadPropertiesFile() {
        try {
            InputStream inputStream = new FileInputStream(FILE_CONFIG);
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException ex) {
            // On créer alors le fichier puis rappel cette fonction
            makeDefaultDbProperties();
            loadPropertiesFile();
        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}