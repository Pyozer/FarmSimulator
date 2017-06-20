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
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class VehiculeSQL {

    private static ObservableList<Vehicule> vehiculeList = FXCollections.observableArrayList();

    private final static String UPDATE_VEHICULE = "UPDATE VEHICULE SET marque_vehi:marque_vehi, modele_vehi=:modele_vehi, etat_vehi=:etat_vehi WHERE id_vehi:id_vehi";
    private final static String INSERT_VEHICULE = "INSERT INTO Vehicule(marque_vehi, modele_vehi, etat_vehi) VALUES (:marque, :modele, :etat);";

    public static ObservableList<Vehicule> getVehiculeList() {
        vehiculeList.clear();

        loadBotteleuse();
        loadMoissonneuse();
        loadTracteur();

        return vehiculeList;
    }

    private static int addVehicule(String marque, String modele, String etat) throws SQLException {
        int last_inserted_id = 0;

        // On insert le vehicule
        NamedParameterStatement addVehiculeStatement = new NamedParameterStatement(DBConnection.getConnection(), INSERT_VEHICULE, Statement.RETURN_GENERATED_KEYS);
        addVehiculeStatement.setString("marque", marque);
        addVehiculeStatement.setString("modele", modele);
        addVehiculeStatement.setString("etat", etat);

        addVehiculeStatement.executeUpdate();

        ResultSet rs = addVehiculeStatement.getStatement().getGeneratedKeys();

        if (rs.next())
            last_inserted_id = rs.getInt(1);

        addVehiculeStatement.close();

        return last_inserted_id;
    }

    private static void editVehicule(int id, String marque, String modele, String etat) throws SQLException {
        // On update le vehicule
        NamedParameterStatement editVehiculeStatement = new NamedParameterStatement(DBConnection.getConnection(), UPDATE_VEHICULE);

        editVehiculeStatement.setString("marque_vehi", marque);
        editVehiculeStatement.setString("modele_vehi", modele);
        editVehiculeStatement.setString("etat_vehi", etat);
        editVehiculeStatement.setInt("id_vehi", id);
        // Execute SQL statement
        editVehiculeStatement.executeUpdate();

        editVehiculeStatement.close();
    }

    public static Point getActualPositionVehicule(int id_vehi) {

        Point position = EtaSettings.getInfosEta().getPositionVehi();
        try {
            String requestPosition = "SELECT coord_centre_champ FROM Champ " +
                    "INNER JOIN Commande ON Commande.id_champ=Champ.id_champ " +
                    "INNER JOIN Ordre ON Ordre.id_com=Commande.id_com " +
                    "INNER JOIN Vehicule ON Vehicule.id_vehi=Ordre.id_vehi " +
                    "WHERE date_com=:date AND Vehicule.id_vehi=:idVehi";

            NamedParameterStatement getPositionVehi = new NamedParameterStatement(DBConnection.getConnection(), requestPosition);
            System.out.println(LocalDate.now().toString());
            getPositionVehi.setString("date", LocalDate.now().toString());
            getPositionVehi.setInt("idVehi", id_vehi);
            // Execute select SQL statement
            ResultSet rs_position = getPositionVehi.executeQuery();

            if (rs_position.next()) {
                String position_vehi = rs_position.getString("coord_centre_champ");

                if (!position_vehi.isEmpty()) {
                    position = JSONManager.readPoint(position_vehi);
                    System.out.println(position);
                }
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return position;
    }

    private static void loadTracteur() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, cap_rem_tract FROM Vehicule " +
                "INNER JOIN Tracteur ON Vehicule.id_vehi=Tracteur.id_vehi";

        try {
            PreparedStatement loadTrateurStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute select SQL statement
            ResultSet rs = loadTrateurStatement.executeQuery();

            while (rs.next()) {

                vehiculeList.add(new Tracteur(
                        rs.getInt("id_vehi"),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
                        getActualPositionVehicule(rs.getInt("id_vehi")),
                        rs.getInt("cap_rem_tract")
                ));
            }
            rs.close();
            loadTrateurStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private static void loadBotteleuse() {
        String request = "SELECT Vehicule.id_vehi, marque_vehi, modele_vehi, etat_vehi, type_bott FROM Vehicule " +
                "INNER JOIN Botteleuse ON Vehicule.id_vehi=Botteleuse.id_vehi";

        try {
            PreparedStatement loadBotteleuseStatement = DBConnection.getConnection().prepareStatement(request);

            // execute select SQL stetement
            ResultSet rs = loadBotteleuseStatement.executeQuery();

            while (rs.next()) {

                vehiculeList.add(new Botteleuse(
                        rs.getInt("id_vehi"),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
                        getActualPositionVehicule(rs.getInt("id_vehi")),
                        rs.getBoolean("type_bott")
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

                vehiculeList.add(new Moissonneuse(
                        rs.getInt("id_vehi"),
                        rs.getString("marque_vehi"),
                        rs.getString("modele_vehi"),
                        rs.getString("etat_vehi"),
                        getActualPositionVehicule(rs.getInt("id_vehi")),
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
            int idVehi = addVehicule(marque, modele, etat);

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
            editVehicule(bott.getId(), bott.getMarque(), bott.getModele(), bott.getEtat());

            NamedParameterStatement editBotteleuseStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editBotteleuseStatement.setString("type_bott", bott.getType());
            editBotteleuseStatement.setInt("id_vehi", bott.getId());

            // Execute SQL statement
            editBotteleuseStatement.executeUpdate();

            editBotteleuseStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void addMoissonneuse(String modele, String marque, String etat, int consoFonctionnement, int consoRoute, float hauteur, float largeurCoupe, float largeurRoute, float poids, int tailleReservoir, int tailleTremis) {
        String insertMois = "INSERT INTO Moissonneuse (`id_vehi`, `taille_tremis_moi`, `taille_reserve_moi`, `largeur_route_moi`, `hauteur_moi`, `largeur_coupe_moi`, `conso_fonct_moi`, `conso_route_moi`, `poids_moi`) VALUES " +
                "(:id_vehi, :taille_tremis_moi, :taille_reserve_moi, :largeur_route_moi, :hauteur_moi, :largeur_coupe_moi, :conso_fonct_moi, :conso_route_moi, :poids_moi)";

        try {
            // On insert le vehicule
            int idVehi = addVehicule(marque, modele, etat);

            // On insert la moissonneuse
            NamedParameterStatement addMoissonneuseStatement = new NamedParameterStatement(DBConnection.getConnection(), insertMois);
            addMoissonneuseStatement.setInt("id_vehi", idVehi);
            addMoissonneuseStatement.setInt("conso_route_moi", consoRoute);
            addMoissonneuseStatement.setFloat("conso_fonct_moi", consoFonctionnement);
            addMoissonneuseStatement.setFloat("poids_moi", poids);
            addMoissonneuseStatement.setFloat("hauteur_moi", hauteur);
            addMoissonneuseStatement.setFloat("largeur_coupe_moi", largeurCoupe);
            addMoissonneuseStatement.setFloat("largeur_route_moi", largeurRoute);
            addMoissonneuseStatement.setInt("taille_tremis_moi", tailleTremis);
            addMoissonneuseStatement.setInt("taille_reserve_moi", tailleReservoir);

            addMoissonneuseStatement.executeUpdate();
            addMoissonneuseStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void editMoissonneuse(Moissonneuse moi) {
        String request = "UPDATE Moissonneuse SET taille_tremis_moi=:taille_tremis_moi, taille_reservoir_moi=:taille_reservoir_moi, " +
                "largeur_route_moi=:largeur_route_moi, hauteur_moi=:hauteur_moi, largeur_coupe_moi=:largeur_coupe_moi, conso_fonct_moi=:conso_fonct_moi, " +
                "conso_route_moi=:conso_route_moi, poids_moi=:poids_moi WHERE id_vehi=:id_vehi";

        try {
            // On update le véhicule
            editVehicule(moi.getId(), moi.getMarque(), moi.getModele(), moi.getEtat());

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
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void addTracteur(String modele, String marque, int cap_rem, String etat) {
        String insertTract = "INSERT INTO Tracteur(id_vehi, cap_rem_tract) VALUES (:id_vehi, :cap_rem_tract)";

        try {
            // On insert le vehicule
            int idVehi = addVehicule(marque, modele, etat);

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
            editVehicule(tracteur.getId(), tracteur.getMarque(), tracteur.getModele(), tracteur.getEtat());

            NamedParameterStatement editTracteurStatement = new NamedParameterStatement(DBConnection.getConnection(), requestTracteur);

            editTracteurStatement.setInt("cap_rem_tract", tracteur.getCapacite_remorque());
            editTracteurStatement.setInt("id_vehi", tracteur.getId());
            // Execute SQL statement
            editTracteurStatement.executeUpdate();

            editTracteurStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static List<Integer> getVehiculeUseToday(Commande commande) {
        System.out.println(commande.getId());

        String request = "SELECT Vehicule.id_vehi FROM Vehicule " +
                "INNER JOIN Ordre ON Vehicule.id_vehi=Ordre.id_vehi " +
                "INNER JOIN Commande ON Commande.id_com=Ordre.id_com " +
                "WHERE date_com=:today AND Commande.id_com=:idCom";

        List<Integer> listId = new ArrayList<>();

        try {
            NamedParameterStatement getVehiculeUsedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            getVehiculeUsedStatement.setString("today", LocalDate.now().toString());
            getVehiculeUsedStatement.setInt("idCom", commande.getId());
            // Execute select SQL statement
            ResultSet rs = getVehiculeUsedStatement.executeQuery();

            while (rs.next())
                listId.add(rs.getInt("id_vehi"));

            rs.close();
            getVehiculeUsedStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listId;
    }
}