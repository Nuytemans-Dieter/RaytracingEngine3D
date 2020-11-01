package maths;

public class Vector {

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

    /**
     * Get the result of adding another vector to this vector
     * This vector is NOT modified
     * The first three elements are simply added
     * The last element will be equal to whatever vector's last element is highest
     *
     * @param vector the vector to be added to this
     * @return the result of the addition
     */
    public Vector add(Vector vector)
    {
        return new Vector(
            new double[]{
                this.getX() + vector.get(0),
                this.getY() + vector.get(1),
                this.getZ() + vector.get(2),
                this.get(3) + vector.get(3)
            }
        );
    }

    /**
     * Get the result of subtracting another vector to this vector
     * This vector is NOT modified
     * The first three elements are simply subtracted
     * The last element will be equal to whatever vector's last element is highest
     *
     * @param vector the vector to be subtracted from this
     * @return the result of the addition
     */
    public Vector subtract(Vector vector)
    {
        return new Vector(
            new double[]{
                this.getX() - vector.get(0),
                this.getY() - vector.get(1),
                this.getZ() - vector.get(2),
                this.get(3) - vector.get(3)
            }
        );
    }

    public Vector multiply(double value)
    {
        return new Vector(
            new double[]{
                this.getX() * value,
                this.getY() * value,
                this.getZ() * value,
                this.get(3)
            }
        );
    }

    public Vector divide(double value)
    {
        return new Vector(
            new double[]{
                this.getX() / value,
                this.getY() / value,
                this.getZ() / value,
                this.get(3)
            }
        );
    }

    public double dotProduct(Vector vector)
    {
        return  this.getX() * vector.getX() +
                this.getY() * vector.getY() +
                this.getZ() * vector.getZ();
    }


    public double getNorm()
    {
        return Math.sqrt(Math.pow(this.getX(), 2.0) + Math.pow(this.getY(), 2.0) + Math.pow(this.getZ(), 2.0));
    }

    public Vector normalise()
    {
        double magnitude = this.getNorm();
        return new Vector(
            this.getX() / magnitude,
            this.getY() / magnitude,
            this.getZ() / magnitude,
            this.get(3)
        );
    }

    /**
     * Get the element at a specified location
     *
     * @param i the location [0, 3]
     * @return the element (double) at this location
     */
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

    /**
     * Modifies the value at a specified location and returns the resulting IVector
     *
     * @param i     the location [0, 3]
     * @param value the new value
     * @return the resulting IVector
     */
    public Vector modify(int i, double value)
    {
        assert (i >= 0 && i < 4);

        this.vector[i] = value;
        return this;
    }

    @Override
    public boolean equals(Object object)
    {
        if (!(object instanceof Vector))
            return false;

        Vector vector = (Vector) object;
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
