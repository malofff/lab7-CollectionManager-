package Server.command.commands;

import Server.collection.CollectionManager;
import Server.collection.ServerHumanBeing;
import Server.command.StudyGroupCommand;
import Server.connection.response.ResponseCreator;
import exceptions.InvalidCommandType;
import exceptions.NotOwnerException;
import general.AbstractCommand;

import java.util.LinkedList;
import java.util.Vector;
import java.util.stream.Collectors;

public class RemoveGreaterCommand extends AbstractCommand implements StudyGroupCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public RemoveGreaterCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("remove_greater {element}", " : удалить из коллекции все элементы, превышающие заданный", true);
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        throw new InvalidCommandType("remove greater command required HumanBeing instance");
    }

    @Override
    public void execute(String[] args, ServerHumanBeing humanBeing) {
        if (args.length != 3)
            throw new RuntimeException("Неизвестная ошибка, не найдены все аргументы команды!");

        String username = args[2];
        Vector<ServerHumanBeing> humanBeings = collectionManager.getHumanBeingsSortedById().stream()
                .filter(x -> x.getUsername().equals(username))
                .collect(Collectors.toCollection(Vector::new));

        long cnt = humanBeings.stream()
                .filter(x -> x.getImpactSpeed() > humanBeing.getImpactSpeed()).count();

        humanBeings.stream()
                .filter(x -> x.getImpactSpeed() > humanBeing.getImpactSpeed())
                .forEach(x -> {
                    try {
                        collectionManager.removeById(x.getId(), username);
                    } catch (NotOwnerException unexpected) {
                        unexpected.printStackTrace();
                        responseCreator.addToMsg(unexpected.getMessage());
                    }
                });

        responseCreator.addToMsg(cnt + " elements removed at all");
    }
}
