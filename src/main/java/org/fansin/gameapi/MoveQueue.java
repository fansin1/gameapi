package org.fansin.gameapi;

import java.util.List;

public class MoveQueue {

    private List<BattleUnitStack> stacks;
    private int move = 0;

    public MoveQueue(List<BattleUnitStack> stacks) {
        this.stacks = stacks;
    }

    public void nextMove() {
        stacks.removeIf(stack -> !stack.isAlive());

        if (move >= stacks.size()) {
            move = 0;
        }
    }

    public void currentStack() {

    }

}
