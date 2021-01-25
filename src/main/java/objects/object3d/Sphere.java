package objects.object3d;

import datacontainers.HitInfo;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import objects.Material;
import objects.Ray;
import objects.Object3D;

public class Sphere extends Object3D {

    @Override
    public HitInfo calcHitInfo(Ray ray, double epsilon) {
        HitInfo hitInfo = new HitInfo();

        Vector origin = ray.getOrigin();

        double A = ray.getDirection().dotProduct(ray.getDirection());
        double B = ray.getDirection().dotProduct(origin);
        double C = Math.pow(origin.getNorm(), 2) - 1;

        double D = Math.pow(B, 2) - (A * C);
        if (D < 0) {
            return hitInfo;
        }

        boolean mayEnter = D != 0;

        D = Math.sqrt(D);
        double t1 = (-B + D) / A;
        double t2 = (-B - D) / A;

        Point p1 = ray.getPoint( t1 );
        Point p2 = ray.getPoint( t2 );

        Direction n1 = new Direction( p1 );
        Direction n2 = new Direction( p2 );

        Direction transposedN1 = this.getInverseCache().transpose().multiply( n1 );
        Direction transposedN2 = this.getInverseCache().transpose().multiply( n2 );

        Direction inDir = ray.getDirection().multiply( -1 ).toDirection();
        boolean doesEnter1 = inDir.dotProduct( transposedN1 ) >= 0;
        boolean doesEnter2 = inDir.dotProduct( transposedN2 ) >= 0;

        if (t1 >= epsilon)
            hitInfo.addHit(t1, n1, mayEnter && doesEnter1);
        if (t2 >= epsilon)
            hitInfo.addHit(t2, n2, mayEnter && doesEnter2);

        return hitInfo;
    }

    @Override
    public Double enclosedDistance(Point location) {
        Point simplifiedLocation = this.getInverseCache().multiply( location );

        // Get the squared distance to the origin
        double distance = simplifiedLocation.getNorm();
        return distance <= 1 ? distance : null;
    }

    @Override
    public double getU(Point location) {
        double angle = Math.atan2( -location.getZ(), location.getX() );
        return ( angle + Math.PI ) / ( 2 * Math.PI );
    }

    @Override
    public double getV(Point location) {
        double angle = Math.acos( -location.getY() );
        return angle / Math.PI;
    }
}
