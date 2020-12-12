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
    public HitInfo calcHitInfo(Ray ray)
    {
        HitInfo hitInfo = new HitInfo();

        Vector origin = ray.getOrigin();

        double A = ray.getDirection().dotProduct(ray.getDirection());
        double B = ray.getDirection().dotProduct(origin);
        double C = Math.pow(origin.getNorm(), 2) - 1;

        double D = Math.pow(B, 2) - (A * C);
        if (D < 0) {
            return hitInfo;
        }

        D = Math.sqrt(D);
        double t1 = (-B + D) / A;
        double t2 = (-B - D) / A;

        if (t1 >= 0)
            hitInfo.addHit(t1, new Direction( ray.getPoint( t1 )) );
        if (t2 >= 0)
            hitInfo.addHit(t2, new Direction( ray.getPoint( t2 )) );

        return hitInfo;
    }

    @Override
    public Double enclosedDistance(Point location) {
        Point simplifiedLocation = this.getInverseCache().multiply( location );

        // Get the squared distance to the origin
        double distance = simplifiedLocation.getNorm();
        return distance <= 1 ? distance : null;
    }
}
