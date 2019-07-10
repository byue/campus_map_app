package hw8;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.Set;
import hw5.LabeledEdge;
import hw7.MinimumCostPathFinder.Path;

/** <b>CampusPaths</b> Prompts user for input and displays shortest path between two buildings.
 * 
 * @author Bryan Yue
 * @version 1.0
 */

public class CampusPaths {
	// Note: CampusPaths is not an ADT so no abstraction function or representational invariant
	// was specified.	
	
	/**
	 * Takes user input and runs program. Outputs menu options, buildings, or shortest path 
	 * between two buildings based on user input. Echoes empty input or input starting with "#".
	 *
	 * @param args - ignore
	 */
	public static void main(String[] args) {
		CampusPathsModel model = new CampusPathsModel();
		model.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		showMenu();
		promptInput();
		String input;
		Scanner reader = new Scanner(System.in); 
		while (!(input = reader.nextLine()).equals("q")) {
			if (input.equals("m")) {
				showMenu();
			} else if (input.equals("b")) {
				printBuildings(model.getBuildings());
			} else if (input.equals("r")) {
				System.out.print("Abbreviated name of starting building: ");
				String startAbbrev = reader.nextLine();
				System.out.print("Abbreviated name of ending building: ");
				String endAbbrev = reader.nextLine();
				boolean bothPresent = true;
				if (!model.containsBuilding(startAbbrev)) {
					bothPresent = false;
					System.out.println("Unknown building: " + startAbbrev);
				}
				if (!model.containsBuilding(endAbbrev)) {
					bothPresent = false;
					System.out.println("Unknown building: " + endAbbrev);
				}
				if (bothPresent) {
					printPath(startAbbrev, endAbbrev, model);
				}
				System.out.println();
			} else if (input.isEmpty() || input.startsWith("#")) {
				System.out.println(input);
				continue;
			} else {
				System.out.println("Unknown option");
				System.out.println();
			}
			promptInput();
		}
		reader.close();
	}
	
	/**
	 * Prints path from start building to end building, directions, and total distance.
	 * 
	 * @param    startAbbrev the starting building abbreviated name
	 * @param    endAbbrev the ending building abbreviated name
	 * @param    model the campus model that holds building data.
	 * @requires startAbbrev != null && endAbbrev != null && model != null && startAbbrev is 
	 *           present in data && endAbbrev is present in data.
	 */
	private static void printPath(String startAbbrev, String endAbbrev, CampusPathsModel model) {
		String startFullName = model.getBuilding(startAbbrev).getFullName(); 
		String endFullName = model.getBuilding(endAbbrev).getFullName();
		System.out.println("Path from " + startFullName + " to " + endFullName + ":");
		Path<Coordinates> pathPackage = model.shortestPath(startAbbrev, endAbbrev);
		ArrayList<LabeledEdge<Coordinates, Double>> path = pathPackage.getPath();
		for (LabeledEdge<Coordinates, Double> edge : path) {
			Coordinates startCoordinates = edge.getSourceData();
			Coordinates endCoordinates = edge.getDestData();
			int distance = (int) Math.round(edge.getLabel());
			int endX = (int) Math.round(endCoordinates.getX());
			int endY = (int) Math.round(endCoordinates.getY());
			System.out.println("\tWalk " + distance + " feet " + model.getDirection(startCoordinates,
					endCoordinates) + " to " + "(" + endX + ", " + endY + ")");
		}
		int totalDistance = (int) Math.round(pathPackage.getCost());
		System.out.println("Total distance: " + totalDistance + " feet");
	}
	
	/**
	 * Prints the building abbreviation and full name for each building.
	 * 
	 * @param buildings the set of buildings to print.
	 * @requires buildings != null
	 */
	private static void printBuildings(Set<Building> buildings) {
		System.out.println("Buildings:");
		for (Building building : buildings) {
			System.out.println("\t" + building.getShortName() + ": " + building.getFullName());
		}
		System.out.println();
	}
	
	/**
	 * Prints the menu options for the program, and an empty line.
	 */
	private static void showMenu() {
		System.out.println("Menu:");
		System.out.println("\tr to find a route");
		System.out.println("\tb to see a list of all buildings");
		System.out.println("\tq to quit");
		System.out.println();
	}
	
	/**
	 * Prompts user for options.
	 */
	private static void promptInput() {
		System.out.print("Enter an option ('m' to see the menu): ");
	}
}
