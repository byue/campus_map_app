package hw8;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import hw7.MarvelPaths2.Tuple;

/** <b>CampusParser</b> reads buildings and paths files and stores data.
 * 
 * @author Bryan Yue
 * @version 1.0
 */

public class CampusParser {
  // Note: CampusParser is not an ADT so no abstraction function or representational invariant
  // was specified.	

  /**
   * Reads the input buildings file.
   * Each line of the input buildings file contains an abbreviated building name,
   * a full building name, an x-coordinate, and a y-coordinate separated by tabs.
   * Each block of the input paths file contains one comma-separated starting coordinate pair
   * with no spaces, followed by destination lines that start with a tab and have 
   * a comma-separated ending coordinate pair, followed by a colon, space, and the distance
   * between the start and end locations.
   * 
   * @requires filename is a valid file path, file is formatted as stated in description.
   * @param    filename the file that will be read
   * @param    buildings the set that the buildings will be stored in.
   * @param    abbrevToBuildings a map from building abbreviation name to building.
   * @modifies buildings, abbrevToBuildings
   * @effects  fills buildings with unique buildings.
   * @effects  fills abbrevToBuildings with a map from building name abbreviation to building.
   */
  public static void parseBuildings(String filename, Set<Building> buildings, 
		  Map<String, Building> abbrevToBuildings) {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        String inputLine;
        while ((inputLine = reader.readLine()) != null) {
        	// Ignore comment lines.
            if (inputLine.startsWith("#")) {
                continue;
            }
            // parse for building information, add building information to buildings
            // and abbrecToBuildings
            String[] tokens = inputLine.split("\t");
            String shortName = tokens[0];
            String fullName = tokens[1];
            Double xCoordinate = Double.parseDouble(tokens[2]);
            Double yCoordinate = Double.parseDouble(tokens[3]);
            Building building = new Building(shortName, fullName, xCoordinate, yCoordinate);
            abbrevToBuildings.put(shortName, building);
            buildings.add(building);
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }
  
  /**
   * Reads the input paths file.
   * Each block of the input paths file contains one comma-separated starting coordinate pair
   * with no spaces, followed by destination lines that start with a tab and have 
   * a comma-separated ending coordinate pair, followed by a colon, space, and the distance
   * between the start and end locations.
   * 
   * @requires filename is a valid file path, file is formatted as stated in description.
   * @param    filename the file that will be read
   * @param    startToEnd the map from start coordinates to all ending coordinates.
   * @param    pairToDistance the map from start/end coordinate pairs to distances between 
   *           coordinates.
   * @modifies startToEnd, pairToDistance
   * @effects  fills startToEnd with map from start coordinate to a set of end coordinates.
   * @effects  fills abbrevToBuildings with a map from start/end coordinate pairs to distances 
   *           between coordinates.
   */
  public static void parsePaths(String filename, Map<Coordinates, Set<Coordinates>> startToEnd,
		  Map<Tuple<Coordinates>, Double> pairToDistance) {
    BufferedReader reader = null;
    try {
        reader = new BufferedReader(new FileReader(filename));
        String inputLine;
        Coordinates start = null;
        while ((inputLine = reader.readLine()) != null) {
        	// Ignore comment lines.
            if (inputLine.startsWith("#")) {
                continue;
            }
            // parse start coordinates
        	if (inputLine.charAt(0) != '\t') {
        		 String[] startPoints = inputLine.split(",");
        		 start = new Coordinates(Double.parseDouble(startPoints[0]), 
         				Double.parseDouble(startPoints[1]));
        	} else {
        		// parse end coordinates followed by distances
        		// adds start and end coordinates to startToEnd, adds start/end coordinates
        		// and distnace to pairToDistance
        		String[] endTokens = inputLine.trim().split(": ");
        		String[] endPoints = endTokens[0].split(",");
        		Coordinates end = new Coordinates(Double.parseDouble(endPoints[0]), 
        				Double.parseDouble(endPoints[1]));
        		Tuple<Coordinates> startAndEnd = new Tuple<Coordinates>(start, end);
        		double distance = Double.parseDouble(endTokens[1]);
        		pairToDistance.put(startAndEnd, distance);
        		Set<Coordinates> endpoints = startToEnd.get(start);
        		if (endpoints == null) {
        			endpoints = new HashSet<Coordinates>();
        		}
        		endpoints.add(end);
        		startToEnd.put(start, endpoints);
        	}
        }
    } catch (IOException e) {
        System.err.println(e.toString());
        e.printStackTrace(System.err);
    } finally {
        if (reader != null) {
            try {
                reader.close();
            } catch (IOException e) {
                System.err.println(e.toString());
                e.printStackTrace(System.err);
            }
        }
    }
  }
}
