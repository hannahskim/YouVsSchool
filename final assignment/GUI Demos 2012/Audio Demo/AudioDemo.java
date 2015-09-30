// The "AudioDemot" class.
// By Ridout
import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

public class AudioDemo extends JFrame
{
    private AudioClip backGroundSound;
    private AudioClip beep;
    private AudioClip hello;
    private AudioClip goodbye;
    private String message;
    private MenuItem playSound;

    public AudioDemo ()
    {
	super ("Audio Demo");
	setSize (500, 100);

	Container contentPane = getContentPane ();
	contentPane.add (new DrawingPanel (), BorderLayout.CENTER);
	backGroundSound = Applet.newAudioClip (getCompleteURL ("loop.au"));
	beep = Applet.newAudioClip (getCompleteURL ("beep.au"));
	hello = Applet.newAudioClip (getCompleteURL ("hello.wav"));
	goodbye = Applet.newAudioClip (getCompleteURL ("goodbye.wav"));


	message = "Press 'H' - Horn or 'B' - Background Sound";
	addWindowListener (new GoodbyeWindowListener ());
	hello.play ();
    } // AudioDemo constructor


    // Gets the URL needed for newAudioClip
    public URL getCompleteURL (String fileName)
    {
	try
	{
	    return new URL ("file:" + System.getProperty ("user.dir") + "/" + fileName);
	}
	catch (MalformedURLException e)
	{
	    System.err.println (e.getMessage ());
	}
	return null;
    }


    // Inner class for the drawing area
    private class DrawingPanel extends JPanel
    {
	/** Constructs a new DrawingPanel object
	  */
	public DrawingPanel ()
	{
	    // Add key listeners and setting focus
	    this.setFocusable (true);
	    this.addKeyListener (new KeyHandler ());
	    this.requestFocusInWindow ();
	}


	/** Repaint the drawing panel
	  * @param g The Graphics context
	  */
	public void paintComponent (Graphics g)
	{
	    super.paintComponent (g);
	    g.setColor (Color.blue);
	    g.drawString (message, 20, 50);

	} // paint component method
    }


    // Inner class to handle key events
    private class KeyHandler extends KeyAdapter
    {
	public void keyPressed (KeyEvent event)
	{
	    char ch = Character.toUpperCase (event.getKeyChar ());
	    if (ch == 'H')
		beep.play ();
	    else if (ch == 'B')
	    {
		message = "Background Music Playing Press 'H' - Horn";
		backGroundSound.loop ();
	    }
	    else
	    {
		message = "Press 'H' - Horn or 'B' - Background Sound";
		backGroundSound.stop ();
	    }
	    repaint ();

	}
    }


    private class GoodbyeWindowListener extends WindowAdapter
    {
	// Deals with window closing
	public void windowClosing (WindowEvent event)
	{
	    goodbye.play ();
	    try
	    {
		Thread.sleep (1200);
	    }
	    catch (InterruptedException exception)
	    {
	    }
	    hide ();
	    System.exit (0);
	}

    }


    public static void main (String[] args)
    {
	// Create a frame
	AudioDemo frame = new AudioDemo ();
	frame.setVisible (true);

    } // main method
} // AudioApplet class


