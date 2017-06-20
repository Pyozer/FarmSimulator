package application.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {

    /**
     * Crypte une chaine de caractère en SHA1
     * @param value Chaine à crypter
     * @return Chaine crypté en SHA1
     * @throws NoSuchAlgorithmException
     */
    public static String cryptToSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("Sha1");
        byte[] result = mDigest.digest(value.getBytes());
        StringBuilder sb = new StringBuilder();
        for (byte aResult : result) {
            sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}