package frc.team166.chopshoplib.controls;

import java.util.HashMap;
import java.util.Map;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * Represents a joystick along with it's associated buttons
 * <p>
 * This class serves as a wrapper for a Joystick and all it's buttons.
 */
public class ButtonXboxController extends XboxController {
    private final Map<Integer, Button> buttons = new HashMap<Integer, Button>();

    /**
     * Construct an instance of a joystick along with each button the joystick has.
     *
     * @param port
     *            The USB port that the joystick is connected to on the Driver
     *            Station
     */
    public ButtonXboxController(final int port) {
        super(port);
    }

    /**
     * Get a button from this joystick
     * <p>
     * Returns the sepcified button of a joystick without having to explicitly
     * create each button.
     * 
     * @param buttonId
     *            The index of the button to accesss
     * @return The button object for the given ID
     */
    public Button getButton(final int buttonId) {
        if(!buttons.containsKey(buttonId)) {
            buttons.put(buttonId, new JoystickButton(this, buttonId));
        }
        return buttons.get(buttonId);
    }

    public Button getButton(final XBoxButton buttonId) {
        return getButton(buttonId.get());
    }

    public enum XBoxButton {
        kBumperLeft(5),
        kBumperRight(6),
        kStickLeft(9),
        kStickRight(10),
        kA(1),
        kB(2),
        kX(3),
        kY(4),
        kBack(7),
        kStart(8);

        private final int value;

        XBoxButton(final int value) {
            this.value = value;
        }

        public int get() {
            return value;
        }
    }
}