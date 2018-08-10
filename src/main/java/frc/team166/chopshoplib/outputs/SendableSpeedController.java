package frc.team166.chopshoplib.outputs;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

/**
 * An object that is both a Sendable and a SpeedController.
 * 
 * <p>Provides a single utility method to wrap an appropriate object.
 * 
 * <p>This is used in a few select scenarios,
 * when we want to keep the exact type of an object general,
 * but at the same time want to access it as one of two disconnected interfaces.
 */
public interface SendableSpeedController extends Sendable, SpeedController {
    static <T extends Sendable & SpeedController> SendableSpeedController wrap(T wrapped) {
        return new SendableSpeedController() {

            @Override
            public String getName() {
                return wrapped.getName();
            }

            @Override
            public void setName(String name) {
                wrapped.setName(name);
            }

            @Override
            public String getSubsystem() {
                return wrapped.getSubsystem();
            }

            @Override
            public void setSubsystem(String subsystem) {
                wrapped.setSubsystem(subsystem);
            }

            @Override
            public void initSendable(SendableBuilder builder) {
                wrapped.initSendable(builder);
            }

            @Override
            public void set(double speed) {
                wrapped.set(speed);
            }

            @Override
            public double get() {
                return wrapped.get();
            }

            @Override
            public void setInverted(boolean isInverted) {
                wrapped.setInverted(isInverted);
            }

            @Override
            public boolean getInverted() {
                return wrapped.getInverted();
            }

            @Override
            public void disable() {
                wrapped.disable();
            }

            @Override
            public void stopMotor() {
                wrapped.stopMotor();
            }

            @Override
            public void pidWrite(double output) {
                wrapped.pidWrite(output);
            }
        };
    }
}
