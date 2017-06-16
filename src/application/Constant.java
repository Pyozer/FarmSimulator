package application;

import application.classes.Point;
import javafx.scene.paint.Color;

/**
 * Toutes les constantes de l'application
 */
public interface Constant {

    // TITRE DE L'APPLICATION
    String TITLE_APP = "Farm Simulator";

    // CHEMINS D'ACCES
    String LAYOUT_PATH = "/application/vues/";
    String STYLE_PATH = "/application/styles/";
    String IMAGE_PATH = "/application/images/";

    // TITRE DES VUES
    String HOME_LOGIN_TITLE = "Connexion au compte";
    String PARAMS_ACCOUNT_TITLE = "Configuration du compte";
    String FIRST_PARAMS_HOME_TITLE = "Configuration du logiciel";
    String FIRST_PARAMS_INFOS_TITLE = "Configuration des informations";
    String FIRST_PARAMS_BDD_TITLE = "Configuration de la base de données";
    String PARAMS_APP_TITLE = "Paramètres du logiciel";
    String ACCUEIL_APP_TITLE = "Accueil principale";
    String CLIENT_APP_TITLE = "Gestion des clients";
    String ADD_CLIENT_APP_TITLE = "Ajouter un client";
    String EDIT_CLIENT_APP_TITLE = "Modificer un client";
    String VEHICULE_APP_TITLE = "Gestions des véhicules";
    String ADD_VEHICULE_APP_TITLE = "Ajouter un véhicule";
    String ADD_BOTTELEUSE_APP_TITLE = "Ajouter une botteleuse";
    String ADD_TRACTEUR_APP_TITLE = "Ajouter un tracteur";
    String ADD_MOISSONNEUSE_APP_TITLE = "Ajouter une moissonneuse";
    String EDIT_VEHICULE_APP_TITLE = "Modifier un véhicule";
    String CHAMP_APP_TITLE = "Gestion des champs";
    String ADD_CHAMP_APP_TITLE = "Ajouter un champ";
    String EDIT_CHAMP_APP_TITLE = "Modifier un champ";
    String GLOBAL_APP_TITLE = "Vue globale";
    String COMMANDE_APP_TITLE = "Gestion des commandes";
    String ADD_COMMANDE_APP_TITLE = "Ajouter une commande";
    String EDIT_COMMANDE_APP_TITLE = "Modifier une commande";
    String AFFECTATION_APP_TITLE = "Affectations véhicules de la commande";
    String ADD_AFFECTATION_APP_TITLE = "Ajouter une affectations pour la commande";
    String RAPPORT_MOISSON_APP_TITLE = "Rapport de moisson";

    // DIMENSIONS DE L'APPLICATION
    int MIN_WIDTH = 1300;
    int MIN_HEIGHT = 900;
    int PREF_WIDTH = 1400;
    int PREF_HEIGHT = 1000;

    // VALEUR POUR LocalDate / LocalTime
    Double HEURE_PAR_AN = 8765.82;
    Double HEURE_PAR_MOIS = 730.5;
    Double HEURE_PAR_JOUR = 24.00;
    Double HEURE_PAR_MINUTE = 0.16666; // (1/60)
    Double HEURE_PAR_SECONDE = 0.000278; // (1/3600)

    // Couleurs par defaut
    Color SUCCESS_COLOR = Color.LIMEGREEN;
    Color ERROR_COLOR = Color.rgb(235, 47, 47);

    String TYPE_BOTT_ROND = "Ronde";
    String TYPE_BOTT_CARRE = "Carré";

    String PROP_HOST = "host";
    String PROP_HOST_DEF = "localhost";
    String PROP_PORT = "port";
    String PROP_PORT_DEF = "3306";
    String PROP_DB = "db";
    String PROP_DB_DEF = "pts2";
    String PROP_USER = "user";
    String PROP_USER_DEF = "root";
    String PROP_PASS = "password";
    String PROP_PASS_DEF = "";

    String PROP_ETA_NAME = "name_eta";
    String PROP_ETA_NAME_DEF = "Nom de l'ETA";
    String PROP_ETA_ADRESSE = "adresse_eta";
    String PROP_ETA_ADRESSE_DEF = "Adresse de l'ETA";
    String PROP_ETA_POSITION = "position_eta";
    String PROP_ETA_POSITION_DEF = "[48.086004,-0.759585]";

    String PROP_ALREADY_RUN = "already_run";
    String PROP_ALREADY_RUN_DEF = "false";
}