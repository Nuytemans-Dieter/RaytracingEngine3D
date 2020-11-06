package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Material;
import objects.Ray;
import objects.Object3D;

public class Sphere extends Object3D {

    private final double radius;


    /**
     * Creates a Sphere at (0, 0, 0) with radius 1
     */
    public Sphere()
    {
        radius = 1.0;
    }

    public Sphere(double radius)
    {
        this.radius = radius;
    }

    @Override
    public HitInfo calcHitInfo(Ray ray)
    {
        HitInfo hitInfo = new HitInfo();

        Vector origin = ray.getOrigin();
        origin = origin.subtract( this.location );

        double A = ray.getDirection().dotProduct(ray.getDirection());
        double B = ray.getDirection().dotProduct(origin);
        double C = Math.pow(origin.getNorm(), 2) - Math.pow(radius, 2);

        double D = Math.pow(B, 2) - (A * C);
        if (D < 0) {
            return hitInfo;
        }

        D = Math.sqrt(D);
        double t1 = (-B + D) / A;
        double t2 = (-B - D) / A;

        if (t1 >= 0)
            hitInfo.addHit(t1);
        if (t2 >= 0)
            hitInfo.addHit(t2);

        return hitInfo;
    }

    @Override
    public Direction getNormal(Point location)
    {
        Point transformedLocation = new Point(this.getTransformation().multiply( this.location ));
        return new Direction(
            (location.getX() - transformedLocation.getX()) / this.radius,
            (location.getY() - transformedLocation.getY()) / this.radius,
            (location.getZ() - transformedLocation.getZ()) / this.radius
        );
    }
}
