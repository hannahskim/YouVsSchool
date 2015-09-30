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
    // Variables for enemies
    public final static String[] IMAGE_NAMES = {"Images/Homework.png", "Images/Assignment.png", "Images/Quiz.png", "Images/Quiz.png",
	"Images/Book Report.png", "Images/Test.png", "Images/Textbook.png", "Images/Project.png", "Images/EQAO.png", "Images/Exam.png"};
    private final static String[] IMAGE_ATTACKING_NAMES = {"Images/Homework Attack.png", "Images/Assignment Attack.png", "Images/Quiz Attack.png", "Images/Quiz Attack.png",
	"Images/Textbook Attack.png", "Images/Test Attack.png", "Images/Book Report Attack.png", "Images/Project Attack.png", "Images/EQAO Attack.png", "Images/Exam.png"};
    private final static int[] SPEEDS = {7, 9, 17, 17, 4, 7, 4, 4, 5, 1};
    private final static int[] HEALTH_POINTS = {200, 300, 125, 125, 500, 1000, 1200, 800, 2000, 5000};
    public final static int[] STRENGTH_POINTS = {25, 25, 25, 25, 25, 25, 25, 25, 25, 100};
    public static final int HOMEWORK = 0;
    public static final int ASSIGNMENT = 1;
    public static final int QUIZ = 2;
    public static final int POP_QUIZ = 3;
    public static final int BOOK_REPORT = 4;
    public static final int TEST = 5;
    public static final int TEXTBOOK = 6;
    public static final int PROJECT = 7;
    public static final int EQAO = 8;
    public static final int EXAM = 9;

    private final static int ATTACK_DISTANCE = 30;
    private int type; //index
    private int speed; // speed variable for each enemy
    private int health; // health variable for each enemy
    public static int strength; // strength point variable
    private boolean isAttacking;
    private int attackSequence;
    private Image image; //image variable for each enemy
    private Image attackingImage;


    public Enemy (int y, int type, int arrivalTime, Component parentFrame)
    {
	super (SPEEDS [type] * arrivalTime + (int) ((Math.random () * 20) + 840), y, 0, 0);
	image = new ImageIcon (IMAGE_NAMES [type]).getImage ();
	attackingImage = new ImageIcon (IMAGE_ATTACKING_NAMES [type]).getImage ();
	speed = SPEEDS [type];
	health = HEALTH_POINTS [type];
	strength = STRENGTH_POINTS [type];
	isAttacking = false;
	attackSequence = 1;

	// Set the size of the enemy based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame) - ATTACK_DISTANCE);
    }

    /** checks if the enemies reach the end
    */
    public boolean isActive ()
    {
	if (x > 840)
	    return false;
	else
	    return true;

    }

    /** Checks if the enemies have any health left
    */
    public boolean hasHealthLeft ()
    {
	if (health > 0)
	    return true;
	else
	    return false;
    }

    /** Starts enemies from attacking
    */
    public void startAttacking ()
    {
	isAttacking = true;

    }

    /** Stops the enemies from attacking
    */
    public void stopAttacking ()
    {
	isAttacking = false;
    }

    /** Decrease the enemies health when they are being attacked
    */
    public void injure (int strength)
    {
	health -= strength;
    }

    /** Kills the enemies
    */
    public void kill ()
    {
	health = 0;
    }


    /** Move the enemies and check if they can move anymore
    */
    public boolean move ()
    {
	if (!this.isAttacking)
	    x -= speed;
	else
	{
	    if (attackSequence == 1)
	    {
		attackSequence = 2;
	    }
	    else
	    {
		attackSequence = 1;
	    }
	}
	if (x < YouVsSchoolBoard.YOU_X)
	    return true;
	return false;
    }


    /** Draws the enemy in a Graphics context
      * @param g Graphics to draw the enemy in
       */
    public void draw (Graphics g)
    {
	// If the enemy is on screen and is not dead
	if (this.isActive () == true && this.hasHealthLeft ())
	{

	    // Animates attack sequence
	    if (this.isAttacking && attackSequence == 1)
	    {
		g.drawImage (attackingImage, x, y, null);
	    }

	    else
	    {
		g.drawImage (image, x, y, null);
	    }

	    // Draws the enemy's health bar
	    g.setColor (Color.RED);
	    g.fillRect (x + 15, y - 10, health * 55 / HEALTH_POINTS [type], 10);
	    g.setColor (Color.BLACK);
	    g.drawRect (x + 15, y - 10, 55, 10);
	}
    }
} // Enemy class
