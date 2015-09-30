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

    private final String[] IMAGE_NAMES = {"Piggy Bank.png", "Gluestick.png", "Pencil Cannon.png", "Eraser.png",
	"Pen.png", "Ruler.png", "Flask.png", "Staple.png", "Highlighter.png", "Pencil Crayon.png"};
    private final double[] PRICE = {0.50, 0.75, 1.00, 1.00, 1.25, 1.75, 1.80, 2.00, 2.20, 3.00};
    private final int[] HEALTH_POINTS = {150, 150, 150, 500, 150, -1, -1, 150, -1, 150};
    private int type; //index
    private int health; // health variable for each enemy
    private int strengthPoints; // strength point variable
    private boolean isAttacking, highlightDef;
    private Image image; //image variable for each enemy
    private Defender[] defenders;
    private int selectedPiece; // Index of a selected defender
    private Point lastPoint;

    private int money;


    public Defender (int x, int y, int type, Component parentFrame)
    {
	super (x, y, 0, 0);
	this.centre = centre;
	this.size = size;

	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	// no health points for flask, ruler, highlighter, pen, gluestick
	// more health points for eraser
	// others have the same health point

	health = 150;
	strengthPoints = 25;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame));
    }


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


    public int buyDefender ()
    {
	//Point itemSelected = event.getPoint();
	//if ()
	return 0;
    }


    public boolean containsDefender ()
    {
	return false;
    }


    // public void placeDefender (MouseEvent event)
    // {
    // public void placeDefender (MouseEvent event)
    // {
    //     int pressedOnColumn = event.getX ();
    //     int pressedOnRow = event.getY ();
    //     if (selectedPiece () > -1)
    //     {
    //         Image defender = IMAGE_NAMES [selectedPiece ()];
    //         g.drawImage (defender, pressedOnRow, pressedOnColumn, this);
    //     }
    // }
    //
    //     // money -= PRICE[type];
    // }


    public boolean coinSelected (MouseEvent event)
    {
	Point pressedOnPoint = event.getPoint ();
	//if (
	return false;
    }


    public void collectCoin ()
    {
	if (true)
	    money += 0.25;
    }


    //.Intersect()

    /** Draws the Defenders in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	g.drawImage (image, x, y, null);
	// public void placeDefender (MouseEvent event)
	// {
	//     int pressedOnColumn = event.getX ();
	//     int pressedOnRow = event.getY ();
	//     if (selectedPiece > -1)
	//     {
	//         Image defender = IMAGE_NAMES [selectedPiece];
	//         g.drawImage (defender, pressedOnRow, pressedOnColumn, this);
	//     }
	// }
    }
} // Defender class


