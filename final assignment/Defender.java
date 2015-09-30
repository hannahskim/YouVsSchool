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
    private final String[] IMAGE_NAMES = {"Images/PiggyBank.png", "Images/Gluestick.png", "Images/Pencil Cannon.png", "Images/Eraser.png",
	"Images/Pen.png", "Images/ruler.png", "Images/Flask.png", "Images/Staple.png", "Images/Highlighter.png", "Images/Pencil Crayon.png"};
    private final double[] PRICE = {0.50, 0.75, 1.00, 1.00, 1.25, 1.75, 1.80, 2.00, 2.20, 3.00};
    //private final int[] HEALTH_POINTS = {150, 150, 125, 125, 500, 1000, 1200, 800, 2000, 5000};
    private int type; //index
    //private int speed; // speed variable for each enemy
    private int health; // health variable for each enemy
    private int strengthPoints; // strength point variable
    private boolean isAttacking;
    private Image image; //image variable for each enemy
    private Defender defenders[] ={};

    
    private int money;


    public Defender (int x, int y, int type, Component parentFrame)
    {
	super (x, y, 0, 0);

	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	// no health points for flask, ruler, highlighter, pen, gluestick
	// more health points for eraser
	// others have the same health point

	health = 150;
	strengthPoints = 25;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame));
    }

    public int selectedPiece()
    {
    return 0;
    //return type;
    //if (selectedPiece != EMPTY)
		//g.drawImage (pieces [selectedPiece], draggedXPos, draggedYPos, this);
    }

    public int buyDefender ()
    {
    //Point itemSelected = event.getPoint();
    //if ()
    return 0;
    }

    public boolean containsDefender()
    {
    return false;
    }

    public void placeDefender ()
    {
	// Inner class
	// private class MouseHandler extends MouseAdapter
	// {
	//     public void mousePressed (MouseEvent event)
	//     {
	//         Point selectedPoint = event.getPoint ();
	//     }
	// }
	// money -= PRICE[type];
    }

    public boolean coinSelected()
    {
    //if()
    //return true;
    //else
    return false;
    }
    
    public void collectCoin ()
    {
    // if (coinSelected==true)
    // money += 0.25;
    }


    //.Intersect()


    /** Draws the Defenders in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	g.drawImage (image, x, y, null);
    }
} // Defender class
