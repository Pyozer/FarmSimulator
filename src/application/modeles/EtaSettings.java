package application.modeles;

import application.Constant;
import application.classes.JSONManager;
import application.classes.Point;
import application.properties.SettingsProperties;

import java.util.Properties;

/**
 * Classe pour la gestion des Eta
 */
public class EtaSettings implements Constant {

    public static void addEta(String nom, String adresse, Point position) {

        Properties prop = SettingsProperties.loadSettingsPropertiesFile();
        if (prop != null) {
            prop.setProperty(PROP_ETA_NAME, nom);
            prop.setProperty(PROP_ETA_ADRESSE, adresse);
            prop.setProperty(PROP_ETA_POSITION, position.toString());
        }

        SettingsProperties.makeSettingsProperties(prop);
    }

    public static boolean checkIfExists(String nom, String adresse) {

        Properties prop = SettingsProperties.loadSettingsPropertiesFile();

        if (prop != null
                && prop.getProperty(PROP_ETA_NAME) != null
                && prop.getProperty(PROP_ETA_ADRESSE) != null
                && prop.getProperty(PROP_ETA_POSITION) != null) {

            String eta_name = prop.getProperty(PROP_ETA_NAME).trim();
            String eta_adresse = prop.getProperty(PROP_ETA_ADRESSE).trim();
            String eta_position = prop.getProperty(PROP_ETA_POSITION).trim();

            if (!eta_name.isEmpty() || !eta_adresse.isEmpty() || !eta_position.isEmpty())
                if (eta_name.equals(nom) && eta_adresse.equals(adresse))
                    return true;
        }

        return false;
    }

    public static boolean isAlreadyETA() {

        Properties prop = SettingsProperties.loadSettingsPropertiesFile();

        return prop != null
                && prop.getProperty(PROP_ETA_NAME) != null
                && prop.getProperty(PROP_ETA_ADRESSE) != null
                && prop.getProperty(PROP_ETA_POSITION) != null;

    }

    public static Eta getInfosEta() {

        Eta eta = null;

        Properties prop = SettingsProperties.loadSettingsPropertiesFile();

        if (prop != null) {
            String eta_name = prop.getProperty(PROP_ETA_NAME).trim();
            String eta_adresse = prop.getProperty(PROP_ETA_ADRESSE).trim();
            String eta_position = prop.getProperty(PROP_ETA_POSITION).trim();

            if (!eta_name.isEmpty() || !eta_adresse.isEmpty() || !eta_position.isEmpty()) {
                Point position_eta = JSONManager.readPoint(eta_position);
                eta = new Eta(1, eta_name, eta_adresse, position_eta);
            }
        }

        return eta;
    }
}