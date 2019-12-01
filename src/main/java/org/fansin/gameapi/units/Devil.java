package org.fansin.gameapi.units;

import org.fansin.gameapi.Damage;
import org.fansin.gameapi.Skills;
import org.fansin.gameapi.Unit;

import java.util.Collections;

public class Devil extends Unit {
    public Devil() {
        super(
                "Devil",
                new Damage(36, 66),
                Collections.emptyList(),
                Collections.singletonList(Skills.WEAKENING),
                27, 166, 25, 11);
    }
}
