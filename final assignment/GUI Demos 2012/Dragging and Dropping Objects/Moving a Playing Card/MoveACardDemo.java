/** The "MoveACardDemo" class.
  * A demo to show how to drag and drop card images
  * Based on the "Move a Piece" demo
  * @author G. Ridout Copyright December 2002
  * @version November 2007 
  */

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;


public class MoveACardDemo extends JFrame
{
    // Information in the Status panel at the bottom of the screen
    Label statusMessage;

    // Card area Panel
    CardArea cardArea;

    // Construct the MoveACardDemo Frame object
    public MoveACardDemo ()
    {
	// Set up the original frame
	super ("Moving a Card");

	// Add in the card area for drawing and managing cards
	Container contentPane = getContentPane ();
	contentPane.setLayout (new BorderLayout ());
	cardArea = new CardArea ();
	contentPane.add (cardArea, BorderLayout.CENTER);

	// Create a status panel with a clear button and a message area
	JPanel statusPanel = new JPanel ();

	JButton clearCards = new JButton ("Clear Cards");
	clearCards.addActionListener (new ActionListener ()
	{
	    /** Responds to the Clear Pieces button being pressed
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		cardArea.clearCards ();
	    }
	}
	);
	statusPanel.add (clearCards);
	statusMessage = new Label ("Welcome to the Move a Card Demo");
	statusPanel.add (statusMessage);
	// Add this new panel to the bottom of the screen
	contentPane.add ("South", statusPanel);
    }


    // Changes the statusMessage
    public void showStatus (String message)
    {
	statusMessage.setText (message);
    }


    /** Inner class to look after the Card Area
    */
    private class CardArea extends JPanel
    {
	// Information about the cards
	Card[] myCards;
	int noOfCards;
	Card selectedCard;

	// Keeps track of the last position of the card
	// Used to find out how much to move the card
	Point lastPoint;

	/** Constructs a new DrawingPanel object
	  */
	public CardArea ()
	{
	    this.setPreferredSize (new Dimension (700, 500));
	    setBackground (new Color (0, 125, 0));
	    // Add mouse listeners to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	    this.addMouseMotionListener (new MouseMotionHandler ());

	    // Set up the initial Card list information
	    myCards = new Card [52];
	    clearCards ();
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    // Draw the cards
	    for (int i = 0 ; i < noOfCards ; i++)
		myCards [i].draw (g);

	    // Draw the selected card last (on top)
	    if (selectedCard != null)
		selectedCard.draw (g);

	} // paint component method

	public void clearCards ()
	{
	    noOfCards = 0;
	    selectedCard = null;
	    repaint ();
	}

	// Inner class to handle mouse events
	private class MouseHandler extends MouseAdapter
	{
	    public void mousePressed (MouseEvent event)
	    {
		Point selectedPoint = event.getPoint ();

		// Check if we are selecting or flipping one of the cards
		// Count down, since higher cards are on top
		for (int i = noOfCards - 1 ; i >= 0 ; i--)
		    if (myCards [i].contains (selectedPoint))
		    {
			// If right mouse button is clicked, flip this card
			if (event.getButton () == MouseEvent.BUTTON3) // event.getButton () == MouseEvent.BUTTON2)
			{
			    myCards [i].flip ();
			    showStatus ("Flipped: " + myCards [i]);
			}
			// If left mouse is clicked, select this card
			else
			{
			    selectedCard = myCards [i];
			    lastPoint = selectedPoint;
			    showStatus ("Selected: " + selectedCard);
			}
			repaint ();
			return;
		    }

		// If a card was not selected, create new card in this spot
		if (noOfCards < myCards.length)
		{
		    // Suit and Rank are based on the current noOfCards
		    myCards [noOfCards] = new Card (noOfCards / 4 + 1,
			    noOfCards % 4 + 1, selectedPoint, true, MoveACardDemo.this);

		    noOfCards++;
		    showStatus ("Creating a new Card");
		    repaint ();
		}
	    }

	    public void mouseReleased (MouseEvent event)
	    {
		if (selectedCard != null)
		{
		    selectedCard = null;
		    showStatus ("Card Dropped");
		    repaint ();
		}
	    }
	}



	// Inner Class to handle mouse movements
	private class MouseMotionHandler implements MouseMotionListener
	{
	    public void mouseMoved (MouseEvent event)
	    {
		// Set the cursor to the hand if we are on a card
		Point currentPoint = event.getPoint ();
		// Count down, since higher cards are on top
		for (int i = noOfCards - 1 ; i >= 0 ; i--)
		    if (myCards [i].contains (currentPoint))
		    {
			setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
			if (myCards [i].isFaceUp ())
			    showStatus ("Card: " + myCards [i]);
			else
			    showStatus ("Use the right mouse to flip");
			return;
		    }

		// Otherwise we just use the default cursor
		showStatus ("No cards here...");
		setCursor (Cursor.getDefaultCursor ());
	    }

	    public void mouseDragged (MouseEvent event)
	    {
		Point currentPoint = event.getPoint ();

		if (selectedCard != null)
		{
		    showStatus ("Moving: " + selectedCard);
		    // We use the difference between the lastPoint and the
		    // currentPoint to move the card so that the position of
		    // the mouse on the card doesn't matter.
		    // i.e. we can drag the card from any point on the card image
		    selectedCard.move (lastPoint, currentPoint);
		    lastPoint = currentPoint;
		    repaint ();
		}
	    }
	}
    }


    public static void main (String[] args)
    {
	MoveACardDemo frame = new MoveACardDemo ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // MoveACardDemo class

