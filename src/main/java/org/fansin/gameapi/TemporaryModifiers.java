package org.fansin.gameapi;

public enum TemporaryModifiers implements TemporaryModifier {

    WEAKENING(1) {
        @Override
        public int defenceChange(int defence, Unit actor, Unit target) {
            return Math.max(defence - 12, 0);
        }
    },

    CURSE(1) {
        @Override
        public int attackChange(int attack, Unit actor, Unit target) {
            return Math.max(attack - 12, 0);
        }
    },

    ACCELERATION(1) {
        @Override
        public double initiativeChange(double initiative) {
            return initiative * 1.4d;
        }
    },

    PUNISHINGBLOW(1) {
        @Override
        public int attackChange(int attack, Unit actor, Unit target) {
            return attack + 12;
        }
    },
    DEFEND(1) {
        @Override
        public int defenceChange(int defence, Unit actor, Unit target) {
            return defence + defence / 3;
        }
    };

    private int rounds;

    TemporaryModifiers(int rounds) {
        this.rounds = rounds;
    }

    @Override
    public void endRound() {
        rounds--;
    }

    @Override
    public int getRounds() {
        return rounds;
    }

    @Override
    public boolean isWorking() {
        return rounds > 0;
    }

}
