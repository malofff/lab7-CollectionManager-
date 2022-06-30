package validation;

public interface InputChecker {
    static boolean checkLong(String string) {
        try {
            Long.parseLong(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
