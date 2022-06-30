package Server.command.commands;

import Server.collection.CollectionManager;
import Server.collection.ServerHumanBeing;
import Server.collection.HumanBeingShower;
import Server.connection.response.ResponseCreator;
import general.AbstractCommand;

public class ShowCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public ShowCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("show", " : вывести в стандартный поток вывода все элементы коллекции в строковом представлении");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + collectionManager.getHumanBeingsSortedById().size()).append("\n");
        for (ServerHumanBeing sg : collectionManager.getHumanBeingsSortedByUsername()) {
            sb.append(HumanBeingShower.toStrView(sg));
        }
        responseCreator.addToMsg(sb.toString());
    }
}
