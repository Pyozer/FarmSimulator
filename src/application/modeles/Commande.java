package application.modeles;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Classe pour les Commandes
 */
public class Commande extends Element {

    private SimpleObjectProperty<Champ> champCommande;
    private SimpleStringProperty transport; //transport de la commande
    private SimpleStringProperty typebott;// type de boot commande
    private SimpleFloatProperty taillemax;// taille max commande
    private SimpleObjectProperty<LocalDate> date;
    private SimpleFloatProperty tonne;
    private SimpleFloatProperty cout;

    public Commande(int id, String transport, String typebott, float taillemax, String date, float tonne, float cout, Champ champCommande) {
        super(id);
        this.transport = new SimpleStringProperty(transport);
        this.typebott = new SimpleStringProperty(typebott);
        this.taillemax = new SimpleFloatProperty(taillemax);
        this.date = new SimpleObjectProperty<>(LocalDate.parse(date));
        this.tonne = new SimpleFloatProperty(tonne);
        this.cout = new SimpleFloatProperty(cout);
        this.champCommande = new SimpleObjectProperty<>(champCommande);
    }

    public Champ getChampCommande() {
        return champCommande.get();
    }

    public void setChampCommande(Champ champCommande) {
        this.champCommande.set(champCommande);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(String date) {
        DateTimeFormatter frenchFormat = DateTimeFormatter.ofLocalizedDate(
                FormatStyle.MEDIUM).withLocale(Locale.FRANCE);
        this.date.set(LocalDate.parse(date, frenchFormat));
    }

    public void setDate(LocalDate date) {
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

    public float getTaillemax() {
        return taillemax.get();
    }

    public void setTailleMax(float taillemax) {
        this.taillemax.set(taillemax);
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

    @Override
    public String toString() {
        return super.toString() + " - " + getDate().toString() + " pour " + getChampCommande().getProprietaire();
    }


}
