package object3d;

import datacontainers.HitInfo;
import maths.Vector;
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

        HitInfo info = sphere.calcHitInfo( ray );
        assert info.getLowestT() == 1.4226497308103736;

        Point expectedHitPoint = new Point(
            0.5773502691896264,
            0.5773502691896264,
            0.5773502691896264
        );


        Point hitPoint = ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() );
        assert hitPoint.equals( expectedHitPoint );
        assert hitPoint.getX() == 0.5773502691896264;
        assert hitPoint.getY() == 0.5773502691896264;
        assert hitPoint.getZ() == 0.5773502691896264;

        Direction expectedNormal = new Direction(
            0.5773502691896264,
            0.5773502691896264,
            0.5773502691896264
        );
        assert info.getLowestTNormal().equals( expectedNormal );

        ray = new Ray(10, 5, 3, -9.5, -5, -3.5);
        HitInfo hitInfo = sphere.calcHitInfo(ray);
        assert hitInfo.getLowestT() == 0.9566324623167374;
        assert hitInfo.isLowestEntering();
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getX() == 0.9119916079909949;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getY() == 0.21683768841631323;
        assert ray.getPoint( sphere.calcHitInfo( ray ).getLowestT() ).getZ() == -0.34821361810858065;

        camLoc = new Point(0,0,0);
        direction = new Direction(-1, -1, -1);
        ray = new Ray(camLoc, direction);
        hitInfo = sphere.calcHitInfo( ray );
        assert !hitInfo.isLowestEntering();
    }

}
