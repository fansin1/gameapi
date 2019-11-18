import org.fansin.gameapi.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;

//Integration test for TemporaryModifier and BattleUnitStack
class TemporaryModifiersTest {

    private Unit testUnit;

    private Unit getTestUnit() {
        if (testUnit == null)
            testUnit = new Unit( () ->
                    "Test", "Test", new Damage(1, 1),
                    Collections.emptyList(), Collections.emptyList(), 10, 10, 10, 10
            );

        return testUnit;
    }

    private interface TestTemporaryModifier extends TemporaryModifier {
        @Override
        default void endRound() {}

        @Override
        default boolean isWorking() {
            return true;
        }

        @Override
        default int getRounds() {
            return 2;
        }
    }

    private BattleUnitStack createTestBUS() {
        return new BattleUnitStack(new UnitStack(getTestUnit(), 10));
    }

    private int calculateAllHp(BattleUnitStack bus) {
        if (bus.getCount() == 0) return 0;
        return bus.getLastUnitHipPoints() + getTestUnit().getHitPoints() * (bus.getCount() - 1);
    }

    @Test
    void canRebuffTestCANT() {
        BattleUnitStack bus1 = createTestBUS();
        BattleUnitStack bus2 = createTestBUS();

        bus1.addModifier(new TestTemporaryModifier() {
            @Override
            public RebuffState canRebuff(RebuffState rebuffState, Unit actor, Unit enemy) {
                return RebuffState.CANT;
            }
        });

        bus2.attack(bus1);
        assertEquals(getTestUnit().getHitPoints() * 10, calculateAllHp(bus2));
    }

    @Test
    void canRebuffTestMUST() {
        BattleUnitStack bus1 = createTestBUS();
        BattleUnitStack bus2 = createTestBUS();

        bus1.addModifier(new TestTemporaryModifier() {
            @Override
            public RebuffState canRebuff(RebuffState rebuffState, Unit actor, Unit enemy) {
                return RebuffState.MUST;
            }
        });

        bus2.attack(bus1);
        int hpAfterFirst = calculateAllHp(bus2);
        bus2.attack(bus1);
        assertNotEquals(hpAfterFirst, calculateAllHp(bus2));
    }

    @Test
    void canRebuffTestSTANDARD() {
        BattleUnitStack bus1 = createTestBUS();
        BattleUnitStack bus2 = createTestBUS();

        bus1.addModifier(new TestTemporaryModifier() {
            @Override
            public RebuffState canRebuff(RebuffState rebuffState, Unit actor, Unit enemy) {
                return RebuffState.STANDARD;
            }
        });

        bus2.attack(bus1);
        int hpAfterFirst = calculateAllHp(bus2);
        bus2.attack(bus1);
        assertEquals(hpAfterFirst, calculateAllHp(bus2));
    }

    private void defenceChangeTest(int newDefence, int hpAll) {
        BattleUnitStack bus1 = createTestBUS();
        BattleUnitStack bus2 = createTestBUS();

        bus1.addModifier(new TestTemporaryModifier() {
            @Override
            public int defenceChange(int defence, Unit actor, Unit enemy) {
                return newDefence;
            }
        });

        bus2.attack(bus1);
        assertEquals(hpAll, calculateAllHp(bus1));
    }

    @Test
    void defenceChangeTestLess() {
        defenceChangeTest(0, 85);
    }

    @Test
    void defenceChangeTestMore() {
        defenceChangeTest(20, 93);
    }

    private void attackChangeTest(int newAttack, int hpAll) {
        BattleUnitStack bus1 = createTestBUS();
        BattleUnitStack bus2 = createTestBUS();

        bus2.addModifier(new TestTemporaryModifier() {
            @Override
            public int attackChange(int attack, Unit actor, Unit enemy) {
                return newAttack;
            }
        });

        bus2.attack(bus1);
        assertEquals(hpAll, calculateAllHp(bus1));
    }

    @Test
    void attackChangeTestLess() {
        attackChangeTest(0, 93);
    }

    @Test
    void attackChangeTestEqual() {
        attackChangeTest(10, 90);
    }

    @Test
    void attackChangeTestMore() {
        attackChangeTest(20, 85);
    }

    private void damageChangeTest(int newDamage, int hpAll) {
        BattleUnitStack bus1 = createTestBUS();
        BattleUnitStack bus2 = createTestBUS();

        bus2.addModifier(new TestTemporaryModifier() {
            @Override
            public int damageChange(int damage, Unit actor, Unit enemy) {
                return newDamage;
            }
        });

        bus2.attack(bus1);
        assertEquals(hpAll, calculateAllHp(bus1));
    }

    @Test
    void damageChangeTestLess() {
        damageChangeTest(0, 100);
    }

    @Test
    void damageChangeTestEqual() {
        damageChangeTest(1, 90);
    }

    @Test
    void damageChangeTestMore() {
        damageChangeTest(10, 0);
    }

}
