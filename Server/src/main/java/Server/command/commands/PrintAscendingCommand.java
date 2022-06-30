package Server.command.commands;


import Server.collection.CollectionManager;
import Server.collection.HumanBeingShower;
import Server.connection.response.ResponseCreator;
import general.*;


public class PrintAscendingCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public PrintAscendingCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("print_ascending", " : вывести элементы коллекции в порядке возрастания");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + collectionManager.getHumanBeingsSortedById().size()).append("\n");
        collectionManager.getHumanBeingsSortedById()
                .forEach(x -> sb.append(HumanBeingShower.toStrView(x)));
        responseCreator.addToMsg(sb.toString());
    }
}
