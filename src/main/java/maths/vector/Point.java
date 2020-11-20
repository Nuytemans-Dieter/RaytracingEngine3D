package maths.vector;

import maths.Vector;

public class Point extends Vector {

    public Point(double x, double y, double z)
    {
        super(x, y, z, 1);
    }


    /**
     * Creates a Point from a given Vector
     * Will set the last element, k, to 1 to indicate being a point
     *
     * @param vector the vector to be transformed into a point
     */
    public Point(Vector vector)
    {
        super(vector.getX(), vector.getY(), vector.getZ(), 1);
    }

    /**
     * Calls the super's Vector#normalise() and wraps it in a Point class
     *
     * @return normalised Point
     */
    public Point normalise()
    {
        return new Point( super.normalise() );
    }

    public double distance(Point point)
    {
        return Math.sqrt(
            Math.pow((this.getX() - point.getX() ), 2 ) +
            Math.pow((this.getY() - point.getY() ), 2 ) +
            Math.pow((this.getZ() - point.getZ() ), 2 )
        );
    }

}
