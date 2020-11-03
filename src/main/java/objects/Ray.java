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
     * @param direction the direction of this ray
     */
    public Ray(Point camLoc, Direction direction)
    {
        this.origin = new Point(camLoc.getX(), camLoc.getY(), camLoc.getZ());
        this.direction = direction;
    }


    public Ray(double x, double y, double z, double xt, double yt, double zt)
    {
        this.origin = new Point(x, y, z);
        this.direction = new Direction(xt, yt, zt);
    }


    public Point getOrigin()
    {
        return origin;
    }

    public Direction getDirection()
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
