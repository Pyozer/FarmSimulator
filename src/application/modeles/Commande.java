package application.modeles;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

/**
 * Classe pour les Commandes
 */
public class Commande extends Element {

    private Champ champCommande;
    private String transport; //transport de la commande
    private String typeBott;// type de boot commande
    private float tailleMax;// taille max commande
    private LocalDate date;
    private float tonne;
    private float cout;
    private boolean effectuer;

    public Commande(int id, String transport, String typeBott, float tailleMax, String date, float tonne, float cout, Champ champCommande, boolean effectuer) {
        super(id);
        this.transport = transport;
        this.typeBott = typeBott;
        this.tailleMax = tailleMax;
        this.date = LocalDate.parse(date);
        this.tonne = tonne;
        this.cout = cout;
        this.champCommande = champCommande;
        this.effectuer = effectuer;
    }

    public Champ getChampCommande() {
        return champCommande;
    }

    public void setChampCommande(Champ champCommande) {
        this.champCommande = champCommande;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(String date) {
        DateTimeFormatter frenchFormat = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(Locale.FRANCE);
        this.date = LocalDate.parse(date, frenchFormat);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getTransport() {
        return transport;
    }

    public void setTransport(String transport) {
        this.transport = transport;
    }

    public String getTypeBott() {
        return typeBott;
    }

    public void setTypeBott(String typeBott) {
        this.typeBott = typeBott;
    }

    public float getTailleMax() {
        return tailleMax;
    }

    public void setTailleMax(float tailleMax) {
        this.tailleMax = tailleMax;
    }

    public float getTonne() {
        return tonne;
    }

    public void setTonne(float tonne) {
        this.tonne = tonne;
    }

    public float getCout() {
        return cout;
    }

    public void setCout(float cout) {
        this.cout = cout;
    }

    public boolean isEffectuer() {
        return effectuer;
    }

    public void setEffectuer(boolean effectuer) {
        this.effectuer = effectuer;
    }

    @Override
    public String toString() {
        return super.toString() + " - " + date.toString() + " pour " + champCommande.getProprietaire();
    }


}
