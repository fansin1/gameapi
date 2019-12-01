package org.fansin.gameapi.units;

import org.fansin.gameapi.*;

import java.util.Collections;

public class BoneDragon extends Unit {
    public BoneDragon() {
        super(
                "Bone Dragon",
                new Damage(15, 30),
                Collections.singletonList(UnitFeatures.UNDEAD),
                Collections.singletonList(Skills.CURSE),
                27, 150, 28, 11);
    }
}
