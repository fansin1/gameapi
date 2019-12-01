package org.fansin.gameapi;

import java.util.Random;

public final class Damage {

    private int mMinDamage;
    private int mMaxDamage;
    private Random mRandom = new Random();

    public int getMinDamage() {
        return mMinDamage;
    }

    public int getMaxDamage() {
        return mMaxDamage;
    }

    public int getRandom() {
        if (mMaxDamage == mMinDamage) return mMaxDamage;
        return mMinDamage + mRandom.nextInt(mMaxDamage - mMinDamage);
    }

    public Damage(int minDamage, int maxDamage) {
        this.mMinDamage = minDamage;
        this.mMaxDamage = maxDamage;
    }
}
