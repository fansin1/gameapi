package org.fansin.gameapi.units;

import org.fansin.gameapi.*;

import java.util.Collections;

public class Griffin extends Unit {
    public Griffin() {
        super(UnitTypes.GRIFFIN,
                "Griffin",
                new Damage(5, 10),
                Collections.singletonList(UnitFeatures.ENDLESSREBUFF),
                Collections.emptyList(),
                7, 30, 5, 15);
    }
}
