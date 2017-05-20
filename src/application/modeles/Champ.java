package application.modeles;

import application.classes.Point;
import javafx.beans.property.*;

import java.util.List;

/**
 * Classe pour les Champs
 */
public class Champ {

    private SimpleIntegerProperty id; // ID du champs
    private SimpleIntegerProperty surface; // Surface en m2
    private SimpleObjectProperty<Point> coord_center;
    private SimpleObjectProperty<List<Point>> coord_champ; // Longitude du champs
    private SimpleStringProperty type_culture; // Type de culture (Blé, Orge,..)
    private SimpleObjectProperty<Agriculteur> proprietaire; // Nom du propriétaire
    private SimpleBooleanProperty bottelage; // Si bottelage ou non
    private SimpleStringProperty transport; // Qui réalise le transport (ETA, Lui, Negociant)

    public Champ(int id, int surface, Point coord_center, List<Point> coord_champ, String type_culture, Agriculteur proprietaire, boolean bottelage, String transport) {
        this.id = new SimpleIntegerProperty(id);
        this.surface = new SimpleIntegerProperty(surface);
        this.coord_center = new SimpleObjectProperty<>(coord_center);
        this.coord_champ = new SimpleObjectProperty<>(coord_champ);
        this.type_culture = new SimpleStringProperty(type_culture);
        this.proprietaire = new SimpleObjectProperty<>(proprietaire);
        this.bottelage = new SimpleBooleanProperty(bottelage);
        this.transport = new SimpleStringProperty(transport);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getSurface() {
        return surface.get();
    }

    public void setSurface(int surface) {
        this.surface.set(surface);
    }

    public Point getCoordCenter() {
        return coord_center.get();
    }

    public void setCoordCenter(Point latitude) { this.coord_center.set(latitude); }

    public List<Point> getCoordChamp() {
        return coord_champ.get();
    }

    public void setCoordChamp(List<Point> coordChamp) {
        this.coord_champ.set(coordChamp);
    }

    public String getType_culture() {
        return type_culture.get();
    }

    public void setType_culture(String type_culture) {
        this.type_culture.set(type_culture);
    }

    public Agriculteur getProprietaire() {
        return proprietaire.get();
    }

    public void setProprietaire(Agriculteur proprietaire) {
        this.proprietaire.set(proprietaire);
    }

    public boolean isBottelage() {
        return bottelage.get();
    }

    public void setBottelage(boolean bottelage) {
        this.bottelage.set(bottelage);
    }

    public String getTransport() {
        return transport.get();
    }

    public void setTransport(String transport) {
        this.transport.set(transport);
    }
}
