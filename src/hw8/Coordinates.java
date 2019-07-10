package hw8;

/** <b>Coordinates/b> represents an immutable coordinate pair.
 * 
 * @author Bryan Yue
 * @version 1.0
 */
public class Coordinates implements Comparable<Coordinates> {
	private double xCoordinate;
	private double yCoordinate;
	
	// Abstraction Function:
	// For a given Coordinates c, c.xCoordinate is synonymous with "x coordinate" and
	// c.yCoordinate is synonymous with "y coordinate". 
	
	// Representational Invariant:
	// xCoordinate != NaN && yCoordinate != NaN

	/**
	 * Creates a new Coordinate.
	 * 
	 * @param xCoordinate the double x coordinate.
	 * @param yCoordinate the double y coordinate.
	 * @effects Constructs this.
     */
	public Coordinates(double xCoordinate, double yCoordinate) {
		this.xCoordinate = xCoordinate;
		this.yCoordinate = yCoordinate;
		checkRep();
	}
	
	/**
	 * Retrieves x coordinate.
	 * 
	 * @return double x coordinate.
     */
	public double getX() {
		return xCoordinate;
	}
	
	/**
	 * Retrieves y coordinate.
	 * 
	 * @return double y coordinate.
     */
	public double getY() {
		return yCoordinate;
	}
	
	/**
	 * Standard hashCode function.
	 *
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
    public int hashCode() {
        int hash = 1610612741;
        hash = (37 * hash) + ((Double) xCoordinate).hashCode();
        hash = (37 * hash) + ((Double) yCoordinate).hashCode();
        return hash;
    }
	
	/** Standard equality operation.
	 * 
     * @param  obj The object to be compared for equality.
     * @return true if and only if 'obj' is an instance of a Coordinates
     *         and 'this' and 'obj' represent the same Coordinates. 'this' and obj
     *         are only equal if all their fields are equal.
     */
	@Override
	public boolean equals(Object obj) {
	    if (obj == null || 
	    		!Coordinates.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final Coordinates other = (Coordinates) obj;
		Double thisX = (Double) xCoordinate;
		Double otherX = (Double) other.getX();
		Double thisY = (Double) yCoordinate;
		Double otherY = (Double) other.getY();
	    if (thisX.equals(otherX) && thisY.equals(otherY)) {
	    	return true;
	    }
	    return false;
	}

	/** Compares this and another Coordinates by magnitude of the coordinate (distance from origin).
	 *  If the coordinate is represented by (x, y) the magnitude is root(x^2 + y^2).
	 * 
     * @param   other the other Coordinates to be compared to.
     * @require other != null
     * @return  0 if the magnitude of this and other are the same, a negative number if the magnitude
     *          of this is less than that of other, or a positive number of the magnitude of this is
     *          more than that of other.
     */
	@Override
	public int compareTo(Coordinates other) {
		double thisMag = Math.sqrt((xCoordinate * xCoordinate) + (yCoordinate * yCoordinate));
		double otherMag = Math.sqrt((other.getX() * other.getX()) + (other.getY() * other.getY()));
		return ((Double) thisMag).compareTo((Double) otherMag);
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (!((Double) xCoordinate).equals(Double.NaN)) : 
			"xCoordinate should never be not a number..";
		assert (!((Double) yCoordinate).equals(Double.NaN)) : 
			"yCoordinate should never be not a number..";
	}
}