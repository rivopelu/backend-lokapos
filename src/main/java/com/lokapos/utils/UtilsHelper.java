package com.lokapos.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lokapos.model.response.ResponseParseFlagId;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class UtilsHelper {
    private static LocalDate lastResetDate = getCurrentDateInJakarta();

    private static LocalDate getCurrentDateInJakarta() {
        return ZonedDateTime.now(ZoneId.of("Asia/Jakarta")).toLocalDate();
    }

    public static BigInteger generateOrderCode(BigInteger latestCode) {
        if (!getCurrentDateInJakarta().isEqual(lastResetDate)) {
            lastResetDate = getCurrentDateInJakarta();
            return BigInteger.valueOf(1000);
        }

        if (latestCode == null || latestCode.compareTo(BigInteger.ZERO) <= 0) {
            return BigInteger.valueOf(1000);
        }

        return latestCode.add(BigInteger.ONE);
    }

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

    public static String generateIdFlag(String flag) {
        return flag + "_" + UUID.randomUUID();
    }

    public static String generateIdFlag(String flag, String id) {
        return flag + "_" + id;
    }

    public static ResponseParseFlagId parseIdFromFlg(String id) {
        String[] split = id.split("_");
        return ResponseParseFlagId.builder()
                .flag(split[0])
                .id(split[1])
                .build();
    }
}
