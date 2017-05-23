package application.modeles;

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
        String request = "SELECT V.id_vehi, marque_vehi, modele_vehi, etat_vehi, cap_rem_tract FROM Vehicule AS V INNER JOIN Tracteur AS T ON V.id_vehi=T.id_vehi";

        try {
            PreparedStatement stmt = dbCon.prepareStatement(request);
            // Execute select SQL statement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehiculeList.add(new Tracteur(
                        Integer.parseInt(rs.getString("id_vehi")),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
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
        String request = "SELECT V.id_vehi, marque_vehi, modele_vehi, etat_vehi, type_bott FROM Vehicule AS V INNER JOIN Botteleuse AS B ON V.id_vehi=B.id_vehi";

        try {
            PreparedStatement stmt = dbCon.prepareStatement(request);

            // execute select SQL stetement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehiculeList.add(new Botteleuse(
                        Integer.parseInt(rs.getString("id_vehi")),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
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
        String request = "SELECT * FROM Vehicule AS V INNER JOIN Moissonneuse AS M ON V.id_vehi=M.id_vehi";

        try {
            PreparedStatement stmt = dbCon.prepareStatement(request);

            // execute select SQL stetement
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                vehiculeList.add(new Moissonneuse(
                        Integer.parseInt(rs.getString("id_vehi")),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
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
