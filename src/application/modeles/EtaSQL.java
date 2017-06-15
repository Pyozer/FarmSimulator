package application.modeles;

import application.Constant;
import application.classes.Point;
import application.database.DBConnection;
import application.database.NamedParameterStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour la gestion des ETA
 */
public class EtaSQL implements Constant {

    public static void addEta(String nom, String adresse, Point position) {
        String request = "INSERT INTO Eta(nom_eta, adresse_eta, position_eta) VALUES(:nom, :adresse, :pos)";
        try {
            NamedParameterStatement addEta = new NamedParameterStatement(DBConnection.getConnection(), request);

            addEta.setString("nom", nom);
            addEta.setString("adresse", adresse);
            addEta.setString("pos", position.toString());

            // Execute SQL statement
            addEta.executeUpdate();

            addEta.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static boolean checkIfExists(String nom, String adresse) {
        int count = 1;

        String request = "SELECT COUNT(id_eta) as rowCount FROM Eta WHERE nom_eta=:nom OR adresse_eta=:adresse";

        try {
            NamedParameterStatement checkExists = new NamedParameterStatement(DBConnection.getConnection(), request);
            checkExists.setString("nom", nom);
            checkExists.setString("adresse", adresse);
            ResultSet res = checkExists.executeQuery();

            res.next();
            count = res.getInt("rowCount");

            res.close();
            checkExists.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count >= 1;
    }

}