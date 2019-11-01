import org.fansin.gameapi.BattleUnitStack;
import org.fansin.gameapi.TemporaryModifiers;
import org.fansin.gameapi.UnitStack;
import org.fansin.gameapi.units.Angel;
import org.fansin.gameapi.units.Devil;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class BattleUnitStackTest {

    @Test
    void AttackTest() {
        BattleUnitStack angleBUS = new BattleUnitStack(new UnitStack(new Angel(), 14));
        BattleUnitStack devilBUS = new BattleUnitStack(new UnitStack(new Devil(), 18));

        assertEquals(166, devilBUS.getLastUnitHipPoints());
        assertEquals(18, devilBUS.getCount());

        angleBUS.attack(devilBUS);

        assertEquals(137, devilBUS.getLastUnitHipPoints());
        assertEquals(14, devilBUS.getCount());
    }

    @Test
    void SkillTest() {
        BattleUnitStack angleBUS = new BattleUnitStack(new UnitStack(new Angel(), 14));

        angleBUS.useSkill(null, 0, angleBUS);

        assertEquals(1, angleBUS.getModifiers().size());
        assertEquals(TemporaryModifiers.PUNISHINGBLOW, angleBUS.getModifiers().get(0));
    }

    @Test
    void TemporaryModifierTest() {
        BattleUnitStack angleBUS = new BattleUnitStack(new UnitStack(new Angel(), 14));
        BattleUnitStack devilBUS = new BattleUnitStack(new UnitStack(new Devil(), 18));

        angleBUS.useSkill(null, 0, angleBUS);

        assertEquals(166, devilBUS.getLastUnitHipPoints());
        assertEquals(18, devilBUS.getCount());

        angleBUS.attack(devilBUS);

        assertEquals(91, devilBUS.getLastUnitHipPoints());
        assertEquals(12, devilBUS.getCount());
    }
}
