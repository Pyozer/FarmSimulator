package application.database;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour la gestion des paramètres de connexion à la base de données.
 * Utilisation d'un fichier "database.properties".
 */
public class DBProperties {

    private Properties properties = new Properties();
    private final static String FILE_CONFIG = "database.properties";
    private final static String HOTE = "88.168.230.33";
    private final static String PORT = "3306";
    private final static String DBNAME = "pts2";
    private final static String USER = "pts2";
    private final static String PASSWORD = "pts2";

    /**
     * Création du fichier "database.properties"
     */
    public void makeDbProperties(String hote, String port, String dbname, String user, String password) {

        try {
            OutputStream output = new FileOutputStream(FILE_CONFIG);
            properties.setProperty("host", hote);
            properties.setProperty("port", port);
            properties.setProperty("db", dbname);
            properties.setProperty("user", user);
            properties.setProperty("password", password);
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