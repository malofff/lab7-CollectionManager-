package Server.collection;


import Server.connection.response.ResponseCreator;
import Server.datebase.HumanBeingDataBase;
import exceptions.InsertException;
import exceptions.NotOwnerException;
import exceptions.SQLNoDataException;
import general.IO;
import general.HumanBeing;

import java.time.ZonedDateTime;
import java.util.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;

/**
 * Класс, который хранит коллекцию и совершает действия над ней
 */
public final class CollectionManagerImpl implements CollectionManager {
    private final ResponseCreator responseCreator;
    private final HumanBeingDataBase dataBase;
    private final HashMap<String, HashSet<Long>> owners;
    private final ReentrantLock locker;
    private final ZonedDateTime creationDate;
    private Vector<ServerHumanBeing> humanBeings;


    public CollectionManagerImpl(ResponseCreator responseCreator, HumanBeingDataBase dataBase) throws SQLNoDataException {
        this.responseCreator = responseCreator;
        this.dataBase = dataBase;

        owners = new HashMap<>();
        locker = new ReentrantLock();
        creationDate = ZonedDateTime.now();
        humanBeings = new Vector<>(dataBase.getAll());
        initOwners();
    }

    /**
     * Метод отвечающий за полную очистку коллекции
     */
    @Override
    public void clear(String username) {
        if (username == null)
            throw new RuntimeException("Non excepted error");

        locker.lock();


        if (!dataBase.deleteHumanBeingsOfUser(username))
            responseCreator.addToMsg("Не удалось очистить коллекцию");

        long cnt = humanBeings.stream().filter(x -> x.getUsername().equals(username)).count();
        humanBeings = humanBeings.stream().
                filter(x -> !x.getUsername().
                        equals(username)).collect(Collectors.toCollection(Vector::new));

        responseCreator.addToMsg("Коллекция очищена, всего удалено: " + cnt + " элементов");
        owners.remove(username);
        newUser(username);
        locker.unlock();

    }


    /**
     * Метод, отвечающий за удаление элемента коллекции по id
     *
     * @param id - искомый  индентификатор
     */
    @Override
    public void removeById(long id, String username) throws NotOwnerException {
        if (!isOwner(id, username))
            throw new NotOwnerException();

        locker.lock();

        if (!dataBase.deleteHumanBeingById(id)) {
            responseCreator.addToMsg("Id is not found");
            return;
        }

        responseCreator.addToMsg("Element with id " + id + " is successfully removed from collection");
        humanBeings = humanBeings.stream()
                .filter(humanBeing -> humanBeing.getId() != id)
                .collect(Collectors.toCollection(Vector::new));
        owners.get(username).remove(id);
        locker.unlock();
    }

    /**
     * Метод, отвечающий за добавление элемента в коллекцию
     */
    @Override
    public void addElement(ServerHumanBeing humanBeing) throws InsertException {
        try {
            locker.lock();
            dataBase.insertHumanBeing(humanBeing);
            humanBeings.add(humanBeing);
            newUser(humanBeing.getUsername());
            owners.get(humanBeing.getUsername()).add(humanBeing.getId());
        } catch (Exception e) {
            throw new InsertException("Не удалось добавить элемент: " + e.getMessage());
        } finally {
            locker.unlock();
        }
    }

    /**
     * @param id id humanBeing, по которому произойдет обновление
     * @param humanBeing обновленный экземпляр
     */
    @Override
    public void update(long id, ServerHumanBeing humanBeing) {
        ServerHumanBeing hb =
                humanBeings.stream().filter(x -> x.getId() == id && isOwner(id, x.getUsername())).
                        findFirst().orElse(null);

        if (hb == null) {
            responseCreator.addToMsg("Input id is not found!");
            return;
        }

        try {
            locker.lock();
            dataBase.updateHumanBeing(id, humanBeing);
            hb.setName(humanBeing.getName());
            hb.setCoordinates(humanBeing.getCoordinates());
            hb.setWeaponType(humanBeing.getWeaponType());
            hb.setRealHero(humanBeing.isRealHero());
            hb.setHasToothpick(humanBeing.isHasToothpick());
            hb.setImpactSpeed(humanBeing.getImpactSpeed());
            hb.setMood(humanBeing.getMood());
            responseCreator.addToMsg("Element is updated!");
        } catch (SQLNoDataException e) {
            responseCreator.addToMsg("Invalid field");
        } finally {
            locker.unlock();
        }
    }

    /**
     * Метод выводит краткую информацию о классе
     */
    @Override
    public void info() {
        String info = "Время инциализации коллекции: " + creationDate + "\n" +
                "Количество элементов в коллекции: " + humanBeings.size() + "\n" +
                "Тип коллекции: " + humanBeings.getClass().getSimpleName();
        responseCreator.addToMsg(info);
    }

    /**
     * Проверяет, есть ли элемент с данным id
     *
     * @param id - искомый
     * @return true - если элемент с данным id существует
     */
    @Override
    public boolean containsId(long id) {
        return humanBeings.stream().anyMatch(x -> x.getId() == id);
    }


    /**
     * @return получить коллекцию, отсортированную по полю id
     */
    @Override
    public Vector<ServerHumanBeing> getHumanBeingsSortedById() {
        sortById();
        return humanBeings;
    }

    /**
     * @return получить коллекцию, отсортированную по полю username
     */
    @Override
    public Vector<ServerHumanBeing> getHumanBeingsSortedByUsername() {
        sortByUsername();
        return humanBeings;
    }

    /**
     * @param username - пользователь, чьи id будут получены
     * @return получить множество id, принадлежащих username
     */
    @Override
    public HashSet<Long> getIds(String username) {
        newUser(username);
        return owners.get(username);
    }

    /**
     * Получить экземпляр ServerHumanBeing, получив id с базы данных
     *
     * @param humanBeing "неготовый" humanBeing
     * @return экземпляр
     */
    @Override
    public ServerHumanBeing getServerHumanBeing(HumanBeing humanBeing) {
        locker.lock();
        try {
            return new ServerHumanBeing(humanBeing, dataBase.getNextId());
        } catch (SQLNoDataException e) {
            IO.errPrint(e.getMessage());
            return null;
        } finally {
            locker.unlock();
        }
    }

    /**
     * Метод, обеспечивающий сортировку коллекции
     * использует компаратор по полю id
     */
    private void sortById() {
        humanBeings = humanBeings.stream()
                .sorted(getComparatorById())
                .collect(Collectors.toCollection(Vector::new));
    }

    /**
     * Метод, обеспечивающий сортировку коллекции
     * использует компаратор по полю userName
     */
    private void sortByUsername() {
        humanBeings = humanBeings.stream()
                .sorted(getComparatorByUsername())
                .collect(Collectors.toCollection(Vector::new));
    }

    /**
     * Получить Comparator сравнения по id
     *
     * @return компаратор
     */
    private Comparator<ServerHumanBeing> getComparatorById() {
        return Comparator.comparing(ServerHumanBeing::getId);
    }

    /**
     * Получить Comparator сравнения по username
     *
     * @return компаратор
     */
    private Comparator<ServerHumanBeing> getComparatorByUsername() {
        return Comparator.comparing(HumanBeing::getUsername);
    }

    private void initOwners() {
        for (ServerHumanBeing s : humanBeings) {
            if (!owners.containsKey(s.getUsername()))
                newUser(s.getUsername());
            owners.get(s.getUsername()).add(s.getId());
        }
    }

    private void newUser(String username) {
        locker.lock();
        if (!owners.containsKey(username))
            owners.put(username, new HashSet<>());
        locker.unlock();
    }

    private boolean isOwner(long id, String username) {
        return owners.containsKey(username) && owners.get(username).contains(id);
    }
}
