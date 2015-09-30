import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** The "RotateDemo" class.
  * Demonstrates how to rotate an image by pressing left/right arrow keys.
  * Note: requires separate java file/clase RotatingImage to be in same directory
  * @author G. Ridout
 */


public class RotateDemo extends JFrame
{
    final static int ROTATE_ANGLE = 30;
    public RotateDemo ()
    {
	// Set up the frame and the grid
	super ("Rotating Demo");
	setLocation (100, 100);

	// Set up for the game
	Container contentPane = getContentPane ();
	contentPane.add (new DrawingPanel (), BorderLayout.CENTER);
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	private RotatingImage image;
	private int angle;

	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    setPreferredSize (new Dimension (300, 300));
	    // Turtle image is from www.iconshock.com
	    image = new RotatingImage ("turtle.png", this);
	    angle = 0;
	    // Sets up for keyboard input (arrow keys) on this panel
	    this.setFocusable (true);
	    this.addKeyListener (new KeyHandler ());
	    this.requestFocusInWindow ();
	}

	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);

	    image.draw (g, 100, 100, angle);
	} // paint component method

	// Inner class to handle key events
	private class KeyHandler extends KeyAdapter
	{
	    public void keyPressed (KeyEvent event)
	    {
		// Change the currentRow and currentColumn of the player
		// based on the key pressed
		if (event.getKeyCode () == KeyEvent.VK_RIGHT)
		{
		    angle += ROTATE_ANGLE; // rotate clockwise
		}
		else if (event.getKeyCode () == KeyEvent.VK_LEFT)
		{
		    angle -= ROTATE_ANGLE; // rotate counter-clockwise
		}
		// Repaint the screen after the change
		repaint ();
	    }
	}

    }


    // Sets up the main frame for the Game
    public static void main (String[] args)
    {
	RotateDemo frame = new RotateDemo ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // RotateDemo class


