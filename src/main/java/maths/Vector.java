package maths;

import interfaces.IVector;

public class Vector implements IVector {

    private final double[] vector;

    public Vector(double x, double y, double z, double k)
    {
        vector = new double[]{x, y, z, k};
    }

    public Vector(double[] vector)
    {
        assert (vector.length == 4);

        this.vector = vector;
    }

    @Override
    public double get(int i)
    {
        assert (i >= 0 && i < 4);

        return vector[i];
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
