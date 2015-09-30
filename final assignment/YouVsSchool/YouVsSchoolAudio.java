import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.net.*;
import javax.swing.*;
import javax.swing.event.*;

/** The "YouVsSchoolAudio" class.
  * Contains the audio files for YouVsSchool
  * @author Melissa Li, Hannah Kim
  * @version Jan 21, 2013
 */
public class YouVsSchoolAudio extends JFrame
{
    // Declares variables for audio
    public AudioClip mainMenuSound;
    public AudioClip backgroundSound;

    public YouVsSchoolAudio ()
    {
	super ("YouVsSchool Audio");

	// Set the variables to the audio clips
	mainMenuSound = Applet.newAudioClip (getCompleteURL ("Title Screen Music.mid"));
	backgroundSound = Applet.newAudioClip (getCompleteURL ("Background Music.mid"));
    }

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


    /** Plays the title screen music of the game in a loop
      */
    public void titleMusic ()
    {
	mainMenuSound.loop ();
    }


    /** Plays the background music of the game in a loop
    */
    public void backgroundMusic ()
    {
	backgroundSound.loop ();
    }
} // YouVsSchoolAudio class
