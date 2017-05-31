package application.modeles;

import application.classes.ElementPair;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Classe pour les Agriculteurs
 */
public class Commande extends Element {

    private SimpleObjectProperty<Champ> champCommande;
    private SimpleStringProperty transport; //transport de la commande
    private SimpleStringProperty typebott;// type de boot commande
    private SimpleStringProperty taillemx;// taille max commande
    private SimpleStringProperty date;
    private float tonne;
    private float cout;

    public Commande(int id, String transport, String typebott, String taillemax, String date, float tonne, float cout, Champ champCommande) {
        super(id);
        this.champCommande = new SimpleObjectProperty<>(champCommande);
        this.transport = new SimpleStringProperty(transport);
        this.typebott = new SimpleStringProperty(typebott);
        this.transport = new SimpleStringProperty(taillemax);
        this.date = new SimpleStringProperty(date);
        this.tonne = tonne;
        this.cout = cout;


        getInformations().add(new ElementPair("Nom", champCommande.getProprietaire().getNom()));
        getInformations().add(new ElementPair("Prénom", champCommande.getProprietaire().getPrenom()));
        getInformations().add(new ElementPair("Surface", champCommande.getSurface()));
        getInformations().add(new ElementPair("Adresse", champCommande.getAdresse()));
        getInformations().add(new ElementPair("Transport", transport));
        getInformations().add(new ElementPair("Type de Botteulage", typebott));
        getInformations().add(new ElementPair("Taille max du transport", taillemax));
        getInformations().add(new ElementPair("Date", date));
        getInformations().add(new ElementPair("Tonne", tonne));
        getInformations().add(new ElementPair("Cout", cout));
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
        return taillemx.get();
    }

    public void setTaillemx(String taillemx) {
        this.taillemx.set(taillemx);
    }

}
