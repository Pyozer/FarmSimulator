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
    private String typebott;// type de boot commande
    private float taillemax;// taille max commande
    private LocalDate date;
    private float tonne;
    private float cout;
    private boolean effectuer;

    public Commande(int id, String transport, String typebott, float taillemax, String date, float tonne, float cout, Champ champCommande, boolean effectuer) {
        super(id);
        this.transport = transport;
        this.typebott = typebott;
        this.taillemax = taillemax;
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

    public String getTypebott() {
        return typebott;
    }

    public void setTypebott(String typebott) {
        this.typebott = typebott;
    }

    public float getTaillemax() {
        return taillemax;
    }

    public void setTailleMax(float taillemax) {
        this.taillemax = taillemax;
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
