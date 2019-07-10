package hw7.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import hw5.Graph;
import hw6.MarvelParser.MalformedDataException;
import hw7.MarvelPaths2;
import hw7.MinimumCostPathFinder;
import hw7.MinimumCostPathFinder.Path;

public class MinimumCostPathFinderTest {
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
//// Test djkPath()
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void checkSourceNullDJK() {
		Graph<String, Double> g = new Graph<String, Double>();
		assertNull(MinimumCostPathFinder.djkPath(null, "b", g));
	}
	
	@Test
	public void checkDestNullDJK() {
		Graph<String, Double> g = new Graph<String, Double>();
		assertNull(MinimumCostPathFinder.djkPath("a", null, g));
	}
	
	@Test
	public void checkGraphNullDJK() {
		assertNull(MinimumCostPathFinder.djkPath("a", "b", null));
	}
	
	@Test
	public void checkSourceDestSameDJK() {
		Graph<String, Double> g = new Graph<String, Double>();
		g.addData("a");
		Path<String> path = MinimumCostPathFinder.djkPath("a", "a", g);
		assertEquals(path.getCost(), 0.0, DELTA);
		assertEquals(path.getPath().size(), 0);	
	}
	
	@Test
	public void checkPathNotFoundDJK() {
		Graph<String, Double> g = new Graph<String, Double>();
		g.addData("a");
		g.addData("b");
		assertNull(MinimumCostPathFinder.djkPath("a", "b", g));
	}
	
	@Test
	public void checkExamplePathDJK() {
		Graph<String, Double> g = new Graph<String, Double>();
		g.addData("a");
		g.addData("b");
		g.addData("c");
		g.addData("d");
		g.addData("e");
		g.connectData("a", "b", 1.4);
		g.connectData("b", "e", 1.0);
		g.connectData("b", "c", 9.0);
		g.connectData("c", "d", 8.0);
		g.connectData("d", "e", 6.2);
		Path<String> path = MinimumCostPathFinder.djkPath("a", "e", g);
		assertEquals(path.getCost(), 2.4, DELTA);
	}	
}
