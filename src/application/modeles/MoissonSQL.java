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

public class MoissonSQL {

    public static void editMoisson(Commande inputCommande, Vehicule inputVehicule, Float inputDuree, String inputH_fin, String inputH_deb, Float inputNbKilo, Float inputNbTonne) {
        String request = "UPDATE Ordre SET tonnes_ordre=:tonnes, nb_km_ordre=:nbKilo, duree_ordre=:duree, heure_arrive_ordre=:heureArrive, heure_debut_ordre=:heureDebut " +
                "WHERE id_vehi=:vehi AND id_com=:com";

        try {
            NamedParameterStatement editMoissonStatement = new NamedParameterStatement(DBConnection.getConnection(), request);

            editMoissonStatement.setFloat("tonnes", inputNbTonne);
            editMoissonStatement.setFloat("nbKilo", inputNbKilo );
            editMoissonStatement.setString("heureArrive", inputH_fin);
            editMoissonStatement.setString("heureDebut", inputH_deb);
            editMoissonStatement.setFloat("duree",  inputDuree);
            editMoissonStatement.setInt("vehi", inputVehicule.getId());
            editMoissonStatement.setInt("com", inputCommande.getId());

            // Execute SQL statement
            editMoissonStatement.executeUpdate();

            editMoissonStatement.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }

    }

    public static ObservableList<Moisson> getMoissonList() {

        String request = "SELECT * FROM Ordre " +
                        "INNER JOIN Commande ON Commande.id_com = Ordre.id_com "+
                        "INNER JOIN Champ ON Champ.id_champ=Commande.id_champ " +
                        "INNER JOIN Agriculteur ON Agriculteur.id_agri=Champ.id_agri " +
                        "INNER JOIN Culture ON Culture.id_cul=Champ.type_champ";

        ObservableList<Moisson> moissonList = FXCollections.observableArrayList();

        try {
            PreparedStatement getMoissonStatement = DBConnection.getConnection().prepareStatement(request);
            // Execute SQL statement
            ResultSet rs = getMoissonStatement.executeQuery();

            while (rs.next()) {

                Vehicule vehi_com = null;

                Point coord_center = JSONManager.readPoint(rs.getString("coord_centre_champ"));
                Polygon coord_champ = new Polygon(JSONManager.readPolygon(rs.getString("coords_champ")));
                Point position = JSONManager.readPoint(rs.getString("position_vehi"));

                if(rs.getString("type_bott") != null){
                    vehi_com = new Botteleuse(
                            Integer.parseInt(rs.getString("id_vehi")),
                            rs.getString("marque_vehi"),
                            rs.getString("modele_vehi"),
                            rs.getString("etat_vehi"),
                            position,
                            Boolean.parseBoolean(rs.getString("type_bott"))
                    );
                }
                else if(rs.getString("taille_tremis_moi") != null){
                    vehi_com = new Moissonneuse(
                            Integer.parseInt(rs.getString("id_vehi")),
                            rs.getString("marque_vehi"),
                            rs.getString("modele_vehi"),
                            rs.getString("etat_vehi"),
                            position,
                            Integer.parseInt(rs.getString("taille_tremis_moi")),
                            Integer.parseInt(rs.getString("taille_reserve_moi")),
                            Integer.parseInt(rs.getString("largeur_route_moi")),
                            Integer.parseInt(rs.getString("hauteur_moi")),
                            Integer.parseInt(rs.getString("largeur_coupe_moi")),
                            Integer.parseInt(rs.getString("conso_fonct_moi")),
                            Integer.parseInt(rs.getString("conso_route_moi")),
                            Integer.parseInt(rs.getString("poids_moi"))
                    );
                }
                else if(rs.getString("cap_rem_tract") != null){
                    vehi_com = new Tracteur(
                            Integer.parseInt(rs.getString("id_vehi")),
                            rs.getString("marque_vehi"),
                            rs.getString("modele_vehi"),
                            rs.getString("etat_vehi"),
                            position,
                            Integer.parseInt(rs.getString("cap_rem_tract"))
                    );
                }

                moissonList.add(new Moisson(Integer.parseInt(rs.getString("id_ordre")),
                        new Commande(
                                Integer.parseInt(rs.getString("id_com")),
                                rs.getString("transp_com"),
                                rs.getString("bott_com"),
                                Float.parseFloat(rs.getString("taille_max_transp_com")),
                                rs.getString("date_com"),
                                Float.parseFloat(rs.getString("tonne_com")),
                                Float.parseFloat(rs.getString("cout_com")),
                                new Champ(
                                        Integer.parseInt(rs.getString("id_agri")),
                                        Float.parseFloat(rs.getString("surf_champ")),
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
                                                ConvertColor.webToColorFX(rs.getString("couleur_agri"))
                                        )
                                ),
                                rs.getBoolean("effectuer_com")
                        ),
                        vehi_com,
                        rs.getString("heure_arrive_ordre"),
                        rs.getString("heure_debut_ordre"),
                        Float.parseFloat(rs.getString("duree_ordre")),
                        Float.parseFloat(rs.getString("nb_km_ordre")),
                        Float.parseFloat(rs.getString("tonnes_ordre"))
                    ));

            }

            rs.close();
            getMoissonStatement.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return moissonList;
    }

   /* public static ObservableList<Moisson> getMoissonList() {
        return getMoissonList();
    }*/

    public static void deleteMoisson(Vehicule vehicule, Commande commande) {
        String request = "DELETE FROM Ordre WHERE id_com=:id_com AND id_vehi=:id_vehi";

        try {
            NamedParameterStatement deleteMoisson = new NamedParameterStatement(DBConnection.getConnection(), request);
            deleteMoisson.setInt("id_vehi", vehicule.getId());
            deleteMoisson.setInt("id_com", commande.getId());
            deleteMoisson.executeUpdate();

            deleteMoisson.close();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static boolean isRapportExist(Commande selectedCommande, Vehicule selectedVehicule) {
        String request = "SELECT heure_arrive_ordre FROM Ordre WHERE id_com=:id_com AND id_vehi=:id_vehi";

        try {
            NamedParameterStatement isRapportStatement = new NamedParameterStatement(DBConnection.getConnection(), request);
            isRapportStatement.setInt("id_vehi", selectedVehicule.getId());
            isRapportStatement.setInt("id_com", selectedCommande.getId());

            ResultSet rs = isRapportStatement.executeQuery();

            rs.next();

            String value = rs.getString("heure_arrive_ordre");
            System.out.println(value);

            isRapportStatement.close();

            return !value.equals("0");

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return false;
    }

    public static Moisson getMoissonSelected(Commande selectedCommande, Vehicule selectedVehicule) {
        String request = "SELECT id_ordre, heure_arrive_ordre, heure_depart_ordre duree_ordre, nb_km_ordre, tonnes_ordre FROM Ordre Where id_vehi=:id_vehi AND id_com=:id_com";
        Moisson moisson = null;
        try {
            NamedParameterStatement getMoissonSelected = new NamedParameterStatement(DBConnection.getConnection(), request);
            getMoissonSelected.setInt("id_vehi", selectedVehicule.getId());
            getMoissonSelected.setInt("id_com", selectedCommande.getId());

            ResultSet rs = getMoissonSelected.executeQuery();
            while (rs.next()){
               moisson = new Moisson(
                       rs.getInt("id_ordre"),
                       selectedCommande,
                       selectedVehicule,
                       rs.getString("heure_arrive_ordre"),
                       rs.getString("heure_depart_ordre"),
                       rs.getFloat("duree_ordre"),
                       rs.getFloat("nb_km_kilo"),
                       rs.getFloat("tonnes_ordre")
               );
            }

            getMoissonSelected.close();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return moisson;
    }
}
