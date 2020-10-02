package interfaces;

import maths.Matrix;

public interface IMatrix {

    /**
     * Multiplies this matrix with another matrix and returns the result
     * Does not modify this object
     * @param matrix the matrix this one should be multiplied with
     * @return the result maths.Matrix of the multiplication
     */
    Matrix Multiply(Matrix matrix);

    /**
     * Adds another matrix to this matrix and returns the result
     * Does not modify this object
     * @param matrix the matrix that should be added to this one
     * @return the result maths.Matrix of the addition
     */
    Matrix add(Matrix matrix);

}
