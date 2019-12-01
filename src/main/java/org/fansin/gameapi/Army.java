package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.TooManyStacksException;
import java.util.Collections;
import java.util.List;

public final class Army {

    private List<UnitStack> mStacks;

    public Army(List<UnitStack> stacks) {
        if (stacks.size() > 6)
            throw new TooManyStacksException();

        this.mStacks = stacks;
    }

    public void addStack(UnitStack stack) {
        mStacks.add(stack);
        if (mStacks.size() > 6)
            throw new TooManyStacksException();
    }

    public int size() {
        return mStacks.size();
    }

    public List<UnitStack> getStacks() {
        return Collections.unmodifiableList(mStacks);
    }
}
