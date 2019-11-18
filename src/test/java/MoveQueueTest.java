import org.fansin.gameapi.*;
import org.fansin.gameapi.units.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class MoveQueueTest {

    @BeforeAll
    public static void createBarracks() {
        Barracks barracks = Barracks.getInstance();
        barracks.registerUnit(new Angel());
        barracks.registerUnit(new Crossbowman());
        barracks.registerUnit(new Cyclops());
        barracks.registerUnit(new Shaman());
        barracks.registerUnit(new Fury());
    }

    private TemporaryModifier getOneRoundModifier() {
        return new TemporaryModifier() {

            int r = 1;

            @Override
            public double initiativeChange(double initiative) {
                return initiative - 2;
            }

            @Override
            public void endRound() {
                r--;
            }

            @Override
            public boolean isWorking() {
                return r > 0;
            }

            @Override
            public int getRounds() {
                return r;
            }
        };
    }

    private BattleUnitStack getBUS(Unit unit) {
        return new BattleUnitStack(new UnitStack(unit, 1));
    }

    private List<BattleUnitStack> getBUSList() {
        List<BattleUnitStack> bus = new ArrayList<>();
        Barracks barracks = Barracks.getInstance();

        bus.add(getBUS(barracks.getUnit(Angel.class)));
        bus.add(getBUS(barracks.getUnit(Crossbowman.class)));
        bus.add(getBUS(barracks.getUnit(Cyclops.class)));
        bus.add(getBUS(barracks.getUnit(Shaman.class)));
        bus.add(getBUS(barracks.getUnit(Fury.class)));

        return bus;
    }

    @Test
    public void clearTestCurrentRound() {
        MoveQueue queue = new MoveQueue(getBUSList());

        List<BattleUnitStack> busQueue = queue.getQueueCurrentRound();

        for (int i = 1; i < busQueue.size(); i++) {
            assertTrue(busQueue.get(i - 1).calculateInitiative() >= busQueue.get(i).calculateInitiative());
        }

        queue.nextMove();

        for (int i = 1; i < busQueue.size(); i++) {
            assertTrue(busQueue.get(i - 1).calculateInitiative() >= busQueue.get(i).calculateInitiative());
        }

        queue.skip();
        queue.nextMove();

        for (int i = 1; i < busQueue.size() - 1; i++) {
            assertTrue(busQueue.get(i - 1).calculateInitiative() >= busQueue.get(i).calculateInitiative());
        }

        assertFalse(busQueue.get(busQueue.size() - 2).calculateInitiative() >
                busQueue.get(busQueue.size() - 1).calculateInitiative());

    }

    @Test
    public void clearTestNextRound() {
        MoveQueue queue = new MoveQueue(getBUSList());

        List<BattleUnitStack> busQueue = queue.getQueueNextRound();

        for (int i = 1; i < busQueue.size(); i++) {
            assertTrue(busQueue.get(i - 1).calculateInitiativeOnNextRound() >= busQueue.get(i).calculateInitiativeOnNextRound());
        }
    }

    @Test
    public void withModifiersCurrentRound() {
        List<BattleUnitStack> busList = getBUSList();

        busList.get(0).addModifier(getOneRoundModifier());

        MoveQueue queue = new MoveQueue(busList);

        List<BattleUnitStack> busQueue = queue.getQueueCurrentRound();

        for (int i = 1; i < busQueue.size(); i++) {
            assertTrue(busQueue.get(i - 1).calculateInitiative() >= busQueue.get(i).calculateInitiative());
        }
    }

    @Test
    public void withModifiersNextRound() {
        List<BattleUnitStack> busList = getBUSList();

        busList.get(0).addModifier(getOneRoundModifier());

        MoveQueue queue = new MoveQueue(busList);

        List<BattleUnitStack> busQueue = queue.getQueueNextRound();

        for (int i = 1; i < busQueue.size(); i++) {
            assertTrue(busQueue.get(i - 1).calculateInitiativeOnNextRound() >= busQueue.get(i).calculateInitiativeOnNextRound());
        }
    }

}
