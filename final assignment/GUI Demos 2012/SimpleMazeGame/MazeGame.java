/** The "MazeGame" class.
  * @author G. Ridout
  * @version last updated December 2011
  */
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class MazeGame extends JFrame
{

    public MazeGame ()
    {
	// Set up the frame and the grid
	super ("Simple Maze Game");
	setLocation (100, 100);

	// Set up for the maze area
	Container contentPane = getContentPane ();
	contentPane.add (new MazeArea (), BorderLayout.CENTER);
    }


    // Inner class for the maze area
    private class MazeArea extends JPanel
    {
	private final int IMAGE_WIDTH;
	private final int IMAGE_HEIGHT;

	private Image[] gridImages;
	private Image playerImage;

	// Variables to keep track of the grid and the player position
	private int[] [] grid;
	private int currentRow;
	private int currentColumn;

	/** Constructs a new MazeArea object
	  */
	public MazeArea ()
	{
	    // Create an array for the gridImages and load them up
	    // Also load up the player image
	    // Turtle and fruit images are from www.iconshock.com
	    gridImages = new Image [6];

	    gridImages [0] = new ImageIcon ("path.gif").getImage ();
	    gridImages [1] = new ImageIcon ("brick.gif").getImage ();
	    gridImages [2] = new ImageIcon ("apple.png").getImage ();
	    gridImages [3] = new ImageIcon ("bananas.png").getImage ();
	    gridImages [4] = new ImageIcon ("grapes.png").getImage ();
	    gridImages [5] = new ImageIcon ("exit.gif").getImage ();
	    playerImage = new ImageIcon ("turtle.png").getImage ();

	    // Starts a new game and loads up the grid (sets size of grid array)
	    newGame ("maze.txt");

	    // Set the image height and width based on the path image size
	    // Aslo sizes this panel based on the image and grid size
	    IMAGE_WIDTH = gridImages [0].getWidth (this);
	    IMAGE_HEIGHT = gridImages [0].getHeight (this);
	    Dimension size = new Dimension (grid [0].length * IMAGE_WIDTH,
		    grid.length * IMAGE_HEIGHT);
	    this.setPreferredSize (size);

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

	    // Redraw the grid with current images
	    for (int row = 0 ; row < grid.length ; row++)
		for (int column = 0 ; column < grid [0].length ; column++)
		{
		    // Put a path underneath everywhere
		    g.drawImage (gridImages [0],
			    column * IMAGE_WIDTH,
			    row * IMAGE_HEIGHT, this);
		    int imageNo = grid [row] [column];
		    g.drawImage (gridImages [imageNo],
			    column * IMAGE_WIDTH,
			    row * IMAGE_HEIGHT, this);
		}

	    // Draw the moving player on top of the grid
	    g.drawImage (playerImage,
		    currentColumn * IMAGE_WIDTH,
		    currentRow * IMAGE_HEIGHT, this);
	} // paint component method



	public void newGame (String mazeFileName)
	{
	    // Initial position of the player
	    currentRow = 1;
	    currentColumn = 1;

	    // Load up the file for the maze (try catch, is for file io errors)
	    try
	    {
		// Find the size of the file first to size the array
		// Standard Java file input (better than hsa.TextInputFile)
		BufferedReader mazeFile =
		    new BufferedReader (new FileReader (mazeFileName));

		// Assume file has at least 1 line
		int noOfRows = 1;
		String rowStr = mazeFile.readLine ();
		int noOfColumns = rowStr.length ();

		// Read and count the rest of rows until the end of the file
		String line;
		while ((line = mazeFile.readLine ()) != null)
		{
		    noOfRows++;
		}
		mazeFile.close ();

		// Set up the array
		grid = new int [noOfRows] [noOfColumns];

		// Load in the file data into the grid (Need to re-open first)
		// In this example  the grid contains the characters '0' to '9'
		// So to translate each character into an integer we subtract '0'
		// If your maze has more than 10 types of squares you could use
		// letters in your file 'a' to 'z' and then subtract 'a' to
		// translate each letter into an integer
		mazeFile =
		    new BufferedReader (new FileReader (mazeFileName));
		for (int row = 0 ; row < grid.length ; row++)
		{
		    rowStr = mazeFile.readLine ();
		    for (int column = 0 ; column < grid [0].length ; column++)
		    {
			grid [row] [column] = (int) (rowStr.charAt (column) - '0');
		    }
		}
		mazeFile.close ();
	    }
	    catch (IOException e)
	    {
		JOptionPane.showMessageDialog (this, mazeFileName +
			" not a valid maze file", "Message - Invalid Maze File",
			JOptionPane.WARNING_MESSAGE);
		System.exit (0);
	    }
	}

	// Inner class to handle key events
	private class KeyHandler extends KeyAdapter
	{
	    public void keyPressed (KeyEvent event)
	    {
		// Change the currentRow and currentColumn of the player
		// based on the key pressed
		if (event.getKeyCode () == KeyEvent.VK_LEFT)
		{
		    currentColumn--;
		}
		else if (event.getKeyCode () == KeyEvent.VK_RIGHT)
		{
		    currentColumn++;
		}
		else if (event.getKeyCode () == KeyEvent.VK_UP)
		{
		    currentRow--;
		}
		else if (event.getKeyCode () == KeyEvent.VK_DOWN)
		{
		    currentRow++;
		}

		// Eat the fruit
		if (grid [currentRow] [currentColumn] >= 2 &&
			grid [currentRow] [currentColumn] <= 4)
		{
		    grid [currentRow] [currentColumn] = 0;
		}

		// Repaint the screen after the change
		repaint ();
	    }
	}
    }


    // Sets up the main frame for the Game
    public static void main (String[] args)
    {
	MazeGame frame = new MazeGame ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // MazeGame class


