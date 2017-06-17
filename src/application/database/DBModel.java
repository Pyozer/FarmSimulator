package application.database;

import application.properties.DBProperties;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Classe pour la gérer la création de la base de données
 * Création de la BDD et des tables
 */
public class DBModel {

    private String db;
    private PreparedStatement pst;

    public DBModel() {
        Properties properties = new DBProperties().loadPropertiesFile();
        db = properties.getProperty("db");
    }

    /**
     * Création de la base de donnée, et des tables
     */
    public void createDataBase() {
        try {
            pst = DBConnection.makeDataBase().prepareStatement("CREATE DATABASE IF NOT EXISTS " + db + " DEFAULT CHARACTER SET utf8 \n"
                    + " DEFAULT COLLATE utf8_general_ci");
            pst.execute();

            // Création des tables
            /*pst = con.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `Eta` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nom` VARCHAR(20) NOT NULL,\n"
                    + "  `adresse` VARCHAR(50) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE INDEX `id` (`id` ASC));");
            pst.execute();

            pst = con.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `user` (\n"
                    + "  `id` int(11) NOT NULL AUTO_INCREMENT,\n"
                    + "  `nom` VARCHAR(20) NOT NULL,\n"
                    + "  `prenom` VARCHAR(50) NOT NULL,\n"
                    + "  `password` VARCHAR(50) NOT NULL,\n"
                    + "  `email` VARCHAR(50) NOT NULL,\n"
                    + "  PRIMARY KEY (`id`),\n"
                    + "  UNIQUE INDEX `id` (`id` ASC));");
            pst.execute();*/

            pst.close();

            System.out.println("Base de donnée crée avec succès !");

        } catch (SQLException ex) {
            System.err.println(ex);
        }
    }
}
