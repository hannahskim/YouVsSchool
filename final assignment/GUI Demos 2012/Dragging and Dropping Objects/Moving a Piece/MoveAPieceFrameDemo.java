// The "MoveAPieceDemo" class.
// Programmed by: G. Ridout
// Copyright December 2001
// Modified November 2007

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class MoveAPieceFrameDemo extends JFrame
{
    private PlayArea playArea;
    private JLabel statusMessage;
    private JCheckBox includeLabel; 

    public MoveAPieceFrameDemo ()
    {
	super ("Moving a Piece");
	setBackground (new Color (190, 200, 200));

	Container contentPane = getContentPane ();
	contentPane.setLayout (new BorderLayout ());
	playArea = new PlayArea ();
	contentPane.add (playArea, BorderLayout.CENTER);

	// Create a piece information panel
	JPanel infoPanel = new JPanel ();

	// Add a colour selector to this panel
	ButtonGroup colourGroup = new ButtonGroup ();
	JRadioButton red = new JRadioButton ("red", true);
	red.addItemListener (new ColourSelector (Color.RED));
	playArea.setCurrentColour(Color.RED);
	JRadioButton green = new JRadioButton ("green", false);
	green.addItemListener (new ColourSelector (Color.GREEN));
	JRadioButton blue = new JRadioButton ("blue", false);
	blue.addItemListener (new ColourSelector (Color.BLUE));
	colourGroup.add (red);
	colourGroup.add (green);
	colourGroup.add (blue);

	infoPanel.add (new JLabel ("Colour of Piece"));
	infoPanel.add (red);
	infoPanel.add (green);
	infoPanel.add (blue);

	// Add a include label option
	includeLabel = new JCheckBox ("Include Label", true);
	includeLabel.addItemListener (new ItemListener ()
	{
	    /** Responds to the Include Label checkbox
	      * @param event The event that selected this menu option
	      */
	    public void itemStateChanged (ItemEvent e)
	    {
		repaint ();
	    }
	}
	);
	infoPanel.add (includeLabel);

	// Add the infoPanel to the top "North" of the frame
	contentPane.add ("North", infoPanel);

	// Create a status panel with a message area and clear button
	JPanel statusPanel = new JPanel ();
	JButton clearPieces = new JButton ("Clear Pieces");
	clearPieces.addActionListener (new ActionListener ()
	{
	    /** Responds to the Clear Pieces button being pressed
	      * @param event The event that selected this menu option
	      */
	    public void actionPerformed (ActionEvent event)
	    {
		playArea.clearPieces ();
	    }
	}
	);

	statusPanel.add (clearPieces);

	statusMessage = new JLabel ("Welcome to the Move a Piece Demo");
	statusPanel.add (statusMessage);
	contentPane.add ("South", statusPanel);

    } // MoveAPieceFrameDemo constructor


    /** Private inner class to handle the change of piece colour
     */
    private class ColourSelector implements ItemListener
    {
	private Color colour;

	/** Constructs a ColourSelector with a given colour
	  * @param colour The colour
	  */
	public ColourSelector (Color colour)
	{
	    this.colour = colour;
	}
	/** Responds to the Include Label checkbox
	  * @param event The event that selected this menu option
	  */
	public void itemStateChanged (ItemEvent e)
	{
	    playArea.setCurrentColour (colour);
	}
    }


    // Changes the statusMessage
    public void showStatus (String message)
    {
	statusMessage.setText (message);
    }


    // Inner class for the drawing area
    private class PlayArea extends JPanel
    {
	private Piece[] myPieces;
	private int noOfPieces;

	private Color currentColour;

	private int selectedPiece;
	private Point lastPoint;

	/** Constructs a new PlayArea object
	  */
	public PlayArea ()
	{
	    //Set up play area and set background colour
	    this.setPreferredSize (new Dimension (700, 500));
	    //   setBackground (new Color (0, 125, 0));

	    // Add mouse listeners to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	    this.addMouseMotionListener (new MouseMotionHandler ());

	    // Set up the pieces
	    selectedPiece = -1;
	    noOfPieces = 0;
	    myPieces = new Piece [200];
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);

	    // Draw the pieces
	    for (int i = 0 ; i < noOfPieces ; i++)
		if (includeLabel.isSelected ())
		    myPieces [i].draw (g, String.valueOf (i));
		else
		    myPieces [i].draw (g);

	    // Draw the selected piece last (on top)
	    if (selectedPiece >= 0)
		if (includeLabel.isSelected ())
		    myPieces [selectedPiece].draw (g, String.valueOf (selectedPiece));
		else
		    myPieces [selectedPiece].draw (g);
	} // paint component method


	/** Clears the PlayArea
	*/
	public void clearPieces ()
	{
	    noOfPieces = 0;
	    repaint ();
	}

	/** Clears the PlayArea
	*/
	public void setCurrentColour (Color colour)
	{
	    currentColour = colour;
	    repaint ();
	}

	// Inner class to handle mouse events
	private class MouseHandler extends MouseAdapter
	{
	    public void mousePressed (MouseEvent event)
	    {
		Point selectedPoint = event.getPoint ();

		// Check if we are selecting one of the pieces
		for (int i = 0 ; i < noOfPieces ; i++)
		    if (myPieces [i].contains (selectedPoint))
		    {
			selectedPiece = i;
			lastPoint = selectedPoint;
			showStatus ("Selected Piece #" + i);
			return;
		    }

		// Create a new piece in this spot with the current colour
		if (noOfPieces < 200)
		{
		    myPieces [noOfPieces] = new Piece (selectedPoint, 30, currentColour);
		    noOfPieces++;
		    showStatus ("Creating a new Piece");
		    repaint ();
		}
	    }

	    public void mouseReleased (MouseEvent event)
	    {
		if (selectedPiece >= 0)
		{
		    selectedPiece = -1;
		    showStatus ("Piece Dropped");
		}
	    }
	}



	// Inner Class to handle mouse movements
	private class MouseMotionHandler implements MouseMotionListener
	{
	    public void mouseMoved (MouseEvent event)
	    {
		// Set the cursor to the hand if we are on that piece
		Point currentPoint = event.getPoint ();
		for (int i = 0 ; i < noOfPieces ; i++)
		    if (myPieces [i].contains (currentPoint))
		    {
			setCursor (Cursor.getPredefinedCursor (Cursor.HAND_CURSOR));
			showStatus ("Piece #" + i);
			return;
		    }

		// Otherwise we just use the default cursor
		showStatus ("No pieces here...");
		setCursor (Cursor.getDefaultCursor ());
	    }

	    public void mouseDragged (MouseEvent event)
	    {
		Point currentPoint = event.getPoint ();

		if (selectedPiece >= 0)
		{
		    showStatus ("Moving Piece #" + selectedPiece);
		    myPieces [selectedPiece].move (lastPoint, currentPoint);
		    lastPoint = currentPoint;
		    repaint ();
		}
	    }
	}
    }


    public static void main (String[] args)
    {
	MoveAPieceFrameDemo frame = new MoveAPieceFrameDemo ();
	frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
	frame.pack ();
	frame.setVisible (true);
    } // main method
} // MoveAPieceFrameDemo class

