import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "Defender" class.
  * Handles all events in relation to the defender characters and data
  * @author Melissa Li and Hannah Kim
  * @version January 10, 2013
 */
public class Defender extends Rectangle
{
    //variables for defender
    private final static String[] IMAGE_NAMES = {"Images/Piggy Bank.png", "Images/Gluestick.png", "Images/Pencil Cannon.png", "Images/Eraser.png",
	"Images/Pen.png", "Images/Ruler.png", "Images/Flask.png", "Images/Staple.png", "Images/Highlighter.png", "Images/Pencil Crayon.png"};
    public final static double[] PRICE = {0.50, 0.75, 1.00, 1.00, 1.25, 1.75, 1.80, 2.00, 2.20, 3.00};
    private final static int ATTACK_DISTANCE = 30;
    private final static int[] HEALTH_POINTS = {150, 150, 150, 500, 150, -1, -1, 150, -1, 150};
    public final static int PIGGY_BANK = 0;
    public final static int GLUESTICK = 1;
    public final static int PENCIL_CANNON = 2;
    public final static int ERASER = 3;
    public final static int PEN = 4;
    public final static int RULER = 5;
    public final static int FLASK = 6;
    public final static int STAPLE = 7;
    public final static int HIGHLIGHTER = 8;
    public final static int PENCIL_CRAYON = 9;
   
    private int type; //index
    private int health; // health variable for each enemy
    private int strengthPoints; // strength point variable
    private boolean beingAttacked;
    private Image image; //image variable for each enemy
    private Defender[] defenders;
    private int selectedPiece; // Index of a selected defender
    private boolean isDefending;


    public Defender (int x, int y, int type, Component parentFrame)
    {
	super (x, y, 0, 0);

	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	// no health points for flask, ruler, highlighter, pen, gluestick
	// more health points for eraser
	// others have the same health point

	// Declare variables
	health = 150;
	strengthPoints = 25;
	beingAttacked = false;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame) - ATTACK_DISTANCE, image.getHeight (parentFrame) - ATTACK_DISTANCE);
    }

    /** Decrease the defenders' health when attacked by enemies
    */
    public void injure (int strength)
    {
	health -= strength;
    }

    /** checks if the defenders have any health left
    */
    public boolean hasHealthLeft ()
    {
	if (health > 0)
	    return true;
	else
	    return false;
    }

    /** Draws the Defenders in a Graphics context
      * @param g Graphics to draw the defender in
       */
    public void draw (Graphics g)
    {
	if (this.hasHealthLeft ())
	{
	    g.drawImage (image, x, y, null);

	    // Draws the defender's health bar
	    g.setColor (Color.BLUE);
	    g.fillRect (x + 15, y - 10, health * 60/ HEALTH_POINTS [type], 10);
	    g.setColor (Color.BLACK);
	    g.drawRect (x + 15, y - 10, 60, 10);
	}
    }
} // Defender class


