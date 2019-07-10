package hw5;

/** <b>LabeledEdge</b> represents a immutable labeled edge 
 * connecting two vertices. 
 * 
<p>
* A LabeledEdge, e, can be notated by the triplet (d1, L, d2), where d1
* is the source vertex data, L is the label, and d2 is the destination vertex data. 
<p>
* @author Bryan Yue
* @version 1.0
*/

/**
 * @param <T> the node data comparable to other node data.
 * @param <E> the edge data comparable to other edge data.
 */
public class LabeledEdge<T extends Comparable<T>, E extends Comparable<E>> 
		implements Comparable<LabeledEdge<T, E>> {
	
	private T sourceData;
	private T destData;
	private E edgeLabel;
	
	// Abstraction Function:
	// For a given LabeledEdge e, "source node" is synonymous with e.source,
	// "destination node" is synonymous with e.dest, and "edge label" is
	// synonymous with e.label. 
	
	// Representational Invariant:
	// source != null && dest != null && label != null
	
	/**
	 * @param sourceData the T source node data of the LabeledEdge to be constructed.
	 * @param destData the T destination node data of the LabeledEdge to be constructed.
	 * @param label the E label of the LabeledEdge to be constructed.
	 * @requires sourceData != null && destData != null && edgeLabel != null
	 * @effects Constructs a new LabeledEdge with source node data, edge label,
	 *          and destination node data initialized.
     */
	public LabeledEdge(T sourceData, T destData, E edgeLabel) {
		this.sourceData = sourceData;
		this.destData = destData;
		this.edgeLabel = edgeLabel;
		checkRep();
	}
	
	/**
	 * Retrieves source data.
	 * 
	 * @requires sourceData != null 
	 * @return   T data of the source node
	 */
	public T getSourceData() {
		return sourceData;
	}
	
	/**
	 * Retrieves destination data.
	 * 
	 * @requires sourceData != null 
	 * @return   T data of the destination node
	 */
	public T getDestData() {
		return destData;
	}
	
	/**
	 * Retrieves edge label.
	 *
	 * @requires label != null
	 * @return   E label of the edge.
	 */
	public E getLabel() {
		return edgeLabel;
	}
	
    /**
	 * Standard hashCode function.
	 *
	 * @return an int that all objects equal to this will also return.
	 */
	@Override
    public int hashCode() {
        int hash = 1610612741;
        hash = (37 * hash) + sourceData.hashCode();
        hash = (37 * hash) + destData.hashCode();
        hash = (37 * hash) + edgeLabel.hashCode();
        return hash;
    }
	
	/** Standard equality operation.
	 * 
     * @param  obj The object to be compared for equality.
     * @return true if and only if 'obj' is an instance of a LabeledEdge
     *         and 'this' and 'obj' represent the same edge. 'this' and obj
     *         are only equal if all their fields are equal.
     */
	@Override
	public boolean equals(Object obj) {
	    if (obj == null || 
	    		!LabeledEdge.class.isAssignableFrom(obj.getClass())) {
	        return false;
	    }
		final LabeledEdge<?, ?> other = (LabeledEdge<?, ?>) obj;
	    if (sourceData.equals(other.getSourceData()) && destData.equals(other.getDestData())
	    		&& edgeLabel.equals(other.getLabel())) {
	    	return true;
	    }
	    return false;
	}
	
	/** Compares this and another LabeledEdge, first by destination node data and
	 *  secondarily by edge data. 
	 * 
     * @param  other the other LabeledEdge to be compared to.
     * @require other != null
     * @return 0 if the LabeledEdges are equal. Returns a negative number if the destination node 
     * data of this is less than that of other or the destination node data ties and the edge 
     * data of this is less than that of other. Returns a positive number if the destination node 
     * data of this is more than that of other or the destination node data ties and the edge data 
     * of this is more than that of other. 
     * 
     */
	@Override
	public int compareTo(LabeledEdge<T, E> other) {
		T destData1 = this.getDestData();
		T destData2 = other.getDestData();
		int val = destData1.compareTo(destData2);
		if (val == 0) {
			return (this.getLabel()).compareTo(other.getLabel());
		} 
		return val;
	}	
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (sourceData != null) : "source data should never be null.";
		assert (destData != null) : "destination data should never be null.";
		assert (edgeLabel != null) : "edge label should never be null.";
	}
}