package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pyozer on 23/05/2017.
 */
public class ChampSQL {

    private ObservableList<Champ> champList;
    private Connection dbCon;

    public ChampSQL() {
        champList = FXCollections.observableArrayList();
        dbCon = new DBConnection().getConnection();
    }

    public ObservableList<Champ> getChampsList() {
        String request = "SELECT * FROM Champ INNER JOIN Agriculteur ON Champ.id_agri=Agriculteur.id_agri INNER JOIN Culture ON Champ.type_champ=Culture.id_cul";

        champList.clear();
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));

                Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));

                champList.add(new Champ(
                        Integer.parseInt(rs.getString("id_agri")),
                        Integer.parseInt(rs.getString("surf_champ")),
                        rs.getString("adr_champ"),
                        coord_center,
                        coord_champ,
                        rs.getString("type_cul"),
                        new Agriculteur(
                                Integer.parseInt(rs.getString("id_agri")),
                                rs.getString("prenom_agri"),
                                rs.getString("nom_agri"),
                                rs.getString("tel_agri"),
                                rs.getString("adr_agri"),
                                rs.getString("email_agri"))
                ));
            }

            rs.close();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return champList;
    }

    public List<Culture> getTypeChampList() {
        String request = "SELECT * FROM culture";

        List<Culture> listCulture = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {

                listCulture.add(new Culture(
                        Integer.parseInt(rs.getString("id_cul")),
                        rs.getString("type_cul"))
                );
            }

            rs.close();
            preparedStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return listCulture;
    }

    public void deleteChamp(Champ champ) {
        String request = "DELETE FROM Champ WHERE id_champ=:id";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(dbCon, request);
            preparedStatement.setInt("id", champ.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

}
