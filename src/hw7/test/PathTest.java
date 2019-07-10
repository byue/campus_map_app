package hw7.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import hw5.LabeledEdge;
import hw7.MinimumCostPathFinder.Path;

public class PathTest {
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
//// Test Constructor
///////////////////////////////////////////////////////////////////////////////////////	
	@Test
	public void checkEmptyConstructor() {
		Path<String> path = new Path<String>();
		assertEquals(path.getCost(), 0.0, DELTA);
		assertEquals(path.getPath().size(), 0);
	}
	
	@Test
	public void checkSelfConstructor() {
		Path<String> path = new Path<String>("Hi");
		assertEquals(path.getCost(), 0.0, DELTA);
		String source = path.getPath().get(0).getDestData();
		String dest = path.getPath().get(0).getDestData();
		assertEquals(source, dest);
	}
	
	@Test
	public void checkCopyConstructor() {
		Path<String> path1 = new Path<String>("Hi");
		Path<String> path2 = new Path<String>(path1);
		Path<String> path3 = new Path<String>("Bye");
		path3.add(new LabeledEdge<String, Double>("Hi", "bye", 3.4));
		assertEquals(0, path1.compareTo(path2));
		assertFalse(path1.compareTo(path3) == 0);
	}

///////////////////////////////////////////////////////////////////////////////////////
//// Test Add
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void checkAdd() {
		Path<String> path = new Path<String>("Hi");
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 3.4));
		assertEquals(path.getCost(), 3.4, DELTA);
		assertEquals(path.getPath().size(), 2);
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 1.2));
		assertEquals(path.getCost(), 4.6, DELTA);
		assertEquals(path.getPath().size(), 3);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// Test getCost()
///////////////////////////////////////////////////////////////////////////////////////
	
	@Test
	public void checkGetCost() {
		Path<String> path = new Path<String>("Hi");
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 3.4));
		assertEquals(path.getCost(), 3.4, DELTA);
		Path<String> path2 = new Path<String>("ji");
		path2.add(new LabeledEdge<String, Double>("Hi", "bye", 24.1));
		assertEquals(path2.getCost(), 24.1, DELTA);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// Test getPath()
///////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void checkGetPath() {
		Path<String> path = new Path<String>("Hi");
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 3.4));
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 24.1));
		assertEquals(path.getPath().size(), 3);
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test compareTo()
///////////////////////////////////////////////////////////////////////////////////////

	@Test
	public void checkCompareToCosts() {
		Path<String> path = new Path<String>("Hi");
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 3.4));
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 24.1));
		Path<String> path2 = new Path<String>();
		path2.add(new LabeledEdge<String, Double>("g", "g", 1000.0));
		assertTrue(path.compareTo(path2) < 0);
	}
	
	@Test
	public void checkCompareToLength() {
		Path<String> path = new Path<String>("Hi");
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 1000.0));
		path.add(new LabeledEdge<String, Double>("Hi", "bye", 1.0));
		Path<String> path2 = new Path<String>();
		path2.add(new LabeledEdge<String, Double>("g", "g", 1001.0));
		assertTrue(path.compareTo(path2) > 0);
	}

}