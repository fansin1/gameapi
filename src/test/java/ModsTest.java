import org.fansin.gameapi.Barracks;
import org.fansin.gameapi.ModLoader;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ModsTest {

    @Test
    public void moddedUnitTest() throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ModLoader modLoader = new ModLoader("/home/fans1n/MyProjects/gameapi/mods");
        modLoader.registerUnits();
        Barracks barracks = Barracks.getInstance();
        assertEquals("Modded", barracks.getAllUnitsNames().next());
    }

}