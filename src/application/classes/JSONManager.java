package application.classes;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONStringer;
import org.json.JSONTokener;

/**
 * Une classe pour encoder et décoder au format JSON<br>
 * un tableau de points (coordonnées GPS)<br>
 * {"polygon":[[48.752110999990684,-3.013601999994632],[48.750417999985736,-3.013448999997121]]}
 * @author BE
 * @version 1.0
 */
public class JSONManager {
	
	/**
	 * Décode une chaîne au format JSON
	 * @param jsonStr la chaîne de caractère au format JSON
	 * @return le tableau de points
	 */
	public static Point[] read(String jsonStr) {

        JSONArray jsonarray = new JSONArray(jsonStr);
        Point[] coords = new Point[jsonarray.length()];

        for (int i = 0; i < jsonarray.length(); i++) {
            JSONArray jsonPoint = jsonarray.getJSONArray(i);
            coords[i] = new Point(jsonPoint.getDouble(0), jsonPoint.getDouble(1));
        }

		return coords;
	}
	
	/**
	 * Encode une chaîne au format JSON
	 * @param coords le tableau de points
	 * @return la chaîne de caractère au format JSON
	 */
	public static String write(Point[] coords) {
        JSONArray jsonArr = new JSONArray();

		for (Point point : coords) {
		    JSONObject jsObj = new JSONObject();
		    jsObj.put("lat", point.x());
		    jsObj.put("lng", point.y());
            jsonArr.put(jsObj);
		}

		return jsonArr.toString();
	}

	/**
	 * Encode une chaîne au format JSON
	 * @param point le points
	 * @return la chaîne de caractère au format JSON
	 */
	public static String write(Point point) {

        JSONArray jsonArr = new JSONArray();

        JSONObject jsObj = new JSONObject();
        jsObj.put("lat", point.x());
        jsObj.put("lng", point.y());

        jsonArr.put(jsObj);

        return jsonArr.toString();
	}
}
