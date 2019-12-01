package org.fansin.gameapi;

public class UnitStack {

    protected Unit mUnit;
    protected int mCount;

    public UnitStack(Unit unit, int count) {
        this.mUnit = unit;
        this.mCount = count;
    }

    public boolean isAlive() {
        return mCount > 0;
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

}
