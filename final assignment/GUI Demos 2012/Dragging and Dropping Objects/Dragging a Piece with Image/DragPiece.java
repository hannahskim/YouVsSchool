import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "DragPiece" class.
  * Shows how to drag and drop pieces.
  * Also shows a print statement, which can be helpful to trace and find problems in your program.
  * @author ICS3U
  * @version December, 2009
  */

public class DragPiece extends JFrame implements ActionListener
{
    // Program constants (declared at the top, these can be used by any method)
    final int NO_OF_PIECES = 4;
    final int NO_OF_ROWS = 6;     // # rows and columns can be changed but...
    final int NO_OF_COLUMNS = 10; // if you change the # rows or columns, you must resize the imageBackground to fit.
    final int EMPTY = 0;   // represents an empty space on the board
    final int BORDER = -1; // represents a perimeter of squares surrounding the board
    final int SQUARE_SIZE = 50;  // The length (in pixels) of the side of each square on the board

    // Program variables (declared at the top, these can be used or changed by any method)

    // The playing board has a perimeter of BORDER squares surrounding it, much like Connect4. The board squares in the area of play are
    // EMPTY or have a positive int value representing a player piece.  This int value is also an index to the pieces[] array of images.
    private int[] [] board;
    private int selectedPiece;  // Keeps track of the currently selected piece (each piece has a unique number)
    private int draggedXPos, draggedYPos;  // keeps track of X,Y position while piece is being dragged.
    private JMenuItem newOption, exitOption, instructionOption, aboutOption;  // for menu and menu events
    private DrawingPanel drawingArea;

    // Background image was made 500x300 pixels (to fit 10 columns by 6 rows, with each square being 50 pixels wide & high)
    Image imageBackground = new ImageIcon ("clouds500x300.png").getImage ();

    // Array of images for each piece on the board. Each piece has a unique number (1, 2, 3...) which is
    // also the index to this array of images.
    private Image pieces[] = new Image [NO_OF_PIECES + 1];


    /** Constructs a new frame and sets up the game.
      */
    public DragPiece ()
    {
	// Sets the 2D array which represents the board.
	board = new int [NO_OF_ROWS + 2] [NO_OF_COLUMNS + 2];
	loadResources ();

	// Sets up the screen.
	setTitle ("Drag Piece");
	setLocation (50, 25);
	setResizable (false);

	// set size of the drawing panel to equal the size of the background image
	// (you may want to size your panel bigger, if you s bigger if you have additional things to show on your panel (eg. move count, score, status, level, etc)
	int imageHeight = imageBackground.getHeight (DragPiece.this);
	int imageWidth = imageBackground.getWidth (DragPiece.this);
	Dimension imageSize = new Dimension (imageWidth, imageHeight);

	//create and add the drawing panel.
	drawingArea = new DrawingPanel (imageSize);
	Container contentPane = getContentPane ();
	contentPane.add (drawingArea, BorderLayout.CENTER);

	addMenus ();
	newGame ();
    }


    /**
      *  Load image and audio files here
      *  These are stored as global variables at top of program, to be accessible to all methods.
      *  The image and audio files (gif, png, wav, etc) must be in the same folder as this program, or in a subfolder
      */
    private void loadResources ()
    {
	// Store the image for each piece in an array.
	pieces [1] = new ImageIcon ("smiley.gif").getImage ();
	pieces [2] = new ImageIcon ("bug.gif").getImage ();
	pieces [3] = new ImageIcon ("cat.gif").getImage ();
	pieces [4] = new ImageIcon ("dog.gif").getImage ();

	setIconImage (Toolkit.getDefaultToolkit ().getImage ("earth.gif"));
    }


    /** Starts or restarts a game
      */
    private void newGame ()
    {
	clearBoard (board);
	setBoardPerimeter (board);
	setPieces (board);
	// Sets the currently selected piece to empty so a new game does not start with a selected piece from last game
	selectedPiece = EMPTY;

	repaint ();  // paint the drawing panel when restarting a new game
    }


    /** Clears the game board (sets each square to EMPTY)
      * @param board the board to clear
      */
    private void clearBoard (int[] [] board)
    {
	// Clear only the playing area of the board, NOT the border perimeter
	// (there is a perimeter row/column surrounding all 4 sides of the playing board,
	//  similar to in ConnectFour demo)
	for (int row = 1 ; row <= NO_OF_ROWS ; row++)
	    for (int column = 1 ; column <= NO_OF_COLUMNS ; column++)
		board [row] [column] = EMPTY;
    }


    /**
      * Sets BORDER values around the PERIMETER of the visible part of the playing board
      * This perimeter could be used to block movement off the board and/or to
      * prevent the game's logic from checking outside the playing board
      */
    private void setBoardPerimeter (int[] [] board)
    {
	// Sets values around the PERIMETER of the board to block movement off the board
	// and/or to prevent the game's logic from checking squares outside the board
	for (int row = 0 ; row <= NO_OF_ROWS + 1 ; row++)
	{
	    for (int column = 0 ; column <= NO_OF_COLUMNS + 1 ; column++)
	    {
		if (row == 0 || row == NO_OF_ROWS + 1)
		    board [row] [column] = BORDER;
		if (column == 0 || column == NO_OF_COLUMNS + 1)
		    board [row] [column] = BORDER;
	    }
	}
    }


    /** Randomly sets all the pieces on the board at the beginning of a game.
      * @param board The game board.
      */
    private void setPieces (int[] [] board)
    {
	for (int piece = 1 ; piece <= NO_OF_PIECES ; piece++)
	{
	    int row, col;
	    // get a random spot that is not already occupied by a pice
	    do
	    {
		row = (int) (Math.random () * (NO_OF_ROWS) + 1);
		col = (int) (Math.random () * (NO_OF_COLUMNS) + 1);
	    }
	    while (board [row] [col] != EMPTY);
	    board [row] [col] = piece;
	}
    }


    /** Makes a move on the board (if possible).
      * @param moveFromRow    The row of the piece being moved.
      * @param moveFromColumn The column of the piece being moved.
      * @param moveToRow      The row the player wants to move the piece to.
      * @param moveToColumn   The column the player wants to move the piece to.
      */
    private void makeMove (int moveFromRow, int moveFromColumn, int moveToRow, int moveToColumn)
    {
	// If the start and target locations are the same, don't move
	if (moveFromRow == moveToRow && moveFromColumn == moveToColumn)
	    return;   // nothing to move
	// Check that the target is in-bounds of your playing board, or any other restrictions
	if (moveToRow > NO_OF_ROWS || moveToColumn > NO_OF_COLUMNS || moveToRow < 1 || moveToColumn < 1)
	    JOptionPane.showMessageDialog (this, "Move cancelled. \nYou must move your piece within the board.",
		    "Invalid Move", JOptionPane.WARNING_MESSAGE);
	// In this demo, disallow one piece to drop on top of another
	else if (board [moveToRow] [moveToColumn] > EMPTY)
	    JOptionPane.showMessageDialog (this, "Move cancelled.  \nYou must move to an empty square.",
		    "Invalid Move", JOptionPane.ERROR_MESSAGE);
	else
	{
	    // move the selected piece to the target location
	    board [moveToRow] [moveToColumn] = selectedPiece;
	    // remove the piece from the start location
	    board [moveFromRow] [moveFromColumn] = EMPTY;

	    // The following line can help you trace your program. Then remove it when your program works.
	    System.out.println ("makeMove() exiting: board[" + moveToRow + "][" + moveToColumn +
		    "] now has piece #" + board [moveToRow] [moveToColumn]);
	}
    }


    // For the menu and menuitems
    private void addMenus ()
    {
	// Sets up the Game MenuItems.
	newOption = new JMenuItem ("New");
	newOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_MASK));
	newOption.addActionListener (this);

	exitOption = new JMenuItem ("Exit");
	exitOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK));
	exitOption.addActionListener (this);

	// Sets up the Help MenuItems.
	instructionOption = new JMenuItem ("Instructions");
	instructionOption.setMnemonic ('I');
	instructionOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_I, InputEvent.CTRL_MASK));
	instructionOption.addActionListener (this);

	aboutOption = new JMenuItem ("About");
	aboutOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_A, InputEvent.CTRL_MASK));
	aboutOption.setMnemonic ('I');
	aboutOption.addActionListener (this);

	// Sets up the Game and Help Menus.
	JMenu gameMenu = new JMenu ("Game");
	gameMenu.setMnemonic ('G');
	JMenu helpMenu = new JMenu ("Help");
	helpMenu.setMnemonic ('H');

	// Add each MenuItem to the Game Menu (with a separator).
	gameMenu.add (newOption);
	gameMenu.addSeparator ();
	gameMenu.add (exitOption);

	// Add each MenuItem to the Help Menu (with a separator).
	helpMenu.add (instructionOption);
	helpMenu.addSeparator ();
	helpMenu.add (aboutOption);


	// Adds the GameMenu and HelpMenu to the JMenuBar.
	JMenuBar mainMenu = new JMenuBar ();
	mainMenu.add (gameMenu);
	mainMenu.add (helpMenu);

	// Displays the menus.
	setJMenuBar (mainMenu);
    }


    /** This method is called by Java when a menu option is chosen
      * @param event The event that triggered this method.
      */
    public void actionPerformed (ActionEvent event)
    {
	// If the new option is selected, the board is reset and a new game begins.
	if (event.getSource () == newOption)
	    newGame ();
	// Closes the game screen if the exit option is selected.
	else if (event.getSource () == exitOption)
	{
	    dispose ();
	    System.exit (0);
	}
	// Displays the instructions if the instruction option is selected.
	else if (event.getSource () == instructionOption)
	{
	    JOptionPane.showMessageDialog (this,
		    "Press the mouse key on the piece you wish to move."
		    + "\nThen drag and drop the piece to an empty square.",
		    "Instructions ",
		    JOptionPane.INFORMATION_MESSAGE);
	}
	// Displays copyright information if the about option is selected.
	else if (event.getSource () == aboutOption)
	{
	    JOptionPane.showMessageDialog (this, "\u00a9 By ... ", "About DragPiece", JOptionPane.INFORMATION_MESSAGE);
	}
    }


    // Creates and draws the main program panel that the user sees
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object of the specified dimension/size
	  */
	public DrawingPanel (Dimension size)
	{
	    setPreferredSize (size);  // set size of this panel
	    // Add mouse listeners  to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	    this.addMouseMotionListener (new MouseMotionHandler ());
	    this.setFocusable (true);
	    this.requestFocusInWindow ();
	}

	/** Repaint the drawing panel.
	  * @param g The Graphics context.
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);

	    g.drawImage (imageBackground, 0, 0, this); // draw background first so it's below the pieces and everything else

	    // Draw the board with current pieces.
	    for (int row = 1 ; row <= NO_OF_ROWS ; row++)
		for (int column = 1 ; column <= NO_OF_COLUMNS ; column++)
		{
		    // Find the x and y positions for each row and column.
		    int xPos = (column - 1) * SQUARE_SIZE;
		    int yPos = (row - 1) * SQUARE_SIZE;

		    // Draw the squares
		    g.setColor (Color.BLUE);
		    g.drawRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);

		    // If this square has a piece on it, draw the image of the piece.
		    if (board [row] [column] > EMPTY)
		    {
			// We use the pieceNumber in board[row][column] to find the image of the piece...
			int pieceNumber = board [row] [column];
			// The image of the piece is in the pieces[] array... and pieceNumber is the index of the image
			// ie., the image is in pieces[pieceNumber]
			g.drawImage (pieces [pieceNumber], xPos, yPos, this);
		    }
		}

	    // Draw the selected piece being DRAGGED. Draw this last so it appears over top everything.
	    if (selectedPiece != EMPTY)
		g.drawImage (pieces [selectedPiece], draggedXPos, draggedYPos, this);

	} // paintComponent Method
    } // DrawingPanel


    // Inner class to handle mouse-pressed and mouse-released events.
    private class MouseHandler extends MouseAdapter
    {
	private int fromRow, fromColumn;   // The board row and column that the piece is moving from.

	/** Finds out which piece was selected
	  * @param     event in formation about the mouse pressed event
	  */
	public void mousePressed (MouseEvent event)
	{
	    // Convert mouse-pressed location to board row and column
	    Point pressedPoint = event.getPoint ();
	    fromColumn = pressedPoint.x / SQUARE_SIZE + 1;  // add one because of the border surrounding the 2D 'board' array)
	    fromRow = pressedPoint.y / SQUARE_SIZE + 1;
	    // The following line can help you trace your program. Then remove it when your program works.
	    System.out.println ("mousePressed(): board[" + fromRow + "][" + fromColumn +
		    "] has piece #" + board [fromRow] [fromColumn]);

	    // Check if the selected square has a player piece
	    if (board [fromRow] [fromColumn] != EMPTY)
	    {
		selectedPiece = board [fromRow] [fromColumn];
		board [fromRow] [fromColumn] = EMPTY;
	    }
	    else
		selectedPiece = EMPTY;
	}

	/** Finds where the mouse was released and moves the piece, if allowed
	 *@param     event information about the mouse released event
	 */
	public void mouseReleased (MouseEvent event)
	{
	    // Convert mouse-released location to board row and column
	    Point releasedPoint = event.getPoint ();
	    int toColumn = (int) ((double) releasedPoint.x / SQUARE_SIZE + 1); //casting double to int preserves fractional negative values
	    int toRow = (int) ((double) releasedPoint.y / SQUARE_SIZE + 1);

	    // Move piece, if a piece is selected
	    if (selectedPiece != EMPTY)
	    {
		// Check if place to move is empty
		// If not, move back to original location
		if (board [toRow] [toColumn] == EMPTY)
		{
		    System.out.println ("mouseReleased(): board[" + toRow + "][" + toColumn +
			    "] dropping piece #" + selectedPiece);
		    board [toRow] [toColumn] = selectedPiece;
		}
		else
		{
		    System.out.println ("mouseReleased(): board[" + toRow + "][" + toColumn +
			    "] Square occupied ");
		    board [fromRow] [fromColumn] = selectedPiece;
		}
	    }
	    selectedPiece = EMPTY;
	    setCursor (Cursor.getDefaultCursor ());
	    repaint ();
	}
    }


    // Inner Class to handle mouse movements over the DrawingPanel
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	/** Changes the mouse curser to a hand if it is over top of a piece
	  *@param event information about the mouse released event
	  */
	public void mouseMoved (MouseEvent event)
	{
	    // Convert current mouse location to board row and column
	    int column = event.getX () / SQUARE_SIZE + 1;  // //add one because of the border surrounding the 2D 'board' array)
	    int row = event.getY () / SQUARE_SIZE + 1;
	    if (board [row] [column] > EMPTY)
		setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
	    else
		setCursor (Cursor.getDefaultCursor ());
	}

	/** Moves the selected piece when the mouse is dragged
	  *@param event information about themouse released event
	  */
	public void mouseDragged (MouseEvent event)
	{
	    draggedXPos = event.getX () - SQUARE_SIZE / 2; // adjust pointer to centre of dragged image
	    draggedYPos = event.getY () - SQUARE_SIZE / 2;
	    repaint ();
	}
    }


    // The main is the starting point of the program and constructs the game.
    public static void main (String[] args)
    {
	DragPiece demo = new DragPiece ();
	demo.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	demo.pack ();
	demo.setVisible (true);
    } // Main method
} //class
