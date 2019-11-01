package org.fansin.gameapi;

import java.util.Random;

public final class Damage {

    private int minDamage;
    private int maxDamage;
    private Random random = new Random();

    public int getMinDamage() {
        return minDamage;
    }

    public int getMaxDamage() {
        return maxDamage;
    }

    public int getRandom() {
        if (maxDamage == minDamage) return maxDamage;
        return minDamage + random.nextInt(maxDamage - minDamage);
    }

    public Damage(int minDamage, int maxDamage) {
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }
}
