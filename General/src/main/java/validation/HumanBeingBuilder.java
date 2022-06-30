package validation;


import exceptions.EnumNotFoundException;
import exceptions.InvalidFieldException;
import general.Car;
import general.WeaponType;
import general.HumanBeing;
import general.Mood;

import java.time.LocalDate;

public interface HumanBeingBuilder {
//    void setId(long id);
//
//    void setIdRandom(long id);

    void setName(String name) throws InvalidFieldException;

    void setCoordinateX(long x) throws InvalidFieldException;

    void setCoordinateY(long y) throws InvalidFieldException;

    void setCreationDate(LocalDate creationDate);

    void setImpactSpeed(int ImpactSpeed) throws InvalidFieldException;

    void setWeaponType(WeaponType weaponType) throws InvalidFieldException;

    void setMood(Mood mood) throws InvalidFieldException;

    void setCar(Car car);

    WeaponType checkWeaponType(String s) throws InvalidFieldException, EnumNotFoundException;

    void setUserName(String username) throws InvalidFieldException;

    Mood checkMood(String s) throws InvalidFieldException, EnumNotFoundException;

    void setRealHero(String s) throws InvalidFieldException;

    void setHasToothpick(String s) throws InvalidFieldException;

    HumanBeing getHumanBeing() throws InvalidFieldException;

    HumanBeing askHumanBeing() throws InvalidFieldException;
}
