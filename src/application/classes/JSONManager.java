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
	public static Point[] readPolygon(String jsonStr) {

        JSONArray jsonarray = new JSONArray(jsonStr);
        Point[] coords = new Point[jsonarray.length()];

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONArray jsonPoint = jsonarray.getJSONArray(i);
            coords[i] = new Point(jsonPoint.getDouble(0), jsonPoint.getDouble(1));
        }

		return coords;
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
	
	/**
	 * Encode un POLYGON au format JSON
	 * @param coords le tableau de points
	 * @return la chaîne de caractère au format JSON
	 */
	public static String writePolygon(Point[] coords) {
        JSONArray jsonArr = new JSONArray();

		for (Point point : coords) {
		    JSONArray pointArray = new JSONArray();
		    pointArray.put(point.x());
		    pointArray.put(point.y());
            jsonArr.put(pointArray);
		}

		return jsonArr.toString();
	}

	/**
	 * Encode un POINT au format JSON
	 * @param point le points
	 * @return la chaîne de caractère au format JSON
	 */
	public static String writePoint(Point point) {

        JSONArray jsonArr = new JSONArray();

        jsonArr.put(point.x());
        jsonArr.put(point.y());

        return jsonArr.toString();
	}
}
