package maths;

import interfaces.IMatrix;

public class Matrix implements IMatrix {

    private final double[][] matrix;

    /**
     * Creates a basic initialised 4x4 Matrix
     */
    public Matrix()
    {
        matrix = new double[][] {
            {1, 0, 0, 0},
            {0, 1, 0, 0},
            {0, 0, 1, 0},
            {0, 0, 0, 1},
        };
    }


    // ----------------------
    // Builder pattern method
    // ----------------------


    /**
     * Sets a position within this matrix to the specified value and returns the resulting Matrix
     * @param x represents the column, with the leftmost column being 0 [0, 3]
     * @param y represents the row, with the top row being 0 [0, 3]
     * @param value the new value of this position
     * @return the resulting matrix
     */
    public Matrix setPosition(int x, int y, double value)
    {
        // Check bounds
        assert (x >= 0 && x < 4 && y >= 0 && y < 4);

        // Update matrix and return result
        matrix[x][y] = value;
        return this;
    }


    // -----------------
    // Operation methods
    // -----------------


    public Matrix Multiply(Matrix matrix)
    {
        return this;
    }

    public Matrix add(Matrix matrix)
    {
        return this;
    }

}
