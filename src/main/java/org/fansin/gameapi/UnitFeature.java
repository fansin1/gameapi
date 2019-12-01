package org.fansin.gameapi;

import java.util.Collections;
import java.util.List;

public interface UnitFeature {

    default List<BattleUnitStack> changeTargets(Battle battle, List<BattleUnitStack> enemies) {
        return enemies;
    }

    default RebuffState canRebuff(RebuffState rebuffState, Unit enemy) {
        return RebuffState.STANDARD;
    }

    default int defenceChange(int defence, Unit enemy) {
        return defence;
    }

    default int attackChange(int attack, Unit enemy) {
        return attack;
    }

    default int damageChange(int damage, Unit enemy) {
        return damage;
    }

    default double initiativeChange(double initiative) {
        return initiative;
    }


    default RebuffState enemyCanRebuff(RebuffState rebuffState, Unit enemy) {
        return RebuffState.STANDARD;
    }

    default int enemyDefenceChange(int defence, Unit enemy) {
        return defence;
    }

    default int enemyAttackChange(int attack, Unit enemy) {
        return attack;
    }

    default int enemyDamageChange(int damage, Unit enemy) {
        return damage;
    }

}
