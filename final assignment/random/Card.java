/** The "Card" class.
  * Keeps track of a standard playing card object
  * This class is the bare minimum for the MoveACard Demo
  * @author G. Ridout Copyright December 2002
  * @version April 2002 (Updated December 2002)
  * (Images updated to png's December 2011)
  */

import hsa.Console;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

// To reduce the amount of code needed, our Card object will inherit data
// and methods from the Rectangle class since a Card is just a rectangle
// with an image. In particular, we will be using the following data:
//      x, y , height and width
// And we will be using the following methods;
//      contains() and translate()

public class Card extends Rectangle
{
    // Back image shared by all Cards
    private static Image BACK_IMAGE =
	new ImageIcon ("images\\blueback.png").getImage ();
    // Card variables to keep track of a cards data (characteristics)
    // We can also use x, y, height and width (inherited from Rectangle)
    private int suit;
    private int rank;
    private boolean isFaceUp;
    private Image image;

    // Constructor that creates a new card object
    public Card (int newRank, int newSuit, Point position,
	    boolean faceUp, Component parentFrame)
    {
	// Set up the underlying rectangle for this Card object
	// Initial size will be zero
	// We will set its size later once we know the size of the image
	super (position.x, position.y, 0, 0);

	// Set up the rank and initial status (face up or down)
	rank = newRank;
	suit = newSuit;
	isFaceUp = faceUp;

	// Load up the appropriate image file for this card
	String imageFileName = "images\\" + " cdhs".charAt (suit) +
	    String.valueOf (rank) + ".png";

	image = new ImageIcon (imageFileName).getImage ();

	// Set the size of the card based on the image size
	setSize (image.getWidth (parentFrame), image.getHeight (parentFrame));
    }


    // Flip the card over
    public void flip ()
    {
	isFaceUp = !isFaceUp;
    }


    // Checks to see if the card is face up
    public boolean isFaceUp ()
    {
	return isFaceUp;
    }


    // Change the position of the card by the difference between
    // the two given points
    public void move (Point initialPos, Point finalPos)
    {
	translate (finalPos.x - initialPos.x, finalPos.y - initialPos.y);
    }


    /** Draws a card in a Graphics context
      * @param g Graphics to draw the card in
       */
    public void draw (Graphics g)
    {
	if (isFaceUp)
	{
	    g.drawImage (image, x, y, null);
	}
	else  // Draws the back of the card
	{
	    g.drawImage (BACK_IMAGE, x, y, null);
	}
    }


    // Used to display the card as text
    public String toString ()
    {
	return " A23456789TJQK".charAt (rank) +
	    " of " + " CDHS".charAt (suit);
    }
}


