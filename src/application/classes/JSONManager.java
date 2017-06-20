package application.classes;

import org.json.JSONArray;

/**
 * Une classe pour encoder et décoder au format JSON<br>
 * un tableau de points (coordonnées GPS)<br>
 */
public class JSONManager {
	
	/**
	 * Décode une chaîne (d'un POLYGON) au format JSON
	 * @param jsonStr la chaîne de caractère au format JSON
	 * @return le tableau de points
	 */
	public static Polygon readPolygon(String jsonStr) {

        JSONArray jsonarray = new JSONArray(jsonStr);
        Point[] coords = new Point[jsonarray.length()];

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONArray jsonPoint = jsonarray.getJSONArray(i);
            coords[i] = new Point(jsonPoint.getDouble(0), jsonPoint.getDouble(1));
        }

		return new Polygon(coords);
	}

	/**
	 * Décode une chaîne (d'un POINT) au format JSON
	 * @param jsonStr la chaîne de caractère au format JSON
	 * @return le tableau de points
	 */
	public static Point readPoint(String jsonStr) {
		JSONArray jsonarray = new JSONArray(jsonStr);

		return new Point(jsonarray.getDouble(0), jsonarray.getDouble(1));
	}
}
