package net.momirealms.sparrow.common.util;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class RandomUtils {

    private final Random random;

    private RandomUtils() {
        random = ThreadLocalRandom.current();
    }

    private static class SingletonHolder {
        private static final RandomUtils INSTANCE = new RandomUtils();
    }

    private static RandomUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static int generateRandomInt(int min, int max) {
        return getInstance().random.nextInt(max - min + 1) + min;
    }

    public static double generateRandomDouble(double min, double max) {
        return min + (max - min) * getInstance().random.nextDouble();
    }

    public static boolean generateRandomBoolean() {
        return getInstance().random.nextBoolean();
    }

    public static <T> T getRandomElementFromArray(T[] array) {
        int index = getInstance().random.nextInt(array.length);
        return array[index];
    }

    public static <T> T[] getRandomElementsFromArray(T[] array, int count) {
        if (count > array.length) {
            throw new IllegalArgumentException("Count cannot be greater than array length");
        }

        @SuppressWarnings("unchecked")
        T[] result = (T[]) new Object[count];

        for (int i = 0; i < count; i++) {
            int index = getInstance().random.nextInt(array.length - i);
            result[i] = array[index];
            array[index] = array[array.length - i - 1];
        }

        return result;
    }
}