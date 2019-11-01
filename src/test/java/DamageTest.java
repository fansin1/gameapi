import org.fansin.gameapi.Damage;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DamageTest {

    @Test
    void RandomTest() {

        Damage damage = new Damage(10, 15);

        for (int i = 0; i < 10000; i++) {
            int d = damage.getRandom();
            assertFalse(d < 10 || d > 15);
        }
    }

}
