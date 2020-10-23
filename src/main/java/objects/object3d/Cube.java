package objects.object3d;

import graphics.Rgb;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Ray;
import objects.Object3D;

public class Cube extends Object3D {

    private double size;

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
    public Double getCollidingT(Ray ray)
    {
        Double lowest = null;
        Point intersection;
        double t;

        Vector origin = ray.getOrigin().subtract( this.location );
        Vector direction = ray.getDirection();

        // X = 1
        t = (size - origin.getX()) / direction.getX();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getY()) <= size && Math.abs(intersection.getZ()) <= size)
            lowest = t;

        // X = -1
        t = (-size - origin.getX()) / direction.getX();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getY()) <= size && Math.abs(intersection.getZ()) <= size)
            if (lowest == null || t < lowest)
                lowest = t;

        // Y = 1
        t = (size - origin.getY()) / direction.getY();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getZ()) <= size)
            if (lowest == null || t < lowest)
                lowest = t;

        // Y = -1
        t = (-size - origin.getY()) / direction.getY();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getZ()) <= size)
            if (lowest == null || t < lowest)
                lowest = t;

        // Z = 1
        t = (size - origin.getZ()) / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
            if (lowest == null || t < lowest)
                lowest = t;

        // Z = -1
        t = (-size - origin.getZ()) / direction.getZ();
        intersection = ray.getPoint(t);
        if (t >= 0 && Math.abs(intersection.getX()) <= size && Math.abs(intersection.getY()) <= size)
            if (lowest == null || t < lowest)
                lowest = t;


        return lowest;
    }
}
