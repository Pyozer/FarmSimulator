package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pyozer on 23/05/2017.
 *
 */
public class VehiculeSQL {

    private static ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    private final static String UPDATE_VEHICULE = "UPDATE VEHICULE SET marque_vehi:marque_vehi, modele_vehi=:modele_vehi, etat_vehi=:etat_vehi, position_vehi=:position_vehi WHERE id_vehi:id_vehi";
    private final static String INSERT_VEHICULE = "INSERT INTO Vehicule(marque_vehi, modele_vehi, etat_vehi, position_vehi) VALUES (:marque, :modele, :etat, :position);";
    private final static String GET_ID_VEHI = "SELECT LAST_INSERT_ID() as lastID";

    public static ObservableList<Vehicule> getVehiculeList() {
        vehiculeList.clear();

        loadBotteleuse();
        loadMoissonneuse();
        loadTracteur();

        return vehiculeList;
    }

    private static int addVehicule(String marque, String modele, String etat, String position) throws SQLException{
        // On insert le vehicule
        NamedParameterStatement addVehiculeStatement = new NamedParameterStatement(DBConnection.getConnection(), INSERT_VEHICULE);
        addVehiculeStatement.setString("marque", marque);
        addVehiculeStatement.setString("modele", modele);
        addVehiculeStatement.setString("etat", etat);
        addVehiculeStatement.setString("position", position);

        addVehiculeStatement.executeUpdate();
        addVehiculeStatement.close();

        // On récupère l'ID du vehicule ajouté
        PreparedStatement getLastIdStmt = DBConnection.getConnection().prepareStatement(GET_ID_VEHI);
        // Execute select SQL statement
        ResultSet result = getLastIdStmt.executeQuery();
        result.next();

        int idVehi = result.getInt("lastID");

        result.close();

        return idVehi;
    }

    private static void editVehicule(int id, String marque, String modele, String etat, String position) throws SQLException{
        // On update le vehicule
        NamedParameterStatement editVehiculeStatement = new NamedParameterStatement(DBConnection.getConnection(), UPDATE_VEHICULE);

        editVehiculeStatement.setString("marque_vehi", marque);
        editVehiculeStatement.setString("modele_vehi", modele);
        editVehiculeStatement.setString("etat_vehi", etat);
        editVehiculeStatement.setString("position_vehi", position);
        editVehiculeStatement.setInt("id_vehi", id);
        // Execute SQL statement
        editVehiculeStatement.executeUpdate();

        editVehiculeStatement.close();
    }

    private static void loadTracteur() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, cap_rem_tract FROM Vehicule " +
                "INNER JOIN Tracteur ON Vehicule.id_vehi=Tracteur.id_vehi";

        try {
            PreparedStatement loadTrateurStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute select SQL statement
            ResultSet rs = loadTrateurStatement.executeQuery();

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
            loadTrateurStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadBotteleuse() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, position_vehi, type_bott FROM Vehicule " +
                "INNER JOIN Botteleuse ON Vehicule.id_vehi=Botteleuse.id_vehi";

        try {
            PreparedStatement loadBotteleuseStatement = DBConnection.getConnection().prepareStatement(request);

            // execute select SQL stetement
            ResultSet rs = loadBotteleuseStatement.executeQuery();

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
            loadBotteleuseStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadMoissonneuse() {
        String request = "SELECT * FROM Vehicule INNER JOIN Moissonneuse ON Vehicule.id_vehi=Moissonneuse.id_vehi";

        try {
            PreparedStatement loadMoissonneuseStatement = DBConnection.getConnection().prepareStatement(request);

            // Execute select SQL stetement
            ResultSet rs = loadMoissonneuseStatement.executeQuery();

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
            loadMoissonneuseStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void deleteVehicule(Vehicule vehicule) {
        String request = "DELETE FROM vehicule WHERE id_vehi=:id";

        try {
            NamedParameterStatement deleteVehiculeStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            deleteVehiculeStatement.setInt("id", vehicule.getId());

            // Execute select SQL statement
            deleteVehiculeStatement.executeUpdate();
            deleteVehiculeStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void addBotteleuse(String modele, String marque, String type, String etat) {

        String insertBott = "INSERT INTO Botteleuse(id_vehi, type_bott) VALUES (:id_vehi, :type_bott)";

        try {
            // On insert le vehicule
            int idVehi = addVehicule(marque, modele, etat, "[47.970575,-1.448591]");

            // On insert la botteleuse
            NamedParameterStatement addBotteleuseStatement = new NamedParameterStatement(DBConnection.getConnection(), insertBott);
            addBotteleuseStatement.setInt("id_vehi", idVehi);
            addBotteleuseStatement.setString("type_bott", type);

            addBotteleuseStatement.executeUpdate();
            addBotteleuseStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editBotteleuse(Botteleuse bott) {
        String request = "UPDATE Botteleuse SET type_bott=:type_bott " +
                "WHERE id_vehi=:id_vehi";

        try {
            // On update le véhicule
            editVehicule(bott.getId(), bott.getMarque(), bott.getModele(), bott.getEtat(), bott.getPosition().toString());

            NamedParameterStatement editBotteleuseStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editBotteleuseStatement.setString("type_bott", bott.getType());
            editBotteleuseStatement.setInt("id_vehi", bott.getId());

            // Execute SQL statement
            editBotteleuseStatement.executeUpdate();

            editBotteleuseStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void addMoissonneuse(String modele, String marque, String etat, int consoFonctionnement, int consoRoute, float hauteur, float largeurCoupe, float largeurRoute, float poids, int tailleReservoir, int tailleTremis) {
        String insertMois = "INSERT INTO Moissonneuse (`id_vehi`, `taille_tremis_moi`, `taille_reserve_moi`, `largeur_route_moi`, `hauteur_moi`, `largeur_coupe_moi`, `conso_fonct_moi`, `conso_route_moi`, `poids_moi`) VALUES " +
                "(:id_vehi, :taille_tremis_moi, :taille_reserve_moi, :largeur_route_moi, :hauteur_moi, :largeur_coupe_moi, :conso_fonct_moi, :conso_route_moi, :poids_moi)";

        try {
            // On insert le vehicule
            int idVehi = addVehicule(marque, modele, etat, "[47.970575,-1.448591]");

            // On insert la moissonneuse
            NamedParameterStatement addMoissonneuseStatement = new NamedParameterStatement(DBConnection.getConnection(), insertMois);
            addMoissonneuseStatement.setInt("id_vehi", idVehi);
            addMoissonneuseStatement.setInt("conso_route_moi", consoRoute );
            addMoissonneuseStatement.setFloat("conso_fonct_moi", consoFonctionnement );
            addMoissonneuseStatement.setFloat("poids_moi", poids );
            addMoissonneuseStatement.setFloat("hauteur_moi", hauteur );
            addMoissonneuseStatement.setFloat("largeur_coupe_moi", largeurCoupe );
            addMoissonneuseStatement.setFloat("largeur_route_moi", largeurRoute );
            addMoissonneuseStatement.setInt("taille_tremis_moi", tailleTremis );
            addMoissonneuseStatement.setInt("taille_reserve_moi", tailleReservoir );

            addMoissonneuseStatement.executeUpdate();
            addMoissonneuseStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void editMoissonneuse(Moissonneuse moi) {
        String request = "UPDATE Moissonneuse SET taille_tremis_moi=:taille_tremis_moi, taille_reservoir_moi=:taille_reservoir_moi, " +
                "largeur_route_moi=:largeur_route_moi, hauteur_moi=:hauteur_moi, largeur_coupe_moi=:largeur_coupe_moi, conso_fonct_moi=:conso_fonct_moi, " +
                "conso_route_moi=:conso_route_moi, poids_moi=:poids_moi" +
                "WHERE id_vehi=:id_vehi";

        try {
            // On update le véhicule
            editVehicule(moi.getId(), moi.getMarque(), moi.getModele(), moi.getEtat(), moi.getPosition().toString());

            NamedParameterStatement editMoissonneuseStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editMoissonneuseStatement.setInt("taille_reservoir_moi", moi.getCapacite_reservoir());
            editMoissonneuseStatement.setInt("taille_tremis_moi", moi.getCapacite_tremis());
            editMoissonneuseStatement.setFloat("largeur_route_moi", moi.getLargeur());
            editMoissonneuseStatement.setFloat("largeur_coupe_moi", moi.getTaille_coupe());
            editMoissonneuseStatement.setFloat("hauteur_moi", moi.getHauteur());
            editMoissonneuseStatement.setInt("conso_fonct_moi", moi.getConso_fonctionnement());
            editMoissonneuseStatement.setInt("conso_route_moi", moi.getConso_route());
            editMoissonneuseStatement.setFloat("poids_moi", moi.getPoids());
            editMoissonneuseStatement.setInt("id_vehi", moi.getId());

            // Execute SQL statement
            editMoissonneuseStatement.executeUpdate();

            editMoissonneuseStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void addTracteur(String modele, String marque, int cap_rem, String etat) {
        String insertTract = "INSERT INTO Tracteur(id_vehi, cap_rem_tract) VALUES (:id_vehi, :cap_rem_tract)";

        try {
            // On insert le vehicule
            int idVehi = addVehicule(marque, modele, etat, "[47.970575,-1.448591]");

            // On insert la botteleuse
            NamedParameterStatement addTracteurStatement = new NamedParameterStatement(DBConnection.getConnection(), insertTract);
            addTracteurStatement.setInt("id_vehi", idVehi);
            addTracteurStatement.setInt("cap_rem_tract", cap_rem);

            addTracteurStatement.executeUpdate();
            addTracteurStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editTracteur(Tracteur tracteur) {

        String requestTracteur = "UPDATE Tracteur SET cap_rem_tract=:cap_rem_tract " +
                "WHERE id_vehi=:id_vehi";

        try {
            // On update le véhicule
            editVehicule(tracteur.getId(), tracteur.getMarque(), tracteur.getModele(), tracteur.getEtat(), tracteur.getPosition().toString());

            NamedParameterStatement editTracteurStatement = new NamedParameterStatement(DBConnection.getConnection(), requestTracteur);

            editTracteurStatement.setInt("cap_rem_tract", tracteur.getCapacite_remorque());
            editTracteurStatement.setInt("id_vehi", tracteur.getId());
            // Execute SQL statement
            editTracteurStatement.executeUpdate();

            editTracteurStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}