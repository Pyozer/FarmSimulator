package application.modeles;

import application.classes.ConvertColor;
import application.classes.JSONManager;
import application.classes.Point;
import application.classes.Polygon;
import application.database.DBConnection;
import application.database.NamedParameterStatement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pyozer on 23/05/2017.
 *
 */
public class ClientSQL {

    public static ObservableList<Agriculteur> getClientsList() {
        String request = "SELECT * FROM Agriculteur";

        ObservableList<Agriculteur> clientList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                clientList.add(new Agriculteur(
                        Integer.parseInt(rs.getString("id_agri")),
                        rs.getString("prenom_agri"),
                        rs.getString("nom_agri"),
                        rs.getString("tel_agri"),
                        rs.getString("adr_agri"),
                        rs.getString("email_agri"),
                        ConvertColor.webToColorFX(rs.getString("couleur_agri"))
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

    public static ObservableList<Champ> getClientsChampsList() {
        return getClientsChampsList(-1);
    }

    public static ObservableList<Champ> getClientsChampsList(int id_agri) {
        String request = "SELECT * FROM Agriculteur " +
                "INNER JOIN Champ ON Agriculteur.id_agri=Champ.id_agri " +
                "INNER JOIN Culture ON Champ.type_champ=Culture.id_cul";

        if(id_agri > 0)
            request += " WHERE Agriculteur.id_agri=" + id_agri;

        ObservableList<Champ> clientChampList = FXCollections.observableArrayList();
        try {
            PreparedStatement preparedStatement = DBConnection.getConnection().prepareStatement(request);
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
                        rs.getString("type_cul"),
                        new Agriculteur(
                                Integer.parseInt(rs.getString("id_agri")),
                                rs.getString("nom_agri"),
                                rs.getString("prenom_agri"),
                                rs.getString("tel_agri"),
                                rs.getString("adr_agri"),
                                rs.getString("email_agri"),
                                ConvertColor.webToColorFX(rs.getString("couleur_agri")))
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

    public static void addClient(String inputNom, String inputPrenom, String inputTel, String inputAdresse, String inputEmail, Color inputCouleur) {
        String request = "INSERT INTO Agriculteur(nom_agri, prenom_agri, adr_agri, tel_agri, email_agri, couleur_agri) " +
                "VALUES(:nom, :prenom, :adresse, :tel, :email, :couleur)";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("nom", inputNom);
            preparedStatement.setString("prenom", inputPrenom);
            preparedStatement.setString("tel", inputTel);
            preparedStatement.setString("adresse", inputAdresse);
            preparedStatement.setString("email", inputEmail);
            preparedStatement.setString("couleur", ConvertColor.ColorFXToWeb(inputCouleur));

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editClient(Agriculteur agri) {
        String request = "UPDATE Agriculteur SET nom_agri=:nom_agri, prenom_agri=:prenom_agri, adr_agri=:adr_agri, tel_agri=:tel_agri, email_agri=:email_agri , couleur_agri=:couleur_agri " +
                "WHERE id_agri=:id_agri";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            preparedStatement.setString("nom_agri", agri.getNom());
            preparedStatement.setString("prenom_agri", agri.getPrenom());
            preparedStatement.setString("adr_agri", agri.getAdresse());
            preparedStatement.setString("tel_agri", agri.getNum_tel());
            preparedStatement.setString("email_agri", agri.getEmail());
            preparedStatement.setString("couleur_agri", ConvertColor.ColorFXToWeb(agri.getCouleur()));
            preparedStatement.setInt("id_agri", agri.getId());

            // Execute SQL statement
            preparedStatement.executeUpdate();

            preparedStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void deleteClient(Agriculteur agriculteur) {
        String request = "DELETE FROM Agriculteur WHERE id_agri=:id";

        try {
            NamedParameterStatement preparedStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            preparedStatement.setInt("id", agriculteur.getId());
            preparedStatement.executeUpdate();

            preparedStatement.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }
}
