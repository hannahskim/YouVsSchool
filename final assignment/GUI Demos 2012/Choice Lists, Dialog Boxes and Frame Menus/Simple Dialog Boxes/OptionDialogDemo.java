/** The "OptionDialogDemo" class.
  * @author Ridout
  * @version November 2007
  */
import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;

public class OptionDialogDemo extends JFrame
{
    private int grade;

    public OptionDialogDemo ()
    {
	super ("Option Dialog Demo");
	setSize (400, 400);
	setLocation (100, 100);
	grade = 11;
	// Add in the DrawingPanel defined below to this frame
	getContentPane ().add (new DrawingPanel (), BorderLayout.CENTER);
    }


    /** Gets the grade level, using a Dialog Box
      * @param low          the lowest grade level to offer
      * @param high         the higest grade level to offer
      * @param currentGrade the grade currently selected
      * @return returns the selected grade in the given range
      *         if the OK button is selected, returns the current
      *         grade if CANCEL button is selected
      */
    public int getGradeLevel (int low, int high, int currentGrade)
    {
	// Create a panel with radio buttons
	JPanel panel = new JPanel ();
	Border lowerEtched =
	    BorderFactory.createEtchedBorder (EtchedBorder.RAISED);

	panel.setBorder (BorderFactory.createTitledBorder (
		    lowerEtched, "Choose a Grade Level"));
	int noOfGrades = high - low + 1;
	int noOfColumns = (int) Math.sqrt (noOfGrades);
	panel.setLayout (new GridLayout (noOfGrades / noOfColumns, noOfColumns));

	// Create a group of radio buttons to add to the Panel
	ButtonGroup gradeGroup = new ButtonGroup ();
	JRadioButton[] buttonList = new JRadioButton [noOfGrades];

	// Create and add each radio button to the panel
	int selectedGrade = currentGrade;
	for (int index = 0 ; index < buttonList.length ; index++)
	{
	    int grade = index + low;
	    if (grade == selectedGrade)
		buttonList [index] = new JRadioButton ("Grade " + grade, true);
	    else
		buttonList [index] = new JRadioButton ("Grade " + grade);
	    gradeGroup.add (buttonList [index]);
	    panel.add (buttonList [index]);
	}

	// Show a dialog with the panel attached
	int choice = JOptionPane.showConfirmDialog (this, panel,
		"Grade Options",
		JOptionPane.OK_CANCEL_OPTION,
		JOptionPane.DEFAULT_OPTION);

	// Update grade if OK is selected
	if (choice == JOptionPane.OK_OPTION)
	{
	    for (int index = 0 ; index < buttonList.length ; index++)
		if (buttonList [index].isSelected ())
		    selectedGrade = index + low;
	}
	return selectedGrade;
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    // Add mouse listeners to the drawing panel
	    this.addMouseListener (new MouseHandler ());
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setColor (Color.red);
	    g.drawString ("Click the mouse to bring up the Option Dialog", 10, 100);


	} // paint component method
    }


    // Inner class to handle mouse events
    private class MouseHandler extends MouseAdapter
    {
	public void mousePressed (MouseEvent event)
	{
	    grade = getGradeLevel (9, 12, grade);
	    // Display which grade was selected
	    System.out.println ("Selected Grade: " + grade);
	}
    }


    public static void main (String[] args)
    {
	//Schedule a job for the event-dispatching thread:
	//creating and showing this application's GUI.
	javax.swing.SwingUtilities.invokeLater (new Runnable ()
	{
	    public void run ()
	    {
		OptionDialogDemo frame = new OptionDialogDemo ();
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setVisible (true);
	    }
	}
	);
    } // main method
} // OptionDialogDemo class
