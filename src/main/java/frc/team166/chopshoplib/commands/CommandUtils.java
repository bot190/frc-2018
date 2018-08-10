package frc.team166.chopshoplib.commands;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class CommandUtils {
    private CommandUtils() {}
    
    public static Command repeat(final int numTimesToRun, final Command cmd) {
        return new Command() {
            private int numTimesRun;

            @Override
            protected void initialize() {
                numTimesRun++;
                cmd.start();
            }

            @Override
            protected void execute() {
                if (cmd.isCompleted()) {
                    numTimesRun++;
                    cmd.start();
                }
            }

            @Override
            protected boolean isFinished() {
                return numTimesRun >= numTimesToRun;
            }

            @Override
            protected void end() {
                numTimesRun = 0;
            }
        };
    }

    public static Command repeat(final int numTimesToRun, final Supplier<Command> cmd) {
        class RepeatedCommand extends CommandGroup {
            public RepeatedCommand() {
                super();
                for (int i = 0; i < numTimesToRun; i++) {
                    addSequential(cmd.get());
                }
            }
        }
        return new RepeatedCommand();
    }

    public static Command repeatWhile(final BooleanSupplier cond, final Command cmd) {
        return new Command() {
            private boolean shouldFinish;

            @Override
            protected void execute() {
                if (!cmd.isRunning()) {
                    if (cond.getAsBoolean()) {
                        cmd.start();
                    } else {
                        shouldFinish = true;
                    }
                }
            }

            @Override
            protected boolean isFinished() {
                return shouldFinish;
            }
        };
    }

    public static Command from(final Runnable func) {
        return new ActionCommand(func);
    }

    public static Command first(final Command... cmds) {
        return new CommandChain(cmds);
    }
}