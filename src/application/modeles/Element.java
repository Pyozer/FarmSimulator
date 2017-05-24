package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.List;

public abstract class Element {

    private SimpleIntegerProperty id; // ID de l'élément

    private List<ElementPair> informations = new ArrayList<>();

    public Element(int id) {
        this.id = new SimpleIntegerProperty(id);

        informations.add(new ElementPair("ID", id));
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public List<ElementPair> getInformations() {
        return informations;
    }

    @Override
    public String toString() {
        return String.valueOf(id.get());
    }
}
