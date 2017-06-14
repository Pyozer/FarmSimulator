package application.properties;

import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour la gestion des paramètres du logiciel
 * Utilisation d'un fichier "settings.properties".
 */
public class SettingsProperties {

    private Properties properties = new Properties();
    private final static String FILE_CONFIG = "settings.properties";
    private final static String ALREADY_RUN = "false";

    /**
     * Création du fichier "settings.properties"
     */
    public void makeSettingsProperties(String already_run) {

        try {
            OutputStream output = new FileOutputStream(FILE_CONFIG);
            properties.setProperty("already_run", already_run);
            properties.store(output, null);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(SettingsProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makeDefaultSettingsProperties() {
        makeSettingsProperties(ALREADY_RUN);
    }

    /**
     * On charge le fichier "settings.properties" en type Properties
     * @return
     */
    public Properties loadPropertiesFile() {
        try {
            InputStream inputStream = new FileInputStream(FILE_CONFIG);
            properties.load(inputStream);
            return properties;
        } catch (FileNotFoundException ex) {
            // On créer alors le fichier puis rappel cette fonction
            makeDefaultSettingsProperties();
            loadPropertiesFile();
        } catch (IOException ex) {
            Logger.getLogger(SettingsProperties.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
}