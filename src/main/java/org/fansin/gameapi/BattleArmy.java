package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.TooManyStacksException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BattleArmy {

    private List<BattleUnitStack> stacks;

    public BattleArmy(List<BattleUnitStack> stacks) {
        if (stacks.size() > 6)
            throw new TooManyStacksException();

        this.stacks = stacks;
    }

    public BattleArmy(Army army) {
        stacks = new ArrayList<>();

        for (UnitStack stack : army.getStacks()) {
            stacks.add(new BattleUnitStack(stack));
        }

        if (stacks.size() > 6)
            throw new TooManyStacksException();
    }

    public void addStack(BattleUnitStack stack) {
        stacks.add(stack);
        if (stacks.size() > 9)
            throw new TooManyStacksException();
    }

    public List<BattleUnitStack> getStacks() {
        return Collections.unmodifiableList(stacks);
    }

    public void endRound() {
        for (BattleUnitStack bus : stacks) {
            bus.endRound();
        }
    }
}
