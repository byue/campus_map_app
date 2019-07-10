package hw8;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import hw5.Graph;
import hw7.MinimumCostPathFinder.Path;
import hw7.MarvelPaths2.Tuple;
import hw7.MinimumCostPathFinder;
import static org.junit.Assert.*;

/** <b>CampusPathsModel</b> represents the data of the campus map, including
 * the buildings and shortest paths between buildings. Each CampusPathsModel
 * can be described by a set of n buildings {b1, b2, b3, ..., bn} and a set of 
 * paths between buildings {p1_2, p1_3, ..., pn-1_n} where each px_y is a path
 * from building x to building y. Each path is an ordered list of locations from
 * the start building to end building.
 * 
 * @author Bryan Yue
 * @version 1.0
 */
public class CampusPathsModel {
	private static final double ANGLE_MULTIPLE = Math.PI / 8;
	private static final boolean EXPENSIVE_CHECK = false;
	private Map<String, Building> abbrevToBuildings;
	private Set<Building> buildings;
	private Graph<Coordinates, Double> campusGraph;
	
	// Abstraction Function:
	// For a given CampusPathsModel m, m.abbrevTobuildings is synonymous with
	// "map from building abbreviated names to buildings", m.buildings is synonymous with
	// "buildings", and m.campusGraph is synonymous with "weighted graph of coordinate points".
		
	// Representational Invariant:
	// abbrevToBuildings != null && buildings != null && campusGraph != null. 
	// The campusGraph has all the coordinates from each building from buildings. The set taken from 
	// the values of abbrevToBuildings must be equivalent to buildings. The buildings from buildings
	// must have all the abbreviations in abbrevToBuildings with no extra abbreviations.
	
	/**
	 * Constructs an empty CampusPathsModel.
	 * 
	 * @effects Constructs a new CampusPathsModel with no buildings or paths.
     */
	public CampusPathsModel() {
		abbrevToBuildings = new HashMap<String, Building>();
	    campusGraph = new Graph<Coordinates, Double>();
	    buildings = new TreeSet<Building>();
	    checkRep();
	}
	
	/**
	 * Retrieves building represented by building name abbreviation.
	 * 
	 * @param    abbrev the String building name abbreviation.
	 * @return   the Building that is represented by the name abbreviation.
     */
	public Building getBuilding(String abbrev) {
		return abbrevToBuildings.get(abbrev);
	}
	
	/**
	 * Retrieves the number of locations (not necessarily buildings).
	 * 
	 * @return   the number of locations.
     */
	public int numLocations() {
		return campusGraph.numNodes();
	}
	
	/**
	 * Retrieves the number of buildings
	 * 
	 * @return   the number of buildings.
     */
	public int numBuildings() {
		return buildings.size();
	}
	
	/**
	 * Reports if this contains the building represented by the building name abbreviation.
	 * 
	 * @param    abbrev the String building name abbreviation.
	 * @return   true iff this contains the building, false otherwise.
     */
	public boolean containsBuilding(String abbrev) {
		return abbrevToBuildings.containsKey(abbrev);
	}
	
	/**
	 * Retrieves the buildings in this.
	 * 
	 * @return   an unmodifiable set of the buildings.
     */
	public Set<Building> getBuildings() {
		return Collections.unmodifiableSet(buildings);
	}
	
	/**
	 * Finds the shortest distance path between two buildings. 
	 *
	 * @param    startAbbrev the String name abbreviation of the starting building.
	 * @param    endAbbrev the String name abbreviation of the destination building.
	 * @requires startAbbrev != null && endAbbrev != null and this contains the start and destination buildings.
	 * @return   the shortest distance path from the start to destination building with associated distances
	 *           and coordinate points. Returns null if no path was found. If the source is the destination too,
	 *           a new empty path is returned.
	 */
	public Path<Coordinates> shortestPath(String startAbbrev, String endAbbrev) {
		Coordinates startCoordinates = abbrevToBuildings.get(startAbbrev).getCoordinates();
		Coordinates endCoordinates = abbrevToBuildings.get(endAbbrev).getCoordinates();
		return MinimumCostPathFinder.djkPath(startCoordinates, endCoordinates, campusGraph);
	}
	
	/**
	 * Loads buildings and paths into this from building and paths files.
	 *
	 * @param    buildingFileName the String name of the building file
	 * @param    pathsFileName the String name of the paths file.
	 * @requires buildingFileName != null && pathsFileName != null && both files are 
	 *           properly formatted && both files are present.
	 * @modifies this
	 * @effect   adds buildings and paths to this.
	 */
	public void loadLocations(String buildingFileName, String pathsFileName) {
		CampusParser.parseBuildings(buildingFileName, buildings, abbrevToBuildings);
		Map<Tuple<Coordinates>, Double> pairToDistance = 
				new HashMap<Tuple<Coordinates>, Double>();
		Map<Coordinates, Set<Coordinates>> startToEnd = 
				new HashMap<Coordinates, Set<Coordinates>>();
		CampusParser.parsePaths(pathsFileName, startToEnd, pairToDistance);
		for (Coordinates coordinates : startToEnd.keySet()) {
			campusGraph.addData(coordinates);
		}
		for (Coordinates startCoordinate: startToEnd.keySet()) {
			for (Coordinates endCoordinate : startToEnd.get(startCoordinate)) {
				Tuple<Coordinates> pair = new Tuple<Coordinates>(startCoordinate, endCoordinate);
				double distance = pairToDistance.get(pair);
			    campusGraph.connectData(startCoordinate, endCoordinate, distance);
			}
		}
		checkRep();
	}
	
	/**
	 * Reports the direction taken from a start coordinate to end coordinate. 
	 * Top left of grid is (0,0) and coordinates increase from left-to-right (west to east)
	 * and up-to-down (north to south).
	 *
	 * @param    startCoordinate the Coordinate of the start location.
	 * @param    endCoordinate the Coordinate of the end location.
	 * @requires startCoordinate != null && endCoordinate != null.
	 * @return   the String direction taken from start to end coordinate.
	 */
	public String getDirection(Coordinates startCoordinate, Coordinates endCoordinate) {
		double deltaX = endCoordinate.getX() - startCoordinate.getX();
		double deltaY = endCoordinate.getY() - startCoordinate.getY();
		double theta = Math.atan2(deltaY, deltaX);
		if (theta < ANGLE_MULTIPLE && theta >= -ANGLE_MULTIPLE) {
			return "E";
		} else if (theta < 3 * ANGLE_MULTIPLE && theta >= ANGLE_MULTIPLE) {
			return "SE";
		} else if (theta < 5 * ANGLE_MULTIPLE && theta >= 3 * ANGLE_MULTIPLE) {
			return "S";
		} else if (theta < 7 * ANGLE_MULTIPLE && theta >= 5 * ANGLE_MULTIPLE) {
			return "SW";
		} else if (theta < -5 * ANGLE_MULTIPLE && theta >= -7 * ANGLE_MULTIPLE) {
			return "NW";
		} else if (theta < -3 * ANGLE_MULTIPLE && theta >= -5 * ANGLE_MULTIPLE) {
			return "N";
		} else if (theta < -ANGLE_MULTIPLE && theta >= -3 * ANGLE_MULTIPLE) {
			return "NE";
		} 
		return "W";	
	}
	
	/**
	 * Checks that the representation invariant holds.
	 */
	private void checkRep() {
		assert (abbrevToBuildings != null) : "building abbreviations should never be null.";
		assert (buildings != null) : "buildings should never be null.";
		assert (campusGraph != null) : "campus graph should never be negative.";
		if (EXPENSIVE_CHECK) {
			for (Building b : buildings) {
				Coordinates c = b.getCoordinates();
				assertTrue(campusGraph.containsData(c));
				String shortName = b.getShortName();
				assertTrue(abbrevToBuildings.containsKey(shortName));
			}
			assertEquals(abbrevToBuildings.keySet().size(), buildings.size());
			for (String key : abbrevToBuildings.keySet()) {
				assertTrue(buildings.contains(abbrevToBuildings.get(key)));
			}
		}
	}
}
