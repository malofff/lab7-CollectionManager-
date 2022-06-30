package general;

import lombok.Getter;

import java.io.Serializable;
@Getter
public enum Mood implements Serializable {
    SADNESS("SADNESS"),
    SORROW("SORROW"),
    APATHY("APATHY"),
    RAGE("RAGE");

    private static final long serialVersionUID = -6358L;

    private final String str;

    Mood(String str) {
        this.str = str;
    }

    public static void printValues() {
        System.out.println("List of Mood enum values:");
        for (Mood mood : Mood.values()) {
            System.out.println(mood.getStr());
        }
    }
    public String get() {
        return str;
    }
}
