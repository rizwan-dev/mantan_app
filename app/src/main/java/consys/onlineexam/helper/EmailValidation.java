package consys.onlineexam.helper;

import org.apache.commons.io.FilenameUtils;

public class EmailValidation {
    static String err = "no error";

    public static void main(String[] args) {
        validate("abgfd.g643cde@gmail.mi");
    }

    public static boolean validate(String str) {
        if (firstCharCheck(str) && atCheck(str) && beforeAfterAtcheck(str) && providerCheck(str) && domainCheck(str) && symbolCheck(str) && spaceCheck(str)) {
            System.out.println("Valid Email Id");
            return true;
        }
        System.out.println("Invalid Email Id");
        System.out.println(err);
        return false;
    }

    private static boolean spaceCheck(String st) {
        int spcnt = 0;
        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == ' ') {
                spcnt++;
            }
        }
        if (spcnt == 0) {
            return true;
        }
        err = "There should not be spaces in email.";
        return false;
    }

    private static boolean atCheck(String st) {
        int atcnt = 0;
        for (int i = 0; i < st.length(); i++) {
            if (st.charAt(i) == '@') {
                atcnt++;
            }
        }
        if (atcnt != 0 && atcnt == 1) {
            return true;
        }
        err = "There should only one '@' in email.";
        return false;
    }

    private static boolean providerCheck(String st) {
        int cnt = 0;
        for (int i = st.lastIndexOf(64); i < st.lastIndexOf(46) - 1; i++) {
            cnt++;
        }
        if (cnt > 0) {
            return true;
        }
        err = "Enter proper provider name.";
        return false;
    }

    private static boolean beforeAfterAtcheck(String st) {
        int j = st.lastIndexOf(64);
        if (st.substring(j - 1, j + 1).equalsIgnoreCase(".")) {
            return false;
        }
        return true;
    }

    private static boolean symbolCheck(String st) {
        int flag = 0;
        for (int i = 0; i < st.length(); i++) {
            char ch = st.charAt(i);
            int p = st.charAt(i);
            if ((p < 48 || p > 57) && ((p < 65 || p > 90) && !((p >= 97 && p <= 122) || ch == '_' || ch == '@' || ch == FilenameUtils.EXTENSION_SEPARATOR || ch == ' '))) {
                err = "There should not be special symbols in email.";
                flag = 1;
                break;
            }
            flag = 0;
        }
        if (flag == 0) {
            return true;
        }
        return false;
    }

    private static boolean lengthCheck(String st) {
        if (st.length() > 11) {
            return true;
        }
        err = "Email length should be greater than 11.";
        return false;
    }

    private static boolean domainCheck(String st) {
        if (st.length() - st.lastIndexOf(46) > 1) {
            return true;
        }
        err = "Enter proper domain name.";
        return false;
    }

    private static boolean firstCharCheck(String st) {
        char p = st.charAt(0);
        if ((p >= 'A' && p <= 'Z') || (p >= 'a' && p <= 'z')) {
            return true;
        }
        err = "First Character Of Email Should be an Alphabate.";
        return false;
    }
}
