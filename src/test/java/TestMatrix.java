import maths.Matrix;
import maths.Vector;
import org.junit.Test;

import java.util.Arrays;

public class TestMatrix {

    @Test
    public void testModify()
    {
        Matrix modLater = new Matrix(
            new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );
        modLater.modify(2, 1, 38);

        Matrix modNow = new Matrix(
            new double[][]{
                {1, 2, 3, 4},
                {5, 6, 38, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );

        assert modLater.equals(modNow);
    }

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
        assert (!matrix.equals( new Vector(matrix.getColumn(0)) ));

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
    public void testToString()
    {
        Matrix mat = new Matrix(
            new double[][] {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );

        assert (mat.toString().replace('\n', '-').equals(
        " | 1.0 | 2.0 | 3.0 | 4.0 | - | 5.0 | 6.0 | 7.0 | 8.0 | - | 9.0 | 10.0 | 11.0 | 12.0 | - | 13.0 | 14.0 | 15.0 | 16.0 | -"
        ));
    }

    @Test
    public void testTranspose()
    {
        Matrix mat = new Matrix(
                new double[][] {
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 16}
                }
        );

        Matrix transposed = new Matrix(
                new double[][] {
                        {1, 5, 9, 13},
                        {2, 6, 10, 14},
                        {3, 7, 11, 15},
                        {4, 8, 12, 16}
                }
        );

        assert mat.transpose().equals( transposed );
    }

    @Test(expected = AssertionError.class)
    public void testFaultyParameter()
    {
        new Matrix(new double[][]{});
    }

    @Test
    public void testAsserts()
    {
        boolean isSuccess = false;
        try {
            new Matrix(new double[][]{{}, {}, {}});
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;

        isSuccess = false;
        try {
            new Matrix(
                new double[][]{
                    {1, 5, 6, 4},
                    {4, 7, 6, 4},
                    {1, 4, 7},
                    {1, 6, 7, 4}
                }
            );
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;

        isSuccess = false;
        try {
            new Matrix().modify(-1, 0, 1);
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;

        isSuccess = false;
        try {
            new Matrix().modify(4, 0, 1);
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;

        isSuccess = false;
        try {
            new Matrix().modify(0, -1, 1);
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;

        isSuccess = false;
        try {
            new Matrix().modify(0, 4, 1);
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;

        isSuccess = false;
        try {
            new Matrix().modify(4, 4, 1);
            isSuccess = true;
        } catch (AssertionError ignored) {}
        assert !isSuccess;
    }

    @Test
    public void testInverse()
    {
        Matrix mat = new Matrix(
            new double[][] {
                {5, -2,  2, 7},
                {1,  0,  0, 3},
                {-3, 1,  5, 0},
                {3, -1, -9, 4}
            }
        );
        Matrix result = new Matrix(
            new double[][] {
                {-0.1363636363636363, 0.8636363636363635,  -0.6818181818181818, -0.409090909090909},
                {-0.6363636363636362,  2.3636363636363633,  -0.9318181818181817, -0.659090909090909},
                {0.045454545454545456, 0.04545454545454545,  -0.022727272727272728, -0.11363636363636362},
                {0.045454545454545456, 0.04545454545454544, 0.22727272727272727, 0.13636363636363635}
            }
        );
        assert mat.inverse().equals(result);
    }

    @Test
    public void testAdd()
    {
        Matrix mat = new Matrix(
            new double[][]{
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
                {1, 2, 3, 4},
            }
        );
        Matrix mat2 = new Matrix(
            new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );
        Matrix expected = new Matrix(
            new double[][]{
                {2, 4, 6, 8},
                {6, 8, 10, 12},
                {10, 12, 14, 16},
                {14, 16, 18, 20},
            }
        );

        assert( expected.equals( mat.add(mat2) ) );
    }

    @Test
    public void testVectorMultiplication()
    {
        Matrix mat = new Matrix(
            new double[][]{
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12},
                {13, 14, 15, 16}
            }
        );
        Vector vector = new Vector(
            new double []{
                1,
                2,
                3,
                4
            }
        );
        Vector expected = new Vector(
            new double[]{
                30,
                70,
                110,
                150
            }
        );

        assert( expected.equals( mat.multiply(vector) ) );

        Matrix matCopy = new Matrix(
                new double[][]{
                        {1, 2, 3, 4},
                        {5, 6, 7, 8},
                        {9, 10, 11, 12},
                        {13, 14, 15, 16}
                }
        );

        assert ( mat.equals( matCopy ) );
    }

    @Test
    public void testMatrixMultiplication()
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

        mat = new Matrix(
            new double[][] {
                {6, 2, 0, 2},
                {3, 7, 8, 3},
                {4, 6, 8, 1},
                {9, 4, 7, 3}
            }
        );
        mat2 = new Matrix(
            new double[][] {
                {12, 4, 26, 5},
                {7, 3, 5, 4},
                {3, 4, 45, 5},
                {7, 5, 3, 3}
            }
        );
        expected = new Matrix(
            new double[][]{
                {100, 40, 172, 44},
                {130, 80, 482, 92},
                {121, 71, 497, 87},
                {178, 91, 578, 105}
            }
        );
        assert( expected.equals( mat.multiply(mat2) ) );
    }

}
