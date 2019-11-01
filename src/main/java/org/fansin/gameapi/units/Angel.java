package org.fansin.gameapi.units;

import org.fansin.gameapi.Damage;
import org.fansin.gameapi.Skills;
import org.fansin.gameapi.Unit;
import org.fansin.gameapi.UnitTypes;

import java.util.Collections;

public class Angel extends Unit {
    public Angel() {
        super(UnitTypes.ANGEL,
                "Angel",
                new Damage(45, 45),
                Collections.emptyList(),
                Collections.singletonList(Skills.PUNISHINGBLOW),
                27, 180, 27, 11);
    }
}
