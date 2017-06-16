package application.modeles;

import application.classes.Point;

/**
 * Classe pour l'Eta
 */
public class Eta extends Element {

    private String nom; // Nom de l'Eta
    private String adresse; // Adresse postal de l'Eta
    private Point position; // Position de l'Eta

    public Eta(int id, String nom, String adresse, Point position) {
        super(id);
        this.nom = nom;
        this.adresse = adresse;
        this.position = position;

    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String toString() {
        return nom + " - " + adresse;
    }
}
