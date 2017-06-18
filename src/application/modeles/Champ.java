package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;
import application.classes.Polygon;

/**
 * Classe pour les Champs
 */
public class Champ extends Element {

    private float surface; // Surface en m2
    private String adresse; // Surface en m2
    private Point coord_center;
    private Polygon coord_champ; // Longitude du champs
    private Culture type_culture; // Type de culture (Blé, Orge,..)
    private Agriculteur proprietaire; // Nom du propriétaire

    public Champ(int id, float surface, String adresse, Point coord_center, Polygon coord_champ, Culture type_culture, Agriculteur proprietaire) {
        super(id);
        this.surface = surface;
        this.adresse = adresse;
        this.coord_center = coord_center;
        this.coord_champ = coord_champ;
        this.type_culture = type_culture;
        this.proprietaire = proprietaire;

        getInformations().add(new ElementPair("Surface", surface + " m²"));
        getInformations().add(new ElementPair("Adresse", adresse));
        getInformations().add(new ElementPair("Coordonnées du centre", coord_center));
        getInformations().add(new ElementPair("Type de culture", type_culture));
        getInformations().add(new ElementPair("Proprietaire", proprietaire));
    }

    public float getSurface() {
        return surface;
    }

    public void setSurface(float surface) {
        this.surface = surface;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public Point getCoordCenter() {
        return coord_center;
    }

    public void setCoordCenter(Point latitude) { this.coord_center = latitude; }

    public Polygon getCoordChamp() {
        return coord_champ;
    }

    public void setCoordChamp(Polygon coordChamp) {
        this.coord_champ = coordChamp;
    }

    public Culture getType_culture() {
        return type_culture;
    }

    public void setTypeCulture(Culture type_culture) {
        this.type_culture = type_culture;
    }

    public Agriculteur getProprietaire() {
        return proprietaire;
    }

    public void setProprietaire(Agriculteur proprietaire) {
        this.proprietaire = proprietaire;
    }

    @Override
    public String toString() {
        return getType_culture() + " : " + getAdresse() + " - " + getProprietaire();
    }

    @Override
    public boolean equals(Object champToCheck) {
        if (this == champToCheck) return true;
        if (champToCheck == null || getClass() != champToCheck.getClass()) return false;

        Champ champ = (Champ) champToCheck;

        if (surface != champ.surface) return false;
        if (!adresse.equals(champ.adresse)) return false;
        if (!coord_center.equals(champ.coord_center)) return false;
        if (!coord_champ.equals(champ.coord_champ)) return false;
        if (!type_culture.equals(champ.type_culture)) return false;
        return proprietaire.equals(champ.proprietaire);
    }
}
