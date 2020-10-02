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
    public Matrix modify(int x, int y, double value)
    {
        // Check bounds
        assert (x >= 0 && x < 4 && y >= 0 && y < 4);

        // Update matrix and return result
        matrix[y][x] = value;
        return this;
    }


    // -------
    // Getters
    // -------

    public double get(int x, int y) {
        // Check bounds
        assert (x >= 0 && x < 4 && y >= 0 && y < 4);

        return matrix[y][x];
    }


    // -----------------
    // Operation methods
    // -----------------


    public IMatrix Multiply(IMatrix matrix)
    {
        return this;
    }

    public IMatrix add(IMatrix matrix)
    {
        Matrix result = new Matrix();
        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                double sum = this.get(x, y) + matrix.get(x, y);
                result = result.modify(x, y, sum);
            }
        }
        return result;
    }

    // ------------------
    // Overridden methods
    // ------------------

    @Override
    public boolean equals(Object comp)
    {
        if ( ! (comp instanceof IMatrix) )
            return false;

        IMatrix mat = (IMatrix) comp;
        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                if (mat.get(x, y) != this.get(x, y))
                    return false;
            }
        }

        return true;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();
        for (int x = 0; x < 4; x++)
        {
            builder.append(" | ");
            for (int y = 0; y < 4; y++)
            {
                builder.append(matrix[y][x]).append(" | ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
