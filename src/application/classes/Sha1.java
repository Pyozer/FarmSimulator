package application.classes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Sha1 {

    public static String cryptToSHA1(String value) throws NoSuchAlgorithmException {
        MessageDigest mDigest = MessageDigest.getInstance("Sha1");
        byte[] result = mDigest.digest(value.getBytes());
        StringBuffer sb = new StringBuffer();
        for (byte aResult : result) {
            sb.append(Integer.toString((aResult & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }

    public static boolean checkEquality(String string, String sha1) throws NoSuchAlgorithmException {
        return cryptToSHA1(string).equals(sha1);
    }
}
