/** Demonstrates a Swing Timer to do background tasks
  * @author ICS3U
  * @version December 2012
  */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class FollowTheMouse extends JFrame
{
    public FollowTheMouse ()
    {
	super ("Follow the Mouse");
	setLocation (100, 100);

	// Add in the DrawingPanel defined below to this frame
	getContentPane ().add (new DrawingPanel (), BorderLayout.CENTER);
    } // Constructor


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	private final int MAX_DISTANCE = 10;
	private final int TIME_INTERVAL = 100;
	private Timer timer;
	private Point mousePos;
	private Image turtle;
	private Point turtlePos;

	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    setPreferredSize (new Dimension (700, 600));

	    // Load in the turtle image and set the initial position
	    turtle = new ImageIcon ("turtle.png").getImage ();
	    turtlePos = new Point (100, 100);
	    mousePos = new Point (0, 0); // Default value to start

	    // Add mouse motion listeners to the drawing panel
	    addMouseMotionListener (new MouseMotionHandler ());

	    // Create a timer object. This object generates an event every
	    // TIME_INTERVAL milliseconds
	    // The TimerEventHandler object that will handle this timer
	    // event is defined below as a inner class
	    timer = new Timer (TIME_INTERVAL, new TimerEventHandler ());
	    timer.start ();
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);

	    // Draw the turtle in its current position
	    g.drawImage (turtle, turtlePos.x - turtle.getWidth (this) / 2,
		    turtlePos.y - turtle.getHeight (this) / 2, this);

	} // paint component method

	/** An inner class to deal with the timer events
	*/
	private class TimerEventHandler implements ActionListener
	{

	    /** The following method is called each time a timer event is
	     * generated (every TIME_INTERVAL milliseconds in this example)
	     * Put your code here that handles this event
	     * @param event the Timer event
	     */
	    public void actionPerformed (ActionEvent event)
	    {
		// Display the timestamp for the Timer event
		// This is a long value (in milliseconds)
		// System.out.println (event.getWhen());

		// Move the turtle towards the mouse pointer
		// MAX_DISTANCE is the maximum movement each time
		int dx = mousePos.x - turtlePos.x;
		int dy = mousePos.y - turtlePos.y;
		double distance = Math.sqrt (dx * dx + dy * dy);

		if (distance <= MAX_DISTANCE)
		{
		    turtlePos.x += dx;
		    turtlePos.y += dy;

		    // Give a message when the turtle gets the mouse
		    if (dx == 0 && dy == 0)
		    {
			timer.stop ();
			int response = JOptionPane.showConfirmDialog (DrawingPanel.this,
				"Do you want to try again?",
				"Gotcha!!", JOptionPane.YES_NO_OPTION);
			if (response == JOptionPane.YES_OPTION)
			    timer.start ();

		    }
		}
		else
		{
		    double reduceDistBy = MAX_DISTANCE / distance;
		    turtlePos.x += dx * reduceDistBy;
		    turtlePos.y += dy * reduceDistBy;
		}

		// Repaint the screen
		repaint ();
	    }
	}

	/** Inner Class to handle mouse movements
	*/
	private class MouseMotionHandler extends MouseMotionAdapter
	{
	    public void mouseMoved (MouseEvent event)
	    {
		//Update the location of the mouse every time it moves
		mousePos = event.getPoint ();
	    }
	}
    }


    public static void main (String[] args)
    {
	FollowTheMouse frame = new FollowTheMouse ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // SwingTimer class


