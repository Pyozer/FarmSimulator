package application.classes;

import java.util.prefs.Preferences;

/**
 * Classe pour gérer les paramètres de l'application
 */
public class Settings {

    private Preferences prefs;

    public Settings() {
        prefs = Preferences.userNodeForPackage(Settings.class);
    }

    /**
     * Permet de récupérer un paramètre
     * @param key
     * @return
     */
    public String getParams(String key) {
        String content = prefs.get(key, null);
        if (content != null) {
            return content;
        } else {
            return null;
        }
    }

    /**
     * Permet d'enregistrer un paramètres
     * @param key
     * @param value
     */
    public void setParams(String key, String value) {
        if (value != null) {
            prefs.put(key, value);
        } else {
            prefs.remove(key);
        }
    }
}
