package consys.onlineexam.helper;

import android.util.Base64;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

public class AES {
    private static String decryptedString;
    private static String encryptedString;
    private static byte[] key;
    private static SecretKeySpec secretKey;

    public static String getDString(String arv) {
        String strPssword = "R!T@S#K$J%P^K&B*S(K)";
        setKey("R!T@S#K$J%P^K&B*S(K)");
        decrypt(arv.trim());
        return getDecryptedString();
    }

    public static void setKey(String myKey) {
        try {
            key = myKey.getBytes("UTF-8");
            key = MessageDigest.getInstance(MessageDigestAlgorithms.SHA_1).digest(key);
            key = copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e2) {
            e2.printStackTrace();
        }
    }

    public static String getDecryptedString() {
        System.out.println("after dec" + decryptedString);
        return decryptedString;
    }

    public static void setDecryptedString(String decryptedString) {
        System.out.println("setting string" + decryptedString);
        decryptedString = decryptedString;
    }

    public static String getEncryptedString() {
        return encryptedString;
    }

    public static void setEncryptedString(String encryptedString) {
        System.out.println("before decrypt" + encryptedString);
        encryptedString = encryptedString;
    }

    public static String encrypt(String strToEncrypt) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(1, secretKey);
            System.out.println("before decrypt" + strToEncrypt);
            setEncryptedString(new String(cipher.doFinal(Base64.encode(strToEncrypt.getBytes(), 0))));
        } catch (Exception e) {
        }
        return null;
    }

    public static String decrypt(String strToDecrypt) {
        try {
            String str;
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(2, secretKey);
            try {
                str = new String(cipher.doFinal(Base64.decode(strToDecrypt, 0)));
            } catch (Exception e) {
                e.printStackTrace();
                str = e.getMessage();
                System.out.println(e.getMessage());
            }
            System.out.println("After decrypt" + str);
            setDecryptedString(str);
        } catch (Exception e2) {
        }
        return null;
    }

    public static byte[] copyOf(byte[] original, int newLength) {
        byte[] copy = new byte[newLength];
        System.arraycopy(original, 0, copy, 0, Math.min(original.length, newLength));
        return copy;
    }
}
