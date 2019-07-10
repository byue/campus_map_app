package hw8.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;

import hw7.MarvelPaths2.Tuple;
import hw7.test.CheckAsserts;
import hw8.Building;
import hw8.CampusParser;
import hw8.Coordinates;

public class CampusParserTest {
	private static final double DELTA = 1e-15;
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
//// Test parseBuildings()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkParseBuildings() {
		Set<Building> buildings = new HashSet<Building>();
		Map<String, Building> abbrevToBuildings = new HashMap<String, Building>();
		CampusParser.parseBuildings("src/hw8/data/parserTestBuildings.dat", 
				buildings, abbrevToBuildings);
		assertEquals(2, buildings.size());
		Building b1 = new Building("BAG", "Bagley Hall (East Entrance)", 1914.5103, 1709.8816);
		Building b2 = new Building("BAG (NE)", "Bagley Hall (Northeast Entrance)", 
				1878.3786, 1661.4083);
		assertTrue(buildings.contains(b1));
		assertTrue(buildings.contains(b2));
		assertEquals(2, abbrevToBuildings.size());
		assertEquals(b1, abbrevToBuildings.get("BAG"));
		assertEquals(b2, abbrevToBuildings.get("BAG (NE)"));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test parsePaths()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkParsePaths() {
		 Map<Coordinates, Set<Coordinates>> outCoordinates =
				 new HashMap<Coordinates, Set<Coordinates>>();
		 Map<Tuple<Coordinates>, Double> distances = new HashMap<Tuple<Coordinates>, Double>();
		 CampusParser.parsePaths("src/hw8/data/parserTestPaths.dat", outCoordinates, distances);
		 assertEquals(2, outCoordinates.size());
		 //first block
		 Coordinates c1 = new Coordinates(1903.7201, 1952.4322);
		 Coordinates r1 = new Coordinates(1906.1864, 1939.0633);
		 double distance1 = 26.583482327919597;
		 // second block
		 Coordinates c2 = new Coordinates(2337.0143, 806.8278);
		 Coordinates r2 = new Coordinates(2346.3446, 817.55768);
		 double distance2 = 29.685363221542797;
		 Coordinates r3 = new Coordinates(2321.6193, 788.16714);
		 double distance3 = 49.5110360968527;
		 // check outCoordinates
		 Set<Coordinates> set1 = outCoordinates.get(c1);
		 assertEquals(1, set1.size());
		 assertTrue(set1.contains(r1));
		 Set<Coordinates> set2 = outCoordinates.get(c2);
		 assertEquals(2, set2.size());
		 assertTrue(set2.contains(r2));
		 assertTrue(set2.contains(r3));
		 // check distances
		 Tuple<Coordinates> pair1 = new Tuple<Coordinates>(c1, r1);
		 assertEquals(distances.get(pair1), distance1, DELTA);
		 Tuple<Coordinates> pair2 = new Tuple<Coordinates>(c2, r2);
		 assertEquals(distances.get(pair2), distance2, DELTA);
		 Tuple<Coordinates> pair3 = new Tuple<Coordinates>(c2, r3);
		 assertEquals(distances.get(pair3), distance3, DELTA);
	}
}
