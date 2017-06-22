package application.modeles;

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
            NamedParameterStatement insertAffectationStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            insertAffectationStatement.setInt("id_com", inputCommande.getId());
            insertAffectationStatement.setInt("id_vehi", inputVehicule.getId());

            // Execute SQL statement
            insertAffectationStatement.executeUpdate();

            insertAffectationStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void deleteAffect(Commande inputCommande, Vehicule inputVehicule) {
        String request = "DELETE FROM Ordre WHERE id_com=:id_com AND id_vehi=:id_vehi";

        try {
            NamedParameterStatement deleteAffectationStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            deleteAffectationStatement.setInt("id_com", inputCommande.getId());
            deleteAffectationStatement.setInt("id_vehi", inputVehicule.getId());

            // Execute SQL statement
            deleteAffectationStatement.executeUpdate();

            deleteAffectationStatement.close();
        } catch (SQLException ex) {
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
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, cap_rem_tract FROM Vehicule " +
                "INNER JOIN Tracteur ON Vehicule.id_vehi=Tracteur.id_vehi " +
                "INNER JOIN Ordre ON Ordre.id_vehi=Vehicule.id_vehi " +
                "WHERE id_com=:id_com";

        try {
            NamedParameterStatement loadTracteurStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            loadTracteurStatement.setInt("id_com", commande.getId());
            // Execute select SQL statement
            ResultSet rs = loadTracteurStatement.executeQuery();

            while (rs.next()) {

                Point position = VehiculeSQL.getActualPositionVehicule(rs.getInt("id_vehi"));
                String etat = VehiculeSQL.getActualEtatVehicule(rs.getInt("id_vehi"));

                vehiculeList.add(new Tracteur(
                        rs.getInt("id_vehi"),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        etat,
                        position,
                        rs.getInt("cap_rem_tract")
                ));
            }
            rs.close();
            loadTracteurStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadBotteleuse(Commande commande) {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, type_bott FROM Vehicule " +
                "INNER JOIN Botteleuse ON Vehicule.id_vehi=Botteleuse.id_vehi " +
                "INNER JOIN Ordre ON Ordre.id_vehi=Vehicule.id_vehi " +
                "WHERE id_com=:id_com";

        try {
            NamedParameterStatement loadBotteleuseStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            loadBotteleuseStatement.setInt("id_com", commande.getId());
            // Execute select SQL statement
            ResultSet rs = loadBotteleuseStatement.executeQuery();

            while (rs.next()) {
                Point position = VehiculeSQL.getActualPositionVehicule(rs.getInt("id_vehi"));
                String etat = VehiculeSQL.getActualEtatVehicule(rs.getInt("id_vehi"));

                vehiculeList.add(new Botteleuse(
                        rs.getInt("id_vehi"),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        etat,
                        position,
                        rs.getBoolean("type_bott")
                ));
            }
            rs.close();
            loadBotteleuseStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadMoissonneuse(Commande commande) {
        String request = "SELECT * FROM Vehicule " +
                "INNER JOIN Moissonneuse ON Vehicule.id_vehi=Moissonneuse.id_vehi " +
                "INNER JOIN Ordre ON Ordre.id_vehi=Vehicule.id_vehi " +
                "WHERE id_com=:id_com";

        try {
            NamedParameterStatement loadMoissonneuseStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            loadMoissonneuseStatement.setInt("id_com", commande.getId());
            // Execute select SQL statement
            ResultSet rs = loadMoissonneuseStatement.executeQuery();

            while (rs.next()) {
                Point position = VehiculeSQL.getActualPositionVehicule(rs.getInt("id_vehi"));
                String etat = VehiculeSQL.getActualEtatVehicule(rs.getInt("id_vehi"));

                vehiculeList.add(new Moissonneuse(
                        rs.getInt("id_vehi"),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        etat,
                        position,
                        rs.getInt("taille_tremis_moi"),
                        rs.getInt("taille_reserve_moi"),
                        rs.getInt("largeur_route_moi"),
                        rs.getInt("hauteur_moi"),
                        rs.getInt("largeur_coupe_moi"),
                        rs.getInt("conso_fonct_moi"),
                        rs.getInt("conso_route_moi"),
                        rs.getInt("poids_moi")
                ));
            }
            rs.close();
            loadMoissonneuseStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}