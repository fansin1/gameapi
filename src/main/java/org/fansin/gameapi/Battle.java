package org.fansin.gameapi;

public class Battle {

    private BattleArmy mFirstArmy;
    private BattleArmy mSecondArmy;

    private int round = 1;

    private MoveQueue moveQueue;

    public Battle(BattleArmy firstArmy, BattleArmy secondArmy) {
        this.mFirstArmy = firstArmy;
        this.mSecondArmy = secondArmy;
        moveQueue = new MoveQueue(firstArmy, secondArmy);
    }

    public boolean isStackInFirstArmy(BattleUnitStack stack) {
        for (BattleUnitStack bus : mFirstArmy.getStacks()) {
            if (bus == stack) {
                return true;
            }
        }

        return false;
    }

    public BattleArmy currentArmy() {
        if (isStackInFirstArmy(moveQueue.currentStack()))
            return mFirstArmy;
        else
            return mSecondArmy;
    }

    public BattleArmy currentEnemyArmy() {
        if (isStackInFirstArmy(moveQueue.currentStack()))
            return mSecondArmy;
        else
            return mFirstArmy;
    }

    private void nextMove() {
        if (moveQueue.size() == 1) {
            round++;
            mFirstArmy.endRound();
            mSecondArmy.endRound();
        }

        moveQueue.nextMove();
    }

    public void attack(BattleUnitStack enemy) {
        moveQueue.currentStack().attack(this, enemy);
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
