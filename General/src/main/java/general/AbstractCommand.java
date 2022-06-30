package general;

import lombok.Getter;

/**
 * Класс - абстракция каждой из команд
 */
public abstract class AbstractCommand implements Command {
    @Getter
    private final String name;
    @Getter
    private final String description;
    private boolean isHumanBeingCommand = false;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public AbstractCommand(String name, String description, boolean isHumanBeingCommand) {
        this(name, description);
        this.isHumanBeingCommand = isHumanBeingCommand;
    }

    public abstract void execute(String[] args);

    @Override
    public boolean isHumanBeingCommand() {
        return isHumanBeingCommand;
    }
}

