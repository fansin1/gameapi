package org.fansin.gameapi;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Battle {

    private BattleArmy first;
    private BattleArmy second;

    private int move = 0;
    private int round = 1;

    private List<BattleUnitStack> moveQueue = new ArrayList<>();

    public Battle(BattleArmy first, BattleArmy second) {
        this.first = first;
        this.second = second;

        moveQueue.addAll(first.getStacks());
        moveQueue.addAll(second.getStacks());
        moveQueue.sort(Comparator.comparingDouble(BattleUnitStack::calculateInitiative));
    }

    public boolean isStackInFirstArmy(BattleUnitStack stack) {
        for (BattleUnitStack bus : first.getStacks()) {
            if (bus == stack) {
                return true;
            }
        }

        return false;
    }

    public BattleArmy currentArmy() {
        if (isStackInFirstArmy(currentStack()))
            return first;
        else
            return second;
    }

    public BattleArmy currentEnemyArmy() {
        if (isStackInFirstArmy(currentStack()))
            return second;
        else
            return first;
    }

    public void nextMove() {
        move++;
        if (move == moveQueue.size()) {
            move = 0;
            round++;
            first.endRound();
            second.endRound();
        }
    }

    public BattleUnitStack currentStack() {
        return moveQueue.get(move);
    }

    public int getRound() {
        return round;
    }
}
