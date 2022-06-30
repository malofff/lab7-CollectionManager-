package Server.command.commands;

import Server.collection.CollectionManager;
import Server.collection.ServerHumanBeing;
import Server.command.StudyGroupCommand;
import Server.connection.response.ResponseCreator;
import exceptions.InvalidCommandType;
import general.AbstractCommand;

import java.util.HashSet;


public class MyIdsCommand extends AbstractCommand implements StudyGroupCommand {

    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public MyIdsCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("my_ids", " : получить список всех id ваших элементов", true);
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args, ServerHumanBeing studyGroup) {
        throw new InvalidCommandType("my_ids command not required HumanBeing instance");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3)
            throw new RuntimeException("Неизвестная ошибка, не найдены все аргументы команды!");
        HashSet<Long> ids = collectionManager.getIds(args[2]);

        StringBuilder sb = new StringBuilder();
        responseCreator.addToMsg(ids.size() + " ids found:");
        for (Long id : ids) {
            sb.append(id).append(", ");
        }
        if (sb.length() > 2) {
            sb.deleteCharAt(sb.length() - 1);
            sb.deleteCharAt(sb.length() - 1);
        }

        responseCreator.addToMsg(sb.toString());
    }

    @Override
    public boolean isHumanBeingCommand() {
        return false;
    }
}
