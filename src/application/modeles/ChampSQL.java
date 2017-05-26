package application.modeles;

import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
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
public class ChampSQL {

    private ObservableList<Champ> champList;
    private Connection dbCon;

    public ChampSQL() {
        champList = FXCollections.observableArrayList();
        dbCon = new DBConnection().getConnection();
    }

    public ObservableList<Champ> getChampsList() {
        String request = "SELECT * FROM Champ INNER JOIN Agriculteur ON Champ.id_agri=Agriculteur.id_agri";

        champList.clear();
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                String[] coord_split = rs.getString("coord_centre_champ").split(",");
                Point coord_center = new Point(Double.parseDouble(coord_split[0]), Double.parseDouble(coord_split[1]));

                Polygon coord_champ = new Polygon(JSONManager.read(rs.getString("coords_champ")));

                champList.add(new Champ(
                        Integer.parseInt(rs.getString("id_agri")),
                        Integer.parseInt(rs.getString("surf_champ")),
                        rs.getString("adr_champ"),
                        coord_center,
                        coord_champ,
                        rs.getString("type_champ"),
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
            dbCon.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return champList;
    }

}
