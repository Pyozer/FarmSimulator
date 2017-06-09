package application.classes;

import javafx.scene.paint.Color;

/**
 * Created by Thecr on 09/06/2017.
 */
public class ConvertColor {

    public static Color webToColorFX(String hex) {

        return Color.web(hex);
    }

    public static String ColorFXToWeb(Color color) {
        return "#" + Integer.toHexString(color.hashCode()).substring(0, 6).toUpperCase();
    }
}
