package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Ray;
import objects.Object3D;

public class Cube extends Object3D {

    static final int size = 1;

    public Cube ()
    {
        super();
    }

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
            hitInfo.addHit(t, new Direction(0, 0, 1));


        return hitInfo;
    }

//    @Override
//    public Direction getNormal(Point location)
//    {
//        // Move the hit point to the simplified coordinate system
//        Vector loc = this.getInverseCache().multiply( location );
//
//        double bias = 1.000001;
//        return new Direction( loc.divide( this.size ).multiply( bias ).round() );
//    }


//    @Override
//    public Direction getNormal(Point location)
//    {
//        location = new Point(this.getInverseCache().multiply( location ));
//
//        double bias = 0.001;
//
//        // Prevent rounding errors
//        Point p = new Point(
//            (int) (location.getX()),
//            (int) (location.getY()),
//            (int) (location.getZ())
//        );
//
//        Direction direction;
//
//        double thresholdHigh = size + bias;
//        double thresholdLow = size - bias;
//
//        if (Math.abs(p.getX()) <= thresholdHigh &&  Math.abs(p.getX()) >= thresholdLow &&
//            Math.abs( p.getY() ) <= thresholdHigh &&         Math.abs( p.getZ() ) <= thresholdHigh)
//        {
//            int sign = (p.getX() > 0) ? 1 : -1;
//            direction = new Direction(size * sign, 0, 0);
//        }
//        else if (   Math.abs(p.getY()) <= thresholdHigh &&  Math.abs(p.getY()) >= thresholdLow &&
//                    Math.abs( p.getX() ) <= thresholdHigh &&         Math.abs( p.getZ() ) <= thresholdHigh)
//        {
//            int sign = (p.getY() > 0) ? 1 : -1;
//            direction = new Direction(0, size * sign, 0);
//        }
//        else if (   Math.abs(p.getZ()) <= thresholdHigh &&      Math.abs(p.getZ()) >= thresholdLow &&
//                    Math.abs( p.getX() ) <= thresholdHigh &&    Math.abs( p.getY() ) <= thresholdHigh)
//        {
//            int sign = (p.getZ() > 0) ? 1 : -1;
//            direction = new Direction(0, 0, size * sign);
//        }
//        else
//        {
//            direction = new Direction(0, 0, 0);
//        }
//
//        direction = new Direction( direction.normalise() );
//        return direction;
//    }
}
