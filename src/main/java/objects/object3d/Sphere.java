package objects.object3d;

import graphics.Rgb;
import maths.Vector;
import maths.vector.Point;
import objects.Ray;
import objects.Object3D;

public class Sphere extends Object3D {

    private final double radius;

    public Sphere(double radius)
    {
        this.radius = radius;
    }


    @Override
    public boolean isColliding(Ray ray)
    {
        // Direction and Location
        Vector d = ray.getDirection();
        Vector l = ray.getLocation();

        double first = - ( (d.get(0) * l.get(0)) + (d.get(1) * l.get(1)) + (d.get(2) * l.get(2)) );
        double underSqrt1 = Math.pow((d.get(0) * l.get(0)) + (d.get(1) * l.get(1)) + (d.get(2) * l.get(2)), 2);
        double underSqrt2 = (d.get(0) * d.get(0)) + (d.get(1) * d.get(1)) + (d.get(2) * d.get(2));
        double underSqrt3 = (l.get(0) * l.get(0)) + (l.get(1) * l.get(1)) + (l.get(2) * l.get(2)) - radius;

        double underSqrt = 4 * (underSqrt1 - (underSqrt2 * underSqrt3));

        if (underSqrt < 0)
        {
            System.out.println("< 0, no intersection");
            return false;
        } else if (underSqrt==0)
        {
            System.out.println("One intersection");
        } else
        {
            System.out.println("No intersections");
        }

//        double result1 = (first + Math.sqrt(underSqrt)) / Math.pow(underSqrt2, 2);
//        double result2 = (first - Math.sqrt(underSqrt)) / Math.pow(underSqrt2, 2);

        return true;
    }
}
