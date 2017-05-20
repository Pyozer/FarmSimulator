package application.classes;

/**
 * Une classe pour représenter un segment.
 * @author BE
 * @version 1.0
 */
public class Segment extends StraightLine {

	Point p1, p2; // coordonnées du segment

	/**
	 * @param p1 premier point
	 * @param p2 deuxième point
	 */
	public Segment(Point p1, Point p2) {
		super(p1, p2); // on contruit la droite associée au segment
		
		this.p1 = p1;
		this.p2 = p2;
	}
	
	/**
	 * @return le milieu du segment
	 */
	public Point middle() {
		return new Point((p1.x() + p2.x()) / 2, (p1.y() + p2.y()) / 2);
	}
	
	/**
	 * @param p le point dont on veut déterminer l'appartenance au segment
	 * @return vrai (le point appartient au segment)
	 *         faux (le point n'appartient pas au segment)
	 */
	public boolean contains(Point p) {
		if (p == null)
			return false;

		// les coordonnée du point p sont-elles comprises entre les extrémités du segment ?
		if (((p.x() >= p1.x()) && (p.x() <= p2.x()) || (p.x() >= p2.x()) && (p.x() <= p1.x())) &&
			((p.y() >= p1.y()) && (p.y() <= p2.y()) || (p.y() >= p2.y()) && (p.y() <= p1.y())))
			return true;
		else
			return false;
	}
	
	/**
	 * @param l la droite dont on veut déterminer l'intersection avec le segment
	 * @return le point d'intersection du segment avec la ligne
	 */
	public Point intersect(StraightLine l) {
		// obtention du point d'intersection de la droite du segment et de l
		Point i = super.intersect(l);
		
		// le segment contient-il ce point d'intersection
		if (this.contains(i))
			return i;    // oui
		else
			return null; // non
	}
	
	/**
	 * @return les extrémités du segment
	 */
	public String toString() {
		return this.p1.toString() + " - " + this.p2.toString();
	}
 }
