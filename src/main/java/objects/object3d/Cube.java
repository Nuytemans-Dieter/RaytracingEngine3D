package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Ray;
import objects.Object3D;

public class Cube extends Object3D {

    private final double size;

    public Cube ()
    {
        super();
        size = 1;
    }

    /**
     * Create a cube at the origin (0,0,0) with a specified size
     *
     * @param size the size in all axis' directions
     */
    public Cube(double size)
    {
        this.size = size;
    }

    @Override
    public HitInfo calcHitInfo(Ray ray)
    {
        HitInfo hitInfo = new HitInfo();
        Point intersection;
        double t;

        Vector origin = ray.getOrigin().subtract( this.location );
        Vector direction = ray.getDirection();

        // X = 1
        t = (size - origin.getX()) / direction.getX();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getY()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t);

        // X = -1
        t = (-size - origin.getX()) / direction.getX();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getY()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t);

        // Y = 1
        t = (size - origin.getY()) / direction.getY();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t);

        // Y = -1
        t = (-size - origin.getY()) / direction.getY();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getZ()) <= size)
            hitInfo.addHit(t);

        // Z = 1
        t = (size - origin.getZ()) / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
            hitInfo.addHit(t);

        // Z = -1
        t = (-size - origin.getZ()) / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
            hitInfo.addHit(t);


        return hitInfo;
    }

    @Override
    public Direction getNormal(Point location)
    {
        // Move the hit point to the simplified coordinate system
        Vector loc = this.getInverseCache().multiply( location );

        double bias = 1.000001;
        return new Direction( loc.divide( this.size ).multiply( bias ).round() );
    }


//    @Override
//    public Direction getNormal(Point location)
//    {
//
//        location = new Point(this.getInverseCache().multiply( location ));
//
//        double bias = 0.0001;
//
//        // Prevent rounding errors
//        Point p = new Point(
//            (int) (location.getX() + bias),
//            (int) (location.getY() + bias),
//            (int) (location.getZ() + bias)
//        );
//
//        Direction direction;
//
//        if (p.getX() == size && Math.abs( p.getY() ) <= size && Math.abs( p.getZ() ) <= size)
//        {
//            direction = new Direction(size, 0, 0);
//        }
//        else if (p.getX() == - size && Math.abs( p.getY() ) <= size && Math.abs( p.getZ() ) <= size)
//        {
//            direction = new Direction(-size, 0, 0);
//        }
//        else if (p.getY() == size && Math.abs( p.getX() ) <= size && Math.abs( p.getZ() ) <= size)
//        {
//            direction = new Direction(0, size, 0);
//        }
//        else if (p.getY() == - size && Math.abs( p.getX() ) <= size && Math.abs( p.getZ() ) <= size)
//        {
//            direction = new Direction(0, -size, 0);
//        }
//        else if (p.getZ() == size && Math.abs( p.getX() ) <= size && Math.abs( p.getY() ) <= size)
//        {
//            direction = new Direction(0, 0, size);
//        }
//        else if (p.getZ() == - size && Math.abs( p.getX() ) <= size && Math.abs( p.getY() ) <= size)
//        {
//            direction = new Direction(0, 0, -size);
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
