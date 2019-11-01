package org.fansin.gameapi;

public class UnitStack {

    protected Unit unit;
    protected int count;

    public UnitStack(Unit unit, int count) {
        this.unit = unit;
        this.count = count;
    }

    public boolean isAlive() {
        return count > 0;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
