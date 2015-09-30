import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "Defender" class.
  * A brief description of this class
  * @author your name
  * @version date
 */
public class Defender extends Rectangle
{
    protected Point centre;
    protected int size;

    private final String[] IMAGE_NAMES = {"Images/Piggy Bank.png", "Images/Gluestick.png", "Images/Pencil Cannon.png", "Images/Eraser.png",
	"Images/Pen.png", "Images/Ruler.png", "Images/Flask.png", "Images/Staple.png", "Images/Highlighter.png", "Images/Pencil Crayon.png"};
    private final double[] PRICE = {0.50, 0.75, 1.00, 1.00, 1.25, 1.75, 1.80, 2.00, 2.20, 3.00};
    private final int[] HEALTH_POINTS = {150, 150, 150, 500, 150, -1, -1, 150, -1, 150};
    private final String[] PIGGY_IMAGE = {"Images/Piggy Bank2.png"};
    private final String[] PENCIL_CANNON_IMAGES = {"Images/Pencil Cannon2.png"};
    private final String[] ERASER_IMAGE = {"Images/Eraser2.png", "Images/Eraser3.png"};
    private final String[] PEN_IMAGE = {"Images/Pen2.png"};
    private final String[] RULER_IMAGE = {"Images/Ruler2.png"};
    private final String[] FLASK_IMAGE = {"Images/Flask2.png", "Images/Flask3.png"};
    private final String[] STAPLE_IMAGE = {"Images/Staple2.png"};
    private final String[] PENCIL_CRAYON_IMAGE = {"Images/Pencil Crayon2.png"};
    private int type; //index
    private int health; // health variable for each enemy
    private int strengthPoints; // strength point variable
    private boolean beingAttacked;
    private Image image; //image variable for each enemy
    private Image image2;
    private Defender[] defenders;
    private int selectedPiece; // Index of a selected defender
    private Point lastPoint;
    private boolean isDefending;

    private int money;


    public Defender (int x, int y, int type, Component parentFrame)
    {
	super (x, y, 0, 0);
	this.centre = centre;
	this.size = size;

	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();

	strengthPoints = 25;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame));
    }


    // Check if a point is contained within a piece
    public boolean contains (Point p)
    {
	int distance = (int) (Math.sqrt (Math.pow (p.x - centre.x, 2) +
		    Math.pow (p.y - centre.y, 2)));
	return (distance < size / 2);
    }


    public void setPosition (int x, int y)
    {
	centre = new Point (x, y);
    }


    public boolean containsDefender (MouseEvent event)
    {
	Point pressedOnPoint = event.getPoint ();
	for (int i = 0 ; i < defenders.length ; i++)
	{
	    if (defenders [i].contains (pressedOnPoint) == true)
		return true;
	}
	return false;
    }


    public boolean beingAttacked ()
    {
	//if (
	return true;
    }


    public boolean isAlive ()
    {
	if (HEALTH_POINTS [type] == 0)
	    return false;
	else
	    return true;
    }


    public int selectedPiece (MouseEvent event)
    {
	Point selectedPoint = event.getPoint ();
	for (int i = 0 ; i < IMAGE_NAMES.length ; i++)
	    if (defenders [i].contains (selectedPoint))
	    {
		selectedPiece = i;
		lastPoint = selectedPoint;
		System.out.println ("Selected Piece #" + i);
		return i;
	    }
	return -1;
    }


    public void injure (int strength)
    {
	health -= strength;
    }


    public boolean hasHealthLeft ()
    {
	if (health > 0)
	    return true;
	else
	    return false;
    }


    public void defend ()
    {

    }


    public boolean coinSelected (MouseEvent event)
    {
	Point pressedOnPoint = event.getPoint ();
	//for (int i = 0 ; i < defenders.length ; i++)
	// {
	// if (PIGGY_IMAGE [0].contains (pressedOnPoint) == true)
	//     return true;
	// }
	return false;
    }


    //
    //
    // public int collectCoin (MouseEvent event)
    // {
    //     Point pressedOnPoint = event.getPoint ();
    //     //if (coinSelected())
    //     money += 0.25;
    //     return money;
    // }


    //.Intersect()

    /** Draws the Defenders in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	g.drawImage (image, x, y, null);

	// Draws the defender's health bar
	g.setColor (Color.BLUE);
	g.fillRect (x + 15, y - 10, health / 3, 10);
	g.setColor (Color.BLACK);
	g.drawRect (x + 15, y - 10, HEALTH_POINTS [type] / 3, 10);
    }
} // Defender class


