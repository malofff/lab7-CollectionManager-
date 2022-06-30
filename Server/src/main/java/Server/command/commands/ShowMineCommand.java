package Server.command.commands;

import Server.collection.CollectionManager;
import Server.collection.HumanBeingShower;
import Server.connection.response.ResponseCreator;
import general.AbstractCommand;

import java.util.stream.Collectors;


public class ShowMineCommand extends AbstractCommand {
    private final CollectionManager collectionManager;
    private final ResponseCreator responseCreator;

    public ShowMineCommand(CollectionManager collectionManager, ResponseCreator responseCreator) {
        super("show_mine", " : вывести в стандартный поток вывода элементы коллекции, принадлежащие текущему пользователю (в виде строки)");
        this.collectionManager = collectionManager;
        this.responseCreator = responseCreator;
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 3)
            throw new RuntimeException("Неизвестная ошибка, не найдены все аргументы команды!");

        String username = args[2];
        long cnt = collectionManager.getHumanBeingsSortedById().stream().filter(x -> x.getUsername().equals(username)).count();

        String str = "Ваших элементов в коллекции: " + cnt + "\n" +
                collectionManager.getHumanBeingsSortedById().stream().
                        filter(x -> x.getUsername().equals(username)).
                        map(HumanBeingShower::toStrView).collect(Collectors.joining());

        responseCreator.addToMsg(str);
    }
}
