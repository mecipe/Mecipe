package org.mecipe.server.common.utils;

import java.security.SecureRandom;

public final class RandomGenerator {

    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成随机ID
     */
    public static String generateNickName() {
        return "user-" + generateRandom(10);
    }

    /**
     * 生成随机ID
     *
     * @param length 生成多少位
     */
    public static String generateRandom(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }

}
