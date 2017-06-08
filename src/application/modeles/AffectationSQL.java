package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour la gestion des affectations d'un véhicule à une commande.
 */
public class AffectationSQL {

    private static ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    public static void addAffect(Commande inputCommande, Vehicule inputVehicule) {
        String request = "INSERT INTO Ordre(id_com, id_vehi) VALUES(:id_com, :id_vehi)";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setInt("id_com", inputCommande.getId());
            preparedStatement.setInt("id_vehi", inputVehicule.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void deleteAffect(Commande inputCommande, Vehicule inputVehicule) {
        String request = "DELETE FROM Ordre WHERE id_com=:id_com AND id_vehi=:id_vehi";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setInt("id_com", inputCommande.getId());
            preparedStatement.setInt("id_vehi", inputVehicule.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static ObservableList<Vehicule> getVehiculeAffect(Commande commande) {
        vehiculeList.clear();

        loadBotteleuse(commande);
        loadMoissonneuse(commande);
        loadTracteur(commande);

        return vehiculeList;
    }

    private static void loadTracteur(Commande commande) {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, cap_rem_tract FROM Vehicule" +
                "INNER JOIN Tracteur ON Vehicule.id_vehi=Tracteur.id_vehi" +
                "INNER JOIN Ordre ON Ordre.id_vehi=Vehicule.id_vehi" +
                "WHERE id_com=:id_com";

        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setInt("id_com", commande.getId());
            // Execute select SQL statement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Point position = JSONManager.readPoint(rs.getString("position_vehi"));

                vehiculeList.add(new Tracteur(
                        Integer.parseInt(rs.getString("id_vehi")),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
                        position,
                        Integer.parseInt(rs.getString("cap_rem_tract"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadBotteleuse(Commande commande) {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, type_bott FROM Vehicule" +
                "INNER JOIN Botteleuse ON Vehicule.id_vehi=Botteleuse.id_vehi" +
                "INNER JOIN Ordre ON Ordre.id_vehi=Vehicule.id_vehi" +
                "WHERE id_com=:id_com";

        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setInt("id_com", commande.getId());
            // Execute select SQL statement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Point position = JSONManager.readPoint(rs.getString("position_vehi"));

                vehiculeList.add(new Botteleuse(
                        Integer.parseInt(rs.getString("id_vehi")),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
                        position,
                        Boolean.parseBoolean(rs.getString("type_bott"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadMoissonneuse(Commande commande) {
        String request = "SELECT * FROM Vehicule" +
                "INNER JOIN Moissonneuse ON Vehicule.id_vehi=Moissonneuse.id_vehi" +
                "INNER JOIN Ordre ON Ordre.id_vehi=Vehicule.id_vehi" +
                "WHERE id_com=:id_com";

        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setInt("id_com", commande.getId());
            // Execute select SQL statement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Point position = JSONManager.readPoint(rs.getString("position_vehi"));

                vehiculeList.add(new Moissonneuse(
                        Integer.parseInt(rs.getString("id_vehi")),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
                        position,
                        Integer.parseInt(rs.getString("taille_tremis_moi")),
                        Integer.parseInt(rs.getString("taille_reserve_moi")),
                        Integer.parseInt(rs.getString("largeur_route_moi")),
                        Integer.parseInt(rs.getString("hauteur_moi")),
                        Integer.parseInt(rs.getString("largeur_coupe_moi")),
                        Integer.parseInt(rs.getString("conso_fonct_moi")),
                        Integer.parseInt(rs.getString("conso_route_moi")),
                        Integer.parseInt(rs.getString("poids_moi"))
                ));
            }
            rs.close();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
