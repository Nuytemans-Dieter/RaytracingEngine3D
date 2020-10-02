package maths;

import interfaces.IVector;

public class Vector implements IVector {

    private final double[] vector;

    public Vector(double x, double y, double z, double k)
    {
        vector = new double[]{x, y, z, k};
    }

}
