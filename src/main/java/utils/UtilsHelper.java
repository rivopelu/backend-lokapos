package utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

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

    public static String generateRandomPassword(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "abcdefghijklmnopqrstuvwxyz"
                + "0123456789";

        StringBuilder password = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            password.append(characters.charAt(index));
        }

        return password.toString();
    }

    public static String generateAvatar(String name) {
        return "https://ui-avatars.com/api/?name=" + name;
    }
}
