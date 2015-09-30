// By Ridout
// Last updated to JFrame - Dec 2007

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class PlacingComponentsAndChoiceLists
    extends JFrame implements ActionListener, ItemListener
{
    Choice category;
    Choice fontType;
    Choice fontSize;
    JButton newGameButton;
    String message;
    String currentFont;
    int currentFontSize;

    public PlacingComponentsAndChoiceLists ()
    {
	super ("Working with Choice Lists");
	setSize (500, 500);
	setBackground (Color.gray);
	message = "Pick a Category and Font";

	// Placing components any where you want
	// This is not recommended for platform independent applications
	// so, if you have something else working OK, stick with it
	// However, if you are having trouble placing components exactly
	// where you want them, try this out

	// One caution, if you use this technique, you must set the bounds
	// for each component you "add" to your frame
	Container contentPane = getContentPane ();
	// First you set the layout manager to null
	contentPane.setLayout (null);
	DrawingPanel panel = new DrawingPanel ();
	panel.setBounds (0, 0, 600, 600);
	contentPane.add (panel);

	// Then you can add new components
	newGameButton = new JButton ("New Game");
	// The setBounds method sets the position (x,y)
	// and size (width, height) of a component
	newGameButton.setBounds (200, 350, 100, 30);
	panel.setLayout (null);
	panel.add (newGameButton);
	newGameButton.addActionListener (this);

	// Here is a category option you could place anywhere
	// I also set the font of this component
	category = new Choice ();
	category.add ("Movies");
	category.add ("Sports");
	category.add ("Music");
	category.add ("TV Shows");
	category.setFont (new Font ("Alor", Font.PLAIN, 15));
	category.setBounds (200, 100, 150, 30);
	category.addItemListener (this);

	contentPane.add (category);

	// Here is a font list
	fontType = new Choice ();

	// In Java 2 we  use this command to get the font list
	String[] fontArray = GraphicsEnvironment.getLocalGraphicsEnvironment ().getAvailableFontFamilyNames ();

	for (int i = 0 ; i < fontArray.length ; i++)
	    fontType.add (fontArray [i]);
	fontType.setBounds (200, 150, 150, 30);

	currentFont = fontArray [0];
	contentPane.add (fontType);
	fontType.addItemListener (this);


	// Let's set the size of the font as well
	fontSize = new Choice ();
	for (int size = 5 ; size <= 60 ; size++)
	    fontSize.add (String.valueOf (size));
	fontSize.setBounds (300, 200, 50, 30);

	contentPane.add (fontSize);
	fontSize.addItemListener (this);
	currentFontSize = 12;
    }


    /** Handles the button choice
      * @param event the event that called this method (only one)
      */
    public void actionPerformed (ActionEvent event)
    {
	if (event.getSource () == newGameButton)
	{
	    message = "Start a New Game";
	}
	repaint (); // Update screen with new message
    }


    /** Response to a change in one of the choice lists
      * @param event the event that called this method
      */
    public void itemStateChanged (ItemEvent event)
    {
	if (event.getItemSelectable () == category)
	{
	    message = category.getSelectedItem () + " was selected";
	}
	else if (event.getItemSelectable () == fontType)
	{
	    currentFont = fontType.getSelectedItem ();
	}
	else if (event.getItemSelectable () == fontSize)
	{
	    currentFontSize = Integer.parseInt (fontSize.getSelectedItem ());
	}
	repaint ();
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setFont (new Font (currentFont, Font.PLAIN, currentFontSize));
	    g.drawString (message, 50, 50);

	} // paint component method
    }


    // Main program just creates the frame
    public static void main (String[] args)
    {
	PlacingComponentsAndChoiceLists mainFrame =
	    new PlacingComponentsAndChoiceLists ();
	mainFrame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	mainFrame.setVisible (true);
    } // main method
}


