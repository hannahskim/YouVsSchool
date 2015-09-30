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
    private boolean drawBoard;
    // How to play images
    private Image[] howToPlayImages;
    private int currentPage;
    private boolean howToPlayScreen;
    private boolean helpScreen = false;
    private boolean garbageOpen = false;
    public final static int YOU_X = 75;
    private double money;
    private int day;
    private int DEFENDER_TYPES = 10;
    private Image[] messages = new Image [20];
    private String[] MESSAGE_IMAGES = {"Images/Welcome_Homework Introduction.png", "Images/Piggy Bank Introduction.png",
	"Images/Pencil Cannon Introduction.png", "Images/Assignment Introduction.png", "Images/Eraser Introduction.png",
	"Images/Quiz Introduction.png", "Images/Pen Introduction.png", "Images/Highlighter Introduction.png", "Images/Pop Quiz Introduction.png",
	"Images/Pencil Crayon Introduction.png", "Images/Book Report Introduction.png", "Images/Ruler Introduction.png", "Images/Test Introduction.png",
	"Images/Text Book Introduction.png", "Images/Project Introduction.png", "Images/EQAO Introduction.png", "Images/Exam Introduction.png",
	"Images/Lost Level.png", "Images/Level Won.png"};
    private Image[] messages = new Image [MESSAGE_IMAGES.length];
    private int messageNo = 0; // messages index
    private final int LEVEL_WON_MESSAGE = 18;
    private final int LEVEL_LOST_MESSAGE = 17;
    private final int TIME_CHANGE_INTERVAL = 500;
    private final double STARTING_MONEY = 2.00;
    private final int NON_WAVE_ENEMIES = 32;
    private final int WAVE_ENEMIES = 15;
    private final int INITIAL_DELAY = 0;
    private final int MINUTES = 1;
    private final int LEVEL_LENGTH = 1000 * 60 * MINUTES; /// TIME_CHANGE_INTERVAL;
    private final int TIME_TRACKER_SPEED = 4;
    public final int[] GENERATING_CHANCES = {2, 3, 8, 15, 10, 15, 11, 13, 0, 0}; // Chances of generating each type of enemy
    private int RECTANGLE_HEIGHT = 76;
    private int RECTANGLE_WIDTH = 75;
    private int SHOP_SQUARE_WIDTH = 84;
    private int SHOP_SQUARE_HEIGHT = 75;
    private int BOARD_WIDTH = 840;
    private int BOARD_HEIGHT = 600;
    private int timePassed = 0;
    private int[] waveArrivalTimes = new int [4];
    private int noOfWaves;
    public Enemy[] enemies;
    private ArrayList defenders;
    public static Timer enemyTimer;
    private boolean levelStarted, levelLost;
    private boolean levelWon = false;
    private int selectStatus;
    private int howToPlayNo;

    public final Dimension BOARD_SIZE = new Dimension (BOARD_WIDTH, BOARD_HEIGHT);

    /** Constructs a new YouVsSchool object
      */
    public YouVsSchoolBoard ()
    {
	// Sets up the board area, loads in background images and starts a new game
	setPreferredSize (BOARD_SIZE);
	setFont (new Font ("Arial", Font.BOLD, 20));
	boardImage = new ImageIcon ("Images/Board.png").getImage ();
	titleScreen = new ImageIcon ("Images/Title Screen.png").getImage ();
	timeTracker = new ImageIcon ("Images/Time Tracker.png").getImage ();
	garbage = new ImageIcon ("Images/Garbage.png").getImage ();
	fireAlarm = new ImageIcon ("Images/FireAlarm.png").getImage ();
	openedGarbage = new ImageIcon ("Images/Garbage2.png").getImage ();


	// Loads up level images
	for (int i = 0 ; i < messages.length ; i++)
	    messages [i] = new ImageIcon (MESSAGE_IMAGES [i]).getImage ();


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
	System.out.println (day);

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
	    if (timePassed >= LEVEL_LENGTH)
	    {
		messageNo = LEVEL_WON_MESSAGE;
		levelWon = true;
		levelStarted = false;
		removeAllDefenders ();
		//enemyTimer.stop ();
	    }
	    //if (allDestroyed (enemies))
	    //{

	    //}

	    // Moves enemies
	    for (int i = 0 ; i < enemies.length ; i++)
	    {
		if (enemies [i].hasHealthLeft ())
		{
		    boolean lost = enemies [i].move ();
		    if (lost)
		    {
			enemyTimer.stop ();
			levelLost = true;
			levelStarted = false;
			messageNo = LEVEL_LOST_MESSAGE;
		    }


		    for (int j = 0 ; j < defenders.size () ; j++)
		    {
			Defender def = (Defender) defenders.get (j);

			// if (!def.hasHealthLeft ())
			// {
			//     enemies [i].stopAttacking ();
			// }
			if (def.hasHealthLeft () && enemies [i].intersects (def))
			{
			    enemies [i].startAttacking ();
			    def.injure (10);
			    if (!def.hasHealthLeft ())
				enemies [i].stopAttacking ();

			}
		    }
		}

	    }
	    for (int i = 0 ; i < defenders.size () ; i++)
	    {
		Defender def = (Defender) defenders.get (i);
		//def.defend();

		for (int j = 0 ; j < enemies.length ; j++)
		{
		}
	    }
	    repaint ();
	}
    }



    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);

	if (!drawBoard)
	{
	    g.drawImage (titleScreen, 0, 0, this);

	    // Displays how to play instructions
	    if (howToPlayScreen == true)
		g.drawImage (howToPlayImages [currentPage], 110, 80, this);

	}

	else
	{
	    g.drawImage (boardImage, 0, 0, this);
	    // Draws time tracker
	    g.setColor (new Color (50, 225, 50));
	    g.fillRect (100, 553, TIME_TRACKER_SPEED * LEVEL_LENGTH / TIME_CHANGE_INTERVAL, 15);
	    g.drawImage (timeTracker, TIME_TRACKER_SPEED * timePassed / TIME_CHANGE_INTERVAL + 77, 538, this);
	    // Draws the garbage
	    g.drawImage (garbage, 1, 515, this);
	    // Draws the fireAlarm
	    g.drawImage (fireAlarm, 780, 550, this);
	    if (garbageOpen == false)
		g.drawImage (garbage, 1, 515, this);
	    else if (garbageOpen == true)
		g.drawImage (openedGarbage, 1, 505, this);

	    // Draws the amount of money on the top left corner of the board
	    if ((int) (money * 100) % 10 == 0)
	    {
		g.drawString ("$" + money + "0", 5, 100);
	    }
	    else
		g.drawString ("$" + money, 5, 100);
	    // Blocks out defenders not yet unlocked in the game
	    // for (int i = 0; i < DEFENDER_TYPES; i ++)
	    // {
	    //
	    // }

	    // For drawing level screens
	    if (!levelStarted)
	    {
		for (int i = 0 ; i <= messages.length ; i++)
		{
		    if (messageNo == i)
			g.drawImage (messages [messageNo], 140, 134, this);
		}
	    }

	    // Draws enemies
	    if (levelStarted)
	    {
		// Selects the defender
		if (selectStatus != -1)
		{
		    g.setColor (new Color (100, 225, 100, 50));
		    g.fillRect (selectStatus * SHOP_SQUARE_WIDTH + 1, 1, SHOP_SQUARE_WIDTH, SHOP_SQUARE_HEIGHT);
		}

		// Draws defenders
		for (int i = 0 ; i < defenders.size () ; i++)
		{
		    Defender def = (Defender) defenders.get (i);
		    def.draw (g);
		}

		// Draws enemies
		for (int i = 0 ; i < enemies.length ; i++)
		{
		    enemies [i].draw (g);
		}

	    }

	    if (levelLost || levelWon)
	    {
		g.drawImage (messages [messageNo], 140, 134, this);
	    }
	}
    } // end of PaintComponent


    /** Starts a new game
      */
    public void newGame ()
    {
	day = 0;
	messageNo = 0;
	repaint ();

	enemyTimer = new Timer (TIME_CHANGE_INTERVAL, new TimerEventHandler ());
    }


    public int findWaves ()
    {
	// Calculates the number of waves
	if (day <= 3)
	    return 1;
	else if (day <= 6)
	    return 2;
	else if (day <= 10)
	    return 3;
	else
	    return 4;
    }


    /** Generates a type of enemy for individual attacks
	 * @return the type of enemy (integer)
	 */
    public int generateType (int arrivalTime)
    {
	// Determines enemy type based on how far the game and level has progressed as well as the chances of encountering a type
	int generate = 1;
	int newCharacterInterval = 0;
	int dayCheck = 1;
	// while (true)
	// {

	// For each type
	for (int type = 0 ; type < enemies.length ; type++)
	{
	    // If they were the most recent enemy to be introduced, or if enough time in the level has passed, or a randomly generated number based on their chances
	    // is the generate number
	    if (dayCheck == day || dayCheck + 1 == day || timePassed > newCharacterInterval || (int) (Math.random () * GENERATING_CHANCES [type]) == generate)
	    {
		return type;
	    }
	    dayCheck += 2;
	    newCharacterInterval += 20000;

	}
	// Dummy return for code to run: return homework
	return 0;

	//     if (type >= GENERATING_CHANCES.length)
	//         type = 0;
	//
	// }
    }


    /** Generates a type of enemy for a wave
      * @param wave the wave number
      * @return the type of enemy (integer)
      */
    public int generateWaveType (int wave)
    {
	{
	    int generate = 1;
	    int type = 0;
	    // For each space for an enemy in the array, generate a type depending on its chances of being generated
	    while (true)
	    {
		if (type == day / 2 || type == day / 2 - 1 || (int) (Math.random () * GENERATING_CHANCES [type]) == generate)
		    return type;
		type++;
		if (type >= GENERATING_CHANCES.length)
		    type = 0;
	    }
	}
    }


    public Enemy[] generateEnemies (int noOfWaves)
    {
	// Creates enemies offscreen
	// Sets up array of enemies in waves (2D array, row represents wave, column represents the enemy)
	Enemy[] enemies = new Enemy [NON_WAVE_ENEMIES + noOfWaves * WAVE_ENEMIES];
	for (int wave = 0 ; wave < noOfWaves ; wave++)
	{
	    // Used to divide the level into portions at the end of which waves will occur
	    // Math.random ensures the enemies will not be on top of each other
	    waveArrivalTimes [wave] = LEVEL_LENGTH / TIME_CHANGE_INTERVAL / noOfWaves * (wave + 1) - (int) ((Math.random () * WAVE_ENEMIES));

	    for (int i = 0 ; i < WAVE_ENEMIES ; i++)
		enemies [NON_WAVE_ENEMIES + i + wave * WAVE_ENEMIES] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateWaveType (wave), waveArrivalTimes [wave], this);
	}

	// Sets up array of individual attacking enemies
	for (int i = 0 ; i < NON_WAVE_ENEMIES ; i += 4)
	{
	    int arrivalTime = (int) (Math.random () * (LEVEL_LENGTH / TIME_CHANGE_INTERVAL - INITIAL_DELAY)) + INITIAL_DELAY;
	    enemies [i] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	    arrivalTime = (int) (Math.random () * (LEVEL_LENGTH / TIME_CHANGE_INTERVAL - 90)) + 90;
	    enemies [i + 1] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	    arrivalTime = (int) (Math.random () * (LEVEL_LENGTH / TIME_CHANGE_INTERVAL - 180)) + 180;
	    enemies [i + 2] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	    arrivalTime = (int) (Math.random () * (LEVEL_LENGTH / TIME_CHANGE_INTERVAL - 270)) + 270;
	    enemies [i + 3] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	}
	return enemies;
    }


    /** Starts the level
      */
    public void beginLevel ()
    {
	noOfWaves = findWaves ();

	levelStarted = true;
	levelLost = false;
	levelWon = false;
	timePassed = 0;

	money = STARTING_MONEY;
	day++;
	System.out.println (day);

	enemies = generateEnemies (noOfWaves);

	// Begins enemyTimer
	enemyTimer.restart ();
	enemyTimer.start ();
    }


    public void addDefender (int x, int y)
    {
	int type = selectStatus;
	//double moneyLeft = money - Defender.PRICE [type];
	//if (moneyLeft >= 0)
	//{
	// money = moneyLeft;
	if (type == 5)
	{
	    y = RECTANGLE_HEIGHT;
	    Defender newDef = new Defender (x, y, type, this);
	    defenders.add (newDef);
	    for (int i = 0 ; i < enemies.length ; i++)
	    {
		if (newDef.intersects (enemies [i]))
		    enemies [i].kill ();

	    }
	}
	else
	{
	    if (type == 1 || type == 4 || type == 8)
	    {
		y -= RECTANGLE_HEIGHT;
	    }

	    Defender newDef = new Defender (x, y, type, this);
	    defenders.add (newDef);
	}
	//}

    }


    public void removeAllDefenders ()
    {
	for (int i = 0 ; i < defenders.size () ; i++)
	{
	    //Defender def = (Defender) defenders.get (i);
	    defenders.remove (i);
	}
    }


    public boolean allDestroyed (Enemy[] enemies)
    {
	for (int i = 0 ; i < enemies.length ; i++)
	{
	    if (enemies [i].hasHealthLeft ())
		return false;
	}
	return true;
    }


    public boolean allDestroyed (ArrayList defenders)
    {

	for (int i = 0 ; i < defenders.size () ; i++)
	{
	    Defender def = (Defender) defenders.get (i);
	    if (def.hasHealthLeft ())
		return false;
	}
	return true;
    }


    public boolean hasMoneyLeft ()
    {
	if (money > 0)
	    return true;
	else
	    return false;
    }


    public void collectCoin ()
    {
	money += 0.25;
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
	if (event.getKeyCode () == KeyEvent.VK_ENTER)
	{
	    if (levelLost)
	    {
		drawBoard = false;
	    }

	    if (!levelStarted || levelWon)
	    {
		messageNo++;
		if (messageNo >= 3)
		{
		    beginLevel ();
		}

	    }
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
	    if (!drawBoard)
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
	if (!drawBoard)
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
	    // Determine which defender was selected
	    if (selectStatus == -1 && pos.y < SHOP_SQUARE_HEIGHT - 15)
	    {
		selectStatus = pos.x / SHOP_SQUARE_WIDTH;
	    }

	    // Place the defender, centered in the square chosen
	    else if (selectStatus > -1 && pos.x > 85 && pos.y > SHOP_SQUARE_HEIGHT && pos.y < 530)
	    {
		int row = pos.y / RECTANGLE_WIDTH;
		int col = pos.x / RECTANGLE_HEIGHT;
		int x = col * RECTANGLE_WIDTH + 10;
		int y = row * RECTANGLE_HEIGHT;

		addDefender (x, y);

		selectStatus = -1;

	    }

	    // If the user wishes to cancel their selection
	    else if (pos.y < SHOP_SQUARE_HEIGHT && pos.x > SHOP_SQUARE_WIDTH * selectStatus && pos.x < SHOP_SQUARE_WIDTH * selectStatus + SHOP_SQUARE_WIDTH)
	    {
		selectStatus = -1;
	    }
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


