package Server.collection;


import exceptions.InsertException;
import exceptions.NotOwnerException;
import general.HumanBeing;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Vector;

public interface CollectionManager {
    void clear(String username);

    void removeById(long id, String username) throws NotOwnerException;

    void update(long id, ServerHumanBeing studyGroup);

    void addElement(ServerHumanBeing studyGroup) throws InsertException;

    void info();

    boolean containsId(long id);

    Vector<ServerHumanBeing> getHumanBeingsSortedById();

    Vector<ServerHumanBeing> getHumanBeingsSortedByUsername();

    ServerHumanBeing getServerHumanBeing(HumanBeing humanBeing);

    HashSet<Long> getIds(String username);

}
