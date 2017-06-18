package application.modeles;

import application.classes.ElementPair;
import application.classes.Point;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Vehicule extends Element {

    private String type;
    private String marque;
    private String modele;
    private String etat;
    private Point position;

    public Vehicule(int id, String type, String marque, String modele, String etat, Point position) {
        super(id);
        this.type = type;
        this.marque = marque;
        this.modele = modele;
        this.etat = etat;
        this.position = position;

        getInformations().add(new ElementPair("Type", type));
        getInformations().add(new ElementPair("Marque", marque));
        getInformations().add(new ElementPair("Modèle", modele));
        getInformations().add(new ElementPair("Etat", etat));
        getInformations().add(new ElementPair("Position", position));
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele= modele;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return type + " - " + marque + " " + modele;
    }
}
