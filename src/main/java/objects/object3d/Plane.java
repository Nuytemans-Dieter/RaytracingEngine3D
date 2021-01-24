package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;

public class Plane extends Object3D {

    private final int size = 1;

    @Override
    public HitInfo calcHitInfo(Ray ray, double epsilon) {

        HitInfo hitInfo = new HitInfo();
        Point intersection;
        double t;

        Point origin = ray.getOrigin();
        Direction direction = ray.getDirection();
        Direction inDir = direction.multiply( -1 ).toDirection();

        // The t for which z=0
        t = -origin.getZ() / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= epsilon && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
        {
            Direction normal = new Direction(0, 0, 1);
            hitInfo.addHit(t, normal, true);    // Ray object entering is checked by finding the last exited (isEntering==false) object. This prevents rays from entering planes
        }

        return hitInfo;
    }

    @Override
    public Double enclosedDistance(Point location) {
        return null;
    }
}
