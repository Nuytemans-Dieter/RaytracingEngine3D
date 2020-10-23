package objects.lighting;

import maths.Vector;
import maths.vector.Point;
import objects.LightEmitter;
import objects.Positionable;
import objects.Ray;

public class LightSource extends LightEmitter {

    public LightSource(Point location, double intensity)
    {
        super(location, intensity);
    }

    @Override
    public double getTValue()
    {
        // Always equal to 1
//        Vector difference = this.location.subtract( ray.getOrigin() );
//        double t1 = (this.location.getX() - ray.getOrigin().getX()) / ray.getDirection().getX();
//        double t2 = difference.getY() / ray.getDirection().getY();
//        double t3 = difference.getZ() / ray.getDirection().getZ();
//        return t1;
        return 1;
    }
}
