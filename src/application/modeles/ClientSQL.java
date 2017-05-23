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
public class ClientSQL {

    private ObservableList<Agriculteur> clientList;
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
            dbCon.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return clientList;
    }
}
