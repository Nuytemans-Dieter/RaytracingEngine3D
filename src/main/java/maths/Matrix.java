package maths;

import maths.vector.Direction;
import maths.vector.Point;

public class Matrix {

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


    /**
     * Sets a position within this matrix to the specified value and returns the resulting Matrix
     *
     * @param x     represents the column, with the leftmost column being 0 [0, 3]
     * @param y     represents the row, with the top row being 0 [0, 3]
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

    /**
     * Gets the element at the specified location
     * Bounds are not checked (!) and nothing is returned This improves performance
     */
    public void unsafeModify(int x, int y, double value)
    {
        matrix[y][x] = value;
    }


    // -------
    // Getters
    // -------

    /**
     * Gets the value at a specified location
     *
     * @param x represents the column, with the leftmost column being 0 [0, 3]
     * @param y represents the row, with the top row being 0 [0, 3]
     * @return the value at this location
     */
    public double get(int x, int y) {
        // Check bounds
        assert (x >= 0 && x < 4 && y >= 0 && y < 4);

        return matrix[y][x];
    }


    /**
     * Gets the element at the specified location
     * Bounds are not checked! This improves performance
     */
    public double unsafeGet(int x, int y)
    {
        return matrix[y][x];
    }


    /**
     * Get a row(array of doubles)
     *
     * @param y the number of the row, with the top row being 0. [0, 3]
     * @return the row at the requested location
     */
    public double[] getRow(int y)
    {
        // Enforce range
        assert (y >= 0 && y < 4);

        return matrix[y];
    }


    /**
     * Get a column (array of doubles)
     *
     * @param x the number of the column, with the leftmost column being 0. [0, 3]
     * @return the column at the requested location
     */
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


    /**
     * Get the inverse of this matrix
     * Does not alter this object!
     *
     * @return the result of the inverse calculation
     */
    public Matrix inverse()
    {
        Jama.Matrix helper = new Jama.Matrix( this.matrix );
        helper = helper.inverse();
        return new Matrix( helper.getArray() );
    }


    /**
     * Get the transpose of this matrix
     * Does not alter this object!
     *
     * @return the result of transposing this matrix
     */
    public Matrix transpose()
    {
        double[][] newMatrix = new double[this.matrix[0].length][this.matrix.length];
        for (int i = 0; i < this.matrix.length; i++)
            for (int j = 0; j < this.matrix[0].length; j++)
                newMatrix[j][i] = this.matrix[i][j];
        return new Matrix( newMatrix );
    }


    /**
     * Multiplies this matrix with a vector and returns the result
     * Does not modify this object
     *
     * @param vector the vector this matrix should be multiplied with
     * @return the result of the multiplication
     */
    public Vector multiply(Vector vector)
    {
        Vector result = new Vector(0, 0, 0, 0);

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


    /**
     * Internally identical to Matrix#multiply(Vector) but wraps this method and returns a Point instead of a Vector
     *
     * @param point the point we want to transform
     * @return the result of multiplication with the given point, which is a point as well
     */
    public Point multiply(Point point)
    {
        return new Point( this.multiply( point.getVectorCopy() ) );
    }


    /**
     * Internally identical to Matrix#multiply(Vector) but wraps this method and returns a Direction instead of a Vector
     *
     * @param direction the direction we want to transform
     * @return the result of multiplication with the given direction, which is a direction as well
     */
    public Direction multiply(Direction direction)
    {
        return new Direction( this.multiply( direction.getVectorCopy() ) );
    }


    /**
     * Multiplies this matrix with another matrix and returns the result
     * Does not modify this object
     *
     * @param matrix the matrix this one should be multiplied with
     * @return the result of the multiplication
     */
    public Matrix multiply(Matrix matrix)
    {
        Matrix result = new Matrix();

        // Loop through all the positions of the resulting matrix
        for (int x = 0; x < 4; x++)
        {
            for (int y = 0; y < 4; y++)
            {
                // Multiply each corresponding double
                double sum = 0;
                for (int i = 0; i < 4; i++)
                {
                    sum += this.unsafeGet(i, y) * matrix.unsafeGet(x, i);
                }

                // Place the result at (x, y) in the resulting matrix
                result.unsafeModify(x, y, sum);
            }
        }

        return result;
    }


    /**
     * Adds another matrix to this matrix and returns the result
     * Does not modify this object
     *
     * @param matrix the matrix that should be added to this one
     * @return the result maths.Matrix of the addition
     */
    public Matrix add(Matrix matrix)
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
        if ( ! (comp instanceof Matrix) )
            return false;

        Matrix mat = (Matrix) comp;
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
