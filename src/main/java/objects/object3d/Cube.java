package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Ray;
import objects.Object3D;

public class Cube extends Object3D {

    static final int size = 1;

    @Override
    public HitInfo calcHitInfo(Ray ray)
    {
        HitInfo hitInfo = new HitInfo();
        Point intersection;
        double t;

        Vector origin = ray.getOrigin();
        Vector direction = ray.getDirection();

        // X = 1
        t = (size - origin.getX()) / direction.getX();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getY()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t, new Direction(1, 0, 0));

        // X = -1
        t = (-size - origin.getX()) / direction.getX();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getY()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t, new Direction(-1, 0, 0));

        // Y = 1
        t = (size - origin.getY()) / direction.getY();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t, new Direction(0, 1, 0));

        // Y = -1
        t = (-size - origin.getY()) / direction.getY();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t, new Direction(0, -1, 0));

        // Z = 1
        t = (size - origin.getZ()) / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
            hitInfo.addHit(t, new Direction(0, 0, 1));

        // Z = -1
        t = (-size - origin.getZ()) / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
            hitInfo.addHit(t, new Direction(0, 0, -1));


        return hitInfo;
    }

    @Override
    public Double enclosedDistance(Point location) {
        Point simple = this.getInverseCache().multiply( location );

        if (Math.abs(simple.getX()) <= 1 && Math.abs(simple.getY()) <= 1 && Math.abs(simple.getZ()) <= 1)
            return simple.getNorm(); // Return the squared distance to the origin
        else
            return null;
    }
}
