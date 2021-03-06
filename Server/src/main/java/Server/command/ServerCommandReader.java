package Server.command;
import Server.collection.ServerHumanBeing;
import general.*;
import exceptions.*;

import java.util.HashMap;

public interface ServerCommandReader {
    LimitedQueue<String> getHistory();

    void executeCommand(String userCommand, ServerHumanBeing studyGroup) throws CommandIsNotExistException;

    void executeServerCommand(String userCommand) throws CommandIsNotExistException;

    void addCommand(String commandName, AbstractCommand command);

    void addServerCommand(String commandName, ServerCommand command);

    HashMap<String, AbstractCommand> getCommandMap();

    AbstractCommand getCommandByName(String commandName);

}
