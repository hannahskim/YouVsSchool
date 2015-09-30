// The "MenuDemo" class. Programmed by: Ridout
// Updated November 2007

import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;

public class MenuDemo extends JFrame
{
    private String message;
    private int level;

    public MenuDemo ()
    {
	super ("Working with Menus");

	Container contentPane = getContentPane ();
	contentPane.add (new DrawingPanel (), BorderLayout.CENTER);

	message = "Welcome to the Menu Demo";    // initial message
	addMenus ();

    } // Constructor



    /** Adds the menus to the main frame
    *Includes adding ActionListeners to respond to menu commands
    */
    private void addMenus ()
    {
	JMenuBar menuBar = new JMenuBar ();
	JMenu gameMenu = new JMenu ("Game");
	gameMenu.setMnemonic ('G');

	JMenuItem newMenu = new JMenuItem ("New");
	newMenu.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_N, InputEvent.CTRL_MASK));

	newMenu.addActionListener (new ActionListener ()
	{
	    /** Responds to the New menu option
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent e)
	    {
		newGame ();
	    }
	}
	);
	// Create a sub menu
	JMenu subMenu = new JMenu ("Sub Menu");
	JMenuItem subMenuOne = new JMenuItem ("First Sub Menu Choice");
	subMenuOne.addActionListener (new SubMenuListener ("First Sub Menu Choice Selected"));

	JMenuItem subMenuTwo = new JMenuItem ("Second Sub Menu Choice");
	subMenuTwo.addActionListener (new SubMenuListener ("Second Sub Menu Choice Selected"));

	subMenu.add (subMenuOne);
	subMenu.add (subMenuTwo);

	JMenuItem exitMenu = new JMenuItem ("Exit");
	exitMenu.setAccelerator (
		KeyStroke.getKeyStroke (KeyEvent.VK_X, InputEvent.CTRL_MASK));

	exitMenu.addActionListener (new ActionListener ()
	{
	    /** Responds to the exit game option being selected
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		System.exit (0);
	    }
	}
	);
	gameMenu.add (newMenu);
	gameMenu.add (subMenu);

	gameMenu.addSeparator ();
	gameMenu.add (exitMenu);
	menuBar.add (gameMenu);

	// Set up the Level Menu
	ButtonGroup levelGroup = new ButtonGroup ();
	JRadioButtonMenuItem beginnerOption =
	    new JRadioButtonMenuItem ("Beginner", true);
	beginnerOption.addActionListener (new LevelListener (1));
	level = 1;
	JRadioButtonMenuItem intermediateOption =
	    new JRadioButtonMenuItem ("Intermediate", false);
	intermediateOption.addActionListener (new LevelListener (2));
	JRadioButtonMenuItem advancedOption =
	    new JRadioButtonMenuItem ("Advanced", false);
	advancedOption.addActionListener (new LevelListener (3));
	JMenu levelMenu = new JMenu ("Level");
	levelMenu.setMnemonic ('L');
	levelGroup.add (beginnerOption);
	levelGroup.add (intermediateOption);
	levelGroup.add (advancedOption);
	levelMenu.add (beginnerOption);
	levelMenu.add (intermediateOption);
	levelMenu.add (advancedOption);

	menuBar.add (levelMenu);

	JMenu helpMenu = new JMenu ("Help");
	helpMenu.setMnemonic ('H');
	JMenuItem rulesMenuItem = new JMenuItem ("Rules", 'R');
	rulesMenuItem.addActionListener (new ActionListener ()
	{
	    /** Responds to the help option being selected
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		JOptionPane.showMessageDialog (MenuDemo.this,
			"Line 1\n" +
			"Line 2\n" +
			"Line 3\n", "Help",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	);
	JMenuItem aboutMenuItem = new JMenuItem ("About...", 'A');
	aboutMenuItem.addActionListener (new ActionListener ()
	{
	    /** Responds to the about option being selected
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		JOptionPane.showMessageDialog (MenuDemo.this,
			"by G. Ridout \u00a9 2007", "About Menu Demo",
			JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	);
	helpMenu.add (rulesMenuItem);
	helpMenu.add (aboutMenuItem);
	menuBar.add (helpMenu);
	setJMenuBar (menuBar);
    }


    // private inner class to deal with sub menu items
    private class SubMenuListener implements ActionListener
    {
	private String setMessage;

	public SubMenuListener (String message)
	{
	    setMessage = message;
	}
	/** Responds to the sub menu being selected
	       * @param event The event that selected this menu option
	       */
	public void actionPerformed (ActionEvent event)
	{
	    message = setMessage;
	    repaint ();
	}
    }


    // private inner class to deal with level changes
    private class LevelListener implements ActionListener
    {
	private int setLevel;

	public LevelListener (int level)
	{
	    setLevel = level;
	}
	/** Responds to the sub menu being selected
	       * @param event The event that selected this menu option
	       */
	public void actionPerformed (ActionEvent event)
	{
	    level = setLevel;
	}
    }



    void newGame ()
    {
	message = "New Game Selected at the ";
	if (level == 1)
	    message += "Beginner Level";
	else if (level == 2)
	    message += "Intermediate Level";
	else
	    message += "Advanced Level";
	repaint ();
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    setPreferredSize (new Dimension (400, 400));
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.drawString (message, 50, 200);

	} // paint component method
    }


    public static void main (String[] args) throws Exception
    {
	MenuDemo frame = new MenuDemo ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);

    } // main method
} // MenuDemo class
