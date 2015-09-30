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

    private final static String[] IMAGE_NAMES = {"Images/Piggy Bank.png", "Images/Gluestick.png", "Images/Pencil Cannon.png", "Images/Eraser.png",
	"Images/Pen.png", "Images/Ruler.png", "Images/Flask.png", "Images/Staple.png", "Images/Highlighter.png", "Images/Pencil Crayon.png"};
    public final static double[] PRICE = {0.50, 0.75, 1.00, 1.00, 1.25, 5.00, 1.80, 2.00, 2.20, 3.00};
    private final static int ATTACK_DISTANCE = 30;
    public final static int[] HEALTH_POINTS = {150, 150, 150, 500, 150, -1, -1, 150, -1, 150};
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
    private int strengthPoints; // strength point variable
    private boolean beingAttacked;
    private Image image; //image variable for each defender
    private Image image2;
    private Defender[] defenders;
    private int selectedPiece; // Index of a selected defender
    private Point lastPoint;
    private boolean isDefending;
    private int health;
    private final int highlighterSpeed = 10;
    int highlightX;
    int highlightY;
    int highlightLength = 0;
    int highlightWidth = 20;

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
	beingAttacked = false;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame) - ATTACK_DISTANCE, image.getHeight (parentFrame) - ATTACK_DISTANCE);
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
	// if (type == HIGHLIGHTER)
	// {
	//     highlightX = x;
	//     highlightY = y + YouVsSchoolBoard.RECTANGLE_HEIGHT / 2;
	// 
	//     x += highlighterSpeed;
	//     highlightLength += highlighterSpeed;
	// }
	// if (type == RULER)
	// {
	// }
    }


    /** Draws the Defenders in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	if (this.hasHealthLeft ())
	{
	    g.drawImage (image, x, y, null);

	    if (type == HIGHLIGHTER)
	    {
		g.setColor (Color.YELLOW);
		g.fillRect (x, y, highlightLength, highlightWidth);
	    }

	    // Draws the defender's health bar
	    // if (type != HIGHLIGHTER || type != RULER || type != PEN || type != FLASK)
	    // {
	    //     g.setColor (Color.BLUE);
	    //     g.fillRect (x + 15, y - 10, health / 3, 10);
	    //     g.setColor (Color.BLACK);
	    //     g.drawRect (x + 15, y - 10, HEALTH_POINTS [type] / 3, 10);
	    // }
	}
    }
} // Defender class


