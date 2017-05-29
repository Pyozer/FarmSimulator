package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Vehicule extends Element {

    private SimpleStringProperty type;
    private SimpleStringProperty marque;
    private SimpleStringProperty modele;
    private SimpleStringProperty etat;
    private SimpleObjectProperty<Point> position;

    public Vehicule(int id, String type, String marque, String modele, String etat, Point position) {
        super(id);
        this.type = new SimpleStringProperty(type);
        this.marque = new SimpleStringProperty(marque);
        this.modele = new SimpleStringProperty(modele);
        this.etat = new SimpleStringProperty(etat);
        this.position = new SimpleObjectProperty<>(position);

        getInformations().add(new ElementPair("Type", type));
        getInformations().add(new ElementPair("Marque", marque));
        getInformations().add(new ElementPair("Modèle", modele));
        getInformations().add(new ElementPair("Etat", etat));
        getInformations().add(new ElementPair("Position", position));
    }

    public String getType() {
        return type.get();
    }

    public void setType(String type) {
        this.type.set(type);
    }

    public String getMarque() {
        return marque.get();
    }

    public void setMarque(String marque) {
        this.marque.set(marque);
    }

    public String getModele() {
        return modele.get();
    }

    public void setModele(String modele) {
        this.modele.set(modele);
    }

    public String getEtat() {
        return etat.get();
    }

    public void setEtat(String etat) {
        this.etat.set(etat);
    }

    public Point getPosition() {
        return position.get();
    }

    public void setPosition(Point position) {
        this.position.set(position);
    }

    @Override
    public String toString() {
        return type.get() + " " + marque.get() + " " + modele.get();
    }
}
