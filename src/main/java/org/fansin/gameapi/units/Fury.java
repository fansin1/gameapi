package org.fansin.gameapi.units;

import org.fansin.gameapi.Damage;
import org.fansin.gameapi.Unit;
import org.fansin.gameapi.UnitFeatures;
import org.fansin.gameapi.UnitTypes;

import java.util.Collections;

public class Fury extends Unit {

    public Fury() {
        super(UnitTypes.FURY,
                "Fury",
                new Damage(5, 7),
                Collections.singletonList(UnitFeatures.ENEMYNORESIST),
                Collections.emptyList(),
                5, 16, 3, 16);
    }
}
