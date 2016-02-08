/**
 * Demonstrates several inheritance concepts, including basic inheritance,
 * upcasting and downcasting, nested classes, and anonymous classes.
 *
 * @see {@link ShapeDemo}
 * @see {@link Shape}
 * @see {@link Rectangle}
 * @see {@link Square}
 */
public class Square extends Rectangle {
	/*
	 * Since we can access protected objects, we can fix the shape name
	 * directly. (Even when it is in an indirect superclass!)
	 */

	public Square(double width) {
		super(width, width);
		shapeName = "Square";
	}

    public Square() {
        this(0.0);
    }
}
