package application.modeles;

/**
 * Created by justin on 22/06/2017.
 */
public class User {

    private String nom;
    private String prenom;
    private String password;
    private String email;
    private  int id;

    public User(String nom, String prenom, String password, String email, int id) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.email = email;
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
