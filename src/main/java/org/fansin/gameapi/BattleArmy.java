package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.TooManyStacksException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleArmy {

    private List<BattleUnitStack> mStacks;

    public BattleArmy(Army army) {
        mStacks = new ArrayList<>();

        for (UnitStack stack : army.getStacks()) {
            mStacks.add(new BattleUnitStack(stack));
        }

        if (mStacks.size() > 6)
            throw new TooManyStacksException();
    }

    public void addStack(BattleUnitStack stack) {
        mStacks.add(stack);
        if (mStacks.size() > 9)
            throw new TooManyStacksException();
    }

    public List<BattleUnitStack> getStacks() {
        return Collections.unmodifiableList(mStacks);
    }

    public void endRound() {
        for (BattleUnitStack bus : mStacks) {
            bus.endRound();
        }
    }
}
