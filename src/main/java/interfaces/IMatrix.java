package interfaces;

import maths.Matrix;

/**
 * The interface Matrix.
 */
public interface IMatrix {

    /**
     * Gets the value at a specified location
     *
     * @param x represents the column, with the leftmost column being 0 [0, 3]
     * @param y represents the row, with the top row being 0 [0, 3]
     * @return the value at this location
     */
    double get(int x, int y);

    /**
     * Sets a position within this matrix to the specified value and returns the resulting Matrix
     *
     * @param x     represents the column, with the leftmost column being 0 [0, 3]
     * @param y     represents the row, with the top row being 0 [0, 3]
     * @param value the new value of this position
     * @return the resulting matrix
     */
    Matrix modify(int x, int y, double value);

    /**
     * Multiplies this matrix with another matrix and returns the result
     * Does not modify this object
     *
     * @param matrix the matrix this one should be multiplied with
     * @return the result of the multiplication
     */
    IMatrix multiply(IMatrix matrix);

    /**
     * Multiplies this matrix with a vector and returns the result
     * Does not modify this object
     *
     * @param vector the vector this matrix should be multiplied with
     * @return the result of the multiplication
     */
    IVector multiply(IVector vector);

    /**
     * Adds another matrix to this matrix and returns the result
     * Does not modify this object
     *
     * @param matrix the matrix that should be added to this one
     * @return the result maths.Matrix of the addition
     */
    IMatrix add(IMatrix matrix);

    /**
     * Get a row(array of doubles)
     *
     * @param y the number of the row, with the top row being 0. [0, 3]
     * @return the row at the requested location
     */
    double[] getRow(int y);

    /**
     * Get a column (array of doubles)
     *
     * @param x the number of the column, with the leftmost column being 0. [0, 3]
     * @return the column at the requested location
     */
    double[] getColumn(int x);
}
