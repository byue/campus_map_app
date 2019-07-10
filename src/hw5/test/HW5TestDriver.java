package hw5.test;

import java.io.*;
import java.util.*;

import hw5.Graph;
import hw5.LabeledEdge;

/**
 * This class implements a testing driver which reads test scripts
 * from files for testing Graph.
 **/
public class HW5TestDriver {

    public static void main(String args[]) {
        try {
            if (args.length > 1) {
                printUsage();
                return;
            }

            HW5TestDriver td;

            if (args.length == 0) {
                td = new HW5TestDriver(new InputStreamReader(System.in),
                                       new OutputStreamWriter(System.out));
            } else {

                String fileName = args[0];
                File tests = new File (fileName);

                if (tests.exists() || tests.canRead()) {
                    td = new HW5TestDriver(new FileReader(tests),
                                           new OutputStreamWriter(System.out));
                } else {
                    System.err.println("Cannot read from " + tests.toString());
                    printUsage();
                    return;
                }
            }

            td.runTests();

        } catch (IOException e) {
            System.err.println(e.toString());
            e.printStackTrace(System.err);
        }
    }

    private static void printUsage() {
        System.err.println("Usage:");
        System.err.println("to read from a file: java hw5.test.HW5TestDriver <name of input script>");
        System.err.println("to read from standard in: java hw5.test.HW5TestDriver");
    }

    /** String -> Graph: maps the names of graphs to the actual graph **/
    //TODO for the student: Parameterize the next line correctly.
    private final Map<String, Graph<String, String>> graphs = 
    		new HashMap<String, Graph<String, String>>();
    private final PrintWriter output;
    private final BufferedReader input;

    /**
     * @requires r != null && w != null
     *
     * @effects Creates a new HW5TestDriver which reads command from
     * <tt>r</tt> and writes results to <tt>w</tt>.
     **/
    public HW5TestDriver(Reader r, Writer w) {
        input = new BufferedReader(r);
        output = new PrintWriter(w);
    }

    /**
     * @effects Executes the commands read from the input and writes results to the output
     * @throws IOException if the input or output sources encounter an IOException
     **/
    public void runTests()
        throws IOException
    {
        String inputLine;
        while ((inputLine = input.readLine()) != null) {
            if ((inputLine.trim().length() == 0) ||
                (inputLine.charAt(0) == '#')) {
                // echo blank and comment lines
                output.println(inputLine);
            }
            else
            {
                // separate the input line on white space
                StringTokenizer st = new StringTokenizer(inputLine);
                if (st.hasMoreTokens()) {
                    String command = st.nextToken();

                    List<String> arguments = new ArrayList<String>();
                    while (st.hasMoreTokens()) {
                        arguments.add(st.nextToken());
                    }

                    executeCommand(command, arguments);
                }
            }
            output.flush();
        }
    }

    private void executeCommand(String command, List<String> arguments) {
        try {
            if (command.equals("CreateGraph")) {
                createGraph(arguments);
            } else if (command.equals("AddNode")) {
                addNode(arguments);
            } else if (command.equals("AddEdge")) {
                addEdge(arguments);
            } else if (command.equals("ListNodes")) {
                listNodes(arguments);
            } else if (command.equals("ListChildren")) {
                listChildren(arguments);
            } else {
                output.println("Unrecognized command: " + command);
            }
        } catch (Exception e) {
            output.println("Exception: " + e.toString());
        }
    }

    private void createGraph(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to CreateGraph: " + arguments);
        }

        String graphName = arguments.get(0);
        createGraph(graphName);
    }

    private void createGraph(String graphName) {
        // Insert your code here.
    	Graph<String, String> g = new Graph<String, String>();
        graphs.put(graphName, g);
        output.println("created graph " + graphName);
    }

    private void addNode(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to addNode: " + arguments);
        }

        String graphName = arguments.get(0);
        String nodeName = arguments.get(1);

        addNode(graphName, nodeName);
    }

    private void addNode(String graphName, String nodeName) {
        // Insert your code here.
    	if (!graphs.containsKey(graphName)) {
    		output.println(graphName + " has not been created");
    		return;
    	} 
    	Graph<String, String> g = graphs.get(graphName);
    	if (g.containsData(nodeName)) {
    		output.println(graphName + " already contains node " + nodeName);
    	} else {
	        g.addData(nodeName);
	        output.println("added node " + nodeName + " to " + graphName);
    	}
    }

    private void addEdge(List<String> arguments) {
        if (arguments.size() != 4) {
            throw new CommandException("Bad arguments to addEdge: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        String childName = arguments.get(2);
        String edgeLabel = arguments.get(3);

        addEdge(graphName, parentName, childName, edgeLabel);
    }

    private void addEdge(String graphName, String parentName, String childName,
            String edgeLabel) {
        // Insert your code here.
    	if (!graphs.containsKey(graphName)) {
    		output.println(graphName + " has not been created");
    		return;
    	} 
    	Graph<String, String> g = graphs.get(graphName);
    	if (!g.containsData(parentName) || !g.containsData(childName)) {
    		output.println("nodes are not present in graph");
    		return;
    	}
    	LabeledEdge<String, String> a = 
    			new LabeledEdge<String, String>(parentName, childName, edgeLabel);
    	if (g.containsEdge(a)){
    		output.println(graphName + " already contains the edge");
    		return;
    	}
        g.connectData(parentName, childName, edgeLabel);
        output.println("added edge " + edgeLabel + " from " + parentName + " to " + childName + " in " + graphName);
    }

    private void listNodes(List<String> arguments) {
        if (arguments.size() != 1) {
            throw new CommandException("Bad arguments to listNodes: " + arguments);
        }

        String graphName = arguments.get(0);
        listNodes(graphName);
    }

    private void listNodes(String graphName) {
        // Insert your code here.
    	if (!graphs.containsKey(graphName)) {
    		output.println(graphName + " has not been created");
    	} else {
	        Graph<String, String> g = graphs.get(graphName);
	        Set<String> a = g.getAllNodeData();
	        Set<String> out = new TreeSet<String>();
	        for (String name : a) {
	        	out.add(name);
	        }
	        output.print(graphName + " contains:");
	        for(String n : out) {
	        	output.print(" " + n);
	        }
	        output.println();
    	}
    }

    private void listChildren(List<String> arguments) {
        if (arguments.size() != 2) {
            throw new CommandException("Bad arguments to listChildren: " + arguments);
        }

        String graphName = arguments.get(0);
        String parentName = arguments.get(1);
        listChildren(graphName, parentName);
    }

    private void listChildren(String graphName, String parentName) {
        // Insert your code here.
    	if (!graphs.containsKey(graphName)) {
    		output.println(graphName + " has not been created");
    		return;
    	} 
        Graph<String, String> g = graphs.get(graphName);
        if (!g.containsData(parentName)) {
        	output.println("nodes are not present in graph");
    		return;
        }
        Set<LabeledEdge<String, String>> edges = g.getOutEdges(parentName);
        Set<LabeledEdge<String, String>> childrenEdges = new TreeSet<LabeledEdge<String, String>>();
        childrenEdges.addAll(edges);
        output.print("the children of " + parentName + " in " + graphName + " are:");
        for (LabeledEdge<String, String> edge : childrenEdges) {
        	output.print(" " + edge.getDestData() + "(" + edge.getLabel() + ")");
        }
        output.println();
    }

    /**
     * This exception results when the input file cannot be parsed properly
     **/
    static class CommandException extends RuntimeException {

        public CommandException() {
            super();
        }
        public CommandException(String s) {
            super(s);
        }

        public static final long serialVersionUID = 3495;
    }
}
