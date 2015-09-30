/** A class to keep track of a simple Rotating Image
  * @author ICS3U
  * @version July 2009
  */

import java.awt.*;
import javax.swing.*;
  
public class RotatingImage
{
    private Image image;
    private Container container;

    /* Creates a new RotatingImage object
     * @param fileName the file name for the image
     * @param container the container (e.g. JFrame) the image will be drawn in
     */
    public RotatingImage (String fileName, Container container)
    {
	image = new ImageIcon (fileName).getImage ();
	this.container = container;
    }

    /** Draws this image in the given Graphics context
      * @param g the graphics object to draw this image in
      * @param x the x coordinate of the upper left corner
      * @param y the y coordinate of the upper left corner
      * @param angle the angle (in degrees) to rotate this image
      */
    public void draw (Graphics g, int x, int y, int angle)
    {
	// We need a Graphics2D object for rotate
	Graphics2D g2D = (Graphics2D) g;
	       // Used to prevent jagged edges on circle pieces
       g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
			RenderingHints.VALUE_ANTIALIAS_ON);
	
	// Find the angle in radians and the x and y position of the centre of the object
	double angleInRadians = Math.toRadians (angle);
	int centreX = x + image.getWidth (container) / 2;
	int centreY = y + image.getHeight (container) / 2;

	// Rotate the graphic context, draw the image and then rotate back
	g2D.rotate (angleInRadians, centreX, centreY);
	g.drawImage (image, x, y, container);
	g2D.rotate (-angleInRadians, centreX, centreY);
    }
}
