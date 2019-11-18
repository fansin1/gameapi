package org.fansin.gameapi;

import java.util.HashMap;
import java.util.Map;

public class Barracks {

    private Map<Class<?>, Unit> units = new HashMap<>();
    private static Barracks instance;

    private Barracks() { }

    public static Barracks getInstance() {
        if (instance == null)
            instance = new Barracks();

        return instance;
    }

    public void registerUnit(Unit unit) {
        units.put(unit.getClass(), unit);
    }

    public Unit getUnit(Class<? extends Unit> unitClass) {
        return units.get(unitClass);
    }

}
