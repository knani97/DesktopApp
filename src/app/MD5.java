package app;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import de.mkammerer.argon2.Argon2Factory.Argon2Types;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5 {
    private static MessageDigest digester;
    private static Argon2 argon2 = Argon2Factory.create(Argon2Types.ARGON2id);

    static {
        try {
            digester = MessageDigest.getInstance("MD5");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

   public static String crypt(String password) {
        /*if (str == null || str.length() == 0) {
            throw new IllegalArgumentException("String to encript cannot be null or zero length");
        }

        digester.update(str.getBytes());
        byte[] hash = digester.digest();
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < hash.length; i++) {
            if ((0xff & hash[i]) < 0x10) {
                hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
            }
            else {
                hexString.append(Integer.toHexString(0xFF & hash[i]));
            }
        }
        return hexString.toString();*/
        String hash = argon2.hash(4, 65536, 1, password);
        argon2.wipeArray(password.toCharArray());
        return hash;
    }
   
   public static boolean matches(String hash, String password) {
       return argon2.verify(hash, password);
   }
}

   
  