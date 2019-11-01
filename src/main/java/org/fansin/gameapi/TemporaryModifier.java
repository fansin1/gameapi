package org.fansin.gameapi;

public interface TemporaryModifier {

    default RebuffState canRebuff(RebuffState rebuffState, Unit actor, Unit enemy) {
        return RebuffState.STANDARD;
    }

    default int defenceChange(int defence, Unit actor, Unit enemy) {
        return defence;
    }

    default int attackChange(int attack, Unit actor, Unit enemy) {
        return attack;
    }

    default int damageChange(int damage, Unit actor, Unit enemy) {
        return damage;
    }

    default double initiativeChange(double initiative) {
        return initiative;
    }

    default RebuffState enemyCanRebuff(RebuffState rebuffState, Unit actor, Unit enemy) {
        return RebuffState.STANDARD;
    }

    default int enemyDefenceChange(int defence, Unit actor, Unit enemy) {
        return defence;
    }

    default int enemyAttackChange(int attack, Unit actor, Unit enemy) {
        return attack;
    }

    default int enemyDamageChange(int damage, Unit actor, Unit enemy) {
        return damage;
    }

    void endRound();

    boolean isWorking();

    int getRounds();

}
