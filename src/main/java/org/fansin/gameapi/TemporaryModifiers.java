package org.fansin.gameapi;

public enum TemporaryModifiers implements TemporaryModifier {

    WEAKENING(1),

    CURSE(1),

    BOOST(1),

    PUNISHINGBLOW(1) {
        @Override
        public int attackChange(int attack, Unit actor, Unit enemy) {
            return attack + 12;
        }
    },
    DEFEND(1) {
        @Override
        public int defenceChange(int defence, Unit actor, Unit enemy) {
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
