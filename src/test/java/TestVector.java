import interfaces.IVector;
import maths.Matrix;
import maths.Vector;
import org.junit.Test;

public class TestVector {

    @Test
    public void testEquals()
    {
        IVector vec1 = new Vector(
            new double[] {
                1,
                2,
                3,
                4
            }
        );

        IVector vec2 = new Vector(
            new double[] {
                1,
                2,
                3,
                4
            }
        );

        // Check if equal vectors are considered equal
        assert (vec1.equals(vec1));
        assert (vec1.equals(vec2));
        // Check if modified vector is NOT equal
        assert (!vec1.equals(vec2.modify(1, 10)));
        // Check if two (same) modified vectors are equal
        assert (vec1.modify(1, 10).equals(vec2));
        // Check if different kind of Object is NOT equal
        assert (!vec1.equals(new Matrix()));
    }

}
