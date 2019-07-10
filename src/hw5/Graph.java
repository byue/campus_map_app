package hw5;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** <b>Graph</b> represents a mutable directed, labeled, multi-graph.
 * Graph is a collection of nodes and edges. Each graph can be described
 * by a set of vertices {v1, v2, ..., vn} and a set of incoming/outgoing edges 
 * {(v1, v2), (v3, v4), ..., (vn, vm)}. Each edge (va, vb) is labeled and
 * is directed, going from va to vb (one-way). As a multi-graph, multiple edges
 * are allowed between two nodes. Nodes can have no edges, an edge, or multiple edges,
 * and are labeled. Edges can connect a node to itself. The graph does not support 
 * multiple edges between the same nodes with the same label. 
 * 
 * @author Bryan Yue
 * @version 2.0 (generics added)
 */

/**
 * @param <T> the node data comparable to other node data.
 * @param <E> the edge data comparable to other edge data.
 */
public class Graph<T extends Comparable<T>, E extends Comparable<E>> {	
	private static final boolean CHECK_VALID_EDGES = false;
	private Map<T, AllEdges> graph;
	private Set<LabeledEdge<T, E>> edges;
	
	// Abstraction Function:
	// For a given Graph g, "graph" is synonymous of g.graph, which is a map
	// of node labels to sets of edges. "all edges" is synonymous with "g.edges".
	// (used for quickly accessing if an edge exists/number of edges).
	
	// Representational Invariant:
	// graph != null && edges !=  null.
	// For all edges e in graph.values(), both the source and destination nodes must
	// be valid nodes in the graph, and there are no duplicate edges with identical fields.
	// (automatically handled by set usage). Each node label in graph does
	// not have a null edge set. 
	
	/**
	 * Constructs an empty graph.
	 * 
	 * @effects Constructs a new graph with no edges or nodes.
     */
	public Graph() {
		this.graph = new HashMap<T, AllEdges>();
		this.edges = new HashSet<LabeledEdge<T, E>>();
		checkRep();
	}
	
	/**
	 * Constructs a graph with a single node.
	 * 
	 * @param nodeData the T label of the graph node to be constructed.
	 * @requires nodeData != null, nodeData will not be mutated
	 * @effects Constructs a new graph with a single node with no edges.
	 *         
     */
	public Graph(T nodeData) {
		this();
		addData(nodeData);
		checkRep();
	}
	
	/**
	 * Constructs a graph with several disconnected nodes.
	 * 
	 * @param nodeDataList the T label list of graph nodes to be constructed.
	 * @requires nodeDataList != null, contents of nodeDataList will not be mutated
	 * @effects Constructs a new graph with a series of disconnected nodes.
     */
	public Graph(List<T> nodeDataList) {
		this();
		for (T nodeData : nodeDataList) {
			addData(nodeData);
		}
		checkRep();
	}
	
    /**
	 * Adds a new node to the graph.
	 *
	 * @param    nodeData the T label of the graph node to be added.
	 * @modifies graph
	 * @effects  adds node to graph if graph does not have node already and node is not null.
	 * @requires nodeData will not be mutated
	 * @returns  true if node was added to graph, false if graph has node already or node is null.
	 */
	public boolean addData(T nodeData) {
		if (nodeData == null || containsData(nodeData)){
			return false;
		}
		graph.put(nodeData, new AllEdges());
		checkRep();
		return true;
	}
	
	/**
	 * Connects two nodes by a labeled and directed edge.
	 *
	 * @param    sourceData the T label of the source node.
	 * @param    destData the T label of the destination node.
	 * @param    label the E label of the edge.
	 * @modifies graph, edges
	 * @effects  creates a new edge with (source node, destination node, node label).
	 *           Adds new edge to graph if same edge does not already exist in the graph, 
	 *           if node data is not null, and if label is not null.
	 * @requires sourceData, destData, and label will not be mutated
	 * @returns  true if the edge was added successfully, false if node data are null
	 *           , label is null, or nodes are not present in the graph, or if edge is
	 *           already present in the graph.
	 */
	public boolean connectData(T sourceData, T destData, E label) {
		if (sourceData == null || destData == null || label == null 
				|| !containsData(sourceData) || !containsData(destData)) {
			return false;
		} 
		LabeledEdge<T, E> edge = new LabeledEdge<T, E>(sourceData, destData, label);
		(graph.get(destData)).getInEdges().add(edge);
		(graph.get(sourceData)).getOutEdges().add(edge);
		boolean added = edges.add(edge);
		checkRep();
		return added;
	}
	
	/**
	 * Reports if two nodes are connected.
	 *
	 * @param    data1 the T data of the first node.
	 * @param    data2 the T data of the second node.
	 * @returns  true if the two nodes are connected by an edge, false otherwise.  
	 *           Returns false if either node data is null or the graph does not have the nodes.
	 */
	public boolean isConnected(T data1, T data2) {
		if (data1 == null || data2 == null || !containsData(data1) || !containsData(data2)) {
			return false;
		}
		for (LabeledEdge<T, E> edge : getOutEdges(data1)) {
			if (edge.getDestData().equals(data2)) {
				return true;
			}
		}
		for (LabeledEdge<T, E> edge : getInEdges(data1)) {
			if (edge.getSourceData().equals(data2)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * Retrieves outgoing edges of a node as an unsorted set.
	 * 
	 * @param    nodeData the T data of the node edges will be reported from.
	 * @return   an unsorted unmodifiable set of outgoing LabeledEdges from the GraphNode.
	 *           Returns null if nodeData is null or if graph does not contain nodeData.
     */
	public Set<LabeledEdge<T, E>> getOutEdges(T nodeData) {
		if (nodeData == null || !containsData(nodeData)) {
			return null;
		}
		return Collections.unmodifiableSet((graph.get(nodeData)).getOutEdges());
	}
	
	/**
	 * Retrieves incoming edges of a node as an unsorted set.
	 * 
	 * @param    nodeData the T data of the node edges will be reported from.
	 * @return   an unsorted unmodifiable set of incoming LabeledEdges from the GraphNode.
	 *           Returns null if nodeData is null or if graph does not contain nodeData.
     */
	public Set<LabeledEdge<T, E>> getInEdges(T nodeData) {
		if (nodeData == null || !containsData(nodeData)) {
			return null;
		}
		return Collections.unmodifiableSet((graph.get(nodeData)).getInEdges());
	}
	
	/**
	 * Reports if the graph contains a node with the label.
	 *
	 * @param    nodeData the T label of the node.
	 * @return   true if and only if the graph contains a node with
	 *           the label, false otherwise. Returns false if node is null.
	 */
	public boolean containsData(T nodeData) {
		if (nodeData == null) {
			return false;
		}
		return graph.containsKey(nodeData);
	}
	
	/**
	 * Reports if the graph contains the edge.
	 *
	 * @param    edge the LabeledEdge edge to be searched in the graph.
	 * @return   true if and only if the graph contains the edge,
	 *           false otherwise. Returns false if edge is null.
	 */
	public boolean containsEdge(LabeledEdge<T, E> edge) {
		if (edge == null) {
			return false;
		}
		return edges.contains(edge);
	}
	
	/**
	 * Reports if the graph node contains the edge.
	 * 
	 * @param    nodeData the T data of the node we are searching at.
	 * @param    edge the LabeledEdge edge of the GraphNode to search for.
	 * @return   true iff the node contains the edge, false otherwise. Returns
	 *           false if either the nodeData or edge is null.
     */
	public boolean containsEdge(T nodeData, LabeledEdge<T, E> edge) {
		if (nodeData == null || edge == null) {
			return false;
		}
		return (graph.get(nodeData)).getOutEdges().contains(edge) ||
		(graph.get(nodeData)).getInEdges().contains(edge);
	}

	/**
	 * Retrieves all node data from the graph.
	 *
	 * @return   an unsorted unmodifiable T set containing node data in the graph.
	 */
	public Set<T> getAllNodeData() {
		return  Collections.unmodifiableSet(graph.keySet());
	}
	
	/**
	 * Retrieves all edges from the graph.
	 *
	 * @return  an unsorted unmodifiable LabeledEdge set.
	 */
	public Set<LabeledEdge<T, E>> getAllEdges() {
		return Collections.unmodifiableSet(edges);
	}
	
	/**
	 * Reports number of outgoing edges in a node.
	 * 
	 * @param    nodeData the T data of the node of interest.
	 * @requires nodeData != null && the node is in the graph.
	 * @return an int equal to the number of outgoing edges.
	 */
	public int numOutEdges(T nodeData) {
		return (graph.get(nodeData)).getOutEdges().size();
	}
	
	/**
	 * Reports number of incoming edges in a node.
	 * 
	 * @param    nodeData the T data of the node of interest.
	 * @requires nodeData != null && the node is in the graph.
	 * @return an int equal to the number of incoming edges.
	 */
	public int numInEdges(T nodeData) {
		return (graph.get(nodeData)).getInEdges().size();
	}
	
	/**
	 * Reports the number of nodes in the graph.
	 *
	 * @return   the number of nodes in the graph as an int.
	 */
	public int numNodes() {
		return graph.size();
	}
	
	/**
	 * Reports the number of edges in the graph.
	 *
	 * @return   the number of edges in the graph as an int.
	 */
	public int numEdges() {
		return edges.size();
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (graph != null) : "graph should never be null.";
		assert (edges != null) : "edges should never be null.";
		if (CHECK_VALID_EDGES) {
			Set<LabeledEdge<T, E>> labels = getAllEdges();
			for (LabeledEdge<T, E> label : labels) {
				if (!containsData(label.getDestData()) 
						|| !containsData(label.getSourceData())) {
					assert (false) : "edge is invalid";
				}
			}
		}
	}
	
	/** <b>AllEdges</b> represents all the edges of a node, including outgoing
	 *  and incoming edges. Note: AllEdges is mutable only through connectData() in Graph.
	 */
	private class AllEdges {
		private Set<LabeledEdge<T, E>> out;
		private Set<LabeledEdge<T, E>> in;
		
		// Abstraction Function:
		// For a given AllEdges a, "outgoing edges" is synonymous with a.out.
		// "Incoming edges" is synonymous with a.in.
		
		// Representational Invariant:
		// out != null && in != null
		
		/**
		 * Creates a new AllEdges with no edges.
		 * 
		 * @effects Constructs a new AllEdges with no outoging or incoming edges.
	     */
		private AllEdges() {
			this.out = new HashSet<LabeledEdge<T, E>>();
			this.in = new HashSet<LabeledEdge<T, E>>();
			checkRepInner();
		}
		
		/**
		 * Retrieves outgoing edges of node.
		 * 
		 * @requires node data is not null && the node is in the graph.
		 * @return a LabeledEdge set containing the out edges.
		 */
		private Set<LabeledEdge<T, E>> getOutEdges() {
			// NOTE: Set is not a copy and is mutable. Only the implementor can access.
			return out;
		}
		
		/**
		 * Retrieves incoming edges of node.
		 * 
		 * @requires node data is not null && the node is in the graph.
		 * @return a LabeledEdge set containing the in edges.
		 */
		private Set<LabeledEdge<T, E>> getInEdges() {
			// NOTE: Set is not a copy and is mutable. Only the implementor can access. 
			return in;
		}
		
		/**
		 * Checks that the representation invariant holds.
		 */
		private void checkRepInner() {
			assert (in != null) : "in should never be null.";
			assert (out != null) : "out should never be null.";
		}
	}
}
