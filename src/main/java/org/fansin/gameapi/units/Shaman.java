package org.fansin.gameapi.units;

import org.fansin.gameapi.*;

import java.util.Collections;

public class Shaman extends Unit {
    public Shaman() {
        super(UnitTypes.SHAMAN,
                "Shaman",
                new Damage(7, 12),
                Collections.emptyList(),
                Collections.singletonList(Skills.ACCELERATION),
                12, 40, 10, 10.5);
    }
}
