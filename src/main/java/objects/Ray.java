package objects;

import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;

public class Ray {

    private Point origin;
    private Direction direction;

    /**
     * Create a new Ray from a certain Location, through a given x, y and z point
     *
     * @param camLoc a Point (Vector) indicating the location of the point camera
     * @param x the x-location through which this ray should pass
     * @param y the y-location through which this ray should pass
     * @param z the z-location through which this ray should pass
     */
    public Ray(Point camLoc, double x, double y, double z)
    {
        this.origin = new Point(camLoc.getX(), camLoc.getY(), camLoc.getZ());
        this.direction = new Direction(camLoc.getX() - x, camLoc.getY() - y, camLoc.getZ() - z);
    }


    public Vector getOrigin()
    {
        return origin;
    }

    public Vector getDirection()
    {
        return direction;
    }

    /**
     * Get the point on this ray for a specific t
     *
     * @param t the t for which the location should be retrieved
     * @return the location where this ray satisfies the applied restriction
     */
    public Point getPoint(double t)
    {
        return new Point(
                origin.getX() + direction.getX() * t,
                origin.getY() + direction.getY() * t,
                origin.getZ() + direction.getZ() * t
        );
    }


}
