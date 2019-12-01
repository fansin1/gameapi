package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.SkipException;
import java.util.*;

public class MoveQueue {

    private List<BattleUnitStack> mCurrentRound;
    private List<BattleUnitStack> mNextRound;
    private boolean mSkipped = false;
    private int mSkippedCount = 0;

    public MoveQueue(List<BattleUnitStack> stacks) {
        mCurrentRound = new ArrayList<>(stacks);
        mNextRound = new ArrayList<>(stacks);

        updateOrder();
    }

    public MoveQueue(BattleArmy first, BattleArmy second) {
        mCurrentRound = new ArrayList<>(first.getStacks());
        mCurrentRound.addAll(second.getStacks());
        mNextRound = new ArrayList<>(mCurrentRound);
        updateOrder();
    }

    private void updateOrder() {
        if (mCurrentRound.size() >= mSkippedCount) {
            mCurrentRound.subList(0, mCurrentRound.size() - mSkippedCount)
                    .sort(Comparator.comparingDouble(BattleUnitStack::calculateInitiative).reversed());
        }

        mNextRound.sort(Comparator.comparingDouble(BattleUnitStack::calculateInitiativeOnNextRound).reversed());
    }

    private void startNewRound() {
        mCurrentRound.addAll(mNextRound);
    }

    private void deleteDead() {
        mCurrentRound.removeIf(stack -> !stack.isAlive());
        mNextRound.removeIf(stack -> !stack.isAlive());
    }

    public void nextMove() {
        deleteDead();

        if (mSkipped) {
            BattleUnitStack tmp = mCurrentRound.get(0);
            mCurrentRound.remove(0);
            mCurrentRound.add(tmp);
            mSkippedCount++;
        } else {
            mCurrentRound.remove(0);
        }

        if (mCurrentRound.isEmpty()) {
            startNewRound();
        }

        mSkipped = false;
        updateOrder();
    }

    public void skip() {
        if (!canSkip())
            throw new SkipException();

        mSkipped = true;
    }

    public boolean canSkip() {
        return mSkippedCount < mCurrentRound.size();
    }

    public void addStack(BattleUnitStack bus) {
        mCurrentRound.add(bus);
        mNextRound.add(bus);
    }

    public BattleUnitStack currentStack() {
        return mCurrentRound.get(0);
    }

    public List<BattleUnitStack> getQueueCurrentRound() {
        return Collections.unmodifiableList(mCurrentRound);
    }

    public List<BattleUnitStack> getQueueNextRound() {
        return Collections.unmodifiableList(mNextRound);
    }

    public int size() {
        return mCurrentRound.size();
    }

}
