package hw8.test;

import static org.junit.Assert.*;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import hw7.MinimumCostPathFinder.Path;
import hw7.test.CheckAsserts;
import hw8.Building;
import hw8.CampusPaths;
import hw8.CampusPathsModel;
import hw8.Coordinates;

public class CampusPathsModelTest {
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
//// Test constructor()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkConstructor() {
		CampusPathsModel m = new CampusPathsModel();
		assertTrue(m != null);
	}

///////////////////////////////////////////////////////////////////////////////////////
//// Test loadLocations()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkLoadLocations() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/buildingSample.dat", "src/hw8/data/pathsSample.dat");
		Set<Building> buildings = m.getBuildings();
		assertEquals(2, buildings.size());
		Building b1 = new Building("BAG", "Bagley Hall (East Entrance)", 1914.5103, 1709.8816);
		Building b2 = new Building("BGR", "By George", 1923.4389, 1721.8816);
		assertTrue(buildings.contains(b1));
		assertTrue(buildings.contains(b2));
		assertEquals(m.getBuilding("BAG"), b1);
		assertEquals(m.getBuilding("BGR"), b2);
		assertEquals(2, m.numBuildings());
		assertEquals(2, m.numLocations());
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// Test numBuildings()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkLoadLocationsNumBuildings() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		Set<Building> buildings = m.getBuildings();
		assertEquals(51, buildings.size());
		assertEquals(51, m.numBuildings());
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// Test numLocations()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkLoadLocationsNumLocations() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		assertEquals(2067, m.numLocations());
	}

///////////////////////////////////////////////////////////////////////////////////////
////Test containsBuilding()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkContainsBuilding() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		assertTrue(m.containsBuilding("CSE"));
		assertTrue(m.containsBuilding("CHL (NE)"));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
//// Test getBuilding()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkGetBuilding() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		Building b1 = m.getBuilding("CSE");
		Building b2 = new Building("CSE", "Paul G. Allen Center for Computer Science & Engineering", 
				2259.7112, 1715.5273);
		assertEquals(b1, b2);
		Building b3 = m.getBuilding("CHL");
		Building b4 = new Building("CHL", "Chemistry Library (West Entrance)", 1707.6629, 1671.5098);
		assertEquals(b3, b4);
	}

///////////////////////////////////////////////////////////////////////////////////////
////Test shortestPath()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkShortestPathSmall() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/buildingSample.dat", "src/hw8/data/pathsSample.dat");
		Path<Coordinates> p = m.shortestPath("BAG", "BGR");
		assertEquals(((Double) p.getCost()), ((Double) 21.364006252347988));
	}
	
	@Test
	public void checkShortestPathLarge() {
		CampusPathsModel m = new CampusPathsModel();
		m.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		Path<Coordinates> p = m.shortestPath("RAI", "RAI (E)");
		assertEquals(((Double) p.getCost()), ((Double) 280.33840043471395));
	}
	
///////////////////////////////////////////////////////////////////////////////////////
////Test getDirection()
///////////////////////////////////////////////////////////////////////////////////////
	@Test
	public void checkgetDirection() {
		CampusPathsModel m = new CampusPathsModel();
		Coordinates c1 = new Coordinates(0.0, 0.0);
		Coordinates c2 = new Coordinates(0.0, 1.0);
		Coordinates c3 = new Coordinates(2.0, 0.0);
		Coordinates c4 = new Coordinates(2.0, 2.0);
		Coordinates c5 = new Coordinates(2.0, -2.0);
		assertEquals("S", m.getDirection(c1, c2));
		assertEquals("N", m.getDirection(c2, c1));
		assertEquals("E", m.getDirection(c1, c3));
		assertEquals("W", m.getDirection(c3, c1));
		assertEquals("SE", m.getDirection(c1, c4));
		assertEquals("NW", m.getDirection(c4, c1));
		assertEquals("NE", m.getDirection(c1, c5));
		assertEquals("SW", m.getDirection(c5, c1));
	}
}
