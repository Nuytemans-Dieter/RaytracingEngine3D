package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Object3D;
import objects.Ray;
import raytracing.RayTracer;

public class Cylinder extends Object3D {

    @Override
    public HitInfo calcHitInfo(Ray ray, double epsilon)
    {
        HitInfo hitInfo = new HitInfo();

        Point origin = ray.getOrigin();
        Direction direction = ray.getDirection();
        Direction inDir = direction.multiply( -1 ).toDirection();

        // Check collision with the cylinder walls (All points on X²+Z²=1 and |Y|<=1)

        double A = Math.pow( direction.getX(), 2) + Math.pow( direction.getZ(), 2);
        double B = 2 * ( origin.getX() * direction.getX() + origin.getZ() * direction.getZ() );
        double C = Math.pow( origin.getX(), 2) + Math.pow( origin.getZ(), 2) - 1;

        double sqrt = Math.sqrt( Math.pow(B, 2) - 4 * A  * C );
        double t1 = (- B + sqrt ) / (2 * A);
        double t2 = (- B - sqrt ) / (2 * A);

        boolean mayEnter = sqrt != 0;

        Direction n1 = new Direction( ray.getPoint( t1 ).modify(1, 0) );
        Direction n2 = new Direction( ray.getPoint( t2 ).modify(1, 0) );

        boolean isEntering1 = inDir.dotProduct( n1 ) >= 0;
        boolean isEntering2 = inDir.dotProduct( n2 ) >= 0;

        if (t1 >= epsilon && isYInRange(ray, t1))
            hitInfo.addHit( t1, n1, mayEnter && isEntering1);
        if (t2 >= epsilon && isYInRange(ray, t2))
            hitInfo.addHit( t2, n2, mayEnter && isEntering2);


        // Check collision with the cylinder surfaces (All points on |Y|=1, X²+Z²<1)

        double t3 = ( 1 - origin.getY()) / direction.getY();
        double t4 = (-1 - origin.getY()) / direction.getY();

        Direction n3 = new Direction(0, 1, 0);
        Direction n4 = new Direction(0, -1, 0);

        boolean isEntering3 = inDir.dotProduct( n3 ) >= 0;
        boolean isEntering4 = inDir.dotProduct( n4 ) >= 0;

        if (t3 >= epsilon && areXAndZInRange(ray, t3))    // Y = 1
            hitInfo.addHit( t3, n3, isEntering3 );
        if (t4 >= epsilon && areXAndZInRange(ray, t4))    // Y = -1
            hitInfo.addHit( t4, n4, isEntering4 );

        return hitInfo;
    }

    @Override
    public Double enclosedDistance(Point location) {
        Point simple = this.getInverseCache().multiply( location );

        if (Math.abs(simple.getY()) <= 1 && (Math.pow(simple.getX(), 2) + Math.pow(simple.getZ(), 2) <= 1))
            return simple.getNorm();
        else
            return null;
    }

    @Override
    public double getU(Point location) {

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        // If the point belongs on the rounded part of the cylinder
        if (Math.abs( y ) <= 1- RayTracer.EPSILON)
        {

            double theta = Math.asin(z / ((z * z) + (x * x)));

            // Handle negative angles
            if (theta < 0)
                theta = 1 + theta;

            // Prevent out of bounds indices
            theta = theta % 1;

            return theta;
        }
        else return (x + 1) / 2;
    }

    @Override
    public double getV(Point location) {

        double x = location.getX();
        double y = location.getY();
        double z = location.getZ();

        if ( Math.abs( z ) <= 1 - RayTracer.EPSILON )
        {
            return (y + 1) / 2;
        }
        else
        {
            return (z + 1) / 2;
        }
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
