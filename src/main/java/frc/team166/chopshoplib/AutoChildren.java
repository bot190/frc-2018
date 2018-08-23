package frc.team166.chopshoplib;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.team166.chopshoplib.commands.DefaultDashboard;

public interface AutoChildren {
    default void addChildren(Subsystem system) {
        Class<? extends Subsystem> aClass = system.getClass();

        for (Field field : aClass.getDeclaredFields()) {
            // Make the field accessible, because apparently we're allowed to do that
            field.setAccessible(true);
            try {
                // See if the returned object implements sendable.
                // If it does then lets add it as a child.
                if (Sendable.class.isAssignableFrom(field.getType())) {
                    System.out.println("Adding field: " + field.getName());
                    system.addChild(field.getName(), (Sendable) field.get(system));
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        for (Method method : aClass.getDeclaredMethods()) {
            try {
                for (DefaultDashboard annotation : method
                        .getAnnotationsByType(DefaultDashboard.class)) {
                    Double[] args = RobotUtils.toBoxed(annotation.value());
                    Command command = (Command) method.invoke(this, (Object[]) args);
                    if (command != null) {
                        System.out.println("Adding command: " + command);
                        system.addChild(command.getName(), command);
                    }
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}