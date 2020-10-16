package maths.vector;

import maths.Vector;

public class Direction extends Vector {

    public Direction(double x, double y, double z)
    {
        super(x, y, z, 0);
    }

    /**
     * Creates a Direction from a given Vector
     * Will set the last element, k, to 0 to indicate being a direction
     *
     * @param vector the vector to be transformed into a direction
     */
    public Direction(Vector vector)
    {
        super(vector.getX(), vector.getY(), vector.getZ(), 0);
    }

}
