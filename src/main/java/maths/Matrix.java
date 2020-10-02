package maths;

import interfaces.IMatrix;
import interfaces.IVector;

import javax.swing.text.LabelView;

public class Matrix implements IMatrix {

    private final double[][] matrix;

    /**
     * Creates a basic initialised 4x4 Matrix
     * Will contain all zeroes, except for where y=x (row index equals column index)
     *     [1 0 0 0;
     *      0 1 0 0;
     *      0 0 1 0;
     *      0 0 0 1]
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

    /**
     * Create a specific matrix
     * Keep in mind that the first dimension specifies the row and the second specifies the column
     * Eg. providing:
     *     new double[][] {
     *         {1, 2, 3, 4},
     *         {5, 6, 7, 8},
     *         {9, 10, 11, 12},
     *         {13, 14, 15, 16}
     *     }
     *     Will result in the matrix
     *     [1  2  3  4;
     *      5  6  7  8;
     *      9  10 11 12;
     *      13 14 15 16]
     */
    public Matrix( double[][] matrix )
    {
        // Verify given matrix dimensions
        assert ( matrix.length == 4 );
        for (int i=0; i < 4; i++)
            assert (matrix[i].length == 4);

        this.matrix = matrix;
    }


    // ----------------------
    // Builder pattern method
    // ----------------------


    @Override
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


    @Override
    public double[] getRow(int y)
    {
        // Enforce range
        assert (y >= 0 && y < 4);

        return matrix[y];
    }


    @Override
    public double[] getColumn(int x)
    {
        // Enforce range
        assert (x >= 0 && x < 4);

        return new double[] {
                get(x,0),
                get(x,1),
                get(x,2),
                get(x,3)
        };
    }


    // -----------------
    // Operation methods
    // -----------------


    @Override
    public IVector multiply(IVector vector)
    {
        IVector result = new Vector(0, 0, 0, 0);

        for (int i = 0; i < 4; i++)
        {
            double sum = 0;
            for (int j = 0; j < 4; j++)
            {
                sum += this.get(j, i) * vector.get(j);
            }

            result.modify(i, sum);
        }

        return result;
    }


    @Override
    public IMatrix multiply(IMatrix matrix)
    {
        Matrix result = new Matrix();

        // Loop through all the positions of the resulting matrix
        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                // Get row X of this matrix
                double[] row = this.getRow(y);

                // Get column Y of the given matrix
                double[] column = matrix.getColumn(x);

                // Check row and column length
                assert (row.length == 4 && column.length == 4);

                // Multiply each corresponding double
                double sum = 0;
                for (int i = 0; i < 4; i++)
                {
                    sum += row[i] * column[i];
                }

                // Place the result at (x, y) in the resulting matrix
                result.modify(x, y, sum);
            }
        }

        return result;
    }


    @Override
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
        for (int y = 0; y < 4; y++)
        {
            for (int x = 0; x < 4; x++)
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
        for (int y = 0; y < 4; y++)
        {
            builder.append(" | ");
            for (int x = 0; x < 4; x++)
            {
                builder.append( this.get(x, y) ).append(" | ");
            }
            builder.append("\n");
        }

        return builder.toString();
    }
}
