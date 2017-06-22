package application.modeles;

import application.classes.Point;

import static application.Constant.DECALAGE_LAT;
import static application.Constant.DECALAGE_LONG;

/**
 * Classe pour l'Eta
 */
public class Eta extends Element {

    private String nom; // Nom de l'Eta
    private String adresse; // Adresse postal de l'Eta
    private Point position_eta; // Position de l'Eta
    private Point position_vehi; // Position des véhicules

    public Eta(String nom, String adresse, Point position_eta) {
        super(0);
        this.nom = nom;
        this.adresse = adresse;
        this.position_eta = position_eta;
        this.position_vehi = new Point(position_eta.getX() - DECALAGE_LAT, position_eta.getY() - DECALAGE_LONG);

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
        return position_eta;
    }

    public void setPosition(Point position_eta) {
        this.position_eta = position_eta;
    }

    public Point getPositionVehi() {
        return position_vehi;
    }

    public String toString() {
        return "ETA " + nom + " - " + adresse;
    }
}
