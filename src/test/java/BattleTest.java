import org.fansin.gameapi.*;
import org.fansin.gameapi.units.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

public class BattleTest {

    private void fillFirstUnitStack(List<UnitStack> unitStacks) {
        Barracks barracks = Barracks.getInstance();
        unitStacks.add(new UnitStack(barracks.getUnit(Angel.class), 1));
        unitStacks.add(new UnitStack(barracks.getUnit(Crossbowman.class), 3));
        unitStacks.add(new UnitStack(barracks.getUnit(Fury.class), 2));
    }

    private void fillSecondUnitStack(List<UnitStack> unitStacks) {
        Barracks barracks = Barracks.getInstance();
        unitStacks.add(new UnitStack(barracks.getUnit(Devil.class), 1));
        unitStacks.add(new UnitStack(barracks.getUnit(Cyclops.class), 3));
        unitStacks.add(new UnitStack(barracks.getUnit(Hydra.class), 2));
    }

    private void printHPInfo(BattleUnitStack battleUnitStack) {
        System.out.println(battleUnitStack.getUnit().getName() + " last unit HP " +
                battleUnitStack.getLastUnitHipPoints() + "      " +
                battleUnitStack.getUnit().getName() + "'s count " + battleUnitStack.getCount());
    }

    @BeforeAll
    public static void fillBarracks() {
        Barracks barracks = Barracks.getInstance();
        barracks.registerUnit(new Angel());
        barracks.registerUnit(new BoneDragon());
        barracks.registerUnit(new Crossbowman());
        barracks.registerUnit(new Cyclops());
        barracks.registerUnit(new Devil());
        barracks.registerUnit(new Fury());
        barracks.registerUnit(new Griffin());
        barracks.registerUnit(new Hydra());
        barracks.registerUnit(new Lich());
        barracks.registerUnit(new Shaman());
        barracks.registerUnit(new Skeleton());
    }

    @Test
    public void test() {
        List<UnitStack> firstUnitStack = new ArrayList<>();
        List<UnitStack> secondUnitStack = new ArrayList<>();
        fillFirstUnitStack(firstUnitStack);
        fillSecondUnitStack(secondUnitStack);

        Army firstArmy = new Army(firstUnitStack);
        Army secondArmy = new Army(secondUnitStack);

        BattleArmy firstBattleArmy = new BattleArmy(firstArmy);
        BattleArmy secondBattleArmy = new BattleArmy(secondArmy);
        Battle battle = new Battle(firstBattleArmy, secondBattleArmy);

        for (BattleUnitStack bus : battle.getMoveQueue().getQueueCurrentRound()) {
            System.out.println(bus.getUnit().getName());
        }

        BattleUnitStack devilStack = secondBattleArmy.getStacks().get(0);
        BattleUnitStack furyStack = firstBattleArmy.getStacks().get(2);
        BattleUnitStack angelStack = firstBattleArmy.getStacks().get(0);

        System.out.println();

        printHPInfo(devilStack);
        printHPInfo(furyStack);
        battle.attack(devilStack);
        printHPInfo(devilStack);
        printHPInfo(furyStack);

        System.out.println();

        printHPInfo(devilStack);
        printHPInfo(angelStack);
        battle.attack(devilStack);
        printHPInfo(devilStack);
        printHPInfo(angelStack);

        battle.defend();

        battle.defend();

        battle.attack(furyStack);

        battle.attack(furyStack);

        for (BattleUnitStack bus : battle.getMoveQueue().getQueueCurrentRound()) {
            System.out.println(bus.getUnit().getName());
        }

    }
}
