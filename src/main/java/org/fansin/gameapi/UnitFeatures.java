package org.fansin.gameapi;

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
    HITEVERYONE,
    ENDLESSREBUFF {
        @Override
        public RebuffState canRebuff(RebuffState rebuffState, Unit enemy) {
            return RebuffState.MUST;
        }
    };
}
