package org.fansin.gameapi;

import java.util.List;

public interface TemporaryModifier {

    default List<BattleUnitStack> changeTargets(Battle battle, List<BattleUnitStack> enemies) {
        return enemies;
    }

    default RebuffState canRebuff(RebuffState rebuffState, Unit actor, Unit target) {
        return RebuffState.STANDARD;
    }

    default int defenceChange(int defence, Unit actor, Unit target) {
        return defence;
    }

    default int attackChange(int attack, Unit actor, Unit target) {
        return attack;
    }

    default int damageChange(int damage, Unit actor, Unit target) {
        return damage;
    }

    default double initiativeChange(double initiative) {
        return initiative;
    }

    default RebuffState enemyCanRebuff(RebuffState rebuffState, Unit actor, Unit target) {
        return RebuffState.STANDARD;
    }

    default int enemyDefenceChange(int defence, Unit actor, Unit target) {
        return defence;
    }

    default int enemyAttackChange(int attack, Unit actor, Unit target) {
        return attack;
    }

    default int enemyDamageChange(int damage, Unit actor, Unit target) {
        return damage;
    }

    void endRound();

    boolean isWorking();

    int getRounds();

}
