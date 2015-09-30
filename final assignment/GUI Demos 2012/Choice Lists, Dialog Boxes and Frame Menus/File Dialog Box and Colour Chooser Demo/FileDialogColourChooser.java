// The "FileDialogColourChooser" class. Programmed by: Ridout
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class FileDialogColourChooser extends JFrame implements ActionListener
{
    // Variables for the frame
    JMenuItem newOption, exitOption, aboutOption;
    JMenuItem openOption, saveOption;
    JMenuItem chooseBackGroundColour, chooseMessageColour;
    Color messageColour;

    String message;
    DrawingPanel drawingArea;

    public FileDialogColourChooser ()
    {
	super ("File Dialogs and Colour Choosers");
	setSize (700, 200);

	Container contentPane = getContentPane ();
	drawingArea = new DrawingPanel ();
	contentPane.add (drawingArea);

	// Set up the Menu
	// Set up the Game MenuItems with Short cuts
	newOption = new JMenuItem ("New");
	newOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_MASK));
	newOption.addActionListener (this);
	openOption = new JMenuItem ("Open");
	openOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_O, InputEvent.CTRL_MASK));

	openOption.addActionListener (this);
	saveOption = new JMenuItem ("Save");
	saveOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_S, InputEvent.CTRL_MASK));

	saveOption.addActionListener (this);
	exitOption = new JMenuItem ("Exit");
	exitOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK));

	exitOption.addActionListener (this);

	// Set up the Game Menu
	JMenu gameMenu = new JMenu ("Game");

	// Add each MenuItem to the Game Menu (with a separator)
	gameMenu.add (newOption);
	gameMenu.add (openOption);
	gameMenu.add (saveOption);
	gameMenu.addSeparator ();
	gameMenu.add (exitOption);

	// Create a colour menu
	JMenu colourPreferences = new JMenu ("Colour");
	chooseBackGroundColour = new JMenuItem ("Background");
	chooseBackGroundColour.addActionListener (this);
	chooseMessageColour = new JMenuItem ("Message Text");
	chooseMessageColour.addActionListener (this);
	messageColour = Color.black;
	colourPreferences.add (chooseBackGroundColour);
	colourPreferences.add (chooseMessageColour);


	// Set up the Help Menu
	aboutOption = new JMenuItem ("About...");
	aboutOption.addActionListener (this);
	JMenu helpMenu = new JMenu ("Help");
	helpMenu.add (aboutOption);
	helpMenu.addActionListener (this);

	// Set up the Menu Bar and add the above Menus
	JMenuBar mainMenu = new JMenuBar ();
	mainMenu.add (gameMenu);
	mainMenu.add (colourPreferences);
	mainMenu.add (helpMenu);

	// Set the menu bar for this frame to mainMenu
	setJMenuBar (mainMenu);

	message = "Welcome to the Menu Demo";    // initial message
    } // Constructor


    /** Method that deals with the menu options
     * @param event the event that triggered this method
     */
    public void actionPerformed (ActionEvent event)
    {
	if (event.getSource () == newOption)
	{
	    newGame ();
	}
	else if (event.getSource () == openOption)
	{
	    FileDialog openDialog = new FileDialog (this, "Open a new file", FileDialog.LOAD);
	    openDialog.setVisible (true);
	    String fileName = openDialog.getFile ();
	    String dir = openDialog.getDirectory ();
	    message = "Open file" + dir + fileName;
	}
	else if (event.getSource () == saveOption)
	{
	    FileDialog saveDialog = new FileDialog (this, "Save a file", FileDialog.SAVE);
	    saveDialog.setVisible (true);
	    String fileName = saveDialog.getFile ();
	    String dir = saveDialog.getDirectory ();
	    message = "Save file" + dir + fileName;
	}
	else if (event.getSource () == chooseBackGroundColour)
	{
	    Color newColour = JColorChooser.showDialog (this,
		    "Choose Background Color",
		    this.getBackground ());

	    if (newColour != null)
	    {
		drawingArea.setBackground (newColour);
	    }


	}
	else if (event.getSource () == chooseMessageColour)
	{
	    Color newColour = JColorChooser.showDialog (this,
		    "Choose Message Color",
		    messageColour);

	    if (newColour != null)
	    {
		messageColour = newColour;
	    }
	}
	else if (event.getSource () == exitOption)
	{
	    hide ();
	    System.exit (0);
	}
	else if (event.getSource () == aboutOption)
	{
	    JOptionPane.showMessageDialog (this,
		    "File Dialogs and \nColour Choosers Demo", "About...",
		    JOptionPane.INFORMATION_MESSAGE);

	}
	repaint (); // Update screen with new message

    }


    void newGame ()
    {
	message = "New Game was selected";
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    setFont (new Font ("Arial", Font.PLAIN, 12));
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setColor (messageColour);
	    g.drawString (message, 20, 100);

	} // paint component method
    }


    public static void main (String[] args)
    {
	FileDialogColourChooser frame = new FileDialogColourChooser ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.setVisible (true);                // Show the frame
    } // main method
} // FileDialogColourChooser class
