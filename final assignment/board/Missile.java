import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "Missile" class.
  * A brief description of this class
  * @author your name
  * @version date
 */
public class Missile extends Rectangle
{
    private final static String[] IMAGE_NAMES = {"Images/Yellow Pencil.png", "Images/Red Pencil.png", "Images/Green Pencil.png",
	"Images/Pencil.png"};
    private final static int ATTACK_DISTANCE = 30;
    private int type; //index
    private int strengthPoints; // strength point variable
    private Missile[] missiles;
    private Image image; //image variable for each missile
    private Defender[] defenders;
    public Enemy[] enemies;


    public Missile (int x, int y, int type, Component parentFrame)
    {
	super (x, y, 0, 0);

	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	// no health points for flask, ruler, highlighter, pen, gluestick
	// more health points for eraser
	// others have the same health point

	strengthPoints = 25;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame) - ATTACK_DISTANCE, image.getHeight (parentFrame) - ATTACK_DISTANCE);
    }




    /** Draws the Missiles in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	if (defenders.hasHealthLeft ())
	{
	    g.drawImage (image, x, y, null);
	}
    }
} // Missile class
