package general;

import lombok.Getter;

import java.io.Serializable;

@Getter
public enum WeaponType implements Serializable {
    SHOTGUN("SHOTGUN"),
    KNIFE("KNIFE"),
    BAT("BAT");
    private static final long serialVersionUID = 25236L;

    private final String str;

    WeaponType(String str) {
        this.str = str;
    }

    public static void printValues() {
        System.out.println("List of WeaponType enum values:");
        for (WeaponType weaponType : WeaponType.values()) {
            System.out.println(weaponType.getStr());
        }
    }
    public String get() {
        return str;
    }

}
