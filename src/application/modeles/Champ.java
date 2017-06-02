package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;
import application.classes.Polygon;
import javafx.beans.property.*;

/**
 * Classe pour les Champs
 */
public class Champ extends Element {

    private SimpleFloatProperty surface; // Surface en m2
    private SimpleStringProperty adresse; // Surface en m2
    private SimpleObjectProperty<Point> coord_center;
    private SimpleObjectProperty<Polygon> coord_champ; // Longitude du champs
    private SimpleStringProperty type_culture; // Type de culture (Blé, Orge,..)
    private SimpleObjectProperty<Agriculteur> proprietaire; // Nom du propriétaire

    public Champ(int id, float surface, String adresse, Point coord_center, Polygon coord_champ, String type_culture, Agriculteur proprietaire) {
        super(id);
        this.surface = new SimpleFloatProperty(surface);
        this.adresse = new SimpleStringProperty(adresse);
        this.coord_center = new SimpleObjectProperty<>(coord_center);
        this.coord_champ = new SimpleObjectProperty<>(coord_champ);
        this.type_culture = new SimpleStringProperty(type_culture);
        this.proprietaire = new SimpleObjectProperty<>(proprietaire);

        getInformations().add(new ElementPair("Surface", surface + " m²"));
        getInformations().add(new ElementPair("Adresse", adresse));
        getInformations().add(new ElementPair("Coordonnées du centre", coord_center));
        getInformations().add(new ElementPair("Type de culture", type_culture));
        getInformations().add(new ElementPair("Proprietaire", proprietaire));
    }

    public float getSurface() {
        return surface.get();
    }

    public void setSurface(float surface) {
        this.surface.set(surface);
    }

    public String getAdresse() {
        return adresse.get();
    }

    public void setAdresse(String adresse) {
        this.adresse.set(adresse);
    }

    public Point getCoordCenter() {
        return coord_center.get();
    }

    public void setCoordCenter(Point latitude) { this.coord_center.set(latitude); }

    public Polygon getCoordChamp() {
        return coord_champ.get();
    }

    public void setCoordChamp(Polygon coordChamp) {
        this.coord_champ.set(coordChamp);
    }

    public String getType_culture() {
        return type_culture.get();
    }

    public void setTypeCulture(String type_culture) {
        this.type_culture.set(type_culture);
    }

    public Agriculteur getProprietaire() {
        return proprietaire.get();
    }

    public void setProprietaire(Agriculteur proprietaire) {
        this.proprietaire.set(proprietaire);
    }

    public String toString() {
        return getType_culture() + " : " + getAdresse() + " - " + getProprietaire();
    }

}
