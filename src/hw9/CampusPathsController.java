package hw9;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Set;
import javax.swing.*;
import hw8.Building;
import hw8.CampusPathsModel;

/** <b>CampusPathsController</b> is the controller of the Campus Paths Application.
 * User input of start and end buildings is entered in selection boxes, and buttons exist to
 * find the shortest path, generate a random path, or reset the application.
 * 
 * @author Bryan Yue
 * @version 1.0
 */
public class CampusPathsController extends JPanel {
	// Note: CampusPathsController is not an ADT so no abstraction function or representational invariant
	// was specified.	
	private static final long serialVersionUID = 1L;
	private CampusPathsView viewPane;
	JFrame frame;
	
	/**
	 * Creates a new CampusPathsController. 
	 * 
	 * @param model the CampusPathsModel the controller communicates with
	 * @param frame the frame the controller is put on
	 * @param mapFile the String name for the map picture file.
	 * @requires model != null && frame != null
	 * @throws IOException if the file does not exist.
	 * @effects creates a new CampusPathsModel
	 */
	public CampusPathsController(CampusPathsModel model, JFrame frame, String mapFile) throws IOException {
		setLayout(new BorderLayout());
		this.frame = frame;
		this.viewPane = new CampusPathsView(model, mapFile);
		viewPane.setPreferredSize(new Dimension(1024, 768));
		add(viewPane, BorderLayout.CENTER);
		JPanel controls = initControls(model);
		add(controls, BorderLayout.NORTH);
	}
	
	/**
	 * Returns controller with new drop-down menus with source and destination names, 
	 * random button, find button, and reset button.
	 * 
	 * @param model the CampusPathsModel the controller communicates with
	 * @requires model != null
	 * @return a new JPanel with the user controls. 
	 */
     public JPanel initControls(CampusPathsModel model) {
    	// format/get building names
    	JPanel controls = new JPanel(new FlowLayout());  
    	Set<Building> buildings = model.getBuildings();
    	ArrayList<String> locations = new ArrayList<String>();
    	for (Building building : buildings) {
    		locations.add(building.getShortName() + ": " + building.getFullName());
    	}
    	String[] buildingNames = new String[locations.size()];
    	buildingNames = locations.toArray(buildingNames);
    	// create dropDown menus
    	JComboBox<String> buildingBoxInitial = new JComboBox<String>(buildingNames);
    	JComboBox<String> buildingBoxFinal = new JComboBox<String>(buildingNames);
    	JLabel labelSource = new JLabel("Source Building (Green): ");
    	JLabel labelDest = new JLabel("Destination Building (Red): ");
    	controls.add(labelSource);
    	controls.add(buildingBoxInitial);
    	controls.add(labelDest);
    	controls.add(buildingBoxFinal);	
    	// create buttons
    	JButton findButton = new JButton("Find Path");
     	findButton.addActionListener(e -> {
     		sendChoice(buildingBoxInitial, buildingBoxFinal);
         });
     	JButton randButton = new JButton("Random");
     	randButton.addActionListener(e -> {
     		Random r = new Random();
     		buildingBoxInitial.setSelectedIndex(r.nextInt(model.numBuildings()));
     		buildingBoxFinal.setSelectedIndex(r.nextInt(model.numBuildings()));
     		sendChoice(buildingBoxInitial, buildingBoxFinal);
         });
     	JButton resetButton = new JButton("Reset");
     	resetButton.addActionListener(e -> {
     		buildingBoxInitial.setSelectedIndex(0);
     		buildingBoxFinal.setSelectedIndex(0);
     		sendChoice(buildingBoxInitial, buildingBoxFinal);
         	frame.setSize(new Dimension(1024, 768));
         	frame.pack();
        });
     	controls.add(findButton);
     	controls.add(randButton);
      	controls.add(resetButton);
     	return controls;
    }
     
     /**
 	 * Returns controller with new drop-down menus with source and destination names, 
 	 * random button, find button, and reset button.
 	 * 
 	 * @param startBox the start building box
 	 * @param endBox the end building box
 	 * @requires startBox != null && endBox != null
 	 * @modifies view
 	 * @effects updates view
 	 */
    private void sendChoice(JComboBox<String> startBox, JComboBox<String> endBox) {
    	String start = (String) startBox.getSelectedItem();
 		String end = (String) endBox.getSelectedItem();
 		String startShort = start.split(": ")[0];
 		String endShort = end.split(": ")[0];
 		viewPane.notifyViewer(startShort, endShort);
    }
}
