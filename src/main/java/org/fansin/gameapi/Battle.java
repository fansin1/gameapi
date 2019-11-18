package org.fansin.gameapi;

public class Battle {

    private BattleArmy firstArmy;
    private BattleArmy secondArmy;

    private int round = 1;

    private MoveQueue moveQueue;

    public Battle(BattleArmy firstArmy, BattleArmy secondArmy) {
        this.firstArmy = firstArmy;
        this.secondArmy = secondArmy;
        moveQueue = new MoveQueue(firstArmy, secondArmy);
    }

    public boolean isStackInFirstArmy(BattleUnitStack stack) {
        for (BattleUnitStack bus : firstArmy.getStacks()) {
            if (bus == stack) {
                return true;
            }
        }

        return false;
    }

    public BattleArmy currentArmy() {
        if (isStackInFirstArmy(moveQueue.currentStack()))
            return firstArmy;
        else
            return secondArmy;
    }

    public BattleArmy currentEnemyArmy() {
        if (isStackInFirstArmy(moveQueue.currentStack()))
            return secondArmy;
        else
            return firstArmy;
    }

    private void nextMove() {
        if (moveQueue.size() == 1) {
            round++;
            firstArmy.endRound();
            secondArmy.endRound();
        }

        moveQueue.nextMove();
    }

    public void attack(BattleUnitStack enemy) {
        moveQueue.currentStack().attack(enemy);
        nextMove();
    }

    public void useSkill(int skillIndex, BattleUnitStack... targets) {
        moveQueue.currentStack().useSkill(this, skillIndex, targets);
        nextMove();
    }

    public void defend() {
        moveQueue.currentStack().defend();
        nextMove();
    }

    public void skip() {
        moveQueue.skip();
        nextMove();
    }

    public int getRound() {
        return round;
    }

    public MoveQueue getMoveQueue() {
        return moveQueue;
    }

}
