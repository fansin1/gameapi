package org.fansin.gameapi;

import java.util.List;

public enum UnitFeatures implements UnitFeature {
    SHOOTER {
        @Override
        public RebuffState canRebuff(RebuffState rebuffState, Unit enemy) {
            return RebuffState.CANT;
        }

        @Override
        public RebuffState enemyCanRebuff(RebuffState rebuffState,Unit enemy) {
            return RebuffState.CANT;
        }
    },

    HITEVERYONE {
        @Override
        public List<BattleUnitStack> changeTargets(Battle battle, List<BattleUnitStack> enemies) {
            return battle.currentEnemyArmy().getStacks();
        }
    },

    UNDEAD {},

    ACCURATESHOT {
        @Override
        public int enemyDefenceChange(int defence, Unit enemy) {
            return 0;
        }
    },

    ENEMYNORESIST {
        @Override
        public RebuffState enemyCanRebuff(RebuffState rebuffState, Unit enemy) {
            return RebuffState.CANT;
        }
    },
    ENDLESSREBUFF {
        @Override
        public RebuffState canRebuff(RebuffState rebuffState, Unit enemy) {
            return RebuffState.MUST;
        }
    }
}
