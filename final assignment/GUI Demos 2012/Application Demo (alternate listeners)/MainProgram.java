import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/** The "MainProgram" class.
  * Purpose: To demonstrate a simple JFrame application
  * In this version ActionListeners are attached directly to each MenuItem
  * @author Ridout
  * @version November 2007
 */

// A Frame for the main program
public class MainProgram extends JFrame
{
    // Main drawing area inside the main frame
    private DrawingPanel drawingArea;
    private JMenuItem newOption, rulesOption, quitOption, aboutOption;

    /** Constructs a new main frame
      */
    public MainProgram ()
    {
	super ("Top Bar Title");
	setLocation (70, 20);

	// Add in an Icon
	setIconImage (new ImageIcon ("icon.gif").getImage ());

	// We need to get the contentPane for the Frame
	// And then we can add the drawing area to the contentPane
	Container contentPane = getContentPane ();
	drawingArea = new DrawingPanel ();
	contentPane.add (drawingArea, BorderLayout.CENTER);

	// Add in the menus
	addMenus ();
    }


    /** Adds the menus to the main frame
      * Includes adding ActionListeners to respond to menu commands
      */
    private void addMenus ()
    {
	JMenuBar menuBar = new JMenuBar ();
	JMenu gameMenu = new JMenu ("Game");
	gameMenu.setMnemonic ('G');

	newOption = new JMenuItem ("New");
	newOption.addActionListener (new ActionListener ()
	{
	    /** Responds to the New Menu choice
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		newGame ();
	    }
	}
	);

	quitOption = new JMenuItem ("Exit");
	quitOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK));
	quitOption.addActionListener (new ActionListener ()
	{
	    /** Responds to the Exit Menu choice
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		hide ();
		System.exit (0);
	    }
	}
	);
	gameMenu.add (newOption);
	gameMenu.add (quitOption);
	menuBar.add (gameMenu);

	JMenu helpMenu = new JMenu ("Help");
	helpMenu.setMnemonic ('H');
	rulesOption = new JMenuItem ("Rules");
	rulesOption.addActionListener (new ActionListener ()
	{
	    /** Responds to the Help Menu choice
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		JOptionPane.showMessageDialog (drawingArea,
			"Help Line 1\n" +
			"Help Line 2\n" +
			"Help Line 3\n", "Help",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	);
	aboutOption = new JMenuItem ("About...");
	aboutOption.addActionListener (new ActionListener ()
	{
	    /** Responds to the About Menu choice
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		JOptionPane.showMessageDialog (drawingArea,
			event.getActionCommand (), "About",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	);
	helpMenu.add (rulesOption);
	helpMenu.add (aboutOption);
	menuBar.add (helpMenu);
	setJMenuBar (menuBar);
    }


    /** Starts a new game
      */
    public void newGame ()
    {
	// Put your new game code here
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    setBackground (Color.GRAY);
	    setFont (new Font ("Arial", Font.PLAIN, 40));
	    setPreferredSize (new Dimension (660, 480));

	    // Add mouse listeners to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	    this.addMouseMotionListener (new MouseMotionHandler ());

	    // Add key listeners and setting focus
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

	    g.setColor (Color.magenta);
	    g.drawString ("Title", 170, 50);

	} // paint component method
    }


    // Inner class to handle mouse events
    private class MouseHandler extends MouseAdapter
    {
	public void mousePressed (MouseEvent event)
	{
	}

	public void mouseReleased (MouseEvent event)
	{

	}
    }



    // Inner Class to handle mouse movements
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	public void mouseMoved (MouseEvent event)
	{
	}

	public void mouseDragged (MouseEvent event)
	{
	}
    }


    // Inner class to handle key events
    private class KeyHandler extends KeyAdapter
    {
	public void keyPressed (KeyEvent event)
	{

	}
    }


    public static void main (String[] args)
    {
	// Set up the main frame
	MainProgram mainFrame = new MainProgram ();
	mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	mainFrame.pack ();
	mainFrame.setVisible (true);
    }
}


