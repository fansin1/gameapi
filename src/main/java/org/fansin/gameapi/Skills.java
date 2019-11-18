package org.fansin.gameapi;

public enum Skills implements Skill {
    PUNISHINGBLOW {
        @Override
        public void cast(Battle battle, BattleUnitStack actor, BattleUnitStack... targets) {
            super.cast(battle, actor, targets);
            for (BattleUnitStack bus : targets) {
                bus.addModifier(TemporaryModifiers.PUNISHINGBLOW);
            }
        }
    },

    CURSE {
        @Override
        public void cast(Battle battle, BattleUnitStack actor, BattleUnitStack... targets) {
            super.cast(battle, actor, targets);
            for (BattleUnitStack bus : targets) {
                bus.addModifier(TemporaryModifiers.CURSE);
            }
        }
    },

    WEAKENING {
        @Override
        public void cast(Battle battle, BattleUnitStack actor, BattleUnitStack... targets) {
            super.cast(battle, actor, targets);
            for (BattleUnitStack bus : targets) {
                bus.addModifier(TemporaryModifiers.WEAKENING);
            }
        }
    },

    ACCELERATION {
        @Override
        public void cast(Battle battle, BattleUnitStack actor, BattleUnitStack... targets) {
            super.cast(battle, actor, targets);
            for (BattleUnitStack bus : targets) {
                bus.addModifier(TemporaryModifiers.ACCELERATION);
            }
        }
    },

    RESURRECTION {

        private static final int HIT_POINT_PER_UNIT = 100;

        @Override
        public boolean canBeCastedOn(BattleUnitStack target) {
            for (UnitFeature uf : target.unit.getFeatures()) {
                if (uf == UnitFeatures.UNDEAD) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public void cast(Battle battle, BattleUnitStack actor, BattleUnitStack... targets) {
            super.cast(battle, actor, targets);
            for (BattleUnitStack bus : targets) {
                bus.healBy(HIT_POINT_PER_UNIT * actor.getCount());
            }
        }
    };
}
