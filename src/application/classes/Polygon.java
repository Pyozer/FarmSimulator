package application.classes;

/**
 * Une classe pour représenter un polygone.
 * @author BE
 * @version 1.0
 */
public class Polygon {

	private Point[] points; // ensemble de points constituant le polygone
	
	/**
	 * @param points un tableau de points
	 */
	public Polygon(Point[] points) {
		this.points = points;
	}
	
	/**
	 * @return un tableau des points du polygone
	 */
	public Point[] getPoints() {
		return this.points;
	}

	public Point getCenter() {
		double x = 0.;
		double y = 0.;

		int pointCount = points.length;
		for (int i = 0;i < pointCount - 1;i++){
			final Point point = points[i];
			x += point.x();
			y += point.y();
		}

		x = x/pointCount;
		y = y/pointCount;

		return new Point(x, y);
	}
	
	/**
	 * @return l'ensemble des points de la droite
	 */
	public String toString() {
		StringBuilder result = new StringBuilder("[");

		result.append(points[0]);

		for (int i = 1; i < points.length; i++) {
            result.append(",");
            result.append(points[i].toString());
        }
		result.append("]");
		return result.toString();
	}
}
