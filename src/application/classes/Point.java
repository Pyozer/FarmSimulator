package application.classes;

/**
 * Une classe pour représenter une coordonnée GPS.
 * @author BE
 * @version 1.0
 */
public class Point {

	private double x, y; // coordonnées GPS

	/**
	 * @param x latitude
	 * @param y longitude
	 */
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * @return la latitude
	 */
	public double getX() {
		return this.x;
	}
	
	/**
	 * @return la longitude
	 */
	public double getY() {
		return this.y;
	}
	
	/**
	 * @return les coordonnées sous la forme (latitude, longitude)
	 */
	public String toString() {
		return "[" + x + "," + y + "]";
	}
}
