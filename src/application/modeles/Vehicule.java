package application.modeles;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Vehicule {

    private SimpleIntegerProperty id;
    private SimpleStringProperty type;
    private SimpleStringProperty marque;
    private SimpleStringProperty modele;
    private SimpleStringProperty etat;

    public Vehicule(int id, String type, String marque, String modele, String etat) {
        this.id = new SimpleIntegerProperty(id);
        this.type = new SimpleStringProperty(type);
        this.marque = new SimpleStringProperty(marque);
        this.modele = new SimpleStringProperty(modele);
        this.etat = new SimpleStringProperty(etat);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
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
}
