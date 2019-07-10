package hw7;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import hw5.Graph;
import hw5.LabeledEdge;

/** <b>MinimumCostPathFinder</b> finds the minimum-cost path between two nodes on a weighted graph.
 *  Mininum cost path is defined as a path with the least overall weight, with ties broken by least
 *  path length. At each step the least next node value is chosen, and ties are broken with
 *  the least edge weights. 
 * 
 * @author Bryan Yue
 * @version 1.0
 */

public class MinimumCostPathFinder {
	// Note: MinimumCostPathFinder is not an ADT so no abstraction function or representational invariant
    // was specified.
	
	/**
	 * Finds the minimum-cost between two nodes, searching by lexicographically least destination
	 * and breaking ties with lowest edge weights. 
	 *
	 * @param    <T> the type of the node data
	 * @param    source the T label of the source node.
	 * @param    dest the T label of the destination node.
	 * @param    g the weighted graph to find the shortest path on.
	 * @return   the mininum-cost path from the source to destination character with associated weights.
	 * 			 Returns null if the source, dest, or g is null. Also returns null if the source or dest
	 *           is not in g. Also returns null if no path was found. If the source is the destination too,
	 *           a new empty path is returned.
	 */
	public static <T extends Comparable<T>> Path<T> djkPath(T source, T dest, Graph<T, Double> g) {
		if (source == null || dest == null || g == null || !g.containsData(source)
				|| !g.containsData(dest)) {
			return null;
		}
		if (source.equals(dest)) {
			return new Path<T>();
		}
		Queue<Path<T>> active = new PriorityQueue<Path<T>>();
		Set<T> finished = new HashSet<T>();
		// load path with source connected to itself with weight 0.0
		Path<T> path = new Path<T>(source);
		active.add(path);
		while (!active.isEmpty()) {
			Path<T> minPath = active.poll();
			ArrayList<LabeledEdge<T, Double>> edges = minPath.getPath();
			T minDest = edges.get(edges.size() - 1).getDestData();
			if (minDest.equals(dest)) {
				return minPath;
			}
			if (finished.contains(minDest)) {
				continue;
			}
			Set<LabeledEdge<T, Double>> outEdges = g.getOutEdges(minDest);
			for (LabeledEdge<T, Double> edge : outEdges) {
				if (!finished.contains(edge.getDestData())) {
					Path<T> newPath;
					// avoid adding self connected source edge
					if (minDest.equals(source)) {
						newPath = new Path<T>();
					} else {
						newPath = new Path<T>(minPath);
					}
					newPath.add(edge);
					active.add(newPath);
				}
			}
			finished.add(minDest);
		}
		return null;
	}
	
	/** <b>Path</b> represents a double weighted path from T source to T destination. 
	 * 
	 *  @param <T> the datatype of the source and destination
	 */
	public static class Path<T extends Comparable<T>> implements Comparable<Path<T>>  {
		private ArrayList<LabeledEdge<T, Double>> edges;
		private double totalCost;
		
		// Abstraction Function:
		// For a given Path<T> p, "edges" is synonymous with the path from source to destination, 
		// containing weights and intermediate nodes. Edges are ordered in the path from source to
		// destination. "totalCost" is synonymous with the total
		// weight of the path, and is the sum of all the individual weights of each edge.  
		
		// Representational Invariant:
		// edges != null && totalCost >= 0.0
		
		/**
		 * Creates a new empty Path.
		 * 
		 * @effects Constructs a new empty path with 0 weight.
	     */
		public Path() {
			edges = new ArrayList<LabeledEdge<T, Double>>();
			totalCost = 0.0;
			checkRepPath();
		}
		
		/**
		 * Creates a new Path from a source to itself with 0 weight.
		 * 
		 * @param source the source of the path.
		 * @requires source != null
		 * @effects Constructs a new empty path with 0 weight from the source to itself.
	     */
		public Path(T source) {
			edges = new ArrayList<LabeledEdge<T, Double>>();
			edges.add(new LabeledEdge<T, Double>(source, source, 0.0));
			totalCost = 0.0;
			checkRepPath();
		}
		
		/**
		 * Creates a new Path by copying contents from another path.
		 * 
		 * @param original the original path to be copied.
		 * @requires original != null
		 * @effects Constructs a new empty path with the original path and weight.
	     */
		public Path(Path<T> original) {
			edges = new ArrayList<LabeledEdge<T, Double>>(original.getPath());
			totalCost = original.getCost();
			checkRepPath();
		}
		
		/**
		 * Adds an edge to the path.
		 * 
		 * @param edge the LabeledEdge to be added to the path.
		 * @requires edge != null
		 * @modifies this
		 * @effects adds edge to the path and increments the total cost of the path by the edge cost.
	     */
		public void add(LabeledEdge<T, Double> edge) {
			totalCost += edge.getLabel();
			edges.add(edge);
			checkRepPath();
		}
		
		/**
		 * Retrieves the total cost of the path.
		 * 
		 * @return the total cost of the path.
	     */
		public double getCost() {
			return totalCost;
		}
		
		/**
		 * Retrieves the path.
		 * 
		 * @return the path.
	     */
		public ArrayList<LabeledEdge<T, Double>> getPath() {
			return new ArrayList<LabeledEdge<T, Double>>(edges);
		}

		/** Compares this and another Path, first by smallest cost then by smallest length.
		 * 
	     * @param  other the other Path to be compared to.
	     * @requires other != null
	     * @return 0 if the Paths are equal. Returns a negative number if the total cost of this
	     *         is less than the cost of other or the costs are equal and the length of this is less
	     *         than the length of other. Returns a positive number if the total cost of this is more
	     *         than the cost of other or the costs are equal and the length of this is more than the 
	     *         length of other.
	     */
		@Override
		public int compareTo(Path<T> other) {
			int val = new Double(this.getCost()).compareTo(new Double(other.getCost()));
			if (val == 0) {
				return this.getPath().size() - other.getPath().size();
			}
			return val;
		}
		
		/**
		 * Checks that the representation invariant holds.
		 */
		private void checkRepPath() {
			assert (edges != null) : "edges should never be null.";
			assert (totalCost >= 0.0) : "totalCost should never be negative.";
		}
	}

}
