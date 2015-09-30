/** The "SwingTimer" class.
  * Demonstrates a Swing Timer
  * @author Ridout
  * @version November 2007
  */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.Timer;

public class SwingTimer extends JFrame
{
    static final Font LABEL_FONT = new Font ("Arial", Font.PLAIN, 16);
    static final Font TIME_FONT = new Font ("Arial", Font.PLAIN, 44);

    public SwingTimer ()
    {
	super ("Swing Timer");
	setLocation(100,100);

	// Add in the DrawingPanel defined below to this frame
	getContentPane ().add (new DrawingPanel (), BorderLayout.CENTER);
    } // Constructor


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel implements MouseListener
    {
	private Timer timer;
	private boolean timerOn;
	private int time;
	private int timeAllowed;

	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    setPreferredSize (new Dimension (350, 300));

	    // Set up the timer variables
	    timerOn = false;
	    time = 0;
	    timeAllowed = 100;   //  10 seconds in this example

	    // Create a timer object. This object generates an event every
	    // 1/10 second (100 milliseconds)
	    // The TimerEventHandler object that will handle this timer
	    // event is defined below as a inner class
	    timer = new Timer (100, new TimerEventHandler ());

	    // Add mouse listeners to the drawing panel
	    this.addMouseListener (this);
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setFont (LABEL_FONT);
	    g.setColor (Color.BLUE);
	    if (timerOn)
		g.drawString ("Press the left mouse button to stop the timer", 10, 150);
	    else
		g.drawString ("Press the left mouse button to start the timer", 10, 150);
	    g.drawString ("Press the right mouse button to reset the timer", 10, 250);

	    // You should add in a method to display the time in a format
	    // with minutes and seconds such as "0:00"  Hint: use / and %
	    g.setFont (TIME_FONT);
	    g.setColor (Color.RED);
	    g.drawString ("Time: " + (time / 10.0), 50, 100);

	} // paint component method

	// An inner class to deal with the timer events
	private class TimerEventHandler implements ActionListener
	{
	    // The following method is called each time a timer event is
	    // generated (every 100 milliseconds in this example)
	    // Put your code here that handles this event
	    public void actionPerformed (ActionEvent event)
	    {
		// Time is up!
		if (time >= timeAllowed && timerOn)
		{
		    timerOn = false;
		    timer.stop ();
		    JOptionPane.showMessageDialog (SwingTimer.this,
			    "Time is Up", "Timer", JOptionPane.INFORMATION_MESSAGE);
		}
		else
		{
		    // Increment the time (you could also count down)
		    time++;

		    // Beep every second for this demo
		    // You probably don't want this annoying beep in your game
		    if (time % 10 == 0)
			Toolkit.getDefaultToolkit ().beep ();

		    // Repaint the screen
		    repaint ();
		}
	    }
	}

	// Use the mouse buttons to reset the timer and turn the timer on/off
	public void mousePressed (MouseEvent event)
	{
	    // If right mouse button is clicked, reset the time
	    if (event.getButton () == MouseEvent.BUTTON3)
	    {
		time = 0;
	    }
	    // Toggle the timer on and off with left mouse button
	    else if (timerOn)
	    {
		timerOn = false;
		timer.stop ();
	    }
	    else
	    {
		timerOn = true;
		timer.start ();
	    }
	    repaint ();
	}

	// Dummy methods needed since this Panel is a MouseListener
	public void mouseClicked (MouseEvent e)
	{
	}
	public void mouseEntered (MouseEvent e)
	{
	}
	public void mouseExited (MouseEvent e)
	{
	}
	public void mouseReleased (MouseEvent e)
	{
	}
    }


    public static void main (String[] args)
    {
	SwingTimer frame = new SwingTimer ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // SwingTimer class


