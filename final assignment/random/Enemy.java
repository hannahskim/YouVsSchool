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
    private final String[] IMAGE_NAMES = {"Images/Homework.png", "Images/Assignment.png", "Images/Quiz.png", "Images/Pop Quiz.png",
	"Images/Textbook.png", "Images/Test.png", "Images/Book Report.png", "Images/Project.png", "Images/EQAO.png", "Images/Exam.png"};
    private final int[] SPEEDS = {20, 25, 40, 40, 5, 20, 15, 10, 20, 3};
    private final int[] HEALTH_POINTS = {200, 300, 125, 125, 500, 1000, 1200, 800, 2000, 5000};
    private int type; //index
    private int speed; // speed variable for each enemy
    private int health; // health variable for each enemy
    private int strengthPoints; // strength point variable
    private boolean isAttacking;
    private Image image; //image variable for each enemy
    private Enemy enemies[] = {};

    public Enemy (int x, int y, int type, Component parentFrame)
    {
	super (x, y, 0, 0);

	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	speed = SPEEDS [type];
	health = HEALTH_POINTS [type];
	strengthPoints = 25;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame));
    }


    /* Deals with the enemy attacking the defender
     */
    public void attackDefender ()
    {
    }


    // public boolean endReached (int x)
    // {
    // }
    //
    //
    // public boolean defenderReached (int x)
    // {
    // }


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
