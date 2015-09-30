import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

/** A help-screen demo that also shows how to draw images and move a single player using the keyboard.
  * NOTE: you must have the "Images" folder (and all its images) in the same folder as this program
  * @author: ICS3U
  * @version: 2009 ICS3U
 */

public class HelpDemo extends JFrame implements ActionListener
{
    // Program constants (declared at the top, these can be used by any method)
    final int NO_OF_ROWS = 7;
    final int NO_OF_COLUMNS = 11; // If you change the # rows or columns, you must resize the imageBackground to fit.
    final int SQUARE_SIZE = 50;  // The length (in pixels) of the side of each square on the board
    final int EMPTY = 0;  // represents an empty space on the board
    final int BEGIN = 1;  // the "Begin" square on the board
    final int EXIT =  2;  // the "Exit" square on the board 

    // Program variables (declared at the top, these can be used or changed by any method)   
    private DrawingPanel drawingArea;
    private Image imageBackground, imageBackground2, sideImage, imageExitTarget, imageBegin, imageWinner, imageWinner1, imageWinner2,
		  imageHelp1, imageHelp2, imageExitHelp, imageArrowNext, imageArrowBack, imageHelp, imageHelpRed;
    private int helpScreen = 0;  // which help screen to show, if any (zero means don't show help)
    private Image playerImage;
    private JMenuItem newOption, exitOption, gridOption, noGridOption, instructionsOption, aboutOption; // for Menu and menu events
    private boolean highlightHelp, showGrid, gameOver;

    // Variables to keep track of the board contents, and the player position
    private int[] [] board;   
    private int currentRow, currentColumn;       
    private int level;     
    

    /** Constructs a HelpDemo frame and sets up the game to start.
      */
    public HelpDemo ()
    {
	board = new int [NO_OF_ROWS] [NO_OF_COLUMNS];   // create our 2D-array board    
    
	// Sets up the game frame
	setTitle ("Help Demo");
	setLocation (100, 50);
	setResizable (false);

	// load images
	// background image was made 550x350 pixels (to fit 11 columns by 7 rows, with each square being 50 pixels wide & high)
	imageBackground = new ImageIcon ("Images/clouds550x350.png").getImage ();
	imageBackground2 = new ImageIcon ("Images/space550x350.png").getImage ();
	sideImage = new ImageIcon ("Images/sidebar150x350.png").getImage ();
	imageBegin = new ImageIcon("Images/begin.gif").getImage();
	imageExitTarget = new ImageIcon ("Images/exitTarget.gif").getImage();
	imageHelp1 = new ImageIcon ("Images/HelpInstructions1.png").getImage ();
	imageHelp2 = new ImageIcon ("Images/HelpInstructions2.png").getImage ();
	imageArrowNext = new ImageIcon ("Images/arrow_nexty.png").getImage ();
	imageArrowBack = new ImageIcon ("Images/arrow_backy.png").getImage();
	imageExitHelp = new ImageIcon ("Images/help_exity.png").getImage(); 
	imageHelp = new ImageIcon("Images/Help100x50.gif").getImage();
	imageHelpRed = new ImageIcon("Images/HelpRed.gif").getImage();
	imageWinner1 = new ImageIcon("Images/winner1.png").getImage();
	imageWinner2 = new ImageIcon("Images/winner2.png").getImage();        
	playerImage = new ImageIcon("Images/sun.gif").getImage();        

	// Image for the HelpDemo icon
	setIconImage (Toolkit.getDefaultToolkit ().getImage ("Images/sun.gif"));
	
	newGame(1);  // set up game to start at level 1

	// set size of the drawing panel to accommodate your images (in this case the clouds and the side images)
	Dimension imageSize = new Dimension (700, 350);

	//create and add the drawing panel.
	drawingArea = new DrawingPanel (imageSize);
	Container contentPane = getContentPane ();
	contentPane.add (drawingArea, BorderLayout.CENTER);

	addMenus ();
    }

    /**
      *  Starts or restarts the game at the specified level
      *  @param gameLevel  Affects...
      */
    private void newGame (int gameLevel)
    {
	level = gameLevel;
	
	// Start player at top left square 
	currentRow = 0;
	currentColumn = 0;  
	gameOver = false;        

	// Set the BEGIN and EXIT locations on the board 
	board[0][0] = BEGIN;
	board[NO_OF_ROWS-1][NO_OF_COLUMNS-1] = EXIT; 
	
	// show any changes on the screen
	repaint();   
    }
    
    /** Game logic for making a move  
      */
    private void makeMove()
    { 
	// If player moved onto the EXIT square, advance to next level   - ie., if ( board[currentRow][currentColumn] == EXIT)         
	if ( currentRow == NO_OF_ROWS-1 && currentColumn == NO_OF_COLUMNS-1 ) 
	{   
	    if ( level < 2 )
		newGame(level+1);  // Advance to next level higher
	    else
	    {   // Game over - flash winner screen
		gameOver = true;
		for (int i=0; i<=20; i++)
		{
		    // Quickly alternate between 2 images repeatedly to create a "flash" effect                
		    if ( i%2 == 0 )
			imageWinner = imageWinner1;
		    else
			imageWinner = imageWinner2;
		    drawingArea.paintImmediately (0, 0, 700, 350);  // Repaints the entire DrawingPanel immediately            
		    // Note: Before a delay(), you must paintImmediately your drawingArea (DrawingPanel). Using repaint() won't work! 
		    delay(100);
		}
	    }    
	}     
	// show any changes on the screen
	repaint();   
    } 
    
    /**  Pauses or delays the program for a specified amount of time
      *  @param milliseconds Length of pause in thousands of a second
      */ 
    private void delay(long milliseconds)
    {
	try            
	{
	    Thread.sleep (milliseconds);  // delay time in thousandths of a second
	}
	catch (InterruptedException exception)
	{
	}                
    }
    
    /** This method is called by Java when a menu option is chosen
      * @param event The event that triggered this method.
      */
    public void actionPerformed (ActionEvent event)
    {   
	// If the new option is selected, the board is reset and a new game begins.
	if (event.getSource () == newOption)
	    newGame (1);
	// Closes the game screen if the exit option is selected.
	else if (event.getSource () == exitOption)
	{
	    dispose ();
	    System.exit (0);
	}
	//Display help screen if the instructions option was selected by user.
	else if (event.getSource () == instructionsOption)
	{
	    helpScreen = 1;
	    repaint ();
	}
	// Displays copyright information if the about option is selected.
	else if (event.getSource () == aboutOption)
	{
	    JOptionPane.showMessageDialog (this, "\u00a9 2009 By ... ",
		    "About HelpDemo", JOptionPane.INFORMATION_MESSAGE);
	}
	// Shows or hides the screen grid when the user presses one of the Grid radio buttons in the top menu
	else if (event.getSource() == gridOption)
	{         
	    showGrid = true;
	    repaint();
	}    
	else if (event.getSource() == noGridOption)
	{        
	    showGrid = false;
	    repaint();
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
	instructionsOption = new JMenuItem ("Instructions");
	instructionsOption.setMnemonic ('I');
	instructionsOption.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_I, InputEvent.CTRL_MASK));
	instructionsOption.addActionListener (this);
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
	helpMenu.add (instructionsOption);
	helpMenu.addSeparator ();
	helpMenu.add (aboutOption);
	
	// Set up the Grid Menu (a "radio button" group)
	ButtonGroup gridGroup = new ButtonGroup ();
	gridOption = new JRadioButtonMenuItem ("Grid", true);  // true means select this button when the program starts
	showGrid = true;            
	gridOption.addActionListener (this);
	noGridOption = new JRadioButtonMenuItem ("No Grid", true);    
	noGridOption.addActionListener (this);
	JMenu gridMenu = new JMenu ("Grid");
	gridMenu.setMnemonic ('G');
	gridGroup.add (gridOption);
	gridGroup.add (noGridOption);
	gridMenu.add (gridOption);
	gridMenu.add (noGridOption);         
	
	// Add the GameMenu, GridMenu and HelpMenu to the top menu bar. 
	JMenuBar mainMenu = new JMenuBar ();
	mainMenu.add (gameMenu);
	mainMenu.add (gridMenu);
	mainMenu.add (helpMenu); 
	// Displays the menus.
	setJMenuBar (mainMenu);
    }


    // Creates and draws the main program panel that the user sees
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object of the specified dimension/size
	  */
	public DrawingPanel (Dimension size)
	{
	    setPreferredSize (size);  // set size of this panel
	    setFont (new Font ("Arial", Font.BOLD, 20));  // Font for drawing text in this panel with g.drawString()            
	    
	    // Add mouse listeners  to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	    this.addMouseMotionListener (new MouseMotionHandler ());
	    
	    // Sets up for keyboard input (arrow keys) on this panel
	    this.setFocusable (true);
	    this.addKeyListener (new KeyHandler ());
	    this.requestFocusInWindow (); 
	}

	/** Repaint the drawing panel.
	  * @param g The Graphics context.
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);  // Tells java to do its preliminary work for drawing. 
	    
	    // If game is over, just show a winner image and message 
	    if ( gameOver )
	    {
		g.drawImage(imageWinner, 0, 0, this);
		g.drawString("Press CTRL + N to play again", 15, 335);
		return;
	    } 
 
	    // Draw background and side images first, so they're shown below the pieces and everything else
	    if ( level == 1 )
		g.drawImage (imageBackground, 0, 0, this);
	    else  // at level 2 
		g.drawImage (imageBackground2, 0, 0, this);
	    
	    // draw side image and its contents    
	    g.drawImage (sideImage, 550, 0, this);  
	    g.setColor (Color.YELLOW);
	    g.drawString ("Level: " + String.valueOf (level), 575, 25); 
	    g.drawString ("Moves: ", 575, 50); 
	    g.drawString ("Row: " + String.valueOf(currentRow), 575, 75);
	    g.drawString ("Col: " + String.valueOf(currentColumn), 575, 100);
	    
	    // Draw help image in the sidebar. If the mouse is over the help image, "highlight" it by drawing a RED "Help" image.  
	    // The highlightHelp variable is set to true in the mouseMoved method when the mouse is over the "Help" image. 
	    if  ( highlightHelp )   
		// Images can be scaled to size, such as in the line below (70 x 40 pixels), but scaling slows down things.
		g.drawImage (imageHelpRed, 570, 200, 70, 40, this);  
	    else
		g.drawImage (imageHelp, 570, 200, 70, 40, this);;     

	    // Redraw all the grid squares and the images for any square
	    for (int row = 0 ; row < NO_OF_ROWS ; row++)
	    {
		for (int column = 0 ; column < NO_OF_COLUMNS ; column++)
		{
		    // Convert each square's row and column number to the x and y positions on the screen
		    int xPos = column * SQUARE_SIZE;
		    int yPos = row * SQUARE_SIZE;

		    // If user has selected the Grid option from the menu, draw a grid square
		    if ( showGrid )
		    {
			g.setColor (Color.BLUE);
			g.drawRect (xPos, yPos, SQUARE_SIZE, SQUARE_SIZE);
		    }
		    
		    // Check whether the Exit or Begin should be drawn in this square of the board
		    if ( board[row][column] == BEGIN )  
			g.drawImage(imageBegin, xPos, yPos, this);        
		    else if (board[row][column] == EXIT)
			g.drawImage(imageExitTarget, xPos, yPos, this);                     
		}
	    }
		
	    // Draw the player next, so it shows on top of the board image  
	    g.drawImage (playerImage, currentColumn * SQUARE_SIZE, currentRow * SQUARE_SIZE, this);     

	    // Display a help screen, if requested. Draw helpscreen last so it appears over top everything else.
	    if (helpScreen == 1)
	    {
		g.drawImage (imageHelp1, 50, 25, this);
		g.drawImage (imageArrowNext, 575, 275, this);
		g.drawImage (imageExitHelp, 325, 275, this);
	    }
	    else if (helpScreen == 2)
	    {
		g.drawImage (imageHelp2, 50, 25, this);
		g.drawImage (imageArrowBack, 80, 275, this);
		g.drawImage (imageExitHelp, 325, 275, this); 
	    } 
	} // paintComponent Method
    } // DrawingPanel


    /** Responds when mouse is pressed
     */
    private class MouseHandler extends MouseAdapter
    {
	/** Responds to a mousePressed event
	 * @param event   Information about the the mouse when its button was pressed.
	 */
	public void mousePressed (MouseEvent event)
	{
	    Point pressed = event.getPoint (); 
	    
	    // Respond if a mouse button was pressed over the Help image
	    if (pressed.x >= 570 && pressed.x < 640 && pressed.y >= 200 && pressed.y < 240 )
	    {
		if ( helpScreen == 0 )  // if a help screen isn't already displayed
		{
		    // Repaint the panel with help screen #1 showing
		    helpScreen = 1;     
		    repaint();
		    setCursor (Cursor.getDefaultCursor ()); // change mouse cursor from a hand to a regular pointer 
		}       
	    }

	    // Respond if a mouse button was pressed while mouse was located within the Y coordinates of the arrows on the help screens
	    if (pressed.y >= 275 && pressed.y < 299)
	    {            
		// Check for the forward arrow on help screen 1
		if (helpScreen == 1 && pressed.x >= 575 && pressed.x < 615)
		    helpScreen++;
		// Check for the backward arrow on help screen 2
		else if (helpScreen == 2 && pressed.x >= 80 && pressed.x <=119)
		    helpScreen--;
		// Check if exit image was pressed in either help screen
		else if (helpScreen > 0 && pressed.x >= 325 && pressed.x < 380)
		     helpScreen = 0;  // zero means don't show any help screen                    
		repaint ();  //Repaint the screen to show any changes
	    } 
	}
    }

    /** Monitors mouse movement over the game panel and responds 
    */
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	/** Responds to mouse-movement inputs
	  * @param event   The event created by the mouse movement
	*/
	public void mouseMoved (MouseEvent event)
	{
	    Point pos = event.getPoint (); 
		   
	    // If mouse is over the "Help" image of the main screen, then highlight/change the "help" image            
	    if (pos.x >= 570 && pos.x < 640 && pos.y >= 200 && pos.y < 240 && helpScreen == 0)
	    {
		highlightHelp = true;
		setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR)); // change mouse cursor to a hand
	    }    
	    else 
	    {    
		highlightHelp = false;
		setCursor (Cursor.getDefaultCursor ());  // change mouse cursor to its normal image 
	    }      
	    
	    // You could add code below here, similar to the above few lines, to change the 
	    // mouse cursor to a hand when the mouse is over an exit or arrow image in the help screens.    
	    
	    repaint();  //Repaint the screen to show any changes
	}            
    }     
    
    // Responds when any key is pressed
    private class KeyHandler extends KeyAdapter
    {
	public void keyPressed (KeyEvent event)
	{
	    if ( gameOver == false )
	    {        
		// Change the currentRow and currentColumn of the player based on the key pressed.
		// You could add code or new method calls here to keep the player within the board,   
		// track #moves, direction, check for traps, etc. (be creative)
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
     
		makeMove();

		// Repaint the screen after the change
		repaint ();
	   }   
	} // keyPressed
    } // KeyHandler    
 
    // The main is the starting point of the program and constructs the game.
    public static void main (String[] args)
    {
	HelpDemo demo = new HelpDemo ();
	demo.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	demo.pack ();
	demo.setVisible (true);
    } // Main method
} //class
