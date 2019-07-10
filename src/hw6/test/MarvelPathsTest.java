package hw6.test;

import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Before;
import org.junit.Test;
import hw5.Graph;
import hw5.LabeledEdge;
import hw6.MarvelParser.MalformedDataException;
import hw6.MarvelPaths;
import hw6.test.CheckAsserts;

public class MarvelPathsTest {
///////////////////////////////////////////////////////////////////////////////////////
////Setup
///////////////////////////////////////////////////////////////////////////////////////
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}

///////////////////////////////////////////////////////////////////////////////////////
//// Edge Comparator Test
///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void checkComparator() {
		LabeledEdge<String, String> edge1 = new LabeledEdge<String, String>("a", "g", "i");
		LabeledEdge<String, String> edge2 = new LabeledEdge<String, String>("a", "m", "j");
		LabeledEdge<String, String> edge3 = new LabeledEdge<String, String>("a", "q", "j");
		LabeledEdge<String, String> edge4 = new LabeledEdge<String, String>("a", "g", "m");
		LabeledEdge<String, String> edge5 = new LabeledEdge<String, String>("a", "a", "m");
		LabeledEdge<String, String> edge6 = new LabeledEdge<String, String>("a", "g", "a");
		LabeledEdge<String, String> edge7 = new LabeledEdge<String, String>("a", "g", "z");
		Set<LabeledEdge<String, String>> edges = new TreeSet<LabeledEdge<String, String>>();
		List<LabeledEdge<String, String>> listVerify = new ArrayList<LabeledEdge<String, String>>();
		// added one edge
		edges.add(edge1);
		listVerify.add(edge1);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));
		// added 2 edges
		listVerify.clear();
		edges.add(edge2);
		listVerify.add(edge1);
		listVerify.add(edge2);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));	
		// added 3 edges
		listVerify.clear();
		edges.add(edge3);
		listVerify.add(edge1);
		listVerify.add(edge2);
		listVerify.add(edge3);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));
		// added 4 edges
		listVerify.clear();
		edges.add(edge4);
		listVerify.add(edge1);
		listVerify.add(edge4);
		listVerify.add(edge2);
		listVerify.add(edge3);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));
		// added 5 edges
		listVerify.clear();
		edges.add(edge5);
		listVerify.add(edge5);
		listVerify.add(edge1);
		listVerify.add(edge4);
		listVerify.add(edge2);
		listVerify.add(edge3);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));
		// added 6 edges
		listVerify.clear();
		edges.add(edge6);
		listVerify.add(edge5);
		listVerify.add(edge6);
		listVerify.add(edge1);
		listVerify.add(edge4);
		listVerify.add(edge2);
		listVerify.add(edge3);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));
		// added 7 edges
		listVerify.clear();
		edges.add(edge7);
		listVerify.add(edge5);
		listVerify.add(edge6);
		listVerify.add(edge1);
		listVerify.add(edge4);
		listVerify.add(edge7);
		listVerify.add(edge2);
		listVerify.add(edge3);
		assertEquals(listVerify, new ArrayList<LabeledEdge<String, String>> (edges));
	}

///////////////////////////////////////////////////////////////////////////////////////
//// buildGraph() tests
///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void checkBuildZero() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data0.tsv");
		assertEquals(0, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildOne() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data1.tsv");
		assertEquals(1, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildTwoNotConnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data2a.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildTwoConnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data2b.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(2, g.numEdges());
	}
	
	@Test
	public void checkBuildTwoMultipleConnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data2c.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(4, g.numEdges());
	}
	
	@Test
	public void checkBuildThreeUnconnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3a.tsv");
		assertEquals(3, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildThreeTwoConnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3b.tsv");
		assertTrue(g.isConnected("a", "b"));
		assertFalse(g.isConnected("a", "c"));
		assertFalse(g.isConnected("b", "c"));
		assertEquals(3, g.numNodes());
		assertEquals(2, g.numEdges());
		assertEquals(0, g.numOutEdges("c"));
		assertEquals(0, g.numInEdges("c"));
		assertEquals(1, g.numOutEdges("a"));
		assertEquals(1, g.numInEdges("a"));
		assertEquals(1, g.numOutEdges("b"));
		assertEquals(1, g.numInEdges("b"));
	}
	
	@Test
	public void checkBuildThreeTwoMultipleConnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3c.tsv");
		assertTrue(g.isConnected("a", "b"));
		assertFalse(g.isConnected("a", "c"));
		assertFalse(g.isConnected("b", "c"));
		assertEquals(3, g.numNodes());
		assertEquals(4, g.numEdges());
		assertEquals(0, g.numOutEdges("c"));
		assertEquals(0, g.numInEdges("c"));
		assertEquals(2, g.numOutEdges("a"));
		assertEquals(2, g.numInEdges("a"));
		assertEquals(2, g.numOutEdges("b"));
		assertEquals(2, g.numInEdges("b"));
	}
	
	@Test
	public void checkBuildThreeLinear() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3d.tsv");
		assertTrue(g.isConnected("a", "b"));
		assertTrue(g.isConnected("b", "c"));
		assertFalse(g.isConnected("a", "c"));
		assertEquals(3, g.numNodes());
		assertEquals(4, g.numEdges());
		assertEquals(1, g.numOutEdges("a"));
		assertEquals(1, g.numInEdges("a"));
		assertEquals(2, g.numOutEdges("b"));
		assertEquals(2, g.numInEdges("b"));
		assertEquals(1, g.numOutEdges("c"));
		assertEquals(1, g.numInEdges("c"));
	}
	
	@Test
	public void checkBuildThreeLinearMultipleConnectionsOne() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3e.tsv");
		assertTrue(g.isConnected("a", "b"));
		assertTrue(g.isConnected("b", "c"));
		assertFalse(g.isConnected("a", "c"));
		assertEquals(3, g.numNodes());
		assertEquals(6, g.numEdges());
		assertEquals(2, g.numOutEdges("a"));
		assertEquals(2, g.numInEdges("a"));
		assertEquals(3, g.numOutEdges("b"));
		assertEquals(3, g.numInEdges("b"));
		assertEquals(1, g.numOutEdges("c"));
		assertEquals(1, g.numInEdges("c"));
	}
	
	@Test
	public void checkBuildThreeLinearMultipleConnectionsBoth() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3f.tsv");
		assertTrue(g.isConnected("a", "b"));
		assertTrue(g.isConnected("b", "c"));
		assertFalse(g.isConnected("a", "c"));
		assertEquals(3, g.numNodes());
		assertEquals(8, g.numEdges());
		assertEquals(2, g.numOutEdges("a"));
		assertEquals(2, g.numInEdges("a"));
		assertEquals(4, g.numOutEdges("b"));
		assertEquals(4, g.numInEdges("b"));
		assertEquals(2, g.numOutEdges("c"));
		assertEquals(2, g.numInEdges("c"));
	}
	
	@Test
	public void checkBuildThreeTriangle() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3g.tsv");
		assertEquals(3, g.numNodes());
		assertEquals(6, g.numEdges());
	}
	
	@Test
	public void checkMalformedException() {
		boolean caught = false;
		try {
			MarvelPaths.buildGraph("src/hw6/data/malformed.tsv");
		} catch (MalformedDataException e) {
			caught = true;
		}
		assertTrue(caught);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// bfsShortestPath() / getShortestPath() Tests
///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void checkBFSShortestPathZero() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data0.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "b", g);
		assertNull(path);
	}
	
	@Test
	public void checkBFSShortestPathOneNotFound() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data1.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "b", g);
		assertNull(path);
	}
	
	@Test
	public void checkBFSShortestPathOneSelf() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data1.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "a", g);
		assertEquals(0, path.size());
	}
	
	@Test
	public void checkBFSShortestPathTwoNotFound() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data2a.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "h", g);
		assertNull(path);
	}
	
	@Test
	public void checkBFSShortestPathTwoUnconnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data2a.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "b", g);
		assertNull(path);
	}
	
	@Test
	public void checkBFSShortestPathTwoConnected() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data2b.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "b", g);
		List<String> verify = new ArrayList<String>();
		verify.add("a");
		verify.add("1");
		verify.add("b");
		assertEquals(path, verify);
	}
	
	@Test
	public void checkBFSShortestPathLinear() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3d.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "c", g);
		List<String> verify = new ArrayList<String>();
		verify.add("a");
		verify.add("1");
		verify.add("b");
		verify.add("2");
		verify.add("c");
		assertEquals(path, verify);
	}
	
	@Test
	public void checkBFSShortestPathTriangle() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3g.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "c", g);
		List<String> verify = new ArrayList<String>();
		verify.add("a");
		verify.add("9");
		verify.add("c");
		assertEquals(path, verify);
	}
	
	@Test
	public void checkBFSShortestPathTriangleSelf() throws MalformedDataException {
		Graph<String, String> g = MarvelPaths.buildGraph("src/hw6/data/data3g.tsv");
		List<String> path = MarvelPaths.bfsShortestPath("a", "a", g);
		assertEquals(0, path.size());
	}
}
