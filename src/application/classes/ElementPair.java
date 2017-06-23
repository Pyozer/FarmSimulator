package application.classes;

public class ElementPair {

	private Object elem1, elem2;


	public ElementPair(Object elem1, Object elem2) {
		this.elem1 = elem1;
		this.elem2 = elem2;
	}

	public String toString() {
		return elem1 + " : " + elem2;
	}
}
