package Server;


import Server.collection.CollectionManager;
import Server.collection.CollectionManagerImpl;
import Server.command.ServerCommandReaderImpl;
import Server.connection.ServerConnectionManagerImpl;
import Server.connection.request.RequestHandler;
import Server.connection.request.RequestHandlerImpl;
import Server.connection.request.RequestReader;
import Server.connection.request.RequestReaderImpl;
import Server.connection.response.ResponseCreator;
import Server.connection.response.ResponseCreatorImpl;
import Server.connection.response.ResponseSender;
import Server.connection.response.ResponseSenderImpl;
import Server.datebase.*;
import Server.server.Server;
import Server.server.ServerApp;
import Server.user_manager.UserManager;
import Server.user_manager.UserManagerImpl;
import exceptions.SQLNoDataException;
import general.IO;
import validation.HumanBeingBuilder;
import validation.HumanBeingBuilderImpl;
import validation.HumanBeingValidatorImpl;

public class Main {
    public static void main(String[] args) {
//        if (args.length != 1) {
//            IO.errPrint("Input arguments: [port]");
//            return;
//        }

        try {
            HumanBeingBuilder builder = new HumanBeingBuilderImpl(IO.getReader(),
                    false, new HumanBeingValidatorImpl());

            DataBaseConnector.init();
            HumanBeingDataBase humanBeingDataBase = new PostgreHumanBeingDataBase(builder);
            UserDataBase userDataBase = new PostgreUserDataBase();

            ResponseCreator responseCreator = new ResponseCreatorImpl();

            UserManager userManager = new UserManagerImpl(userDataBase);
            CollectionManager collectionManager = new CollectionManagerImpl(responseCreator, humanBeingDataBase);

            ServerCommandReaderImpl commandReader = new ServerCommandReaderImpl();
            RequestHandler requestHandler = new RequestHandlerImpl(userManager, commandReader, collectionManager, responseCreator);

            ServerConnectionManagerImpl connectionManager = new ServerConnectionManagerImpl();
            RequestReader requestReader = new RequestReaderImpl();
            ResponseSender responseSender = new ResponseSenderImpl();
            ServerApp server = new Server(
                    collectionManager,
                    commandReader,
                    connectionManager,
                    responseCreator,
                    requestReader,
                    requestHandler,
                    responseSender,
                    Integer.parseInt("8080")
            );

            server.start();
        } catch (NumberFormatException | SQLNoDataException e) {
            IO.errPrint(e.getMessage());
        }
    }
}
