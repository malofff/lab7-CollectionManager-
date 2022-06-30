package validation;


import exceptions.InvalidFieldException;
import general.Mood;
import general.WeaponType;

/**
 * класс, осуществялющий валидацию полей класса HumanBeing
 */

public class HumanBeingValidatorImpl implements HumanBeingValidator {

    @Override
    public void validateId(long id) throws InvalidFieldException {
        if (id < 0) throw new InvalidFieldException("Invalid field value");
    }

    @Override
    public void validateName(String name) throws InvalidFieldException {
        if (name == null || name.equals("")) throw new InvalidFieldException("Field mustn't be null or empty");
    }

    @Override
    public void validateCoordinateX(Long x) throws InvalidFieldException {
        if (x == null) throw new InvalidFieldException("Invalid value for HumanBeing Coordinate x");
    }

    @Override
    public void validateCoordinateY(Long y) throws InvalidFieldException {
        if (y == null || y > 74) throw new InvalidFieldException("Invalid value for HumanBeing Coordinate y");
    }

    @Override
    public void validateWeaponType(WeaponType weaponType) throws InvalidFieldException {
        if (weaponType == null) throw new InvalidFieldException("Invalid value for HumanBeing weaponType");
    }

    @Override
    public void validateMood(Mood mood) throws InvalidFieldException {
        if ((mood == null)) throw new InvalidFieldException("Invalid value for HumanBeing mood");
    }

    @Override
    public void validateRealHero(String s) throws InvalidFieldException {
        if (!((s.equalsIgnoreCase("true")) || s.equalsIgnoreCase("false"))) throw new InvalidFieldException("You should input boolean");
    }

    @Override
    public void validateHasToothpick(String s) throws InvalidFieldException {
        if (!((s.equalsIgnoreCase("true"))|s.equalsIgnoreCase("false"))) throw new InvalidFieldException("You should input boolean");
    }

    @Override
    public void validateUsername(String userName) throws InvalidFieldException {
        if (userName == null)
            throw new InvalidFieldException("Invalid value for Username");
    }

}
