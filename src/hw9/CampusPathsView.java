package hw9;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import hw5.LabeledEdge;
import hw7.MinimumCostPathFinder.Path;
import hw8.CampusPathsModel;
import hw8.Coordinates;

/** <b>CampusPathsView</b> is the viewer of the Campus Paths Application. A map of the campus
 * is displayed with shortest path markings and the total distance of the path.
 * 
 * @author Bryan Yue
 * @version 1.0
 */
public class CampusPathsView extends JPanel {
	// Note: CampusPathsView is not an ADT so no abstraction function or representational invariant
	// was specified.	
	private static final long serialVersionUID = 1L;
	private CampusPathsModel model;
	private Path<Coordinates> path;
	private BufferedImage map;
	private JLabel distanceText; 
	
	/**
	 * Creates a new CampusPathsView.
	 *
	 * @param model the CampusPathsModel the view communicates with.
	 * @param mapFile the String filename of the map picture.
	 * @effects creates a new CampusPathsView.
	 */
	public CampusPathsView (CampusPathsModel model, String mapFile) throws IOException {
		setLayout(new BorderLayout());
		this.path = new Path<Coordinates>();
		this.model = model;
		this.map = ImageIO.read(new File(mapFile));
		distanceText = new JLabel("Total Distance: 0.0", SwingConstants.CENTER);
		JPanel distancePanel = new JPanel();
		distancePanel.add(distanceText);
		add(distancePanel, BorderLayout.SOUTH);
	}
	
	/**
	 * Notifies this of changes in start and end location.
	 *
	 * @param startAbbrev the abbreviation of the source building.
	 * @param endAbbrev the abbreviation of the destination building.
	 * @modifies this
	 * @effects updates view
	 */
	public void notifyViewer(String startAbbrev, String endAbbrev) {
		path = model.shortestPath(startAbbrev, endAbbrev);
		repaint();		
	}
	
	/**
	 * Sets the path in the view.
	 *
	 * @param g the Graphics
	 * @requires g != null
	 * @modifies this
	 * @effects repaints this with updated shortest path and distance.
	 */
    @Override
	public void paintComponent(Graphics g) {
    	 ArrayList<LabeledEdge<Coordinates, Double>> pathList = path.getPath();
		 Graphics2D g2 = (Graphics2D) g;
		 super.paintComponent(g);
		 int windowWidth = getWidth();
		 int windowHeight = getHeight();
		 double ratioX = (double) windowWidth / map.getWidth();
		 double ratioY = (double) windowHeight / map.getHeight();
		 g2.drawImage(map, 0, 0, windowWidth, windowHeight, null);
		 if (pathList.size() > 0) {
			 g2.setColor(Color.MAGENTA);
			 for (LabeledEdge<Coordinates, Double> edge : pathList) {
				 Coordinates start = edge.getSourceData();
				 Coordinates end = edge.getDestData();
				 g2.setStroke(new BasicStroke(2));
				 g2.drawLine((int) Math.round(start.getX() * ratioX), 
						 (int) Math.round(start.getY() * ratioY), 
						 (int) Math.round(end.getX() * ratioX), 
						 (int) Math.round(end.getY() * ratioY));
			 }
			 g2.setColor(Color.GREEN);
			 Coordinates start = pathList.get(0).getSourceData(); 
			 Coordinates end = pathList.get(pathList.size() - 1).getSourceData(); 
			 Ellipse2D.Double startPoint = new Ellipse2D.Double(start.getX() * 
					 ratioX, start.getY() * ratioY, ratioX * 25, ratioY * 25);
			 Ellipse2D.Double endPoint = new Ellipse2D.Double(end.getX() * 
					 ratioX, end.getY() * ratioY, ratioX * 25, ratioY * 25);
			 g2.fill(startPoint);
			 g2.draw(startPoint);
			 g2.setColor(Color.RED);
			 g2.fill(endPoint);
			 g2.draw(endPoint);
		 }
		 distanceText.setText(String.format("Total Distance: %.3f Feet", path.getCost()));
	 }
}
