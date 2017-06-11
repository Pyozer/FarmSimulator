package application.modeles;

import application.classes.ConvertColor;
import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChampSQL {

    public static ObservableList<Champ> getChampsList() {
        String request = "SELECT * FROM Champ " +
                "INNER JOIN Agriculteur ON Champ.id_agri=Agriculteur.id_agri " +
                "INNER JOIN Culture ON Champ.type_champ=Culture.id_cul";

        ObservableList<Champ> champList = FXCollections.observableArrayList();
        try {
            PreparedStatement getChampsStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = getChampsStatement.executeQuery();

            while (rs.next()) {
                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));

                Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));

                champList.add(new Champ(
                        Integer.parseInt(rs.getString("id_champ")),
                        Integer.parseInt(rs.getString("surf_champ")),
                        rs.getString("adr_champ"),
                        coord_center,
                        coord_champ,
                        new Culture(rs.getInt("id_cul"), rs.getString("type_cul")),
                        new Agriculteur(
                                Integer.parseInt(rs.getString("id_agri")),
                                rs.getString("prenom_agri"),
                                rs.getString("nom_agri"),
                                rs.getString("tel_agri"),
                                rs.getString("adr_agri"),
                                rs.getString("email_agri"),
                                ConvertColor.webToColorFX(rs.getString("couleur_agri")))
                ));
            }

            rs.close();
            getChampsStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return champList;
    }

    public static List<Culture> getTypeChampList() {
        String request = "SELECT * FROM culture";

        List<Culture> listCulture = new ArrayList<>();
        try {
            PreparedStatement getTypeChampStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = getTypeChampStatement.executeQuery();

            while (rs.next()) {

                listCulture.add(new Culture(
                        Integer.parseInt(rs.getString("id_cul")),
                        rs.getString("type_cul"))
                );
            }

            rs.close();
            getTypeChampStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listCulture;
    }

    public static void addChamp(float surf_champ, String adr_champ, Point coord_centre, Polygon coords_champ, Culture type_champ, Agriculteur proprio) {
        String request = "INSERT INTO Champ(surf_champ, adr_champ, coord_centre_champ, coords_champ, type_champ, id_agri) " +
                "VALUES(:surf_champ, :adr_champ, :coord_centre_champ, :coords_champ, :type_champ, :id_agri)";

        try {
            NamedParameterStatement addChampStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            addChampStatement.setFloat("surf_champ", surf_champ);
            addChampStatement.setString("adr_champ", adr_champ);
            addChampStatement.setString("coord_centre_champ", coord_centre.toString());
            addChampStatement.setString("coords_champ", coords_champ.toString());
            addChampStatement.setInt("type_champ", type_champ.getId());
            addChampStatement.setInt("id_agri", proprio.getId());

            addChampStatement.executeUpdate();

            addChampStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editChamp(Champ champ) {
        String request = "UPDATE Champ SET surf_champ=:surf_champ, adr_champ=:adr_champ, coord_centre_champ=:coord_centre_champ, coords_champ=:coords_champ, type_champ=:type_champ, " +
                "id_agri=:id_agri WHERE id_champ=:id_champ";

        try {
            NamedParameterStatement editChampStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            editChampStatement.setFloat("surf_champ", champ.getSurface());
            editChampStatement.setString("adr_champ", champ.getAdresse());
            editChampStatement.setString("coord_centre_champ", champ.getCoordCenter().toString());
            editChampStatement.setString("coords_champ", champ.getCoordChamp().toString());
            editChampStatement.setInt("type_champ", champ.getType_culture().getId());
            editChampStatement.setInt("id_agri", champ.getProprietaire().getId());
            editChampStatement.setInt("id_champ", champ.getId());

            editChampStatement.executeUpdate();

            editChampStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void deleteChamp(Champ champ) {
        String request = "DELETE FROM Champ WHERE id_champ=:id";

        try {
            NamedParameterStatement deleteChampStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            deleteChampStatement.setInt("id", champ.getId());
            deleteChampStatement.executeUpdate();

            deleteChampStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
