package hw7.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import hw5.Graph;
import hw5.LabeledEdge;
import hw6.MarvelParser.MalformedDataException;
import hw7.MarvelPaths2;
import hw7.test.CheckAsserts;
public class MarvelPaths2Test {
	
private static final double DELTA = 1e-15;
///////////////////////////////////////////////////////////////////////////////////////
//// Setup
///////////////////////////////////////////////////////////////////////////////////////
	/**
	* checks that Java asserts are enabled, and exits if not
	*/
	@Before
	public void testAssertsEnabled() {
		CheckAsserts.checkAssertsEnabled();
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// Test buildGraph()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkBuildZero() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data0.tsv");
		assertEquals(0, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildOne() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data1.tsv");
		assertEquals(1, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildTwoNotConnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data2a.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildTwoConnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data2b.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(2, g.numEdges());
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(1.0, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildTwoMultipleConnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data2c.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(2, g.numEdges());
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(0.5, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildTwoTripleConnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data2d.tsv");
		assertEquals(2, g.numNodes());
		assertEquals(2, g.numEdges());
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(1.0/3, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildThreeUnconnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3a.tsv");
		assertEquals(3, g.numNodes());
		assertEquals(0, g.numEdges());
	}
	
	@Test
	public void checkBuildThreeTwoConnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3b.tsv");
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
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(1.0, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildThreeTwoMultipleConnected() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3c.tsv");
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
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(0.5, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildThreeLinear() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3d.tsv");
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
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(1.0, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildThreeLinearMultipleConnectionsOne() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3e.tsv");
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
		for (LabeledEdge<String, Double> edge : g.getOutEdges("a")) {
			assertEquals(0.5, edge.getLabel(), DELTA);
		}
		for (LabeledEdge<String, Double> edge : g.getOutEdges("c")) {
			assertEquals(1.0, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildThreeLinearMultipleConnectionsBoth() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3f.tsv");
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
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(0.5, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkBuildThreeTriangle() throws MalformedDataException {
		Graph<String, Double> g = MarvelPaths2.buildGraph("src/hw7/data/data3g.tsv");
		assertEquals(3, g.numNodes());
		assertEquals(6, g.numEdges());
		for (LabeledEdge<String, Double> edge : g.getAllEdges()) {
			assertEquals(1.0, edge.getLabel(), DELTA);
		}
	}
	
	@Test
	public void checkMalformedException() {
		boolean caught = false;
		try {
			MarvelPaths2.buildGraph("src/hw7/data/malformed.tsv");
		} catch (MalformedDataException e) {
			caught = true;
		}
		assertTrue(caught);
	}
}
