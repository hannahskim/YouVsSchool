import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.util.ArrayList;

/** The "YouVsSchoolBoard" class.
  * Handles the board play You Vs School
  * @author Melissa Li and Hannah Kim
  * @version December 13, 2011
  */
public class YouVsSchoolBoard extends JPanel implements MouseListener, KeyListener
{
    private Image titleScreen, boardImage, garbage, fireAlarm, openedGarbage;
    // How to play images
    private Image[] howToPlayImages;
    //private Image howToPlayOne, howToPlayTwo, howToPlayThree, howToPlayFour;
    private int currentPage;
    private boolean helpScreen = false;
    private boolean garbageOpen = false;
    public final static int YOU_X = 75;
    private int money;
    private int day;
    private int DEFENDER_TYPES = 10;
    private Image[] messages = new Image [20];
    private String[] MESSAGE_IMAGES = {"Images/Welcome_Homework Introduction.png", "Images/Piggy Bank Introduction.png",
	"Images/Pencil Cannon Introduction.png", "Images/Eraser Introduction.png", "Images/Pen Introduction.png",
	"Images/Highlighter Introduction.png", "Images/Pencil Crayon Introduction.png", "Images/Ruler Introduction.png", "Images/Lost Level.png"};
    private int messageNo = 0; // messages index
    private Image[] cover = new Image [5];
    private String[] DEFENDER_COVER_IMAGES = {"Images/Highlight.png", "Images/Load One-third.png", "Images/Load Two-thirds.png", "Images/Load Full.png",
	"Images/Cover.png", };
    private int coverNo = 0; // cover image index
    private final int TIME_CHANGE_INTERVAL = 500;
    private final int NON_WAVE_ENEMIES = 32;
    private final int WAVE_ENEMIES = 15;
    private final int INITIAL_DELAY = 0;
    private final int MINUTES = 3;
    private final int LEVEL_LENGTH = 1000 * 60 * MINUTES / TIME_CHANGE_INTERVAL;
    public final int[] GENERATING_CHANCES = {2, 3, 8, 15, 10, 15, 11, 13, 0, 0}; // Chances of generating each type of enemy
    private int SQUARE_HEIGHT = 76;
    private int SQUARE_WIDTH = 75;
    private int timePassed = 0;
    private int[] waveArrivalTimes = new int [4];
    private int noOfWaves;
    public Enemy[] enemies;
    private ArrayList defenders;
    private Timer timer;
    private boolean levelStarted, levelLost, howToPlayScreen;
    private boolean levelOver = false;
    private int selectStatus;
    private int howToPlayNo;

    public final Dimension BOARD_SIZE = new Dimension (840, 600);


    /** Constructs a new YouVsSchool object
      */
    public YouVsSchoolBoard ()
    {
	// Sets up the board area, loads in background images and starts a new game
	setPreferredSize (BOARD_SIZE);
	boardImage = new ImageIcon ("Images/Board.png").getImage ();
	titleScreen = new ImageIcon ("Images/Title Screen.png").getImage ();
	garbage = new ImageIcon ("Images/Garbage.png").getImage ();
	fireAlarm = new ImageIcon ("Images/FireAlarm.png").getImage ();
	openedGarbage = new ImageIcon ("Images/Garbage2.png").getImage ();
	//currentHowToPlay = new ImageIcon (howToPlayImages [howToPlayNo]).getImage ();


	// Loads up level images
	for (int i = 0 ; i < MESSAGE_IMAGES.length ; i++)
	    messages [i] = new ImageIcon (MESSAGE_IMAGES [i]).getImage ();
	for (int i = 0 ; i < DEFENDER_COVER_IMAGES.length ; i++)
	    cover [i] = new ImageIcon (DEFENDER_COVER_IMAGES [i]).getImage ();

	// How to play Images
	howToPlayImages = new Image [4];
	int screenIndex = 1;
	for (int i = 0 ; i < howToPlayImages.length ; i++)
	{
	    howToPlayImages [i] = new ImageIcon ("Images/HowToPlay" + screenIndex + ".png").getImage ();
	    screenIndex++;
	}

	// Add mouse listeners and Key Listeners to the game board
	addMouseListener (this);
	setFocusable (true);
	addKeyListener (this);
	addMouseMotionListener (new MouseMotionHandler ());
	requestFocusInWindow ();
	selectStatus = -1;
	defenders = new ArrayList ();

	// Set variables
	howToPlayScreen = false;
	howToPlayNo = -1;
	currentPage = -1;
    }


    /** An inner class to deal with the timer events
	*/
    private class TimerEventHandler implements ActionListener
    {

	/** The following method is called each time a timer event is
	 * generated (every TIME_INTERVAL milliseconds in this example)
	 * @param event the Timer event
	 */
	public void actionPerformed (ActionEvent event)
	{
	    timePassed += TIME_CHANGE_INTERVAL;

	    // Moves enemies
	    for (int i = 0 ; i < enemies.length ; i++)
	    {
		boolean lost = enemies [i].move ();
		if (lost)
		{
		    timer.stop ();
		    levelOver = true;
		    messageNo = MESSAGE_IMAGES.length - 1;
		}
		for (int j = 0 ; j < defenders.size () ; j++)
		{
		    Defender def = (Defender) defenders.get (j);
		    if (enemies [i].intersects (def))
		    {
			enemies [i].startAttacking ();
		    }
		    else
		    {
			enemies [i].stopAttacking ();
		    }
		}
		// If an enemy has reached YOU


	    }
	    repaint ();
	}
    }



    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);

	if (day == 0)
	{
	    g.drawImage (titleScreen, 0, 0, this);

	    // Displays how to play instructions
	    if (howToPlayScreen == true)
		g.drawImage (howToPlayImages [currentPage], 110, 80, this);

	}

	else
	{
	    g.drawImage (boardImage, 0, 0, this);
	    g.drawImage (garbage, 1, 515, this);
	    g.drawImage (fireAlarm, 780, 550, this);
	    if (garbageOpen == false)
		g.drawImage (garbage, 1, 515, this);
	    else if (garbageOpen == true)
		g.drawImage (openedGarbage, 1, 505, this);
	    // Blocks out defenders not yet unlocked in the game
	    // for (int i = 0; i < DEFENDER_TYPES; i ++)
	    // {
	    //
	    // }

	    // For drawing level screens
	    if (!levelStarted)
	    {
		for (int i = 0 ; i <= 3 ; i++)
		{
		    if (messageNo == i)
			g.drawImage (messages [messageNo], 160, 152, this);
		}
	    }

	    // Draws enemies
	    if (levelStarted)
	    {
		// Selects the defender
		if (selectStatus != -1)
		{
		    g.setColor (new Color (100, 225, 100, 50));
		    g.fillRect (selectStatus * 84 + 1, 1, 84, 60);
		    //if (
		}
		for (int i = 0 ; i < defenders.size () ; i++)
		{
		    Defender def = (Defender) defenders.get (i);
		    def.draw (g);
		}
		for (int i = 0 ; i < enemies.length ; i++)
		{
		    enemies [i].draw (g);
		    //System.out.println (enemies [i].x + " " + enemies [i].y);
		}
	    }

	    if (levelOver)
	    {
		g.drawImage (messages [messageNo], 160, 152, this);
		//g.drawImage (messages [4], 160, 152, this);
	    }
	}
    } // end of PaintComponent


    /** Starts a new game
      */
    public void newGame ()
    {
	day = 0;
	money = 0;
	timePassed = 0;
	messageNo = 0;
	repaint ();
    }


    /** Generates a type of enemy for individual attacks
	 * @return the type of enemy (integer)
	 */
    public int generateType (int arrivalTime)
    {
	// Determines enemy type based on how far the level has progressed as well as the chances of encountering a type
	int generate = 1;
	int type = 0;
	int dayCheck = 1;
	int timePassedInterval = 20000;
	while (true)
	{
	    if (dayCheck == day || arrivalTime <= timePassedInterval || (int) (Math.random () * GENERATING_CHANCES [type]) == generate)
		return type;
	    type++;
	    if (type >= GENERATING_CHANCES.length)
		type = 0;
	    dayCheck++;
	    timePassedInterval += 20000;
	}
    }


    /** Generates a type of enemy for a wave
      * @param wave the wave number
      * @return the type of enemy (integer)
      */
    public int generateWaveType (int wave)
    {
	int generate = 1;
	int type = 0;
	// For each space for an enemy in the array, generate a type depending on its chances of being generated
	while (true)
	{
	    if ((int) (Math.random () * GENERATING_CHANCES [type]) == generate)
		return type;
	    type++;
	    if (type >= GENERATING_CHANCES.length)
		type = 0;
	}
    }


    /** Starts the level
      */
    public void beginLevel ()
    {
	// Calculates the number of waves
	if (day <= 3)
	    noOfWaves = 1;
	else if (day <= 6)
	    noOfWaves = 2;
	else if (day <= 10)
	    noOfWaves = 3;
	else
	    noOfWaves = 4;

	levelLost = false;
	levelOver = false;
	day++;

	// Creates enemies offscreen
	// Sets up array of enemies in waves (2D array, row represents wave, column represents the enemy)
	enemies = new Enemy [NON_WAVE_ENEMIES + noOfWaves * WAVE_ENEMIES];
	for (int wave = 0 ; wave < noOfWaves ; wave++)
	{
	    // Used to divide the level into portions at the end of which waves will occur
	    // Math.random ensures the enemies will not be on top of each other
	    waveArrivalTimes [wave] = LEVEL_LENGTH / noOfWaves * (wave + 1) - (int) ((Math.random () * WAVE_ENEMIES));

	    for (int i = 0 ; i < WAVE_ENEMIES ; i++)
	    {
		enemies [NON_WAVE_ENEMIES + i + wave * WAVE_ENEMIES] = new Enemy ((int) (Math.random () * 6) * SQUARE_WIDTH + 75, generateWaveType (wave), waveArrivalTimes [wave], this);
	    }
	}

	// Sets up array of individual attacking enemies
	for (int i = 0 ; i < NON_WAVE_ENEMIES ; i += 4)
	{
	    int arrivalTime = (int) (Math.random () * (LEVEL_LENGTH - INITIAL_DELAY)) + INITIAL_DELAY;
	    enemies [i] = new Enemy ((int) (Math.random () * 6) * SQUARE_WIDTH + 75, generateType (arrivalTime), arrivalTime, this);
	    arrivalTime = (int) (Math.random () * (LEVEL_LENGTH - 90)) + 90;
	    enemies [i + 1] = new Enemy ((int) (Math.random () * 6) * SQUARE_WIDTH + 75, generateType (arrivalTime), arrivalTime, this);
	    arrivalTime = (int) (Math.random () * (LEVEL_LENGTH - 180)) + 180;
	    enemies [i + 2] = new Enemy ((int) (Math.random () * 6) * SQUARE_WIDTH + 75, generateType (arrivalTime), arrivalTime, this);
	    arrivalTime = (int) (Math.random () * (LEVEL_LENGTH - 270)) + 270;
	    enemies [i + 3] = new Enemy ((int) (Math.random () * 6) * SQUARE_WIDTH + 75, generateType (arrivalTime), arrivalTime, this);
	}

	// Begins timer
	timer = new Timer (TIME_CHANGE_INTERVAL, new TimerEventHandler ());
	timer.start ();
    }


    /** Delays the given number of milliseconds
    *@param milliSec The number of milliseconds to delay
    */
    private void delay (int milliSec)
    {
	try
	{
	    Thread.sleep (milliSec);
	}


	catch (InterruptedException e)
	{
	}
    }


    // Keyboard events you can listen for since this JPanel is a KeyListener

    /** Responds to a keyPressed event
    * @param event information about the key pressed event
    */

    public void keyPressed (KeyEvent event)
    {
	if (levelStarted == false)
	{
	    if (event.getKeyCode () == KeyEvent.VK_ENTER)
	    {
		messageNo++;
		if (messageNo == 3)
		{
		    beginLevel ();
		    levelStarted = true;
		}

	    }
	}
	if (levelOver == true)
	{
	    day = 0;
	}

	repaint ();
    }


    // Extra methods needed since this game board is a KeyListener
    public void keyReleased (KeyEvent event)
    {
    }


    public void keyTyped (KeyEvent event)
    {
    }


    //* Use for trashcan
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
	    if (day == 0)
	    {
		if (pos.x >= 700 && pos.x < 765 && pos.y >= 488 && pos.y < 558)
		    setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		else
		    setCursor (Cursor.getDefaultCursor ());
		if (howToPlayScreen == true && currentPage < 4)
		{
		    if (pos.x >= 385 && pos.x < 490 &&
			    pos.y >= 380 && pos.y <= 430 ||
			    pos.x >= 550 && pos.x <= 650 &&
			    pos.y >= 380 && pos.y < 435 ||
			    pos.x >= 225 && pos.x <= 330 &&
			    pos.y >= 380 && pos.y <= 435)
		    {

			if (pos.x >= 385 && pos.x < 490 &&
				pos.y >= 380 && pos.y <= 430)
			    setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
			if (currentPage != 3 && pos.x >= 550 && pos.x <= 650 &&
				pos.y >= 380 && pos.y < 435)
			    setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
			if (currentPage != 0 && pos.x >= 225 && pos.x <= 330 &&
				pos.y >= 380 && pos.y <= 435)
			    setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		    }
		    else
			setCursor (Cursor.getDefaultCursor ());
		}
	    }
	    else
	    {
		if (selectStatus == -1
			&& event.getY () > 510 && event.getY () < 580
			&& event.getX () > 10 && event.getX () < 50)
		    garbageOpen = true;
		else
		    garbageOpen = false;
	    }
	    repaint (); //Repaint the screen to show any changes
	}
    }


    // Mouse events you can listen for since this JPanel is a MouseListener

    /** Responds to a mousePressed event
    *@param event information about the mouse pressed event
    */
    public void mousePressed (MouseEvent event)
    {
	Point pos = event.getPoint ();
	System.out.println (pos);
	if (day == 0)
	{
	    if (pos.x >= 700 && pos.x < 765 && pos.y >= 540 && pos.y < 558)
	    {
		day = 1;
		setCursor (Cursor.getDefaultCursor ());
	    }
	    else if (pos.x >= 700 && pos.x < 765 && pos.y >= 490 && pos.y < 510)
	    {
		howToPlayScreen = true;
		currentPage = 0;
		setCursor (Cursor.getDefaultCursor ());
	    }
	    else if (howToPlayScreen == true && currentPage < 4)
	    {
		if (pos.x >= 550 && pos.x <= 650 &&
			pos.y >= 380 && pos.y < 435 &&
			currentPage < 3)
		    currentPage++;
		if (pos.x >= 385 && pos.x < 490 &&
			pos.y >= 380 && pos.y <= 430)
		    howToPlayScreen = false;
		if (currentPage != 0 && pos.x >= 225 && pos.x <= 330 &&
			pos.y >= 380 && pos.y <= 435 &&
			currentPage > 0)
		    currentPage--;

	    }

	}

	else
	{
	    if (selectStatus == -1 && pos.y < 60)
		selectStatus = pos.x / 84;
	    else if (selectStatus == -1 && pos.y > 60)
	    {
		if (event.getY () > 510 && event.getY () < 580
			&& event.getX () > 10 && event.getX () < 50)
		    defenders.remove (0);
	    }
	    else if (pos.x > 85 && pos.x < 840 && pos.y < 530)
	    {
		int row = pos.y / SQUARE_WIDTH;
		int col = pos.x / SQUARE_HEIGHT;
		int x = col * SQUARE_WIDTH + 10;
		int y = row * SQUARE_HEIGHT;
		if (selectStatus == 5)
		    y = SQUARE_HEIGHT;
		else if (selectStatus == 1 || selectStatus == 4 || selectStatus == 8)
		    y -= SQUARE_HEIGHT;

		defenders.add (new Defender (x, y, selectStatus, this));
		selectStatus = -1;
		
		//if 
	    }
	    else if (selectStatus != -1 && pos.y > 60 && pos.x > 84)
	    {
		// highlightDefender = true;
		// highlightDefenderX = 0;
	    }
	    // // Highlights the defender in the shop clicked by the user
	    // if (pos.x >= 0 && pos.x <= 84 && pos.y <= 60)
	    // {
	    //     highlightDefender = true;
	    //     highlightDefenderX = 0;
	    // }
	    // else if (pos.x >= 84 && pos.x <= 168 && pos.y <= 60)
	    // {
	    //     highlightDefender = true;
	    //     highlightDefenderX = 84;
	    // } // Fix for all defenders
	    repaint ();
	}
    }


    public void mouseReleased (MouseEvent event)
    {
    }


    public void mouseClicked (MouseEvent event)
    {

    }


    public void mouseEntered (MouseEvent event)
    {
    }


    public void mouseExited (MouseEvent event)
    {
    }
}


