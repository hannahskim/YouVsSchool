import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

/** The "YouVsSchoolBoard" class.
  * Handles the board play You Vs School
  * @author Melissa Li and Hannah Kim
  * @version December 13, 2011
  */
public class YouVsSchoolBoard extends JPanel implements MouseListener, KeyListener
{
    private Image titleScreen, boardImage;

    private double money;
    private int day;
    private Enemy[] enemies;
    private Timer timer;
    private ArrayList defenders;
    private int selectStatus;


    public final Dimension BOARD_SIZE = new Dimension (840, 560);


    /** Constructs a new YouVsSchool object
      */
    public YouVsSchoolBoard ()
    {
	// Sets up the board area, loads in background images and starts a new game
	setPreferredSize (BOARD_SIZE);
	boardImage = new ImageIcon ("Board.png").getImage ();
	titleScreen = new ImageIcon ("Title Screen.png").getImage ();

	// Add mouse listeners and Key Listeners to the game board
	addMouseListener (this);
	setFocusable (true);
	addKeyListener (this);
	addMouseMotionListener (new MouseMotionHandler ());
	requestFocusInWindow ();
	selectStatus = -1;
	defenders = new ArrayList ();


	enemies = new Enemy [10];
	for (int i = 0 ; i < enemies.length ; i++)
	    enemies [i] = new Enemy ((int) (Math.random () * 20) * 80, (int) (Math.random () * 6) * 80, (int) (Math.random () * 9), this);
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
	    newGame ();
	}
    }



    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);
	// if (day == 0)
	//     g.drawImage (titleScreen, 0, 0, this);
	// else
	// {
       g.drawImage (boardImage, 0, 0, this);
	if (selectStatus != -1)
	{
	    g.setColor (new Color (255, 0, 0, 50));
	    g.fillRect (selectStatus * 80 + 1, 1, 82, 82);
	}
	for (int i = 0 ; i < defenders.size () ; i++)
	{
	    Defender def = (Defender) defenders.get (i);
	    def.draw (g);
	}
	//     String []defenders = {"Piggy Bank.png", "Gluestick.png", "Pencil Cannon.png", "Eraser.png",
	// "Pen.png", "Ruler.png", "Flask.png", "Staple.png", "Highlighter.png", "Pencil Crayon.png"};;
	//     for (int defenderType = 0 ; defenderType < defenders.length ; defenderType++)
	//     {
	//     Image chosen = new ImageIcon (defenders[defenderType]).getImage();
	//         //defenders [defenderType] = new Defender ((int) (Math.random () * 20) * 80, (int) (Math.random () * 6) * 80, (int) (Math.random () * 9), this);
	//     g.drawImage (chosen, (int) defenderType*84 ,(int) 1, this );
	//     }
	// }


	// for (int i = 0 ; i < enemies.length ; i++)
	//     enemies [i].draw (g);
    } // end of PaintComponent


    /** Starts a new game
      */
    public void newGame ()
    {
	day = 1;
	money = 0;
	repaint ();
    }


    /** Starts the level
      */
    public void levelStart ()
    {
	timer = new Timer (100, new TimerEventHandler ());
	timer.start ();
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


    //* Use for trashcan
    /** Monitors mouse movement over the game panel and responds
    */
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	/** Responds to mouse-movement inputs
	  * @param event   The event created by the mouse movement
	*/
	public void mouseMoved (MouseEvent event)
	{
	    // Point pos = event.getPoint ();
	    // repaint (); //Repaint the screen to show any changes
	    //
	    // int pressedOnColumn = event.getX ();
	    // int pressedOnRow = event.getY ();
	    // System.out.println("mousePressed(): Row[" + pressedOnRow + "] Column[" + pressedOnColumn +
	    //   "] " + pos);
	    //
	    // if (pressedOnRow == 20 )
	    // setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
	    // else
	    // setCursor(Cursor.getDefaultCursor ());

	}

    }


    // Mouse events you can listen for since this JPanel is a MouseListener

    /** Responds to a mousePressed event
    *@parameventinformation about the mouse pressed event
    */
    public void mousePressed (MouseEvent event)
    {
	if (selectStatus == -1 && event.getY() < 84)
	    selectStatus = event.getX () / 84;
	else    if (selectStatus == -1 && event.getY() > 84)
	    defenders.remove(0);
	else
	{
	    int row = event.getY () / 84;
	    int col = event.getX () / 84;
	    int x = col * 84;
	    int y = row * 84;
	    defenders.add (new Defender (x, y, selectStatus, this));
	    selectStatus = -1;

	}
   repaint();




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


