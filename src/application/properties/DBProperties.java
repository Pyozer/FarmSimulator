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

    /**
     * Création du fichier "database.properties"
     */
    public void makeDbProperties(String hote, String port, String dbname, String user, String password) {

        try {
            OutputStream output = new FileOutputStream(FILE_CONFIG);
            properties.setProperty(PROP_HOST, hote);
            properties.setProperty(PROP_PORT, port);
            properties.setProperty(PROP_DB, dbname);
            properties.setProperty(PROP_USER, user);
            properties.setProperty(PROP_PASS, password);
            properties.store(output, null);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(DBProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void makeDefaultDbProperties() {
        makeDbProperties(PROP_HOST_DEF, PROP_PORT_DEF, PROP_DB_DEF, PROP_USER_DEF, PROP_PASS_DEF);
    }

    /**
     * On charge le fichier "database.properties" en type Properties
     *
     * @return
     */
    public Properties loadPropertiesFile() {
        try {
            InputStream inputStream = new FileInputStream(FILE_CONFIG);
            properties.load(inputStream);

            if (properties.getProperty(PROP_HOST) == null
                    || properties.getProperty(PROP_PORT) == null
                    || properties.getProperty(PROP_DB) == null
                    || properties.getProperty(PROP_USER) == null
                    || properties.getProperty(PROP_PASS) == null) {


                makeDefaultDbProperties();

                inputStream = new FileInputStream(FILE_CONFIG);
                properties.load(inputStream);
            }

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