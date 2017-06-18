package application.modeles;

import application.classes.ElementPair;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {

    private int id; // ID de l'élément

    private List<ElementPair> informations = new ArrayList<>();

    public Element(int id) {
        this.id = id;

        informations.add(new ElementPair("ID", id));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<ElementPair> getInformations() {
        return informations;
    }

    @Override
    public String toString() {
        return "#" + id;
    }
}
