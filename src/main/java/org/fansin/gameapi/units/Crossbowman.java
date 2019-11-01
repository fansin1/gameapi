package org.fansin.gameapi.units;

import org.fansin.gameapi.*;
import java.util.Arrays;
import java.util.Collections;

public class Crossbowman extends Unit {
    public Crossbowman() {
        super(UnitTypes.CROSSBOWMAN,
                "Crossbowman",
                new Damage(2, 8),
                Arrays.asList(UnitFeatures.SHOOTER, UnitFeatures.ACCURATESHOT),
                Collections.emptyList(),
                4, 10, 4, 8);
    }
}
