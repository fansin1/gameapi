package org.fansin.gameapi;

import java.util.*;

public class Barracks {

    private Map<String, Unit> mUnits = new HashMap<>();
    private static Barracks sInstance;

    private Barracks() { }

    public static Barracks getInstance() {
        if (sInstance == null)
            sInstance = new Barracks();

        return sInstance;
    }

    public void registerUnit(Unit unit) {
        mUnits.put(unit.getName(), unit);
    }

    public Unit getUnit(String unitName) {
        return mUnits.get(unitName);
    }

    public Iterator<String> getAllUnitsNames() {
        return mUnits.keySet().iterator();
    }

    public Iterator<Unit> getAllUnits() {
        return mUnits.values().iterator();
    }

}
