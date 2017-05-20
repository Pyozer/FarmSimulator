package application.classes;

/**
 * Une classe pour représenter une droite.
 * @author BE
 * @version 1.0
 */
public class StraightLine {

	private double a, b, c; // coefficient de la droite d'équation ax + by = c
	
	/**
	* @param p1
	* premier point
	* @param p2
	* deuxième point
	*/
	public StraightLine(Point p1, Point p2) {
		double d = p1.x() * p2.y() - p2.x() * p1.y() ; // déterminant
		
		if (d != 0) {
			this.a = (p2.y() - p1.y()) / d; // calcul du coefficient a
			this.b = (p1.x() - p2.x()) / d; // calcul du coefficient b
			this.c = 1;                     // calcul du coefficient c
		}
		else {
			if ((p1.x() == p2.x()) && (p1.y() == p2.y()))
				// les 2 points ne sont pas distincts
				this.a = this.b = this.c = 0;
			else
				if ((p1.x() == 0) && (p1.y() == 0)) {
					// le point p1 est (0, 0)
					this.a = p2.y();
					this.b = -p2.x();
					this.c = 0;
				}
				else {
					// le point p2 est (0, 0)
					this.a = p1.y();
					this.b = -p1.x();
					this.c = 0;
				}
		}
	}

	/**
	 * @return
	 * le coefficient a
	 */
	public double a() {
		return a;
	}

	/**
	 * @return
	 * le coefficient b
	 */
	public double b() {
		return b;
	}

	/**
	 * @return
	 * le coefficient c
	 */
	public double c() {
		return c;
	}
	
	/**
	 * Calcule de l'intersection de 2 droites
	 * @param l
	 * une droite
	 * @return
	 * le point d'intersection des 2 droites
	 */
	public Point intersect(StraightLine l) {
		double d = this.a * l.b() - l.a() * this.b; // déterminant
		double x, y;                                // coordonnées du point d'intersection
		
		if (d == 0)
			return null;
		else {
			x = (this.c * l.b() - l.c() * this.b) / d;
			y = (this.a * l.c() - l.a() * this.c) / d;
			return new Point(x, y);
		}		
	}

	/**
	 * @return
	 * l'équation de la droite
	 */
	public String toString() {
		
		return this.a + "x +" + this.b + "y = 1";
	}
}
