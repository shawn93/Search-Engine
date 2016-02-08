/**
 * Demonstrates several inheritance concepts, including basic inheritance,
 * upcasting and downcasting, nested classes, and anonymous classes.
 *
 * @see {@link ShapeDemo}
 * @see {@link Shape}
 * @see {@link Rectangle}
 * @see {@link Square}
 */
public abstract class Shape {
	/**
	 * Stores the name of the shape. Since it is protected, other classes
	 * can access this directly.
	 */
	protected String shapeName;

	/**
     * Constructor. Requires name of shape.
     *
     * @param shapeName name of shape
     */
    public Shape(String shapeName) {
        this.shapeName = shapeName;
    }

	/**
	 * Default constructor. Sets name of shape to "Shape" using this() to
	 * call the other constructor for this class.
	 */
	public Shape() {
		this("Shape");
	}

	/**
	 * Calculates and returns the area of the shape. Abstract, so it
	 * must be implemented by non-abstract subclasses.
	 *
	 * @return area of shape
	 */
	public abstract double area();

	/**
	 * Returns the name (circle, square, etc.) of the shape.
	 *
	 * @return name of shape
	 */
	public String getType() 	{
		return this.shapeName;
	}

	/**
	 * Returns a String representation of this object. Note the @Override
	 * annotation, indicating we are overriding a method provided by a
	 * superclass. You should always use this annotation when appropriate
	 * to avoid bugs!
	 *
	 * @return {@link String} representation of object
	 */
	@Override
	public String toString() {
		return getType();
	}
}
