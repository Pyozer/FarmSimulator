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
public class ClientSQL {

    private ObservableList<Agriculteur> clientList;
    private ObservableList<Champ> clientChampList;
    private Connection dbCon;

    public ClientSQL() {
        clientList = FXCollections.observableArrayList();
        dbCon = new DBConnection().getConnection();
    }

    public ObservableList<Agriculteur> getClientsList() {
        String request = "SELECT * FROM Agriculteur";
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                clientList.add(new Agriculteur(
                        Integer.parseInt(rs.getString("id_agri")),
                        rs.getString("prenom_agri"),
                        rs.getString("nom_agri"),
                        rs.getString("tel_agri"),
                        rs.getString("adr_agri"),
                        rs.getString("email_agri")
                ));
            }

            rs.close();
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return clientList;
    }

    public ObservableList<Champ> getClientsChampsList() {
        String request = "SELECT * FROM AGRICULTEUR INNER JOIN CHAMP ON AGRICULTEUR.id_agri=CHAMP.id_agri";
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String[] coord_split = rs.getString("coord_centre_champ").split(",");
                Point coord_center = new Point(Double.parseDouble(coord_split[0]), Double.parseDouble(coord_split[1]));

                System.out.println(rs.getString("coords_champ"));
                Polygon coord_champ = new Polygon(JSONManager.read(rs.getString("coords_champ")));

                clientChampList.add(new Champ(
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

            System.out.println(clientChampList.size() + "");

            rs.close();
            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return clientChampList;
    }
}
