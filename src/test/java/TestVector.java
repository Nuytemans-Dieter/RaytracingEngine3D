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
        assert vec1.equals(vec1);
        assert vec1.equals(vec2);
        // Check if modified vector is NOT equal
        assert !vec1.equals(vec2.modify(1, 10));
        // Check if two (same) modified vectors are equal
        assert vec1.modify(1, 10).equals(vec2);
        // Check if different kind of Object is NOT equal
        assert !vec1.equals(new Matrix());
    }

    @Test
    public void testGet()
    {
        IVector vector = new Vector();
        for (int i = 0; i < 4; i++)
            assert vector.get(i) == 0;

        IVector vector1 = new Vector(1, 2, 3, 4);
        for (int i = 0; i < 4; i++)
            assert vector1.get(i) == i + 1 ;

        IVector vector2 = new Vector(new double[] {0, 1, 2, 3});
        for (int i = 0; i < 4; i++)
            assert vector2.get(i) == i;
    }

    @Test
    public void testConstructorAndEquals()
    {
        IVector vector = new Vector();
        IVector vector1 = new Vector(0, 0, 0, 0);
        IVector vector2 = new Vector(new double[] {0, 0, 0, 0});

        assert vector.equals( vector1 );
        assert vector2.equals(vector);
        assert vector1.equals(vector2);

        assert !vector.equals( 12 );
        assert !vector.equals( new Matrix() );

        vector1.modify(2, 1);
        assert ! vector1.equals(vector);
    }

    @Test
    public void testToString()
    {
        IVector vector = new Vector();
        assert vector.toString().replace("\n", "-").equals("| 0.0 |-| 0.0 |-| 0.0 |-| 0.0 |-");

        IVector vector1 = new Vector(1, 2, 3, 4);
        assert vector1.toString().replace("\n", "-").equals("| 1.0 |-| 2.0 |-| 3.0 |-| 4.0 |-");

        IVector vector2 = new Vector(new double[] {16, 4, 3, 28});
        assert vector2.toString().replace("\n", "-").equals("| 16.0 |-| 4.0 |-| 3.0 |-| 28.0 |-");
    }

}
