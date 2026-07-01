import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.AWTException;
import java.awt.MouseInfo;

/**
 * GolfAim listens for global hotkeys (Alt + specific keys) to allow
 * pixel-perfect mouse adjustments and clicking for the SkyTrac simulator.
 */
public class GolfAim implements NativeKeyListener
{
    // Robot instance handles the mouse movememet and left-clicking
    private Robot r;

    // Handles global key presses across the OS
    public void nativeKeyPressed(NativeKeyEvent e)
    {
        // checks wether alt is currently being pressed 
        boolean isAlt = (e.getModifiers() & NativeKeyEvent.ALT_MASK) != 0;

        // if alt and ' keys are being pressed, move the mouse right using the class's robot object
        if (isAlt && e.getKeyCode() == NativeKeyEvent.VC_QUOTE)
        {
            r.mouseMove(MouseInfo.getPointerInfo().getLocation().x + 1, MouseInfo.getPointerInfo().getLocation().y);
        }

        // if alt and ; keys are being pressed, move the mouse left using the class's robot object
        else if (isAlt && e.getKeyCode() == NativeKeyEvent.VC_SEMICOLON)
        {
            r.mouseMove(MouseInfo.getPointerInfo().getLocation().x - 1, MouseInfo.getPointerInfo().getLocation().y);
        }

        // if alt and [ keys are being pressed, move the mouse up using the class's robot object
        else if (isAlt && e.getKeyCode() == NativeKeyEvent.VC_OPEN_BRACKET)
        {
            r.mouseMove(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y - 1);
        }

        // if alt and / keys are being pressed, move the mouse down using the class's robot object
        else if (isAlt && e.getKeyCode() == NativeKeyEvent.VC_SLASH)
        {
            r.mouseMove(MouseInfo.getPointerInfo().getLocation().x, MouseInfo.getPointerInfo().getLocation().y + 1);
        }

        // if alt and a keys are being pressed, left click using the class's robot object to lock in shot
        else if (isAlt && e.getKeyCode() == NativeKeyEvent.VC_A)
        {
            r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
    }

    // setter for the Robot instance variable
    public void setRobot(Robot r)
    {
        this.r = r;
    }

    public static void main(String[] args)
    {
        // create a new GolfAim object
        GolfAim aimer = new GolfAim();

        // Initialize the Robot for mouse control
        try 
        {
            aimer.setRobot(new Robot());
        }
        catch (AWTException e)
        {
            System.err.println("There was an error initializing Robot for mouse control.");
        }

        // Start the JNativeHook global background key listener
        try 
        {
            GlobalScreen.registerNativeHook();
        }
        catch (NativeHookException ex)
        {
            System.err.println("There was an error registering the native hook.");
        }
        
        // add the GolfAim object to the GlobalScreen
        GlobalScreen.addNativeKeyListener(aimer);

    }
}
