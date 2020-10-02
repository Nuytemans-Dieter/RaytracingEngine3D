import interfaces.IMatrix;
import maths.Matrix;
import org.junit.Test;

import java.util.Arrays;

public class TestMatrix {

    @Test
    public void testEquals()
    {
        Matrix matrix = new Matrix();
        assert(matrix.equals(matrix));

        Matrix newMatrix = new Matrix().modify(0, 0, 16.0);
        Matrix anotherMatrix = new Matrix().modify(0, 0, 16.0);
        assert (newMatrix.equals(anotherMatrix));
        assert (!matrix.equals(anotherMatrix));
        assert (!matrix.equals( 5 ));

        Matrix given = new Matrix(
            new double[][]{
                {1, 0, 0, 0},
                {0, 1, 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1},
            }
        );
        assert (given.equals(matrix));
    }

    @Test
    public void testGetRow()
    {
        Matrix mat = new Matrix(
            new double[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );

        assert (Arrays.equals(mat.getRow(0), new double[]{1, 2, 3, 4}));
        assert (Arrays.equals(mat.getRow(1), new double[]{5, 6, 7, 8}));
        assert (Arrays.equals(mat.getRow(2), new double[]{9, 10, 11, 12}));
        assert (Arrays.equals(mat.getRow(3), new double[]{13, 14, 15, 16}));
    }

    @Test
    public void testGetColumn()
    {
        Matrix mat = new Matrix(
            new double[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );

        assert (Arrays.equals(mat.getColumn(0), new double[]{1, 5, 9, 13}));
        assert (Arrays.equals(mat.getColumn(1), new double[]{2, 6, 10, 14}));
        assert (Arrays.equals(mat.getColumn(2), new double[]{3, 7, 11, 15}));
        assert (Arrays.equals(mat.getColumn(3), new double[]{4, 8, 12, 16}));
    }

    @Test
    public void testMultiplication()
    {
        Matrix mat = new Matrix()
                .modify(0, 0, 2)
                .modify(1, 1, 3)
                .modify(2, 2, 4)
                .modify(3, 3, 5);

        Matrix mat2 = new Matrix();
        Matrix expected = new Matrix(
            new double[][] {
                {2, 0, 0, 0},
                {0, 3, 0, 0},
                {0, 0, 4, 0},
                {0, 0, 0, 5}
            }
        );
        assert( expected.equals( mat.multiply(mat2) ) );

        mat = new Matrix(
            new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
            }
        );
        mat2 = new Matrix(
            new double[][]{
                {1, 2, 1, 2},
                {1, 2, 1, 2},
                {1, 2, 1, 2},
                {1, 2, 1, 2},
            }
        );
        expected = new Matrix(
            new double[][]{
                {10, 20, 10, 20},
                {10, 20, 10, 20},
                {10, 20, 10, 20},
                {10, 20, 10, 20},
            }
        );
        assert( expected.equals( mat.multiply(mat2) ) );

        mat = new Matrix(
            new double[][]{
                {7, 4, 12, 2},
                {4, 8, 6, 3},
                {6, 9, 7, 3},
                {7, 5, 6, 3},
            }
        );
        mat2 = new Matrix(
            new double[][]{
                {5, 6, 7, 7},
                {6, 3, 1, 4},
                {3, 6, 5, 8},
                {4, 7, 6, 9},
            }
        );
        expected = new Matrix(
            new double[][]{
                {103, 140, 125, 179},
                {98, 105, 84, 135},
                {117, 126, 104, 161},
                {95, 114, 102, 144},
            }
        );
        assert( expected.equals( mat.multiply(mat2) ) );

        mat = new Matrix(
            new double[][] {{1,0,4, -4}, {-1,1,2,6},{0,-1,0.5, -7},{9,0.23,-1,-5}}
        );
        mat2 = new Matrix(
            new double[][] {{1, 5, 3, 0}, {-0.2, 3, 9, -17}, {0.8, 0.01, 2, -5}, {3, 1, 1, 1}}
        );
        expected = new Matrix(
            new double[][]{
                {-7.8, 1.04, 7.0, -24.0},
                {18.4, 4.02, 16.0, -21.0},
                {-20.4, -9.995000000000001, -15.0, 7.5},
                {-6.846, 40.68, 22.07, -3.91},
            }
        );
        assert( expected.equals( mat.multiply(mat2) ) );
    }

}
