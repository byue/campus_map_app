package hw7;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import hw5.Graph;
import hw5.LabeledEdge;
import hw6.MarvelParser;
import hw6.MarvelParser.MalformedDataException;
import hw7.MinimumCostPathFinder.Path;

/** <b>MarvelPaths2</b> finds the minimum-cost path between two characters on a weighted graph
 *  based on a provided .tsv file. Weights between characters are based on how many books they appear
 *  in together. The more books they appear in together, the lower the weight is. Mininum cost path
 *  is defined as the path with the least total weight, with ties broken by least path length.
 *  The program traverses the lexicographically least path, that is the 
 *  lexicographically least next character at each step and breaks ties with
 *  the least edge weights. User enters information such as start character, end character, 
 *  and whether or not the user wants to continue using the program.
 * 
 * @author Bryan Yue
 * @version 1.0
 */

public class MarvelPaths2 {
	// Note: MarvelPaths2 is not an ADT so no abstraction function or representational invariant
	// was specified.
	
    /**
	 * Takes user input of filename and runs program. Outputs time to build graph,
	 * total characters, and total connections. Outputs search runtime and minimum-cost path.
	 *
	 * @param    args - ignore
	 * @throws   MalformedDataException if the file is not well-formed:
     *           each line contains exactly two tokens separated by a tab,
     *           or else starting with a # symbol to indicate a comment line. 
	 */
	public static void main(String[] args) throws MalformedDataException {
		System.out.println("Welcome to the Marvel Paths2 Program!");
		System.out.println("Output format: Character ---Weight---> Character");
		System.out.println();
		System.out.println("Building graph...");
		long startTime = System.currentTimeMillis();
		// Builds weighted graph and reports runtime
		Graph<String, Double> g = buildGraph("src/hw7/data/marvel.tsv");
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
			System.out.print( "Enter Second Character: ");
			String character2 = scanner.nextLine();
			if (!g.containsData(character1) || !g.containsData(character2)) {
				System.out.println();
				System.out.println("Invalid character. Please re-enter.");
			} else {
				// compute and report mininum-cost path
				startTime = System.currentTimeMillis();
				Path<String> path = MinimumCostPathFinder.djkPath(character1, character2, g);
				System.out.println("Runtime of search: " + (System.currentTimeMillis() 
						- startTime) + " milliseconds");
				if (path == null) {
					System.out.println("No path exists between " + character1 + 
							" and " + character2);
					System.out.println();
				} else {
					printPath(path);
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
	 * Builds and returns a weighted graph from the .tsv data.
	 *
	 * @param    fileName the String relative path file to build the graph from
	 * @throws   MalformedDataException if the file is not well-formed:
     *           each line contains exactly two tokens separated by a tab,
     *           or else starting with a # symbol to indicate a comment line. 
     * @requires file is present. 
	 * @return   A graph constructed from the .tsv data.
	 */
	public static Graph<String, Double> buildGraph(String fileName) throws MalformedDataException {
		Set<String> characters = new HashSet<String>();
		Map<String, List<String>> books = new HashMap<String, List<String>>();
		MarvelParser.parseData(fileName, characters, books);
		characters.remove(" ");
		characters.remove("");
		Graph<String, Double> g = new Graph<String, Double>();
		// adds nodes to graph
		for (String character : characters) {
			if (character.length() > 0) {
				g.addData(character);
			}
		}
		Map<Tuple<String>, Integer> pairTallies = new HashMap<Tuple<String>, Integer>();		
		for (String book : books.keySet()) {
			// clean data by removing blanks, "", and duplicates
			List<String> heroes = books.get(book);
			if (heroes.contains("")) {
				heroes.remove("");
			}
			if (heroes.contains(" ")) {
				heroes.remove(" ");
			}
			HashSet<String> removeDuplicates = new HashSet<String>(heroes);
			heroes = new ArrayList<String>(removeDuplicates);
			int length = heroes.size();
			// compute how many books each pair of connected characters appears in
			for (int i = 0; i < length; i++) {
				for (int j = 0; j < length; j++) {
					if (i != j) {
						Tuple<String> pair = new Tuple<String>(heroes.get(i), heroes.get(j));
						if (!pairTallies.containsKey(pair)) {
							pairTallies.put(pair, 1);
						} else {
							pairTallies.put(pair, pairTallies.get(pair) + 1);
						}
					}
				}
			}
		}
		// connect characters in graph with weighted edges
		for (Tuple<String> pair : pairTallies.keySet()) {
			double weight = 1.0 / pairTallies.get(pair);
			String character1 = pair.getFirst();
			String character2 = pair.getSecond();
			g.connectData(character1, character2, weight);
			g.connectData(character2, character1, weight);
		}
		return g;
	}
	
	/**
	 * Prints the path from start to destination character, associated costs, and total cost.
	 * 
	 * @param    path the Path<String> to print
	 * @requires path is not null.
	 */
	private static void printPath(Path<String> path) {
		int characters = 1;
		int edges = 0;
		ArrayList<LabeledEdge<String, Double>> edgeList = path.getPath();
		System.out.println("Least-cost path: ");
		for (LabeledEdge<String, Double> edge : edgeList) {
			characters++;
			edges++;
			System.out.println(edge.getSourceData() + "  ---" + String.format("%.3f", edge.getLabel())
					+ "--->  " + edge.getDestData());
		}
		if (edgeList.size() > 0) {
			System.out.println("Characters: " + characters + ", Edges: " + edges);		
		} else {
			System.out.println("Attemped to connect character to itself.");		
		}
		System.out.println("Total Cost: " + String.format("%.3f", path.getCost()));
		System.out.println();
	}
	
	
	/** <b>Tuple<T></b> represents an unordered pair of the same datatype.
	 * 
	 *  @param <T> the datatype of the Tuple
	 */
	public static class Tuple<T> {
		private T first;
		private T second;
		
		// Abstraction Function:
		// For a given Tuple<T> t, "first" is synonymous with an element of the tuple
		// and "second" is synonymous with an element of the tuple, where ordering does
		// not matter. 
		
		// Representational Invariant:
		// first != null && second != null
		
		/**
		 * Constructs a tuple with two elements.
		 * 
		 * @effects Constructs a new tuple with two elements.
	     */
		public Tuple(T first, T second) {
			this.first = first;
			this.second = second;
			checkRepTuple();
		}
		
		/**
		 * Retrieves an element of the tuple.
		 * 
		 * @return a T element of the tuple.
		 */
		private T getFirst() {
			return first;
		}
		
		/**
		 * Retrieves an element of the tuple.
		 * 
		 * @return a T element of the tuple.
		 */
		private T getSecond() {
			return second;
		}
		
		/**
		 * Standard hashCode function.
		 * 
		 * @requires elements of tuple are not null
		 * @return an int that all objects equal to this will also return.
		 */
		@Override
	    public int hashCode() {
	        int hash = 1610612741;
	        hash = (37 * hash) + first.hashCode();
	        hash = (37 * hash) + second.hashCode();
	        return hash;
	    }
		
		/**
		 *  Standard equality operation.
		 *  
	     *  @param  obj The object to be compared for equality.
	     *  @return true if and only if 'obj' is an instance of a Tuple
	     *         and if they contain the same pair of of fields, where
	     *         ordering does not matter.
	     */
		@Override
		public boolean equals(Object obj) {
		    if (obj == null || 
		    		!Tuple.class.isAssignableFrom(obj.getClass())) {
		        return false;
		    }
			final Tuple<?> other = (Tuple<?>) obj;
		    if ((first.equals(other.getFirst()) && second.equals(other.getSecond())) || 
		    		(first.equals(other.getSecond()) && second.equals(other.getFirst()))) {
		    	return true;
		    }
		    return false;
		}
		
		/**
		 * Checks that the representation invariant holds.
		 */
		private void checkRepTuple() {
			assert (first != null) : "elements should never be null.";
			assert (second != null) : "elements should never be null.";
		}
	}
	
}
