package Client.commands;


import exceptions.CommandIsNotExistException;
import general.AbstractCommand;
import general.HumanBeing;

import java.util.Collection;

public interface ClientCommandReader {

    void executeCommand(String userCommand, HumanBeing humanBeing) throws CommandIsNotExistException;

    void addCommand(String commandName, AbstractCommand command);

    Collection<AbstractCommand> getCommands();
}
