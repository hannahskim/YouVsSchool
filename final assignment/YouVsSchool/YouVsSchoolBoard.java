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
    // Game images and image variables
    private Image titleScreen, boardImage, garbage, fireAlarm, openedGarbage, timeTracker;
    private boolean drawBoard;

    // How to play images and variables
    private Image[] howToPlayImages;
    private int currentPage;
    private boolean howToPlayScreen;
    private boolean helpScreen = false;
    private boolean garbageOpen = false;
    private int howToPlayNo;

    // Key Constants
    private final double STARTING_MONEY = 2.00;
    private final int MINUTES = 3;
    public final static int YOU_X = 75;
    private final int LEVEL_LENGTH = 1000 * 60 * MINUTES;
    private int RECTANGLE_HEIGHT = 76;
    private int RECTANGLE_WIDTH = 75;
    private int SHOP_SQUARE_WIDTH = 84;
    private int SHOP_SQUARE_HEIGHT = 75;
    private int BOARD_WIDTH = 840;
    private int BOARD_HEIGHT = 600;

    // Key Variables
    private double money;
    private int day;
    private int timePassed = 0;
    private boolean levelStarted;
    private boolean levelLost;
    private boolean levelWon;

    // Enemy and Defender related variables and constants
    private int[] waveArrivalTimes = new int [4];
    private int noOfWaves;
    public Enemy[] enemies;
    public int[] attackingSomebody;
    private final int NON_WAVE_ENEMIES = 20;
    private final int WAVE_ENEMIES = 10;
    private final int INITIAL_DELAY = 0;
    private final int MAX_ENEMY_DISTANCE = LEVEL_LENGTH / ENEMY_TIME_INTERVAL;
    public final int[] GENERATING_CHANCES = {2, 3, 8, 10, 8, 12, 8, 0, 0, 0}; // Chances of generating each type of enemy

    private ArrayList defenders;
    private int selectStatus;

    // Variables for the audio
    private YouVsSchoolAudio audio;

    // All message related variables and constants
    private String[] MESSAGE_IMAGES = {"Images/Welcome_Homework Introduction.png", "Images/Piggy Bank Introduction.png",
	"Images/Pencil Cannon Introduction.png", "Images/Assignment Introduction.png", "Images/Eraser Introduction.png",
	"Images/Quiz Introduction.png", "Images/Pen Introduction.png", "Images/Highlighter Introduction.png", "Images/Pop Quiz Introduction.png",
	"Images/Pencil Crayon Introduction.png", "Images/Book Report Introduction.png", "Images/Ruler Introduction.png", "Images/Test Introduction.png",
	"Images/Text Book Introduction.png", "Images/Project Introduction.png", "Images/EQAO Introduction.png", "Images/Exam Introduction.png",
	"Images/Lost Level.png", "Images/Level Won.png"};
    private Image[] messages = new Image [MESSAGE_IMAGES.length];
    private int MESSAGE_X = 140;
    private int MESSAGE_Y = 134;
    private int messageNo = 0; // messages index
    private final int LEVEL_WON_MESSAGE = 18;
    private final int LEVEL_LOST_MESSAGE = 17;

    // Timers and timer constants and variables
    public static Timer enemyTimer;
    public static Timer timeTrackerTimer;
    private static final int ENEMY_TIME_INTERVAL = 500;
    private final int TIME_TRACKER_INTERVAL = 300;
    private final int TIME_TRACKER_SPEED = 1;
    private boolean drawTimeTracker;

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

	// Set variables
	howToPlayScreen = false;
	howToPlayNo = -1;
	currentPage = -1;

	//Refer to the audio class
	audio = new YouVsSchoolAudio ();

	// Set timers
	enemyTimer = new Timer (ENEMY_TIME_INTERVAL, new EnemyEventHandler ());
	timeTrackerTimer = new Timer (TIME_TRACKER_INTERVAL, new TimeTrackerHandler ());

	// Add mouse listeners and Key Listeners to the game board
	addMouseListener (this);
	setFocusable (true);
	addKeyListener (this);
	addMouseMotionListener (new MouseMotionHandler ());
	requestFocusInWindow ();
	selectStatus = -1;
	defenders = new ArrayList ();

	// Plays the title screen music of the game
	// and stop the backgroundSound in case if there's any
	audio.backgroundSound.stop ();
	audio.titleMusic ();
    }


    /** An inner class to deal with the enemy timer events
	*/
    private class EnemyEventHandler implements ActionListener
    {

	/** The following method is called each time a timer event is
	 * generated
	 * @param event the Timer event
	 */
	public void actionPerformed (ActionEvent event)
	{
	    // Keep track of the time passing
	    timePassed += ENEMY_TIME_INTERVAL;

	    // If all the enemies have been killed, set all related variables
	    if (allDestroyed (enemies))
	    {
		levelWon = true;
		levelStarted = false;
		enemyTimer.stop ();
	    }

	    // Moves enemies
	    for (int i = 0 ; i < enemies.length ; i++)
	    {
		if (enemies [i].hasHealthLeft ())
		{
		    // If the enemies have reached YOU
		    // Move method also returns boolean value depending on whether the enemy has reached
		    // the end or not
		    boolean lost = enemies [i].move ();
		    if (lost)
		    {
			// Set variables
			enemyTimer.stop ();
			levelLost = true;
			levelStarted = false;
		    }

		    // Check for each defender if the enemy has encountered it
		    for (int j = 0 ; j < defenders.size () ; j++)
		    {
			Defender def = (Defender) defenders.get (j);

			// If the defender has not yet been destroyed and the enemy has reached it
			if (def.hasHealthLeft () && enemies [i].intersects (def))
			{
			    // Attack, and keep track of which defender it is attacking
			    enemies [i].startAttacking ();
			    attackingSomebody [i] = j;
			    def.injure (10);
			}

			// If the enemy is currently in the middle of attacking this defender and it no
			// longer has health left
			if (attackingSomebody [i] == j && !def.hasHealthLeft ())
			{
			    // Stop attacking, reset the variable keeping track of the defender
			    // being attacked
			    enemies [i].stopAttacking ();
			    attackingSomebody [i] = -1;
			}
		    }
		}

	    }
	    repaint ();
	}
    }


    /* An inner class that keeps track of the time tracker movement
    */
    private class TimeTrackerHandler implements ActionListener
    {

	/** The following method is called each time a timer event is
	 * generated
	 * @param event the Timer event
	 */
	public void actionPerformed (ActionEvent event)
	{
	    if (timePassed >= LEVEL_LENGTH)
	    {
		timeTrackerTimer.stop ();
		drawTimeTracker = false;
	    }
	}
    }


    /** Redraws the screen when given a chance
      */
    public void paintComponent (Graphics g)
    {
	super.paintComponent (g);

	// Draw the title screen
	if (!drawBoard)
	{
	    g.drawImage (titleScreen, 0, 0, this);

	    // Displays how to play instructions
	    if (howToPlayScreen == true)
		g.drawImage (howToPlayImages [currentPage], 110, 80, this);

	}

	// During the game
	else
	{
	    g.drawImage (boardImage, 0, 0, this);

	    // Draws time tracker
	    g.setColor (new Color (50, 225, 50));
	    g.fillRect (100, 553, (TIME_TRACKER_SPEED * LEVEL_LENGTH / TIME_TRACKER_INTERVAL), 15);

	    // During level, move time tracker
	    if (drawTimeTracker)
		g.drawImage (timeTracker, TIME_TRACKER_SPEED * timePassed / TIME_TRACKER_INTERVAL + YOU_X, 538, this);
	    // If the level hasn't started, draw at the beginning
	    else if (!levelStarted)
		g.drawImage (timeTracker, YOU_X, 538, this);
	    // If level is over, draw it at the end
	    else
		g.drawImage (timeTracker, TIME_TRACKER_SPEED * LEVEL_LENGTH / TIME_TRACKER_INTERVAL + YOU_X, 538, this);

	    // Draws the garbage
	    if (garbageOpen == false)
		g.drawImage (garbage, 1, 515, this);
	    else
		g.drawImage (openedGarbage, 1, 505, this);

	    // Draws the fireAlarm
	    g.drawImage (fireAlarm, 780, 550, this);

	    // Draws the amount of money on the top left corner of the board
	    if ((int) (money * 100) % 10 == 0)
	    {
		g.drawString ("$" + money + "0", 5, 100);
	    }
	    else
		g.drawString ("$" + money, 5, 100);

	    // For drawing level screens
	    if (!levelStarted)
	    {
		for (int i = 0 ; i <= messages.length ; i++)
		{
		    if (messageNo == i)
			g.drawImage (messages [messageNo], MESSAGE_X, MESSAGE_Y, this);
		}
	    }

	    // When level has begun
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

	    // At the end of a level
	    if (levelWon)
	    {
		g.drawImage (messages [LEVEL_WON_MESSAGE], MESSAGE_X, MESSAGE_Y, this);
	    }
	    else if (levelLost)
	    {
		g.drawImage (messages [LEVEL_LOST_MESSAGE], MESSAGE_X, MESSAGE_Y, this);
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
	audio.mainMenuSound.stop ();
    }


    /** Calculates the number of waves for the level
      */
    public int findWaves ()
    {
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
      * @param arrivalTime the time the enemy will appear on the edge of the screen
      * @return the type of enemy (integer)
      */
    public int generateType (int arrivalTime)
    {
	// Determines enemy type based on how far the game and level has progressed as well as the chances of encountering a type
	int generate = 1;
	int newCharacterInterval = 0;
	int dayCheck = 1;

	// For each type
	for (int type = 0 ; type < Enemy.IMAGE_NAMES.length ; type++)
	{
	    // If they were the most recent enemy to be introduced, or if enough time in the level has passed, or a randomly generated number based on their chances
	    // is the generate number
	    if (dayCheck == day || dayCheck + 1 == day || timePassed > newCharacterInterval || (int) (Math.random () * GENERATING_CHANCES [type]) == generate)
	    {
		return type;
	    }
	    // Plus two because enemies are introduced every other level
	    dayCheck += 2;
	    newCharacterInterval += 20000;

	}
	// Dummy return for code to run: return homework
	return 0;
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
		// Calculations in type are due to each enemy being introduced every 2 days
		if (type == day / 2 || type == day / 2 - 1 || (int) (Math.random () * GENERATING_CHANCES [type]) == generate)
		    return type;
		type++;
		if (type >= GENERATING_CHANCES.length)
		    type = 0;
	    }
	}
    }


    /** Generates an array of enemies
      * @param noOfWaves the number of waves
      * @return the array of enemies
      */
    public Enemy[] generateEnemies (int noOfWaves)
    {
	// Creates enemies offscreen
	// Sets up array of enemies in waves (2D array, row represents wave, column represents the enemy)
	Enemy[] enemies = new Enemy [NON_WAVE_ENEMIES + noOfWaves * WAVE_ENEMIES];
	for (int wave = 0 ; wave < noOfWaves ; wave++)
	{
	    // Used to divide the level into portions at the end of which waves will occur
	    // Math.random ensures the enemies will not be on top of each other
	    waveArrivalTimes [wave] = LEVEL_LENGTH / ENEMY_TIME_INTERVAL / noOfWaves * (wave + 1) - (int) ((Math.random () * WAVE_ENEMIES));

	    for (int i = 0 ; i < WAVE_ENEMIES ; i++)
		enemies [NON_WAVE_ENEMIES + i + wave * WAVE_ENEMIES] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateWaveType (wave), waveArrivalTimes [wave], this);
	}

	// Sets up array of individual attacking enemies
	for (int i = 0 ; i < NON_WAVE_ENEMIES ; i += 4)
	{
	    // Divdes level into fractions and determines arrival time based on that in order to increase the number of enemies as time passes

	    // Spread enemies out within entire level
	    int arrivalTime = (int) (Math.random () * (MAX_ENEMY_DISTANCE - INITIAL_DELAY)) + INITIAL_DELAY;
	    enemies [i] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	    // Spread enemies out within three fourths of the level from the end
	    arrivalTime = (int) (Math.random () * (MAX_ENEMY_DISTANCE - MAX_ENEMY_DISTANCE / 4)) + MAX_ENEMY_DISTANCE / 4;
	    enemies [i + 1] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	    // Spread enemies out within a half of the level from the end
	    arrivalTime = (int) (Math.random () * (MAX_ENEMY_DISTANCE - MAX_ENEMY_DISTANCE / 2)) + MAX_ENEMY_DISTANCE / 2;
	    enemies [i + 2] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	    // Spread enemies out within a fourth of the level from the end
	    arrivalTime = (int) (Math.random () * (MAX_ENEMY_DISTANCE - MAX_ENEMY_DISTANCE / 4 * 3)) + MAX_ENEMY_DISTANCE / 4 * 3;
	    enemies [i + 3] = new Enemy ((int) (Math.random () * 6) * RECTANGLE_WIDTH + SHOP_SQUARE_HEIGHT, generateType (arrivalTime), arrivalTime, this);
	}
	return enemies;
    }


    /** Starts the level
      */
    public void beginLevel ()
    {
	// Resets all variables
	noOfWaves = findWaves ();

	levelStarted = true;
	levelLost = false;
	levelWon = false;
	drawTimeTracker = true;
	timePassed = 0;

	money = STARTING_MONEY;
	day++;

	enemies = generateEnemies (noOfWaves);

	// Play the background music when the game starts
	if (day >= 0)
	{
	    audio.backgroundMusic ();
	}

	enemies = generateEnemies (noOfWaves);

	// This value for each enemy is -1 when it is not attacking anyone. If it is, the value is set to the defender's index
	attackingSomebody = new int [enemies.length];
	for (int i = 0 ; i < attackingSomebody.length ; i++)
	    attackingSomebody [i] = -1;

	// Begins enemyTimer
	enemyTimer.restart ();
	timeTrackerTimer.restart ();
    }


    /** Places the chosen defender on the board
    *@param x the x coordinate of the defender to be placed
    *@param y the y coordinate of the defender to be placed
    */
    public void addDefender (int x, int y)
    {
	int type = selectStatus;

	// If the defender is a ruler, make y automatically at the top
	if (type == Defender.RULER)
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
	// If it is any other one-time attacker, increase y because its height is larger than normal
	else
	{
	    if (type == Defender.GLUESTICK || type == Defender.PEN || type == Defender.HIGHLIGHTER)
	    {
		y -= RECTANGLE_HEIGHT;
		Defender newDef = new Defender (x, y, type, this);
		defenders.add (newDef);
	    }

	    // Checks whether there is a defender already there
	    // Note: the previous defenders do not need to be checked because they are one-time attackers and
	    // will soon disappear
	    Defender newDef = new Defender (x, y, type, this);
	    boolean intersects = false;
	    for (int i = 0 ; i < defenders.size () ; i++)
	    {
		Defender def = (Defender) defenders.get (i);
		if (newDef.intersects (def))
		    intersects = true;
	    }
	    // If not, add it
	    if (!intersects)
		defenders.add (newDef);
	}
    }



    /** Clears the defender array so they do not appear on the board at the
      * start of the next level
      */
    public void removeAllDefenders ()
    {
	defenders.clear ();
    }


    /** Checks whether all enemies have been destroyed
      * @param enemies the enemy array to be checked
      * @return true if they have all been destroyed, false if not
      */
    public boolean allDestroyed (Enemy[] enemies)
    {
	for (int i = 0 ; i < enemies.length ; i++)
	{
	    if (enemies [i].hasHealthLeft ())
		return false;
	}


	return true;
    }


    /** Checks whether the user still has money
      * @return true if there's money left, false if not
      */
    public boolean hasMoneyLeft ()
    {
	if (money > 0)
	    return true;
	else
	    return false;
    }


    // Keyboard events you can listen for since this JPanel is a KeyListener

    /** Responds to a keyPressed event
    * @param event information about the key pressed event
    */

    public void keyPressed (KeyEvent event)
    {
	// If enter has been pressed
	if (event.getKeyCode () == KeyEvent.VK_ENTER)
	{
	    // Return to the title screen after the user has lost the level
	    if (levelLost)
	    {
		drawBoard = false;
	    }

	    if (!levelStarted)
	    {
		// For when three characters are introduced on the first day
		if (day == 0)
		{
		    messageNo++;
		}

		if (messageNo >= 3)
		{
		    removeAllDefenders ();
		    beginLevel ();
		}
	    }
	}


	// If spacebar has been pressed (occurs when the user wishes to move on to the next level)
	if (event.getKeyCode () == KeyEvent.VK_SPACE)
	{
	    if (levelWon)
	    {
		messageNo++;
		levelWon = false;
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

	    // At the title screen
	    if (!drawBoard)
	    {
		// If the mouse is on the menu, set the cursor to the hand
		if (pos.x >= 700 && pos.x < 765 && pos.y >= 488 && pos.y < 558)
		    setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		// If not, set it to default cursor
		else
		    setCursor (Cursor.getDefaultCursor ());

		// If the mouse is on the how to play screen menu, set the cursor to the hand
		if (howToPlayScreen == true && currentPage < 4)
		{
		    if (pos.x >= 385 && pos.x < 490 &&
			    pos.y >= 380 && pos.y <= 430 ||
			    pos.x >= 550 && pos.x <= 650 &&
			    pos.y >= 380 && pos.y < 435 ||
			    pos.x >= 225 && pos.x <= 330 &&
			    pos.y >= 380 && pos.y <= 435)
		    {

			// If the mouse is on either of the three help screen buttons, set the cursor to the hand
			if (pos.x >= 385 && pos.x < 490 &&
				pos.y >= 380 && pos.y <= 430
				|| currentPage != 3 && pos.x >= 550 && pos.x <= 650 &&
				pos.y >= 380 && pos.y < 435
				|| currentPage != 0 && pos.x >= 225 && pos.x <= 330 &&
				pos.y >= 380 && pos.y <= 435)
			    setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
		    }
		    else
			setCursor (Cursor.getDefaultCursor ());
		}
	    }

	    // During the game, if the mouse is on the garbage, garbageOpen is true, if not, garbageOpen is false
	    else
	    {
		if (event.getY () > 510 && event.getY () < 580
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
	if (!drawBoard)
	{
	    // If the mouse is on the start game button, change the image of the background to game image
	    if (pos.x >= 700 && pos.x < 765 && pos.y >= 540 && pos.y < 558)
	    {
		newGame ();
		drawBoard = true;
		setCursor (Cursor.getDefaultCursor ());
	    }
	    // If the mouse is on the how to play button, set the howToPlayScreen true, and currentPage 0
	    else if (pos.x >= 700 && pos.x < 765 && pos.y >= 490 && pos.y < 510)
	    {
		howToPlayScreen = true;
		currentPage = 0;
		setCursor (Cursor.getDefaultCursor ());
	    }
	    // When the mouse is clicked how to play screen button
	    else if (howToPlayScreen == true && currentPage < 4)
	    {
		// If the mouse is clicked next button, go to next screen
		if (pos.x >= 550 && pos.x <= 650 &&
			pos.y >= 380 && pos.y < 435 &&
			currentPage < 3)
		    currentPage++;
		// If the mouse is clicked exit button, close the how to play screen
		if (pos.x >= 385 && pos.x < 490 &&
			pos.y >= 380 && pos.y <= 430)
		    howToPlayScreen = false;
		// If the mouse is clicked before button, go to previous screen
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


