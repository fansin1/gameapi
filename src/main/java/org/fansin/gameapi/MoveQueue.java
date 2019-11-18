package org.fansin.gameapi;

import org.fansin.gameapi.exceptions.SkipException;
import java.util.*;

public class MoveQueue {

    private List<BattleUnitStack> currentRound;
    private List<BattleUnitStack> nextRound;
    private boolean skipped = false;
    private int skippedCount = 0;

    public MoveQueue(List<BattleUnitStack> stacks) {
        currentRound = new ArrayList<>(stacks);
        nextRound = new ArrayList<>(stacks);

        updateOrder();
    }

    public MoveQueue(BattleArmy first, BattleArmy second) {
        currentRound = new ArrayList<>(first.getStacks());
        currentRound.addAll(second.getStacks());
        nextRound = new ArrayList<>(currentRound);
        updateOrder();
    }

    private void updateOrder() {
        if (currentRound.size() >= skippedCount) {
            currentRound.subList(0, currentRound.size() - skippedCount)
                    .sort(Comparator.comparingDouble(BattleUnitStack::calculateInitiative).reversed());
        }

        nextRound.sort(Comparator.comparingDouble(BattleUnitStack::calculateInitiativeOnNextRound).reversed());
    }

    private void startNewRound() {
        currentRound.addAll(nextRound);
    }

    private void deleteDead() {
        currentRound.removeIf(stack -> !stack.isAlive());
        nextRound.removeIf(stack -> !stack.isAlive());
    }

    public void nextMove() {
        deleteDead();

        if (skipped) {
            BattleUnitStack tmp = currentRound.get(0);
            currentRound.remove(0);
            currentRound.add(tmp);
            skippedCount++;
        } else {
            currentRound.remove(0);
        }

        if (currentRound.isEmpty()) {
            startNewRound();
        }

        skipped = false;
        updateOrder();
    }

    public void skip() {
        if (!canSkip())
            throw new SkipException();

        skipped = true;
    }

    public boolean canSkip() {
        return skippedCount < currentRound.size();
    }

    public void addStack(BattleUnitStack bus) {
        currentRound.add(bus);
        nextRound.add(bus);
    }

    public BattleUnitStack currentStack() {
        return currentRound.get(0);
    }

    public List<BattleUnitStack> getQueueCurrentRound() {
        return Collections.unmodifiableList(currentRound);
    }

    public List<BattleUnitStack> getQueueNextRound() {
        return Collections.unmodifiableList(nextRound);
    }

    public int size() {
        return currentRound.size();
    }

}
