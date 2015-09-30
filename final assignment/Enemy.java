import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "Enemy" class.
  * Handles all events in relation to the enemy characters and data
  * @author Melissa Li and Hannah Kim
  * @version January 10, 2013
 */
public class Enemy extends Rectangle
{
    private final static String[] IMAGE_NAMES = {"Images/Homework.png", "Images/Assignment.png", "Images/Quiz.png", "Images/Pop Quiz.png",
	"Images/Textbook.png", "Images/Test.png", "Images/Book Report.png", "Images/Project.png", "Images/EQAO.png", "Images/Exam.png"};
    private final static String[] IMAGE_ATTACKING_NAMES = {};
    private final static int[] SPEEDS = {7, 9, 17, 17, 4, 7, 5, 4, 7, 1};
    private final static int[] HEALTH_POINTS = {200, 300, 125, 125, 500, 1000, 1200, 800, 2000, 5000};
    private int type; //index
    private int speed; // speed variable for each enemy
    private int health; // health variable for each enemy
    private int strengthPoints; // strength point variable
    private boolean isActive;
    private Image image; //image variable for each enemy

    public Enemy (int y, int type, int arrivalTime, Component parentFrame)
    {
	super (SPEEDS [type] * arrivalTime + (int) ((Math.random () * 20) + 840), y, 0, 0);
	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	speed = SPEEDS [type];
	health = HEALTH_POINTS [type];
	strengthPoints = 25;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame));
    }


    public boolean isActive ()
    {
	if (x > 840 || HEALTH_POINTS [type] == 0)
	    return false;
	else
	    return true;

    }


    public boolean defenderReached (int x)
    {
	return false;
    }


    public void move ()
    {
	x -= speed;
    }


    /** Draws the enemy in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	g.drawImage (image, x, y, null);
	//System.out.println (x + "," + y);
    }
} // Enemy class
