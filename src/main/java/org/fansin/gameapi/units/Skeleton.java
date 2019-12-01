package org.fansin.gameapi.units;

import org.fansin.gameapi.Damage;
import org.fansin.gameapi.Unit;
import org.fansin.gameapi.UnitFeatures;

import java.util.Collections;

public class Skeleton extends Unit {

    public Skeleton() {
        super(
                "Skeleton",
                new Damage(1, 1),
                Collections.singletonList(UnitFeatures.UNDEAD),
                Collections.emptyList(),
                1, 5, 2, 10);
    }
}
