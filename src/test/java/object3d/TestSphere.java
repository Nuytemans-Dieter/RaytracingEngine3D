package object3d;

import maths.vector.Direction;
import maths.vector.Point;
import objects.Ray;
import objects.object3d.Sphere;
import org.junit.Test;

public class TestSphere {

    @Test
    public void test()
    {
        Sphere sphere = new Sphere();

        Point camLoc = new Point(2,2,2);
        Direction direction = new Direction(-1, -1, -1);
        Ray ray = new Ray(camLoc, direction);
        assert sphere.calcHitInfo( ray ).getLowestT() == 1.4226497308103736;
        ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() );
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getX() == 0.5773502691896264;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getY() == 0.5773502691896264;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getZ() == 0.5773502691896264;

        ray = new Ray(10, 5, 3, -9.5, -5, -3.5);
        assert sphere.calcHitInfo(ray).getLowestT() == 0.9566324623167374;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getX() == 0.9119916079909949;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getY() == 0.21683768841631323;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getZ() == -0.34821361810858065;
    }

}
