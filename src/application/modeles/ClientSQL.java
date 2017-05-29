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
        clientChampList = FXCollections.observableArrayList();
        dbCon = new DBConnection().getConnection();
    }

    public ObservableList<Agriculteur> getClientsList() {
        String request = "SELECT * FROM Agriculteur";

        clientList.clear();
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
        return getClientsChampsList(-1);
    }

    public ObservableList<Champ> getClientsChampsList(int id_agri) {
        String request = "SELECT * FROM Agriculteur INNER JOIN Champ ON Agriculteur.id_agri=Champ.id_agri";
        if(id_agri > 0)
            request += " WHERE Agriculteur.id_agri=" + id_agri;

        clientChampList.clear();
        try {
            PreparedStatement preparedStatement = dbCon.prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));

                Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));

                clientChampList.add(new Champ(
                        Integer.parseInt(rs.getString("id_champ")),
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
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return clientChampList;
    }

    public void addClient(String inputNom, String inputPrenom, String inputTel, String inputAdresse, String inputEmail) {
        String request = "INSERT INTO Agriculteur(nom_agri, prenom_agri, adr_agri, tel_agri, email_agri) VALUES(:nom, :prenom, :adresse, :tel, :email)";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(dbCon, request);

            preparedStatement.setString("nom", inputNom);
            preparedStatement.setString("prenom", inputPrenom);
            preparedStatement.setString("tel", inputTel);
            preparedStatement.setString("adresse", inputAdresse);
            preparedStatement.setString("email", inputEmail);

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
