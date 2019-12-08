package org.fansin.gameapi;

import org.fansin.gameapi.units.*;

import java.util.*;

public class Barracks {

    private Map<String, Unit> mUnits = new HashMap<>();
    private static Barracks sInstance;

    private Barracks() {
        registerUnit(new Angel());
        registerUnit(new BoneDragon());
        registerUnit(new Crossbowman());
        registerUnit(new Cyclops());
        registerUnit(new Devil());
        registerUnit(new Fury());
        registerUnit(new Griffin());
        registerUnit(new Hydra());
        registerUnit(new Lich());
        registerUnit(new Shaman());
        registerUnit(new Skeleton());
    }

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
