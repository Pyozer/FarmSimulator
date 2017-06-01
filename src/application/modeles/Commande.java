package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe pour les Commandes
 */
public class Commande extends Element {

    private SimpleObjectProperty<Champ> champCommande;
    private SimpleStringProperty transport; //transport de la commande
    private SimpleStringProperty typebott;// type de boot commande
    private SimpleStringProperty taillemax;// taille max commande
    private SimpleStringProperty date;
    private SimpleFloatProperty tonne;
    private SimpleFloatProperty cout;
    private SimpleObjectProperty<Agriculteur> client;


    public Commande(int id, String transport, String typebott, String taillemax, String date, float tonne, float cout, Champ champCommande) {
        super(id);
        this.transport = new SimpleStringProperty(transport);
        this.typebott = new SimpleStringProperty(typebott);
        this.taillemax = new SimpleStringProperty(taillemax);
        this.date = new SimpleStringProperty(date);
        this.tonne = new SimpleFloatProperty(tonne);
        this.cout = new SimpleFloatProperty(cout);
        this.champCommande = new SimpleObjectProperty<>(champCommande);
        this.client = new SimpleObjectProperty<>(champCommande.getProprietaire());
    }

    public Champ getChampCommande() {
        return champCommande.get();
    }

    public void setChampCommande(Champ champCommande) {
        this.champCommande.set(champCommande);
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(String date) {
        this.date.set(date);
    }

    public String getTransport() {
        return transport.get();
    }

    public void setTransport(String transport) {
        this.transport.set(transport);
    }

    public String getTypebott() {
        return typebott.get();
    }

    public void setTypebott(String typebott) {
        this.typebott.set(typebott);
    }

    public String getTaillemx() {
        return taillemax.get();
    }

    public void setTaillemx(String taillemx) {
        this.taillemax.set(taillemx);
    }

    public float getTonne() {
        return tonne.get();
    }

    public void setTonne(float tonne) {
        this.tonne.set(tonne);
    }

    public float getCout() {
        return cout.get();
    }

    public void setCout(float cout) {
        this.cout.set(cout);
    }


}
