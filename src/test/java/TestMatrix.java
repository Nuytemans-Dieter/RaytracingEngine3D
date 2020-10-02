import interfaces.IMatrix;
import maths.Matrix;
import org.junit.Test;

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
    public void testMultiplication()
    {

    }

}
