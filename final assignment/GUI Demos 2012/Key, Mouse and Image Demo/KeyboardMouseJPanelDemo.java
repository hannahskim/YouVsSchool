import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/** The "KeyboardMouseJPanelDemo" class.
  * Purpose: Demonstrates a simple mouse handler, key handler, menu, and basic drawing of a graphic image and text font.
  * Includes a DrawingPanel class which extends JPanel, a general purpose container,
  * Shows on a console a trace of the mouse (x,y) points.
  * @author ICS3U
  * @version December 2009
 */

// A Frame for the main program
public class KeyboardMouseJPanelDemo extends JFrame
{
    // Program variables (declared at the top, these can be used or changed by any method)
    int mousePressedCounter = 0;
    int keyPressedCounter = 0;
    boolean mouseOverImage = false;  
    
    // The main frame of this game program
    private JFrame frame;
    
    // Main drawing area inside the main frame
    private DrawingPanel drawingArea;     

    
    /** Constructs a new main frame
      */
    public KeyboardMouseJPanelDemo ()
    {
	super ("THIS IS THE HEADING IN THE TITLE BAR");
	setLocation (10, 50);
	setIconImage (Toolkit.getDefaultToolkit ().getImage ("earth.gif"));  // for application icon in the top title bar
	
	// Size of the drawing panel the user sees will be 660 pixels wide by 480 pixels high         
	Dimension size = new Dimension (660, 480); 
       
	//create and add the drawing panel.
	drawingArea = new DrawingPanel (size);
	Container contentPane = getContentPane ();        
	contentPane.add (drawingArea, BorderLayout.CENTER);

	// Add in the menus
	addMenus ();
	
	// save the main frame of this program
	frame = this; 
    }


    /** Adds the menus to the main frame
      * Includes adding ActionListeners to respond to menu commands
      */
    private void addMenus ()
    {
	JMenuBar menuBar = new JMenuBar ();
	JMenu gameMenu = new JMenu ("Game");
	gameMenu.setMnemonic ('G');

	JMenuItem newOption = new JMenuItem ("New");
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

	JMenuItem exitOption = new JMenuItem ("Exit");
	exitOption.setMnemonic('x');
	exitOption.addActionListener (new ActionListener ()
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
	gameMenu.add (exitOption);
	menuBar.add (gameMenu);

	JMenu helpMenu = new JMenu ("Help");
	helpMenu.setMnemonic ('H');
	JMenuItem rulesOption = new JMenuItem ("Rules");
	rulesOption.setMnemonic('R');
	rulesOption.addActionListener (new ActionListener ()
	{
	    /** Responds to the Help Menu choice
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		JOptionPane.showMessageDialog (frame,
			"Help Line 1\n" +
			"Help Line 2\n" +
			"Help Line 3\n", "Help",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	);
	JMenuItem aboutOption = new JMenuItem ("About...");
	aboutOption.setMnemonic('A');
	aboutOption.addActionListener (new ActionListener ()
	{
	    /** Responds to the About Menu choice
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		JOptionPane.showMessageDialog (frame,
			"\u00a9 20109 By...", "About",
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
	Image imageClouds = new ImageIcon ("clouds500x250.jpg").getImage ();  // this image was made 500x250 pixels
	
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel (Dimension size)
	{
	    // set size of this drawing panel
	    setPreferredSize(size);  
	    // set background colour of this panel and the font for drawing text in it
	    setBackground (Color.GRAY);  
	    setFont (new Font ("Arial", Font.PLAIN, 40));
	    
	    // Add mouse listeners to the drawing panel
	    addMouseListener (new MouseHandler ());
	    addMouseMotionListener (new MouseMotionHandler ());

	    // Add key listeners and setting focus
	    setFocusable (true);
	    addKeyListener (new KeyHandler ());
	    requestFocusInWindow ();
	}


	/** Repaint the drawing panel.  Java calls this method anytime you call repaint() 
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);

	    g.setColor (Color.magenta);           
	    g.drawString ("Hello, world!", 170, 60);
	    g.setColor (Color.GREEN);
	    g.drawString ("mouse presses: " + String.valueOf(mousePressedCounter), 170, 90);
	    g.setColor(Color.CYAN);
	    g.drawString ("key presses: " + String.valueOf(keyPressedCounter), 170, 120);
	    
	    // draw image and a black border
	    //  g.drawImage(imageClouds, 75, 200, 500, 250, this);  <--- will scale image to 500x250
	    // It's better to NOT have java scale the image repeatedly (ie., re-size your image in photoshop instead)            
	    g.drawImage(imageClouds, 75, 200, this);  
	    g.setColor(Color.BLACK);
	    g.drawRect(75, 200, 500, 250); 
	    
	    if ( mouseOverImage )   // value is set in the mouseMoved method
	    {
		g.setColor(Color.YELLOW);
		g.drawString ("mouse is over the image", 170, 150);
	    } 
	} // paint component method
    }


    // Inner class to handle mouse events
    private class MouseHandler extends MouseAdapter
    {
	public void mousePressed (MouseEvent event)
	{   
	    System.out.println("mouse pressed");         
	    mousePressedCounter++;
	    repaint();        
	}

	public void mouseReleased (MouseEvent event)
	{  
	    System.out.println("mouse released");       
	}
	
	public void mouseClicked (MouseEvent event)
	{
	    System.out.println("mouseClicked");
	}    
    }



    // Inner Class to handle mouse movements
    private class MouseMotionHandler extends MouseMotionAdapter
    {
	// imageRectangle is the location (75, 200) and size (500, 250) of the clouds image drawn on the drawingPanel.  
	// The imageRectangel is used by mouseMoved() to detect when the mouse is over the image.       
	Rectangle imageRectangle = new Rectangle (75, 200, 500, 250); ;  // area of clouds image      
	
	public void mouseMoved (MouseEvent event)
	{
	    // get (x,y) location of mouse                    
	    Point mousePoint = event.getPoint();
	    System.out.println("mouse moved to: " + mousePoint.x + " " + mousePoint.y);
	    
	    // check whether mouse is moved over the clouds image 
	    if ( imageRectangle.contains (mousePoint) )
		mouseOverImage = true;
	    else
		mouseOverImage = false;
	    // must redraw the panel contents to show whether mouse is over the image
	    repaint();                 
	}

	public void mouseDragged (MouseEvent event)
	{
	    System.out.println("mouse dragged");          
	}
    }


    // Inner class to handle key events
    private class KeyHandler extends KeyAdapter
    {
	public void keyPressed (KeyEvent event)
	{
	    keyPressedCounter++;
	    repaint();             
	    JOptionPane.showMessageDialog (frame,
		    "A Key was Pressed", "KeyHandler message",
		    JOptionPane.INFORMATION_MESSAGE);       
	}
    }


    public static void main (String[] args)
    {
	KeyboardMouseJPanelDemo myDemo = new KeyboardMouseJPanelDemo ();
	myDemo.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	myDemo.pack ();
	myDemo.setVisible (true);
    }
}


