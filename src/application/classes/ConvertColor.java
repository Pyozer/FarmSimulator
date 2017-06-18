package application.classes;

import javafx.scene.paint.Color;

import java.util.Locale;


public class ConvertColor {

    /**
     * Convertie une couleur Hexadecimal WEB en objet Color
     * @param hex Couleur hexadecimale WEB à convertir
     * @return  Couleur transformé en objet Color
     */
    public static Color webToColorFX(String hex) {
        return Color.web(hex);
    }

    /**
     * Transforme un objet Color en version Hexadecimal pour le WEB
     * @param color  Couleur à transformer
     * @return Couleur en Hexadecimale
     */
    public static String ColorFXToWeb(Color color) {
        return String.format((Locale) null, "#%02x%02x%02x",
                Math.round(color.getRed() * 255),
                Math.round(color.getGreen() * 255),
                Math.round(color.getBlue() * 255)).toUpperCase();
    }
}
