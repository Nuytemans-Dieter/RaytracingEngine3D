package interfaces;

import maths.Matrix;

public interface IMatrix {

    /**
     * Gets the value at a specified location
     * @param x represents the column, with the leftmost column being 0 [0, 3]
     * @param y represents the row, with the top row being 0 [0, 3]
     * @return the value at this location
     */
    double get(int x, int y);

    /**
     * Multiplies this matrix with another matrix and returns the result
     * Does not modify this object
     * @param matrix the matrix this one should be multiplied with
     * @return the result maths.Matrix of the multiplication
     */
    IMatrix Multiply(IMatrix matrix);

    /**
     * Adds another matrix to this matrix and returns the result
     * Does not modify this object
     * @param matrix the matrix that should be added to this one
     * @return the result maths.Matrix of the addition
     */
    IMatrix add(IMatrix matrix);

}
