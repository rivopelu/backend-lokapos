package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.SecureRandom;
import java.util.Date;

public class UtilsHelper {

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Long getExpireOnMinutes(int minute) {
        long currentTime = new Date().getTime();
        return (currentTime + (long) minute * 60 * 1000);
    }

    public static String generateNumericOTP() {
        SecureRandom random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }
}
