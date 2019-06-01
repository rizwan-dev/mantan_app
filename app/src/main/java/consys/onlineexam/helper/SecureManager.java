package consys.onlineexam.helper;

import android.util.Base64;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class SecureManager {
    public static String getHASH(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_256);
            md.update(password.getBytes());
            byte[] byteData = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte b : byteData) {
                sb.append(Integer.toString((b & 255) + 256, 16).substring(1));
            }
            System.out.println("Hex format : " + sb.toString());
            StringBuffer hexString = new StringBuffer();
            for (byte b2 : byteData) {
                String hex = Integer.toHexString(b2 & 255);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            System.out.println("Hex format : " + hexString.toString());
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String encrypt(String key, String text) {
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(1, aesKey);
            return new String(Base64.encode(cipher.doFinal(text.getBytes()), 0));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return key;
        } catch (NoSuchPaddingException e2) {
            e2.printStackTrace();
            return key;
        } catch (IllegalBlockSizeException e3) {
            e3.printStackTrace();
            return key;
        } catch (BadPaddingException e4) {
            e4.printStackTrace();
            return key;
        } catch (InvalidKeyException e5) {
            e5.printStackTrace();
            return key;
        }
    }

    public static String decrypt(String key, String text) {
        try {
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            byte[] encrypted = text.getBytes();
            cipher.init(2, aesKey);
            return new String(cipher.doFinal(Base64.decode(encrypted, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        String hash = getHASH("JOTIRAM");
        System.out.println("HASH OF NAME IS" + hash);
        System.out.println("ENCRYPTED" + encrypt("consistentsystem", hash));
        System.out.println("DECRYPTED" + decrypt("consistentsystem", hash));
    }
}
