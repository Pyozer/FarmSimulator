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
	
	/**
	 * Pour chaque couple de points du polygone, le segment correspondant est généré.<br>
	 * Pour chaque segment, le milieu est obtenu.<br>
	 * La droite passant par le milieu et le point p est obtenue.<br>
	 * Pour chaque autre segment, le point d'intersection est obtenu.<br>
	 * Le nombre de fois où le point p appartient au segment est comptabilisé.<br>
	 * Si ce nombre est impair, alors le point p n'appartient pas au polygone.
	 * @param p le point dont on veut déterminer l'appartenance au polygone
	 * @return vrai (le point appartient) ou faux (le point n'appartient pas)
	 */
	public boolean contains(Point p) {
		Segment[] s = new Segment[points.length]; // segments du polygone
		int       intersections;             // compteur d'intersections
		int       i, j;
		boolean   inside;
		
		// pour chaque point du polygone
		for (i = 0; i < points.length; i++)
			// on génère le segment avec le point adjacent
			s[i] = new Segment(points[i], points[(i + 1) % points.length]);
		
		inside = true; // on suppose que le polygone contient le point p
		
		i = 0;
		// pour chaque segment
		while (inside && (i < s.length)) {
			// on obtient le milieu du segment
			Point middle = s[i].middle();

			// on obtient la droite passant par le milieu et p
			StraightLine l = new StraightLine(middle, p);

			intersections = 0; // on compte le nombre d'intersections
			
			// pour chaque autre segment
			for (j = 0; j < s.length; j++)
				if (j != i) {
					// on obtient l'intersection de la droite et du segment j
					Point intersection = s[j].intersect(l);
					
					// y a t-il un point d'intersection ?
					if (intersection != null) {
						// oui, donc on obtient le segment entre le milieu et l'intersection
						Segment seg = new Segment(middle, intersection);
						
						// si le segment ne contient pas p
						if (! seg.contains(p))
							// on compte le nombre de fois où le segment ne contient pas p
							intersections++;
					}
				}

			// si ce nombre d'intersections ne contenant pas p est impair
			if (intersections % 2 == 1)
				// alors le point p n'appartient pas au polygone
				inside = false;
			
			i++; // passage au segment suivant
		}
		
		return inside;
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
