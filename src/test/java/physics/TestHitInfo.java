package physics;

import datacontainers.HitInfo;
import maths.vector.Direction;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class TestHitInfo {


    @Test
    public void testConstructor()
    {
        Map<Double, Direction> map = new HashMap<Double, Direction>() {{
            put(12d, new Direction(0, 0, 0));
            put(30d, new Direction(1, 1, 1));
            put(1d, new Direction(2, 2, 2));
            put(20d, new Direction(3, 3, 3));
        }};

        Map<Double, Boolean> entering = new HashMap<Double, Boolean>() {{
            put(12d, true);
            put(30d, false);
            put(1d, true);
            put(20d, false);
        }};

        HitInfo hitInfo = new HitInfo(
            map, entering
        );

        assert hitInfo.getLowestT() == 1;
        assert hitInfo.getLowestTNormal().equals( new Direction(2, 2, 2) );

        hitInfo = new HitInfo();

        assert hitInfo.getLowestT() == null;
        assert hitInfo.getLowestTNormal() == null;
    }


    @Test
    public void testAddHit()
    {
        HitInfo hitInfo = new HitInfo();
        hitInfo.addHit( 12d, new Direction(0, 0, 0), true );
        hitInfo.addHit( 30d, new Direction(1, 1, 1), true );
        hitInfo.addHit( 1d, new Direction(2, 2, 2), true );
        hitInfo.addHit( 20d, new Direction(3, 3, 3), true );

        assert hitInfo.getLowestT() == 1;
        assert hitInfo.getLowestTNormal().equals( new Direction(2, 2, 2) );

        hitInfo = new HitInfo();
        hitInfo.addHit( 1d, new Direction(2, 2, 2), true );
        hitInfo.addHit( 12d, new Direction(0, 0, 0), true );
        hitInfo.addHit( 30d, new Direction(1, 1, 1), true );
        hitInfo.addHit( 20d, new Direction(3, 3, 3), true );

        assert hitInfo.getLowestT() == 1;
        assert hitInfo.getLowestTNormal().equals( new Direction(2, 2, 2) );

        hitInfo = new HitInfo();
        hitInfo.addHit( 12d, new Direction(0, 0, 0), true );
        hitInfo.addHit( 30d, new Direction(1, 1, 1), true );
        hitInfo.addHit( 20d, new Direction(3, 3, 3), true );
        hitInfo.addHit( 1d, new Direction(2, 2, 2), true );

        assert hitInfo.getLowestT() == 1;
        assert hitInfo.getLowestTNormal().equals( new Direction(2, 2, 2) );
    }
}
