// Extra imports required for GUI code
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

/** The YouVsSchool class - creates the JFrame for You Vs School
  * Plays a simple game of YouVsSchool using YouVsSchoolBoard class
  * @author Hannah Kim and Melissa Li
  * @version January 2013
  */
public class YouVsSchoolMain extends JFrame implements ActionListener
{
    // Program variables for the Menu items and the game board
    private JMenuItem newOption, exitOption, restartOption, pauseOption, rulesMenuItem, aboutMenuItem, soundOn, soundOff;
    private YouVsSchoolBoard gameBoard;

    /** Constructs a new YouVsSchoolMain frame (sets up the Game)
      */
    public YouVsSchoolMain ()
    {
	// Sets up the frame for the game
	super ("You VS School");
	setResizable (false);

	// Load up the icon image
	setIconImage (Toolkit.getDefaultToolkit ().getImage ("Images/Homework.png"));

	// Sets up the Connect Four board that plays most of the game
	// and add it to the centre of this frame
	gameBoard = new YouVsSchoolBoard ();
	getContentPane ().add (gameBoard, BorderLayout.CENTER);

	// Centre the frame in the middle (almost) of the screen
	Dimension screen = Toolkit.getDefaultToolkit ().getScreenSize ();
	setLocation ((screen.width - gameBoard.BOARD_SIZE.width) / 2,
		(screen.height - gameBoard.BOARD_SIZE.height) / 2 - 50);

	// Adds the menu and menu items to the frame (see below for code)
	// Set up the Game MenuItems
	restartOption = new JMenuItem ("Restart");
	restartOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_R, InputEvent.CTRL_MASK));
	restartOption.addActionListener (this);

	exitOption = new JMenuItem ("Exit");
	exitOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK));
	exitOption.addActionListener (this);

	pauseOption = new JMenuItem ("Pause");
	pauseOption.setAccelerator (
		KeyStroke.getKeyStroke ('p'));
	pauseOption.addActionListener (this);

	// Set up the Sound Menu
	JMenu soundMenu = new JMenu ("Sound");
	// helpMenu.setMnemonic ('H');
	soundOn = new JMenuItem ("On"); //, 'R');
	soundOn.addActionListener (this);
	soundMenu.add (soundOn);
	soundOff = new JMenuItem ("Off"); //, 'A');
	soundOff.addActionListener (this);
	soundMenu.add (soundOff);

	// Set up the Help Menu
	JMenu helpMenu = new JMenu ("Help");
	helpMenu.setMnemonic ('H');
	rulesMenuItem = new JMenuItem ("Rules...", 'R');
	rulesMenuItem.addActionListener (this);
	helpMenu.add (rulesMenuItem);
	aboutMenuItem = new JMenuItem ("About...", 'A');
	aboutMenuItem.addActionListener (this);
	helpMenu.add (aboutMenuItem);

	// Add each MenuItem to the Game Menu (with a separator)
	JMenu gameMenu = new JMenu ("Game");
	gameMenu.add (restartOption);
	gameMenu.addSeparator ();
	gameMenu.add (pauseOption);
	gameMenu.addSeparator ();
	gameMenu.add (exitOption);
	JMenuBar mainMenu = new JMenuBar ();
	mainMenu.add (gameMenu);
	mainMenu.add (soundMenu);
	mainMenu.add (helpMenu);
	// Set the menu bar for this frame to mainMenu
	setJMenuBar (mainMenu);
    } // Constructor


    /** Responds to a Menu Event.  This method is needed since our
      * Connect Four frame implements ActionListener
      * @param event the event that triggered this method
      */
    public void actionPerformed (ActionEvent event)
    {
	if (event.getSource () == restartOption)   // Selected "Restart"
	{
	    gameBoard.newGame ();
	}
	else if (event.getSource () == exitOption)  // Selected "Exit"
	{
	    hide ();
	    System.exit (0);
	}
	else if (event.getSource () == pauseOption)  // Selected "Pause"
	{
	    hide ();
	    System.exit (0);
	}
	else if (event.getSource () == rulesMenuItem)  // Selected "Rules"
	{
	    JOptionPane.showMessageDialog (this,
		    "Defend yourself from schoolwork by placing supplies in the way." +
		    "\n\nClick the picture of the defender you wish to buy on top and" +
		    "\nplace it by clicking one of the squares in the grid. " +
		    "\n\nDon't let the homework and assignments reach you at all " +
		    "costs. \nTip: Be sure to buy enough piggy banks to supply you with " +
		    "\nmoney for defenders." +
		    "\n\nGood luck!",
		    "Rules",
		    JOptionPane.INFORMATION_MESSAGE);
	}
	else if (event.getSource () == aboutMenuItem)  // Selected "About"
	{
	    JOptionPane.showMessageDialog (this,
		    "by Melissa Li and Hannah Kim" +
		    "\n\u00a9 2013", "About You VS School",
		    JOptionPane.INFORMATION_MESSAGE);
	}
	else if (event.getSource () == soundOn)  // Selected "On"
	{
	}
	else if (event.getSource () == soundOff)  // Selected "Off"
	{
	}
    }


    /** Starts up the YouVsSchoolMain frame
      * @param args An array of Strings (ignored)
      */
    public static void main (String[] args)
    {
	// Starts up the YouVsSchoolMain frame
	YouVsSchoolMain frame = new YouVsSchoolMain ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
}


