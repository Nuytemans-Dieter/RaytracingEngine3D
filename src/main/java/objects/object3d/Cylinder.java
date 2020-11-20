package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;

public class Cylinder extends Object3D {

    @Override
    public HitInfo calcHitInfo(Ray ray)
    {
        HitInfo hitInfo = new HitInfo();

        Point origin = ray.getOrigin();
        Direction direction = ray.getDirection();

        // Check collision with the cylinder walls (All points on X²+Z²=1 and |Y|<=1)

        double A = Math.pow( direction.getX(), 2) + Math.pow( direction.getZ(), 2);
        double B = 2 * ( origin.getX() * direction.getX() + origin.getZ() * direction.getZ() );
        double C = Math.pow( origin.getX(), 2) + Math.pow( origin.getZ(), 2) - 1;

        double sqrt = Math.sqrt( Math.pow(B, 2) - 4 * A  * C );
        double t1 = (- B + sqrt ) / (2 * A);
        double t2 = (- B - sqrt ) / (2 * A);

        if (t1 >= 0 && isYInRange(ray, t1))
            hitInfo.addHit( t1, new Direction( ray.getPoint( t1 ).modify(1, 0) ));
        if (t2 >= 0 && isYInRange(ray, t2))
            hitInfo.addHit( t2, new Direction( ray.getPoint( t2 ).modify(1, 0) ));


        // Check collision with the cylinder surfaces (All points on |Y|=1, X²+Z²<1)

        double t3 = ( 1 - origin.getY()) / direction.getY();
        double t4 = (-1 - origin.getY()) / direction.getY();

        if (t3 >= 0 && areXAndZInRange(ray, t3))    // Y = 1
            hitInfo.addHit( t3, new Direction(0, 1, 0) );
        if (t4 >= 0 && areXAndZInRange(ray, t4))    // Y = -1
            hitInfo.addHit( t4, new Direction(0, -1, 0) );

        return hitInfo;
    }


    private boolean isYInRange(Ray ray, double t)
    {
        double y = ray.getOrigin().getY() + t * ray.getDirection().getY();
        return Math.abs(y) <= 1;
    }

    private boolean areXAndZInRange(Ray ray, double t)
    {
        double x = ray.getOrigin().getX() + t * ray.getDirection().getX();
        double z = ray.getOrigin().getZ() + t * ray.getDirection().getZ();
        return (Math.pow(x, 2) + Math.pow(z, 2)) <= 1;
    }
}
