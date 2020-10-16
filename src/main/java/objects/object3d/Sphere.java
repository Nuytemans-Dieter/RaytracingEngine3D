package objects.object3d;

import maths.Vector;
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
    public Double getCollidingT(Ray ray)
    {
        Vector origin = ray.getOrigin();
        origin = origin.subtract( this.location );

        double a = ray.getDirection().dotProduct(ray.getDirection()); // 1?
        double b = ray.getDirection().dotProduct(origin);
        double c = Math.pow(origin.getNorm(), 2) - Math.pow(radius, 2);

        double D = Math.pow(b, 2) - (a * c);
        if (D < 0) {
            return null;
        }

        D = Math.sqrt(D);
        double t1 = (-b + D) / a;
        double t2 = (-b - D) / a;

        if (t1 < 0 && t2 < 0)
            return null;
        else if (t1 < 0)
            return t2;
        else if (t2 < 0)
            return t1;
        else
            return Math.min(t1, t2);
    }
}
