package Server.command.commands;

import Server.collection.CollectionManager;
import Server.collection.ServerHumanBeing;
import Server.collection.HumanBeingShower;
import Server.connection.response.ResponseCreator;
import general.AbstractCommand;

public class ShowFullCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public ShowFullCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("show_full", " : вывести в стандартный поток вывода все элементы коллекции со всеми полями");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        StringBuilder sb = new StringBuilder("Элементов в коллекции: " + collectionManager.getHumanBeingsSortedById().size()).append("\n");
        for (ServerHumanBeing sg : collectionManager.getHumanBeingsSortedByUsername()) {
            sb.append(HumanBeingShower.toStrViewFull(sg));
        }
        responseCreator.addToMsg(sb.toString());
    }
}
