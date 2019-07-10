package hw6;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import hw5.Graph;
import hw5.LabeledEdge;
import hw6.MarvelParser.MalformedDataException;

/** <b>MarvelPaths</b> finds the shortest path between two characters based on a provided 
 *  .tsv file. The program picks the lexicographically least path, that is the 
 *  lexicographically least character at each step and lexicographically least comic book 
 *  titles. User enters information such as start character, end character, 
 *  and whether or not the user wants to continue using the program.
 * 
 * @author Bryan Yue
 * @version 1.0
 */

public class MarvelPaths {
	// Note: MarvelPaths is not an ADT so no abstraction function or representational invariant
	// was specified.
    
    /**
	 * Takes user input of filename and runs program. Outputs time to build graph,
	 * total characters, and total connections. Outputs search runtime and shortest path.
	 *
	 * @param    args - ignore
	 * @throws   MalformedDataException if the file is not well-formed:
     *           each line contains exactly two tokens separated by a tab,
     *           or else starting with a # symbol to indicate a comment line. 
	 */
	public static void main(String[] args) throws MalformedDataException {
		System.out.println("Welcome to the Marvel Paths Program!");
		System.out.println("Output format: Character ---Book---> Character");
		System.out.println();
		System.out.println("Building graph...");
		long startTime = System.currentTimeMillis();
		// Builds graph and reports runtime
		Graph<String, String> g = buildGraph("src/hw6/data/marvel.tsv");
		System.out.println("Runtime of building graph: " + 
				(System.currentTimeMillis() - startTime) + " milliseconds");
		System.out.println("Total characters: " + g.numNodes());
		System.out.println("Total connections: " + g.numEdges());
		System.out.println();
		String answer = "y";
		// reports path based on user input
		Scanner scanner = new Scanner(System.in);
		while (answer.equals("y")) {
			System.out.print("Enter First Character: ");
			String character1 = scanner.nextLine();
			System.out.print( "Enter Second Character: " );
			String character2 = scanner.nextLine();
			if (!g.containsData(character1) || !g.containsData(character2)) {
				System.out.println();
				System.out.println("Invalid character. Please re-enter.");
			} else {
				startTime = System.currentTimeMillis();
				List<String> path = bfsShortestPath(character1, character2, g);
				System.out.println("Runtime of search: " + (System.currentTimeMillis() 
						- startTime) + " milliseconds");
				if (path == null) {
					System.out.println("No path exists between " + character1 + 
							" and " + character2);
					System.out.println();
				} else {
					printList(path);
				}
			}
			System.out.print("Another input? (y for yes): ");
			answer = scanner.nextLine();
			System.out.println();
		}
		scanner.close();
		System.out.println("Program terminated.");
	}
	
	/**
	 * Builds and returns a graph from the .tsv data.
	 *
	 * @param    fileName the String relative path file to build the graph from
	 * @throws   MalformedDataException if the file is not well-formed:
     *           each line contains exactly two tokens separated by a tab,
     *           or else starting with a # symbol to indicate a comment line. 
     * @requires file is present. 
	 * @return   A graph constructed from the .tsv data.
	 */
	public static Graph<String, String> buildGraph(String fileName) throws MalformedDataException {
		Set<String> characters = new HashSet<String>();
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser.parseData(fileName, characters, books);
		characters.remove(" ");
		characters.remove("");
		Graph<String, String> g = new Graph<String, String>();
		// adds nodes to graph
		for (String character : characters) {
			if (character.length() > 0) {
				g.addData(character);
			}
		}
		// adds edges to graph
		for (String book : books.keySet()) {
			// clean data by removing blanks, "", and duplicates
			List<String> heroes = books.get(book);
			removeBlanks(heroes);
			HashSet<String> removeDuplicates = new HashSet<String>(heroes);
			heroes = new ArrayList<String>(removeDuplicates);
			int length = heroes.size();
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					if (i != j) {
						g.connectData(heroes.get(i), heroes.get(j), book);
					}
				}
			}
		}
		return g;
	}
	
	/**
	 * Returns the shortest lexicographically least path between two nodes.
	 *
	 * @param    source the String label of the source node.
	 * @param    dest the String label of the destination node.
	 * @param    g the graph to find the shortest path on.
	 * @return   A String list path of node values from the source to destination node, 
	 *           formatted by [node1, path1, node2, path2, node3...] (lexicographically least). 
	 *           If the source or destination is null, returns null. Returns null also 
	 *           if source and destination nodes are not in the graph, or if no path is found 
	 *           between characters, or if graph is null. Returns empty list if same character is source and 
	 *           destination node.
	 */
	public static List<String> bfsShortestPath(String source, String dest, Graph<String, String> g) {
		if (source == null || dest == null || g == null || !g.containsData(source)
				|| !g.containsData(dest)) {
			return null;
		}
		// case where character is both source and destination
		if (source.equals(dest)) {
			return new ArrayList<String>();
		}
		Queue<String> nodeQueue = new LinkedList<String>();
		Map<String, String> from = new HashMap<String, String>();
		HashSet<String> seen = new HashSet<String>();
		nodeQueue.add(source);
		from.put(source,  null);
		String next = source;
		// bfs, storing node and previous node in from dictionary
		while (!nodeQueue.isEmpty()){
			String curr = nodeQueue.remove();
			if (!seen.contains(curr)) {
				seen.add(source);
				// sorts edges first by destination then by label, lexicographically least first.
				Set<LabeledEdge<String, String>> edges = new TreeSet<LabeledEdge<String, String>>();
				edges.addAll(g.getOutEdges(curr));
				for (LabeledEdge<String, String> edge : edges) {
					next = edge.getDestData();
				    if (!from.containsKey(next)) {
				    	nodeQueue.add(next);
				    	from.put(next, curr);
				    	// found destination
				    	if (next.equals(dest)) {
				    		return getShortestPath(g, from, dest);
				    	}
				    }
				}
			}
		}
		// destination not found
		return null;
	}
	
	/**
	 * Retrieves path from start to destination node.
	 *
	 * @param    g the graph that contains edge labels. 
	 * @param    from the map that contains node paths.
	 * @param    dest the destination character.
	 * @requires g, from, dest are not null.
	 * @returns  Shortest path between destination and start characters.
	 */
	// helper for bfsShortestPath
	private static List<String> getShortestPath(Graph<String, String> g, Map<String, 
			String> from, String dest) {	
		List<String> path = new ArrayList<String>();
		// start at end of path
		String curr = dest;
		while (curr != null) {
			// insert character at beginning of list
			path.add(0, curr);
			String later = curr;
			curr = from.get(curr);
			if (curr != null) {
				Set<LabeledEdge<String, String>> inEdges = new TreeSet<LabeledEdge<String, String>>();
				inEdges.addAll(g.getOutEdges(curr));
				// retrieves label that matches with next node in path
				for (LabeledEdge<String, String> edge : inEdges) {
					if (edge.getDestData().equals(later)){
						path.add(0, edge.getLabel());
						break;
					}
				}
			}
		}
		return path;
	}
	
	/**
	 * Removes empty and single space Strings from the list.
	 *
	 * @param    list the List to remove elements from. 
	 * @requires list is not null.
	 * @modifies list
	 * @effects  remove elements from list that are empty strings or single space strings.
	 */
	protected static void removeBlanks(List<String> list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).equals("") || list.get(i).equals(" ")) {
				list.remove(i);
			}
		}
	}
	
	/**
	 * Prints path from start to destination character. Alternates characters with
	 * book connections.
	 *
	 * @param    list the List to print
	 * @requires list is not null.
	 */
	private static void printList(List<String> list) {
		String rep = "";
		int characters = 0;
		int books = 0;
		for (int i = 0; i < list.size(); i++) {
			if (i % 2 == 0) {
				characters++;
				rep += (list.get(i));
			} else {
				books++;
				rep += ("  ---" + list.get(i) + "--->  "); 
			}
		}
		if (list.size() > 2) {
			System.out.println("Characters: " + characters + ", Books: " + books);		
		} else {
			System.out.println("Attemped to connect character to itself.");		
		}
		System.out.println("Shortest path: " + rep);
		System.out.println();
	}
}
