package validation;


import exceptions.EnumNotFoundException;
import exceptions.InvalidFieldException;
import general.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.NoSuchElementException;

/**
 * Класс, обеспечивающий ввод элементов HumanBeings
 * Обеспечивает валидность вводимых полей
 */
public class HumanBeingBuilderImpl implements HumanBeingBuilder {

    private final boolean isScript;
    private final HumanBeingValidator validator;
    private final BufferedReader reader;
    private String line;

    protected long id = -1; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private boolean realHero;
    private boolean hasToothpick;
    private int impactSpeed;
    private WeaponType weaponType; //Поле не может быть null
    private Mood mood; //Поле не может быть null
    private Car car; //Поле не может быть null
    private String username;

    public HumanBeingBuilderImpl(BufferedReader bufferedReader , boolean isScript,HumanBeingValidator validator ) {
        reader = bufferedReader;
        this.isScript = isScript;
        this.validator = validator;
    }

//    @Override
//    public void setId(long id) {
//        this.id = id;
//    }
//
//    @Override
//    public void setIdRandom(long id) {
//        this.id = (long) (Math.random() * 15679 + 15);
//    }

    @Override
    public void setName(String name) throws InvalidFieldException {
        validator.validateName(name);
        this.name = name;
    }

    @Override
    public void setCoordinateX(long x) throws InvalidFieldException {
        if (coordinates == null)
            coordinates = new Coordinates();
        validator.validateCoordinateX(x);
        this.coordinates.setX(x);
    }

    @Override
    public void setCoordinateY(long y) throws InvalidFieldException {
        if (coordinates == null)
            coordinates = new Coordinates();
        validator.validateCoordinateY(y);
        this.coordinates.setY(y);
    }

    @Override
    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = LocalDate.now();
    }

    @Override
    public void setRealHero(String s) throws InvalidFieldException {
        validator.validateRealHero(s);
        this.realHero = Boolean.parseBoolean(s);
    }

    @Override
    public void setHasToothpick(String s) throws InvalidFieldException {
        validator.validateHasToothpick(s);
        this.hasToothpick = Boolean.parseBoolean(s);

    }

    @Override
    public void setImpactSpeed(int impactSpeed) throws InvalidFieldException {
        this.impactSpeed = impactSpeed;
    }

    @Override
    public void setWeaponType(WeaponType weaponType) throws InvalidFieldException {
        validator.validateWeaponType(weaponType);
        this.weaponType = weaponType;
    }

    @Override
    public void setMood(Mood mood) throws InvalidFieldException {
        validator.validateMood(mood);
        this.mood = mood;

    }
    @Override
    public void setUserName(String username) throws InvalidFieldException {
        validator.validateUsername(username);
        this.username = username;
    }
    @Override
    public void setCar(Car car) {
        this.car = car;
    }


    @Override
    public HumanBeing getHumanBeing() {
        HumanBeing hb = new HumanBeing(name, coordinates, realHero, hasToothpick, impactSpeed, weaponType, mood, car,username);
        this.coordinates = null;
        return hb;
    }



    public void inputFieldsFile() throws InvalidFieldException {
        askName();
        askCoordinateX();
        askCoordinateY();
        askRealHero();
        askHasToothPick();
        askImpactSpeed();
        askWeaponType();
        askMood();
//        askCar();
    }

    public void askName() {
        System.out.println("Input HumanBeing Name: ");
        try {
            setName(inputLine());
        } catch (InvalidFieldException e) {
            System.out.println("HumanBeing Name should be String, can't be null");
            if (!isScript)
                askName();
        }
    }

    public void askCoordinateX() {
        System.out.println("Input HumanBeing Coordinate X: ");
        try {
            setCoordinateX(Integer.parseInt(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            System.out.println("HumanBeing Coordinate X should be long");
            if (!isScript)
                askCoordinateX();
        }
    }

    public void askCoordinateY() {
        System.out.println("Input HumanBeing Coordinate Y: ");
        try {
            setCoordinateY(Long.parseLong(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            System.out.println("HumanBeing Coordinate Y should be int and less than 75");
            if (!isScript)
                askCoordinateY();
        }
    }

    public void askRealHero() throws InvalidFieldException {
        System.out.println("Input HumanBeing realHero: ");
        try {
            setRealHero(inputLine());
        } catch (InvalidFieldException e) {
            System.out.println("realHero required a boolean");
            if (!isScript)
                askRealHero();
        }
    }

    public void askHasToothPick() throws InvalidFieldException {
        System.out.println("Input HumanBeing hasToothpick: ");
        try {
            setHasToothpick(inputLine());
        } catch (InvalidFieldException e) {
            System.out.println("hasToothPick required a boolean");
            if (!isScript)
                askHasToothPick();
        }
    }

    public void askImpactSpeed() {
        System.out.println("Input HumanBeing impactSpeed: ");
        try {
            setImpactSpeed(Integer.parseInt(inputLine()));
        } catch (InvalidFieldException | NumberFormatException e) {
            System.out.println("HumanBeing impactSpeed should be int");
            if (!isScript)
                askImpactSpeed();
        }
    }

    public WeaponType checkWeaponType(String s) throws InvalidFieldException, EnumNotFoundException {
        for (WeaponType wt : WeaponType.values()) {
            if (s.equalsIgnoreCase(wt.get())) {
                validator.validateWeaponType(wt);
                return wt;
            }
        }
        throw new EnumNotFoundException("There is no enum named " + s);
    }

    public void askWeaponType() {
        System.out.println("Available values for weaponType: SHOTGUN, KNIFE, BAT");
        System.out.println("Input HumanBeing weaponType:");
        //todo
        try {
            setWeaponType(checkWeaponType(inputLine()));
        } catch (InvalidFieldException e) {
            System.out.println("please input available weapon type");
            if (!isScript)
                askWeaponType();
        } catch (EnumNotFoundException e) {
            System.out.println(e.getMessage());
            if (!isScript) {
                askWeaponType();
            }
        }
    }


    public Mood checkMood(String s) throws InvalidFieldException, EnumNotFoundException {
        for (Mood mood : Mood.values()) {
            if (s.equalsIgnoreCase(mood.get())) {
                validator.validateMood(mood);
                return mood;
            }
        }
        throw new EnumNotFoundException("There is no enum named " + s);
    }

    public void askMood() throws InvalidFieldException {
        System.out.println("Available values for mood: RAGE, APATHY, SORROW, SADNESS");
        System.out.println("Input HumanBeing mood: ");
        try {
            setMood(checkMood(inputLine()));

        } catch (InvalidFieldException e) {
            System.out.println("please input available mood");
            if (!isScript)
                askMood();
        } catch (EnumNotFoundException e) {
            System.out.println(e.getMessage());
            if (!isScript)
                askMood();
        }
    }

    public void askCar() {
        System.out.println("Input HumanBeing car: ");
        this.car = new Car(inputLine());
    }

    /**
     * Обеспечивает ввод всех полей и передачу экземпляра HumanBeing
     *
     * @return humanBeing
     */

    @Override
    public HumanBeing askHumanBeing() throws InvalidFieldException {
        inputFieldsFile();
        return getHumanBeing();
    }

    /**
     * Прочитать строку
     *
     * @return line
     */
    private String inputLine() {
        try {
            line = reader.readLine().toLowerCase();
        } catch (IOException ioException) {
            IO.println(ioException.getMessage());
        } catch (NoSuchElementException | NullPointerException e) {
            IO.errPrint("You can't input this");
            System.exit(0);
        }
        return line;
    }
}
