package application.properties;

import application.Constant;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe pour la gestion des paramètres du logiciel
 * Utilisation d'un fichier "settings.properties".
 */
public class SettingsProperties implements Constant {

    private final static String FILE_CONFIG = "settings.properties";

    /**
     * Création du fichier "settings.properties"
     */
    public static void makeSettingsProperties(Properties properties) {

        try {
            OutputStream output = new FileOutputStream(FILE_CONFIG);
            properties.store(output, null);
            output.close();
        } catch (IOException ex) {
            Logger.getLogger(SettingsProperties.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void makeDefaultSettingsProperties() {
        Properties properties = new Properties();
        properties.setProperty(PROP_ALREADY_RUN, "false");
        makeSettingsProperties(properties);
    }

    /**
     * On charge le fichier "settings.properties" en type Properties
     * @return
     */
    public static Properties loadPropertiesFile() {
        try {
            InputStream inputStream = new FileInputStream(FILE_CONFIG);

            Properties properties = new Properties();
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