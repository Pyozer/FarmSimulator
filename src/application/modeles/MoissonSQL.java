package application.modeles;

import application.database.DBConnection;
import application.database.NamedParameterStatement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MoissonSQL {

    public static void editMoisson(Commande inputCommande, Vehicule inputVehicule, LocalDateTime heure_debut, LocalDateTime heure_fin, Float inputNbKilo, Float inputNbTonne) {
        String request = "UPDATE Ordre SET tonnes_ordre=:tonnes, nb_km_ordre=:nbKilo, heure_arrive_ordre=:heureArrive, heure_fin_ordre=:heureFin " +
                "WHERE id_vehi=:vehi AND id_com=:com";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String dateTimeFin = heure_fin.format(formatter);
        String dateTimeDebut = heure_debut.format(formatter);

        try {
            NamedParameterStatement editMoissonStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editMoissonStatement.setFloat("tonnes", inputNbTonne);
            editMoissonStatement.setFloat("nbKilo", inputNbKilo);
            editMoissonStatement.setString("heureArrive", dateTimeDebut);
            editMoissonStatement.setString("heureFin", dateTimeFin);
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
        String request = "SELECT id_ordre, heure_arrive_ordre, heure_fin_ordre, nb_km_ordre, tonnes_ordre FROM Ordre Where id_vehi=:id_vehi AND id_com=:id_com AND heure_arrive_ordre IS NOT NULL";

        Moisson moisson = null;
        try {
            NamedParameterStatement getMoissonSelected = new NamedParameterStatement(DBConnection.getConnection(), request);
            getMoissonSelected.setInt("id_vehi", selectedVehicule.getId());
            getMoissonSelected.setInt("id_com", selectedCommande.getId());

            ResultSet rs = getMoissonSelected.executeQuery();
            if(rs.next()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

                LocalDateTime date_debut = LocalDateTime.parse(rs.getString("heure_arrive_ordre").substring(0, 19), formatter);
                LocalDateTime date_fin = LocalDateTime.parse(rs.getString("heure_fin_ordre").substring(0, 19), formatter);

                moisson = new Moisson(
                        rs.getInt("id_ordre"),
                        selectedCommande,
                        selectedVehicule,
                        date_debut,
                        date_fin,
                        rs.getFloat("nb_km_ordre"),
                        rs.getFloat("tonnes_ordre")
                );
            }

            getMoissonSelected.close();

            return moisson;

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }
}
