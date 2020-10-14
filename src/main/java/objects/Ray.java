package objects;

import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;

public class Ray {

    private Vector location;
    private Vector direction;

    /**
     * Create a new Ray from a certain Location, through a given x, y and z point
     *
     * @param camLoc a Point (Vector) indicating the location of the point camera
     * @param x the x-location through which this ray should pass
     * @param y the y-location through which this ray should pass
     * @param z the z-location through which this ray should pass
     */
    public Ray(Point camLoc, int x, int y, int z)
    {
        this.location = new Point(camLoc.getX(), camLoc.getY(), camLoc.getZ());
        direction = new Direction(camLoc.getX() - x, camLoc.getY() - y, camLoc.getZ() - z);
    }


    public Vector getLocation()
    {
        return location;
    }

    public Vector getDirection()
    {
        return direction;
    }

    public Point getPoint(double t)
    {
        return new Point(
                location.getX() + direction.getX() * t,
                location.getY() + direction.getY() * t,
                location.getZ() + direction.getZ() * t
        );
    }


}
