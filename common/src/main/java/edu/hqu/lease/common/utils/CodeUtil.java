package edu.hqu.lease.common.utils;

import java.util.Random;

public class CodeUtil {

    public static String getRandomCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            int i1 = random.nextInt(10);
            stringBuilder.append(i1);
        }
        return stringBuilder.toString();
    }
}
