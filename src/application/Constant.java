package application;

import javafx.scene.control.TableColumn;

import java.util.ArrayList;
import java.util.List;

/**
 * Toutes les constantes de l'application
 */
public final class Constant {

    // TITRE DE L'APPLICATION
    public final static String TITLE_APP = "Farm Simulator";

    // CHEMINS D'ACCES
    public final static String LAYOUT_PATH = "/application/vues/";
    public final static String STYLE_PATH = "/application/styles/";
    public final static String IMAGE_PATH = "/application/images/";

    // TITRE DES VUES
    public final static String HOME_LOGIN_TITLE = "Connexion au compte";
    public final static String PARAMS_ACCOUNT_TITLE = "Configuration du compte";
    public final static String FIRST_PARAMS_HOME_TITLE = "Configuration du logiciel";
    public final static String FIRST_PARAMS_INFOS_TITLE = "Configuration des informations";
    public final static String FIRST_PARAMS_BDD_TITLE = "Configuration de la base de données";
    public final static String PARAMS_APP_TITLE = "Paramètres du logiciel";
    public final static String ACCUEIL_APP_TITLE = "Accueil principale";
    public final static String CLIENT_APP_TITLE = "Gestion des clients";
    public final static String ADD_CLIENT_APP_TITLE = "Ajouter un client";
    public final static String EDIT_CLIENT_APP_TITLE = "Modificer un client";
    public final static String VEHICULE_APP_TITLE = "Gestions des véhicules";
    public final static String ADD_VEHICULE_APP_TITLE = "Ajouter un véhicule";
    public final static String ADD_BOTTELEUSE_APP_TITLE = "Ajouter une botteleuse";
    public final static String ADD_TRACTEUR_APP_TITLE = "Ajouter un tracteur";
    public final static String ADD_MOISSONNEUSE_APP_TITLE = "Ajouter une moissonneuse";
    public final static String EDIT_BOTTELEUSE_APP_TITLE = "Modifier une botteleuse";
    public final static String EDIT_TRACTEUR_APP_TITLE = "Modifier un tracteur";
    public final static String EDIT_MOISSONNEUSE_APP_TITLE = "Modifier une moissonneuse";
    public final static String CHAMP_APP_TITLE = "Gestion des champs";
    public final static String ADD_CHAMP_APP_TITLE = "Ajouter un champ";
    public final static String EDIT_CHAMP_APP_TITLE = "Modifier un champ";
    public final static String GLOBAL_APP_TITLE = "Vue globale";
    public final static String COMMANDE_APP_TITLE = "Gestion des commandes";
    public final static String ADD_COMMANDE_APP_TITLE = "Ajouter une commande";
    public final static String EDIT_COMMANDE_APP_TITLE = "Modifier une commande";
    public final static String AFFECTATION_APP_TITLE = "Affectations véhicules de la commande";
    public final static String ADD_AFFECTATION_APP_TITLE = "Affectation(s) commande";
    public final static String RAPPORT_MOISSON_APP_TITLE = "Rapport de moisson";

    // DIMENSIONS DE L'APPLICATION
    public final static int MIN_WIDTH = 1000;
    public final static int MIN_HEIGHT = 900;
    public final static int PREF_WIDTH = 1400;
    public final static int PREF_HEIGHT = 1000;

    public final static double DECALAGE_LAT = 0.001;
    public final static double DECALAGE_LONG = 0.0002;

    // VALEUR POUR LocalDate / LocalTime
    public final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public final static String TYPE_BOTT_ROND = "Ronde";
    public final static String TYPE_BOTT_CARRE = "Carré";

    public final static String PROP_HOST = "host";
    public final static String PROP_HOST_DEF = "localhost";
    public final static String PROP_PORT = "port";
    public final static String PROP_PORT_DEF = "3306";
    public final static String PROP_DB = "db";
    public final static String PROP_DB_DEF = "pts2";
    public final static String PROP_USER = "user";
    public final static String PROP_USER_DEF = "root";
    public final static String PROP_PASS = "password";
    public final static String PROP_PASS_DEF = "";

    public final static String PROP_ETA_NAME = "name_eta";
    public final static String PROP_ETA_ADRESSE = "adresse_eta";
    public final static String PROP_ETA_POSITION = "position_eta";

    public final static String PROP_ALREADY_RUN = "already_run";
    public final static String PROP_ALREADY_RUN_DEF = "false";

    public final static String PROP_FIGHT_MODE_STATE = "flightModeState";
    public final static String PROP_FIGHT_MODE_STATE_DEF = "true";

    public static void setWidthColumn(TableColumn column, int percent) {
        column.setMaxWidth( 1f * Integer.MAX_VALUE * percent );
    }

    public static <T> List<T> rechercherValeurListe(List<T> data, String search) {
        List<T> listObject = new ArrayList<>();
        listObject.addAll(data);
        for(T object : data) {
            String value = search.trim().toLowerCase();
            String lineValue = object.toString().toLowerCase();
            if(!lineValue.matches(".*" + value + ".*"))
                listObject.remove(object);
        }
        return listObject;
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}