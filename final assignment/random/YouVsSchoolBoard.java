import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "ConnectFourBoard" class.
  * Handles the board play You Vs School
  * @author Melissa Li and Hannah Kim
  * @version December 13, 2011
  */
public class YouVsSchoolBoard extends JPanel implements MouseListener,
    KeyListener
{
    // For drawing the grid
    private final int SQUARE_SIZE = 80;
    private final int NO_OF_ROWS = 6;
    private final int NO_OF_COLUMNS = 10;

    public final Dimension BOARD_SIZE =
	new Dimension ((NO_OF_COLUMNS + 2) * SQUARE_SIZE + 1,
	    (NO_OF_ROWS + 1) * SQUARE_SIZE + 1);

    private double money;
    private int day;
    private int x;
    private int y;
    private Enemy[] enemies;
    private Timer timer;

    /** Constructs a new YouVsSchool object
      */
    public YouVsSchoolBoard ()
    {
	// Sets up the board area, loads in piece images and starts a new game
	setPreferredSize (BOARD_SIZE);
	setBackground (new Color (225, 225, 225));

	// Add mouse listeners and Key Listeners to the game board
	addMouseListener (this);
	setFocusable (true);
	addKeyListener (this);
	requestFocusInWindow ();

	enemies = new Enemy [10];
	for (int i = 0 ; i < enemies.length ; i++)
	    enemies [i] = new Enemy ((int) (Math.random () * 20) * 80, (int) (Math.random () * 6) * 80, 1, this);
	timer = new Timer (500, new TimerEventHandler ());
	timer.start ();
    }


    /** An inner class to deal with the timer events
	*/
    private class TimerEventHandler implements ActionListener
    {

	/** The following method is called each time a timer event is
	 * generated (every TIME_INTERVAL milliseconds in this example)
	 * @param event the Timer event
	 */
	public void actionPerformed (ActionEvent event)
	{
	    for (int i = 0 ; i < enemies.length ; i++)
		enemies [i].move ();
		repaint();
	}
    }


    // Draw all the grid squares
    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);

	for (int row = 0 ; row < NO_OF_ROWS ; row++)
	{
	    for (int column = 0 ; column < NO_OF_COLUMNS ; column++)
	    {
		// Convert each square's row and column number to the x and y positions on the screen
		int xPos = (column + 1) * SQUARE_SIZE;
		int yPos = row * SQUARE_SIZE;
		g.setColor (Color.GREEN);
		g.drawRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
	    }
	}
	
	for (int i = 0 ; i < enemies.length ; i++)
	    enemies [i].draw (g);
    }


    /** Starts a new game
      */
    public void newGame ()
    {

    }


    /** Delays the given number of milliseconds
    *@param milliSec The number of milliseconds to delay
    */
    private void delay (int milliSec)
    {
	try
	{
	    Thread.sleep (milliSec);
	}
	catch (InterruptedException e)
	{
	}
    }


    // Keyboard events you can listen for since this JPanel is a KeyListener

    /** Responds to a keyPressed event
    * @param event information about the key pressed event
    */

    public void keyPressed (KeyEvent event)
    {
    }


    // Extra methods needed since this game board is a KeyListener
    public void keyReleased (KeyEvent event)
    {
    }


    public void keyTyped (KeyEvent event)
    {
    }



    // Mouse events you can listen for since this JPanel is a MouseListener

    /** Responds to a mousePressed event
    *@parameventinformation about the mouse pressed event
    */
    public void mousePressed (MouseEvent event)
    {

    }


    public void mouseReleased (MouseEvent event)
    {
    }


    public void mouseClicked (MouseEvent event)
    {
    }


    public void mouseEntered (MouseEvent event)
    {
    }


    public void mouseExited (MouseEvent event)
    {
    }
}


