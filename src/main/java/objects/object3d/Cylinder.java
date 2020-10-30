package objects.object3d;

import data.HitInfo;
import maths.Vector;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;

public class Cylinder extends Object3D {

    private double radius;
    private double height;

    public Cylinder()
    {
        this(1, 1);
    }

    public Cylinder(double radius, double height)
    {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public HitInfo calcHitInfo(Ray ray)
    {
        Vector origin = ray.getOrigin();
        Vector dir = ray.getDirection();

        double D =  Math.pow((origin.getX() * dir.getX() + origin.getZ() * dir.getZ()), 2) -
                    ( (Math.pow(dir.getX(), 2) * Math.pow(dir.getZ(), 2)) * ( Math.pow(origin.getX(), 2) + Math.pow( origin.getZ(), 2 ) - Math.pow(radius, 2) ) );
        if (D < 0)
            return null;

        double t1 = (-(origin.getX() * dir.getX() + origin.getZ() * dir.getZ()) + Math.sqrt( D )) / ( Math.pow(dir.getX(), 2) + Math.pow(dir.getZ(), 2) );
        double t2 = (-(origin.getX() * dir.getX() + origin.getZ() * dir.getZ()) - Math.sqrt( D )) / ( Math.pow(dir.getX(), 2) + Math.pow(dir.getZ(), 2) );

        Point p1 = ray.getPoint(t1);
        Point p2 = ray.getPoint(t2);

        boolean t1InRange = (t1 >= 0 && Math.abs( p1.getY() ) <= this.height/2);
        boolean t2InRange = (t2 >= 0 && Math.abs( p2.getY() ) <= this.height/2);

//        if (t1InRange && t2InRange)
//            return Math.min(t1, t2);
//        else if (!t1InRange && t2InRange)
//            return t2;
//        else if (t1InRange)
//            return t1;
//        else
            return null;
    }
}
