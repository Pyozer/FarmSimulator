package application.modeles;

import application.classes.Sha1;
import application.database.DBConnection;
import application.database.NamedParameterStatement;

import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Classe pour la gestion des comptes utilisateurs
 */
public class UserSQL {

    public static User getUser() {
        User user = null;

        String request = "SELECT * FROM User";
        try {
            PreparedStatement stmt = DBConnection.getConnection().prepareStatement(request);
            // execute select SQL statement
            ResultSet rs = stmt.executeQuery();

            if(rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getString("nom_user"),
                        rs.getString("prenom_user"),
                        rs.getString("email_user"),
                        null
                );
            }

            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }

    public static boolean checkIdentifiants(String email, String password) throws NoSuchAlgorithmException {
        boolean login_ok = false;

        String request = "SELECT COUNT(id_user) as rowCount, email_user, password_user FROM User WHERE email_user=:email AND password_user=:password";
        try {
            NamedParameterStatement stmt = new NamedParameterStatement(DBConnection.getConnection(), request);
            stmt.setString("email", email);
            stmt.setString("password", Sha1.cryptToSHA1(password));
            // execute select SQL statement
            ResultSet rs = stmt.executeQuery();

            rs.next();
            if(rs.getInt("rowCount") > 0)
                login_ok = true;

            stmt.close();
            rs.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return login_ok;
    }

    public static void addAccount(User user) throws NoSuchAlgorithmException {
        String request = "INSERT INTO User(nom_user, prenom_user, email_user, password_user) VALUES(:nom, :prenom, :email, :password)";
        try {
            NamedParameterStatement addAccount = new NamedParameterStatement(DBConnection.getConnection(), request);

            addAccount.setString("nom", user.getNom());
            addAccount.setString("prenom", user.getPrenom());
            addAccount.setString("email", user.getEmail());
            addAccount.setString("password", Sha1.cryptToSHA1(user.getPassword()));

            // Execute SQL statement
            addAccount.executeUpdate();

            addAccount.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static void editAccount(User user) throws NoSuchAlgorithmException {
        String requestWithPass = "UPDATE User SET nom_user=:nom_user, prenom_user=:prenom_user, email_user=:email_user, password_user=:password_user WHERE id_user=:id_user";
        String requestNoPass = "UPDATE User SET nom_user=:nom_user, prenom_user=:prenom_user, email_user=:email_user WHERE id_user=:id_user";
        try {
            NamedParameterStatement editAccount;
            if(user.getPassword() == null)
                editAccount = new NamedParameterStatement(DBConnection.getConnection(), requestNoPass);
            else {
                editAccount = new NamedParameterStatement(DBConnection.getConnection(), requestWithPass);
                editAccount.setString("password_user", Sha1.cryptToSHA1(user.getPassword()));
            }

            editAccount.setInt("id_user", user.getId());
            editAccount.setString("nom_user", user.getNom());
            editAccount.setString("prenom_user", user.getPrenom());
            editAccount.setString("email_user", user.getEmail());

            // Execute SQL statement
            editAccount.executeUpdate();

            editAccount.close();
        }
        catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public static boolean checkIfExists(String email) {
        int count = 1;

        String request = "SELECT COUNT(id_user) as rowCount FROM User WHERE email_user=:email";

        try {
            NamedParameterStatement checkExists = new NamedParameterStatement(DBConnection.getConnection(), request);
            checkExists.setString("email", email);
            ResultSet res = checkExists.executeQuery();

            res.next();
            count = res.getInt("rowCount");

            res.close();
            checkExists.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count >= 1;
    }

    public static int getNbAccount() {
        int count = 0;

        String request = "SELECT COUNT(id_user) as rowCount FROM `user`";

        try {
            PreparedStatement nbAccount = DBConnection.getConnection().prepareStatement(request);
            ResultSet res = nbAccount.executeQuery();

            res.next();
            count = res.getInt("rowCount");

            res.close();
            nbAccount.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}