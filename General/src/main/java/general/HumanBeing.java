package general;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@ToString
public class HumanBeing implements Serializable {
    @Serial
    private static final long serialVersionUID = 6666L;
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
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

    public HumanBeing(String name, Coordinates coordinates, boolean realHero, boolean hasToothpick, int impactSpeed, WeaponType weaponType, Mood mood, Car car,String username) {
//        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDate.now();
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        setWeaponType(weaponType);
        setMood(mood);
        setCar(car);
        this.username = username;
    }
}
