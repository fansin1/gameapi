package org.fansin.gameapi.units;

import org.fansin.gameapi.Damage;
import org.fansin.gameapi.Unit;
import org.fansin.gameapi.UnitFeatures;

import java.util.Collections;

public class Cyclops extends Unit {
    public Cyclops() {
        super(
                "Cyclops",
                new Damage(18, 26),
                Collections.singletonList(UnitFeatures.SHOOTER),
                Collections.emptyList(),
                20, 85, 15, 10);
    }
}
