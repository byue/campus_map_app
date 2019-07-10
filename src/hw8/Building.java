package hw8;

/** <b>Building</b> represents an immutable named building that has a location.
 * 
 * @author Bryan Yue
 * @version 1.0
 */
public class Building implements Comparable<Building> {
	private String shortName;
	private String fullName;
	private Coordinates coordinates;

	// Abstraction Function:
	// For a given Building b, b.shortName is synonymous with "abbreviated building name,"
	// b.fullName is synonymous with "full building name," and b.coordinates is synonymous with
	// "building coordinates."
	
	// Representational Invariant:
	// shortName != null && fullName != null && coordinates != null
	
	/**
	 * Creates a new Building.
	 * 
	 * @param shortName the String abbreviated name of this.
	 * @param fullName the String full name.
	 * @param xCoordinate the double x coordinate.
	 * @param yCoordinate the double y coordinate.
	 * @requires shortName != null && fullName != null && edgeLabel != null
	 * @effects Constructs this.
     */
	public Building (String shortName, String fullName, 
			double xCoordinate, double yCoordinate) {
		this.shortName = shortName;
		this.fullName = fullName;
		this.coordinates = new Coordinates(xCoordinate, yCoordinate);
		checkRep();
	}
	
	/**
	 * Retrieves the abbreviated name of the building.
	 * 
	 * @return the abbreviated String name of the building.
     */
	public String getShortName() {
		return shortName;
	}
	
	/**
	 * Retrieves the full name of the building.
	 * 
	 * @return the full String name of the building.
     */
	public String getFullName() {
		return fullName;
	}
	
	/**
	 * Retrieves the coordinates of the building.
	 * 
	 * @return the Coordinates of the building.
     */
	public Coordinates getCoordinates() {
		return coordinates;
	}
	
	/**
	 * Standard hashCode function.
	 *
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
    public int hashCode() {
        int hash = 1610612741;
        hash = (37 * hash) + shortName.hashCode();
        hash = (37 * hash) + fullName.hashCode();
        hash = (37 * hash) + coordinates.hashCode();
        return hash;
    }
	
	/** Standard equality operation.
	 * 
     * @param  obj The object to be compared for equality.
     * @return true if and only if 'obj' is an instance of a Building
     *         and 'this' and 'obj' represent the same Building. 'this' and obj
     *         are only equal if all their fields are equal.
     */
	@Override
	public boolean equals(Object obj) {
	    if (obj == null || 
	    		!Building.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final Building other = (Building) obj;
	    if (shortName.equals(other.getShortName()) && fullName.equals(other.getFullName())
	    		&& coordinates.equals(other.getCoordinates())) {
	    	return true;
	    }
	    return false;
	}
	
	/** Compares this and another Building, first by abbreviated name and
	 *  secondarily by full name. 
	 * 
     * @param   other the other Building to be compared to.
     * @require other != null
     * @return  0 if the Buildings are equal. Returns a negative number if the abbreviated name 
     *          of this is less than that of other or the abbreviated name ties and the full name 
     *          of this is less than that of other. Returns a positive number if the abbreviated 
     *          name data of this is more than that of other or the abbreviated name ties and the 
     *          full name of this is more than that of other. 
     */
	@Override
	public int compareTo(Building other) {
		int val = shortName.compareTo(other.getShortName());
		if (val == 0) {
			return fullName.compareTo(other.getFullName());
		} 
		return val;
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (shortName != null) : "short name should never be null.";
		assert (fullName != null) : "full name should never be null.";
		assert (coordinates != null) : "coordinates should never be negative.";
	}
}
