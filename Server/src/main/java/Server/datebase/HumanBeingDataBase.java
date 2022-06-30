package Server.datebase;

import Server.collection.ServerHumanBeing;
import exceptions.SQLNoDataException;

import java.util.concurrent.CopyOnWriteArrayList;

public interface HumanBeingDataBase {
    ServerHumanBeing getHumanBeing(long id) throws SQLNoDataException;

    int getNextId() throws SQLNoDataException;

    void insertHumanBeing(ServerHumanBeing studyGroup) throws Exception;

    boolean deleteHumanBeingsOfUser(String username);

    boolean deleteHumanBeingById(long id);

    void updateHumanBeing(long id, ServerHumanBeing studyGroup) throws SQLNoDataException;

    CopyOnWriteArrayList<ServerHumanBeing> getAll() throws SQLNoDataException;
}
