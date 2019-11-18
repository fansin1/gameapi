package org.fansin.gameapi.units;

import org.fansin.gameapi.Damage;
import org.fansin.gameapi.Unit;
import org.fansin.gameapi.UnitFeatures;
import org.fansin.gameapi.UnitTypes;

import java.util.Arrays;
import java.util.Collections;

public class Hydra extends Unit {
    public Hydra() {
        super(UnitTypes.HYDRA,
                "Hydra",
                new Damage(7, 14),
                Arrays.asList(/*UnitFeatures.HITEVERYONE, */UnitFeatures.ENEMYNORESIST),
                Collections.emptyList(),
                15, 80, 12, 7);
    }
}
