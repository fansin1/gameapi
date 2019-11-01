package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.TooManyStacksException;
import java.util.Collections;
import java.util.List;

public final class Army {

    private List<UnitStack> stacks;

    public Army(List<UnitStack> stacks) {
        if (stacks.size() > 6)
            throw new TooManyStacksException();

        this.stacks = stacks;
    }

    public Army() {}

    public void addStack(UnitStack stack) {
        stacks.add(stack);
        if (stacks.size() > 6)
            throw new TooManyStacksException();
    }

    public int size() {
        return stacks.size();
    }

    public List<UnitStack> getStacks() {
        return Collections.unmodifiableList(stacks);
    }
}
