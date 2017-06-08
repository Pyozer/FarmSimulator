package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pyozer on 23/05/2017.
 *
 */
public class VehiculeSQL {

    private static ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    public static ObservableList<Vehicule> getVehiculeList() {
        vehiculeList.clear();

        loadBotteleuse();
        loadMoissonneuse();
        loadTracteur();

        return vehiculeList;
    }

    private static void loadTracteur() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, cap_rem_tract FROM Vehicule" +
                "INNER JOIN Tracteur ON Vehicule.id_vehi=Tracteur.id_vehi";

        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
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

    private static void loadBotteleuse() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, type_bott FROM Vehicule" +
                "INNER JOIN Botteleuse ON Vehicule.id_vehi=Botteleuse.id_vehi";

        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);

            // execute select SQL stetement
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

    private static void loadMoissonneuse() {
        String request = "SELECT * FROM Vehicule" +
                "INNER JOIN Moissonneuse ON Vehicule.id_vehi=Moissonneuse.id_vehi";

        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);

            // Execute select SQL stetement
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

    public static void deleteVehicule(Vehicule vehicule) {
        String request = "DELETE FROM vehicule WHERE id_vehi=:id";

        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setInt("id", vehicule.getId());

            // Execute select SQL statement
            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void addBotteleuse(String modele, String marque, String type, String etat) {
        String insertVehi = "INSERT INTO Vehicule(marque_vehi, modele_vehi, etat_vehi, position_vehi) VALUES (:marque, :modele, :etat, :position);";
        String getIdVehi = "SELECT LAST_INSERT_ID()";
        String insertBott = "INSERT INTO Botteleuse(id_vehi, type_bott) VALUES (:id_vehi, :type_bott)";

        try {
            // On insert le vehicule
            NamedParameterStatement insertVehiStmt = new NamedParameterStatement(DBConnection.getConnection(), insertVehi);
            insertVehiStmt.setString("marque", marque);
            insertVehiStmt.setString("modele", modele);
            insertVehiStmt.setString("etat", etat);
            insertVehiStmt.setString("position", "[47.970575,-1.448591]");

            insertVehiStmt.executeUpdate();
            insertVehiStmt.close();

            // On récupère l'ID du vehicule ajouté
            PreparedStatement getLastIdStmt = DBConnection.getConnection().prepareStatement(getIdVehi);
            // Execute select SQL statement
            ResultSet result = getLastIdStmt.executeQuery();
            result.next();
            int idVehi = result.getInt(0);

            result.close();

            // On insert la botteleuse
            NamedParameterStatement insertBottStmt = new NamedParameterStatement(DBConnection.getConnection(), insertVehi);
            insertBottStmt.setInt("id_vehi", idVehi);
            insertBottStmt.setString("type_bott", type);

            insertBottStmt.executeUpdate();
            insertBottStmt.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}