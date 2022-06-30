package Server.command;


import Server.collection.ServerHumanBeing;
import general.Command;

public interface StudyGroupCommand extends Command {
    void execute(String[] args, ServerHumanBeing studyGroup);

    default boolean isHumanBeingCommand() {
        return true;
    }
}
