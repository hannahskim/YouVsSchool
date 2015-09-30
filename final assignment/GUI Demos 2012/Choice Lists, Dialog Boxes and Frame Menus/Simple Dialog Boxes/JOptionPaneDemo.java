// The "JOptionPaneDemo" class.
// By Ridout
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class JOptionPaneDemo extends JFrame
{
    public JOptionPaneDemo ()
    {
	super ("JOptionPaneDemo");      // Set the frame's name
	setSize (400, 400);     // Set the frame's size
	setLocation (100, 100);
	
	// Add in the DrawingPanel defined below to this frame
	getContentPane ().add (new DrawingPanel (), BorderLayout.CENTER);
	setVisible (true);                // Show the frame
    } // Constructor


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    // Add mouse listeners to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setColor (Color.red);
	    g.drawString ("Click the mouse in this window to see" +
		    " a choice of JOptionPane's", 10, 100);
	} // paint component method
    }


    // Inner class to handle mouse events
    private class MouseHandler extends MouseAdapter
    {
	public void mousePressed (MouseEvent event)
	{
	   Component mainFrame = JOptionPaneDemo.this;
	    // Use a message dialog to show the x, y position of the mouse click
	    JOptionPane.showMessageDialog (mainFrame, "x: " + event.getX () +
		    " y: " + event.getY (), "Mouse Click Position",
		     JOptionPane.INFORMATION_MESSAGE);

	    // Create a dialog to input information
	    String name;
	    name = JOptionPane.showInputDialog (JOptionPaneDemo.this, "Please enter your name");

	    // Show a message using a message dialog
	    if (name == null)
		JOptionPane.showMessageDialog (mainFrame, "You selected Cancel",
			"Message", JOptionPane.WARNING_MESSAGE);
	    else
		JOptionPane.showMessageDialog (mainFrame, "Welcome " + name, "Message",
			JOptionPane.INFORMATION_MESSAGE);

	    // The confirm dialog can be used with an if to check
	    // which button was selected on the dialog boxn
	    if (JOptionPane.showConfirmDialog (mainFrame, "Do you want to Play Again?",
			"Message", JOptionPane.YES_NO_OPTION) ==
		    JOptionPane.YES_OPTION)
		JOptionPane.showMessageDialog (mainFrame, "Good Luck", "Message",
			JOptionPane.INFORMATION_MESSAGE);
	    else
	    {
		JOptionPane.showMessageDialog (mainFrame, "Goodbye", "Message",
			JOptionPane.INFORMATION_MESSAGE);
		hide ();
		System.exit (0);
	    }

	    // Using arrays you can create a multiple object message
	    ImageIcon iconImage = new ImageIcon ("strawberry.gif");
	    Object[] objects = {"Text and ", iconImage, "an Icon Image"};
	    JOptionPane.showMessageDialog (mainFrame, objects,
		    "Multiple Object Message", JOptionPane.INFORMATION_MESSAGE);

	}
    }


    public static void main (String[] args)
    {
	JOptionPaneDemo frame = new JOptionPaneDemo ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
    } // main method
} // JOptionPaneDemo class
