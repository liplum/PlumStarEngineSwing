package net.liplum.util;

import java.util.Random;

public class RandomGenerator {

    public static Random random = new Random();

    public static void initRandom() {
        random = new Random();
    }

    public static void initRandom(long seed) {
        random = new Random(seed);
    }

    public static int getIntBetween(int min, int max) {
        return random.nextInt(max - min) + min;
    }
}
