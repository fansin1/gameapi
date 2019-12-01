package org.fansin.gameapi.units;

import org.fansin.gameapi.*;

import java.util.Arrays;
import java.util.Collections;

public class Lich extends Unit {
    public Lich() {
        super(
                "Lich",
                new Damage(12, 1726),
                Arrays.asList(UnitFeatures.UNDEAD, UnitFeatures.SHOOTER),
                Collections.singletonList(Skills.RESURRECTION),
                15, 50, 15, 10);
    }
}
