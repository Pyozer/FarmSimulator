package application.modeles;

import application.classes.ElementPair;
import javafx.scene.paint.Color;

/**
 * Classe pour les Agriculteurs
 */
public class Agriculteur extends Element {

    private String nom; // Nom de l'agriculteur
    private String prenom; // Prénom de l'agriculteur
    private String num_tel; // Numéro de téléphone de l'agriculteur
    private String adresse; // Adresse de l'agriculteur
    private String email; // Adresse email de l'agriculteur
    private Color couleur; // Couleur de l'agriculteur

    public Agriculteur(int id, String nom, String prenom, String num_tel, String adresse, String email, Color couleur) {
        super(id);
        this.nom = nom;
        this.prenom = prenom;
        this.num_tel = num_tel;
        this.adresse = adresse;
        this.email = email;
        this.couleur = couleur;

        getInformations().add(new ElementPair("Nom", nom));
        getInformations().add(new ElementPair("Prénom", prenom));
        getInformations().add(new ElementPair("Numéro téléphone", num_tel));
        getInformations().add(new ElementPair("Adresse", adresse));
        getInformations().add(new ElementPair("Email", email));
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

    public String getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(String num_tel) {
        this.num_tel = num_tel;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public String toString() {
        return nom + " " + prenom;
    }
}
