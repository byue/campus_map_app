package hw9;

import java.io.IOException;
import javax.swing.*;
import hw8.CampusPathsModel;

/** <b>CampusPathsMain</b> runs the Campus Paths application. Opens a window with 
 *     a campus map that displays selected paths. Paths are selected from drop-down boxes
 *     listing possible destinations/locations. Find button plots path on map, random displays
 *     random path, and reset restores the original window size and erases markings.
 * 
 * @author Bryan Yue
 * @version 1.0
 */

public class CampusPathsMain {
	// Note: CampusParser is not an ADT so no abstraction function or representational invariant
	// was specified.	
	
	/**
	 * Runs the Campus Paths Application. 
	 *
	 * @param args - ignore
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		CampusPathsModel model = new CampusPathsModel();
		model.loadLocations("src/hw8/data/campus_buildings.dat", "src/hw8/data/campus_paths.dat");
		JFrame frame = new JFrame("Campus Paths Application");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1024, 768);
		CampusPathsController controller = new CampusPathsController(model, frame, "src/hw8/data/campus_map.jpg");
	    frame.add(controller);
	    frame.pack();
	    frame.setVisible(true);
	}
}
