package application.modeles;

import application.database.DBConnection;
import application.database.NamedParameterStatement;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MoissonSQL {

    public static void editMoisson(Commande inputCommande, Vehicule inputVehicule, String heure_debut, String heure_fin, Float inputNbKilo, Float inputNbTonne) {
        String request = "UPDATE Ordre SET tonnes_ordre=:tonnes, nb_km_ordre=:nbKilo, heure_arrive_ordre=:heureArrive, heure_fin_ordre=:heureFin " +
                "WHERE id_vehi=:vehi AND id_com=:com";

        try {
            NamedParameterStatement editMoissonStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editMoissonStatement.setFloat("tonnes", inputNbTonne);
            editMoissonStatement.setFloat("nbKilo", inputNbKilo);
            editMoissonStatement.setString("heureArrive", heure_debut);
            editMoissonStatement.setString("heureFin", heure_fin);
            editMoissonStatement.setInt("vehi", inputVehicule.getId());
            editMoissonStatement.setInt("com", inputCommande.getId());

            // Execute SQL statement
            editMoissonStatement.executeUpdate();

            editMoissonStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static void deleteMoisson(Vehicule vehicule, Commande commande) {
        editMoisson(commande, vehicule, null, null, (float) 0, (float) 0);
    }

    public static boolean isRapportExist(Commande selectedCommande, Vehicule selectedVehicule) {
        String request = "SELECT heure_arrive_ordre FROM Ordre WHERE id_com=:id_com AND id_vehi=:id_vehi";

        try {
            NamedParameterStatement isRapportStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            isRapportStatement.setInt("id_vehi", selectedVehicule.getId());
            isRapportStatement.setInt("id_com", selectedCommande.getId());

            ResultSet rs = isRapportStatement.executeQuery();

            String value = null;
            if(rs.next())
                value = rs.getString("heure_arrive_ordre");

            isRapportStatement.close();

            return !(value == null || value.equals("NULL"));

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public static Moisson getMoissonSelected(Commande selectedCommande, Vehicule selectedVehicule) {
        String request = "SELECT id_ordre, heure_arrive_ordre, heure_fin_ordre, nb_km_ordre, tonnes_ordre FROM Ordre Where id_vehi=:id_vehi AND id_com=:id_com";
        try {
            NamedParameterStatement getMoissonSelected = new NamedParameterStatement(DBConnection.getConnection(), request);
            getMoissonSelected.setInt("id_vehi", selectedVehicule.getId());
            getMoissonSelected.setInt("id_com", selectedCommande.getId());

            ResultSet rs = getMoissonSelected.executeQuery();
            rs.next();

            String inputDebut = rs.getString("heure_arrive_ordre");
            String inputFin = rs.getString("heure_fin_ordre");

            Moisson moisson = new Moisson(
                    rs.getInt("id_ordre"),
                    selectedCommande,
                    selectedVehicule,
                    inputDebut.substring(0, inputDebut.length() - 2),
                    inputFin.substring(0, inputFin.length() - 2),
                    rs.getFloat("nb_km_ordre"),
                    rs.getFloat("tonnes_ordre")
            );

            getMoissonSelected.close();

            return moisson;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
