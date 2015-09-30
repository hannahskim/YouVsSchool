import java.applet.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** The "MoveByClickingMouse" class.
  * Shows how to move a piece on a board, by using mouse clicks.
  * Also shows a print statement, which can be helpful to trace and find problems in your program. 
  * @author ICS3U
  * @version December, 2009   Updated May 2010 - removed empty border around playing board
  */

public class MoveByClickingMouse extends JFrame implements ActionListener
{
    // Program constants (declared at the top, these can be used by any method)
    
    // Pieces - You may prefer to use an ARRAY for your pieces/images (for example, see the pieces[] array in the demo "Dragging A Piece")
    final int PIECE1 = 1;    
    final int PIECE2 = 2;
    final int PIECE3 = 3;
    final int PIECE4 = 4; 
    final int NO_OF_PIECES = 4;
    
    final int NO_OF_ROWS = 8;    // Number of rows and columns in the game board (instead of constants, you could make                                   
    final int NO_OF_COLUMNS = 8; // these variables, and then let the user choose or change the number of rows & columns)
    final int EMPTY = 0;   // represents an empty space on the board  
    final int NONE = -1; 
    final int SQUARE_SIZE = 50;  // The length (in pixels) of the side of each square on the board   

    // Program variables (declared at the top, these can be used or changed by any method)
     
    // The board squares in the area of play (8 rows by 8 columns) are EMPTY or have a positive int value representing a player piece.     
    private int[] [] board; 
    private int selectedRow, selectedColumn;   // The board row and column of the piece selected.
    private int selectedPiece;  // Keeps track of the currently selected piece (each piece has a unique number)    
    private JMenuItem newOption, exitOption, instructionOption, aboutOption;  // for menu and menu events
    private DrawingPanel drawingArea;
    private Image image1, image2, image3, image4;


    /** Constructs a new frame and sets up the game.
      */
    public MoveByClickingMouse ()
    {
	// Sets the 2D array which represents the board.
	board = new int [NO_OF_ROWS + 2] [NO_OF_COLUMNS + 2];

	// Sets up the screen.
	setTitle ("Move Piece By Clicking Mouse");
	setLocation(75, 50);
	setResizable (false);
	
	// The size of the panel must be calculated based on the size of the all the squares
	// (you may want to size your panel bigger, if you s bigger if you have additional things to show on your panel (eg. move count, score, status, level, etc)         
	Dimension size = new Dimension (NO_OF_COLUMNS * SQUARE_SIZE,
		NO_OF_ROWS * SQUARE_SIZE);
	// Create and add the drawing panel.                
	drawingArea = new DrawingPanel (size);
	Container contentPane = getContentPane ();        
	contentPane.add (drawingArea, BorderLayout.CENTER);

	addMenus();
	loadResources();
	newGame ();
    }
    
    /**
      *  Load image and audio files here
      *  These are stored as global variables at top of program, to be accessible to all methods.
      *  The image and audio files (gif, png, wav, etc) must be in the same folder as this program, or in a subfolder
      */ 
    private void loadResources()
    {
	image1 = new ImageIcon ("smiley.gif").getImage ();
	image2 = new ImageIcon ("bug.gif").getImage ();        
	image3 = new ImageIcon ("cat.gif").getImage();
	image4 = new ImageIcon ("dog.gif").getImage();       
	setIconImage (Toolkit.getDefaultToolkit ().getImage ("earth.gif"));    
    }


    /** Starts or restarts a game
      */
    private void newGame ()
    {
	clearBoard (board);
	setPieces (board);        
	// Sets the selected row, column and piece to NONE, so a new game starts without a selected piece
	selectedRow = NONE;
	selectedColumn = NONE;
	selectedPiece = NONE;
	drawingArea.setCursor (Cursor.getDefaultCursor ()); 
	repaint ();  // paint the drawing panel when restarting a new game 
    } 

    /** Clears the game board (sets each square to EMPTY)
      * @param board the board to clear
      */
    private void clearBoard (int[] [] board)
    {
	for (int row = 0 ; row < NO_OF_ROWS; row++)
	    for (int column = 0 ; column < NO_OF_COLUMNS; column++)
		board [row] [column] = EMPTY;
    }     

    /** Randomly sets all the pieces on the board at the beginning of a game.
      * @param board The game board.
      */
    private void setPieces (int[] [] board)
    {
	for (int piece = 1 ; piece <= NO_OF_PIECES; piece++)
	{
	    int row, col;
	    // get a random spot that is not already occupied by a pice
	    do
	    {
		row = (int) (Math.random() * NO_OF_ROWS);
		col = (int) (Math.random() * NO_OF_COLUMNS); 
	    } while (board [row][col] != EMPTY);             
	    board [row][col] = piece;
	}    
    } 

    /** Makes a move on the board (if possible).
      * @param moveToRow      The row the player wants to move the piece to.
      * @param moveToColumn   The column the player wants to move the piece to.
      */
    private void makeMove (int moveToRow, int moveToColumn)
    {
	// make sure the selected square has a valid piece to move!
	if ( board[selectedRow][selectedColumn] > EMPTY )
	{
	    // move the piece to the target location
	    board[moveToRow][moveToColumn] = board[selectedRow][selectedColumn];
	    // remove the piece from the start location 
	    board[selectedRow][selectedColumn] = EMPTY;
	    // de-select square and piece after moving
	    selectedPiece = NONE;            
	    selectedRow = NONE;
	    selectedColumn = NONE; 
	    
	    // The following line can help you trace your program. Then remove it when your program works. 
	    System.out.println("makeMove(): board[" + moveToRow + "][" + moveToColumn + 
			       "] now has piece #" + board[moveToRow][moveToColumn]);              
	}
    }     
    
    // For the menu bar and menu items  
    private void addMenus()
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
    
    /** Java calls this method when a menu item is selected.
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
		    "Press the mouse on the piece you wish to move"
		    + "\n(the square of the selected piece is highlighted)."
		    + "\n\nThen press the mouse on the square to which"
		    + "\nyou wish to move the selected piece.",
		    "Instructions ",
		    JOptionPane.INFORMATION_MESSAGE);
	}
	// Displays copyright information if the about option is selected.
	else if (event.getSource () == aboutOption)
	{
	    JOptionPane.showMessageDialog (this, "\u00a9 2010 By ...", "About Moving a Piece", JOptionPane.INFORMATION_MESSAGE);
	}
    }
    

    // Creates and draws the main program panel that the user sees
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel (Dimension size)
	{
	    setPreferredSize (size);  // set size of this panel
	    // Add mouse listeners to this drawing panel
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

	    // Draw the board with current pieces.
	    for (int row = 0 ; row < NO_OF_ROWS ; row++)
		for (int column = 0 ; column < NO_OF_COLUMNS ; column++)
		{
		    // Find the x and y positions for each row and column.
		    int xPos = column * SQUARE_SIZE;
		    int yPos = row * SQUARE_SIZE;

		    // Highlights square if a piece is selected                    
		    if (row == selectedRow && column == selectedColumn)
		    {
			g.setColor (Color.GREEN);
			g.fillRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
		    }
		    // Draw board squares
		    else if ((column % 2 == 0 && row % 2 == 0) ||
			    (column % 2 != 0 && row % 2 != 0))
		    {  
			g.setColor(Color.PINK);
			g.fillRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
		    }     
		    else
		    {                    
			g.setColor (Color.MAGENTA);
			g.fillRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);                        
		    }                         
		    
		    // Draw the piece on this square, if there is one.  
		    // We use the pieceNumber in board[row][column] to determine which piece to draw.
		    // (NOTE: This is easier/better done by using an ARRAY for the pieces, especially if you have many different kinds  
		    //        of pieces -- For example, see the pieces[] array and paintComponent() in the demo "Dragging A Piece" 
		    if (board [row] [column] == PIECE1)
			g.drawImage (image1, xPos, yPos, this);
		    else if (board [row] [column] == PIECE2)
			g.drawImage (image2, xPos, yPos, this);
		    else if (board [row] [column] == PIECE3)
			g.drawImage (image3, xPos, yPos, this);
		    else if (board [row] [column] == PIECE4)
			g.drawImage (image4, xPos, yPos, this);                             
		}

	} // paintComponent Method
   

	// Inner class to handle event when mouse is pressed while mouse is over the DrawingPanel.
	private class MouseHandler extends MouseAdapter
	{
	    /** Responds to a mousePressed event
	    * @param event Information about the mouse pressed event.
	    */
	    public void mousePressed (MouseEvent event)
	    {
		// Convert mouse-pressed location to board row and column 
		Point pressedOnPoint = event.getPoint();
		int pressedOnColumn = event.getX () / SQUARE_SIZE;
		int pressedOnRow = event.getY () / SQUARE_SIZE;
	    
		// The following line can help you trace your program. Then remove it when your program works. 
		System.out.println("mousePressed(): board[" + pressedOnRow + "][" + pressedOnColumn + 
				   "] has piece #" + board[pressedOnRow][pressedOnColumn]);  
	    
		// Check if the selected square is empty or has a player piece
		if ( board[pressedOnRow][pressedOnColumn] != EMPTY )
		{
		    // A piece on the board was selected
		    selectedRow = pressedOnRow;
		    selectedColumn = pressedOnColumn;                
		    selectedPiece = board [selectedRow] [selectedColumn]; 
		}
		else  
		{
		    // User pressed mouse on an empty square of the board.
		    // Move a selected piece (if any) to that empty square. 
		    if (selectedPiece > 0 )
		    {
			makeMove(pressedOnRow, pressedOnColumn); 
			setCursor(Cursor.getDefaultCursor ());  // set mouse cursor back to its normal image
		    }    
		}
		repaint();
	    }
	} 

	// Inner Class to handle mouse movements over the drawing panel
	private class MouseMotionHandler extends MouseMotionAdapter
	{
	    /** Changes the mouse curser to a hand, if a piece has been selected to move, and the mouse is over a valid (ie., empty) square.
	    *@param event information about the mouse released event
	    */    
	    public void mouseMoved (MouseEvent event)
	    {        
		if ( selectedPiece > 0 ) 
		{ 
		    // Convert mouse-pressed position to board row and column
		    Point pos = event.getPoint();
		    int column = pos.x / SQUARE_SIZE;
		    int row = pos.y / SQUARE_SIZE;                    
		    
		    // if mouse is over an empty square on the board, then the piece can be moved there, 
		    // so set the mouse cursor to a hand                 
		    if ( board[row][column] == EMPTY ) 
			setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		    else
			setCursor(Cursor.getDefaultCursor()); 
		}
	    }
	} // MouseMotionHandler
    } // DrawingPanel   

    // Program starts here and constructs the game.
    public static void main (String[] args)
    {
	MoveByClickingMouse frame = new MoveByClickingMouse ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // Main method
} //class
