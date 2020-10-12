package maths;

import interfaces.IVector;

public class Vector implements IVector {

    private final double[] vector;

    /**
     * Initialise a vector [0, 0, 0, 0]T
     */
    public Vector()
    {
        vector = new double[]{0, 0, 0, 0};
    }

    /**
     * Initialise a vector [x, y, z, k]T
     *
     * @param x x-value
     * @param y y-value
     * @param z z-value
     * @param k constant value
     */
    public Vector(double x, double y, double z, double k)
    {
        vector = new double[]{x, y, z, k};
    }

    /**
     * Initialise an internal vector (double[] of length 4) equal to the given one
     *
     * @param vector the vector for internal use. Must be of length 4
     */
    public Vector(double[] vector)
    {
        assert (vector.length == 4);

        this.vector = vector;
    }

    @Override
    public IVector add(IVector vector)
    {
        return new Vector(
            new double[]{
                this.getX() + vector.get(0),
                this.getY() + vector.get(1),
                this.getZ() + vector.get(2),
                Math.max( this.get(3), vector.get(3) )
            }
        );
    }

    @Override
    public double get(int i)
    {
        assert (i >= 0 && i < 4);

        return vector[i];
    }

    public double getX()
    {
        return vector[0];
    }

    public double getY()
    {
        return vector[1];
    }

    public double getZ()
    {
        return vector[2];
    }

    @Override
    public IVector modify(int i, double value)
    {
        assert (i >= 0 && i < 4);

        this.vector[i] = value;
        return this;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof IVector))
            return false;

        IVector vector = (IVector) object;
        for (int i = 0; i < 4; i++)
            if (vector.get(i) != this.get(i))
                return false;

        return true;
    }


    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 4; i++)
            builder.append("| ").append(this.get(i)).append(" |\n");

        return builder.toString();
    }
}
