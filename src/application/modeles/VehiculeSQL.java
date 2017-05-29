package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pyozer on 23/05/2017.
 *
 */
public class VehiculeSQL {

    private ObservableList<Vehicule> vehiculeList;
    private Connection dbCon;

    public VehiculeSQL() {
        vehiculeList = FXCollections.observableArrayList();
        dbCon = new DBConnection().getConnection();
    }

    public ObservableList<Vehicule> getVehiculeList() {
        loadBotteleuse();
        loadMoissonneuse();
        loadTracteur();
        try {
            dbCon.close();
        } catch (SQLException e) { e.printStackTrace(); }

        return vehiculeList;
    }

    private void loadTracteur() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, cap_rem_tract FROM Vehicule INNER JOIN Tracteur ON Vehicule.id_vehi=Tracteur.id_vehi";

        vehiculeList.clear();
        try {
            PreparedStatement stmt = dbCon.prepareStatement(request);
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

    private void loadBotteleuse() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, type_bott FROM Vehicule INNER JOIN Botteleuse ON Vehicule.id_vehi=Botteleuse.id_vehi";

        try {
            PreparedStatement stmt = dbCon.prepareStatement(request);

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

    private void loadMoissonneuse() {
        String request = "SELECT * FROM Vehicule INNER JOIN Moissonneuse ON Vehicule.id_vehi=Moissonneuse.id_vehi";

        try {
            PreparedStatement stmt = dbCon.prepareStatement(request);

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
}