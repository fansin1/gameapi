package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.CastedOnWrongTargetException;
import org.fansin.gameapi.exceptions.TooFewTargetsException;
import org.fansin.gameapi.exceptions.TooManyTargetsException;

public interface Skill {

    default int getTargetsCount() {
        return 1;
    }

    default boolean canBeCastedOn(BattleUnitStack target) {
        return true;
    }

    default void cast(Battle battle, BattleUnitStack actor, BattleUnitStack... targets) {
        if (targets.length > getTargetsCount())
            throw new TooManyTargetsException();
        if (targets.length < getTargetsCount())
            throw new TooFewTargetsException();

        for (BattleUnitStack bus : targets) {
            if (!canBeCastedOn(bus))
                throw new CastedOnWrongTargetException();
        }
    }

}
