package co.ke.resilient.loan_calculator.utility;

import org.apache.commons.lang3.RandomStringUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class StringUtils {
    public static String toBase64String(String value) {
        byte[] bytes = value.getBytes(StandardCharsets.ISO_8859_1);
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static String generateTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return dateFormat.format(new Date());
    }

    public static String generateAlphanumeric(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public static String generateNumerics(int length) {
        return RandomStringUtils.randomNumeric(length);
    }

    public static String[] extractOtherNames(String[] nameParts, int start, int end) {
        if (start >= end) {
            return new String[]{};
        }
        String[] otherNames = new String[end - start];
        System.arraycopy(nameParts, start, otherNames, 0, end - start);
        return otherNames;
    }

    public static String getNonNullString(String input) {
        return input != null ? input : " ";
    }
}
