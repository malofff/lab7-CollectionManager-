package general;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class SHA1Coder {
    private final static String encryptAlg = "SHA-1";
    private final static String SALT = "pepper";

    /**
     * @param password password that we want to encrypt
     * @return encrypted password
     */
    public static String encryptPassword(String password) {
           try {
            MessageDigest md = MessageDigest.getInstance(encryptAlg);
            md.update(SALT.getBytes(StandardCharsets.UTF_8));
            byte[] bytes = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

}
}
