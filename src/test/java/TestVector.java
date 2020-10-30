import maths.Matrix;
import maths.Vector;
import maths.vector.Direction;
import maths.vector.Point;
import org.junit.Test;

public class TestVector {

    @Test
    public void testAddVector()
    {
        Vector v1 = new Vector();
        Vector v2 = new Vector();
        Vector result = new Vector(
            new double[] {
                v1.get(0) + v2.get(0),
                v1.get(1) + v2.get(1),
                v1.get(2) + v2.get(2),
                v1.get(3) + v2.get(3)
            }
        );

        assert (v1.add(v2).equals(result));

        v1 = new Vector(1, -1, 0, 1);
        v2 = new Vector(6, 2.2, 17, 0);
        result = new Vector(7, 1.2000000000000002, 17, 1);
        assert (v1.add(v2).equals(result));
    }

    @Test
    public void testEquals()
    {
        Vector vec1 = new Vector(
            new double[] {
                1,
                2,
                3,
                4
            }
        );

        Vector vec2 = new Vector(
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
        Vector vector = new Vector();
        for (int i = 0; i < 4; i++)
            assert vector.get(i) == 0;

        Vector vector1 = new Vector(1, 2, 3, 4);
        for (int i = 0; i < 4; i++)
            assert vector1.get(i) == i + 1 ;

        Vector vector2 = new Vector(new double[] {0, 1, 2, 3});
        for (int i = 0; i < 4; i++)
            assert vector2.get(i) == i;
    }

    @Test
    public void testConstructorAndEquals()
    {
        Vector vector = new Vector();
        Vector vector1 = new Vector(0, 0, 0, 0);
        Vector vector2 = new Vector(new double[] {0, 0, 0, 0});

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
        Vector vector = new Vector();
        assert vector.toString().replace("\n", "-").equals("| 0.0 |-| 0.0 |-| 0.0 |-| 0.0 |-");

        Vector vector1 = new Vector(1, 2, 3, 4);
        assert vector1.toString().replace("\n", "-").equals("| 1.0 |-| 2.0 |-| 3.0 |-| 4.0 |-");

        Vector vector2 = new Vector(new double[] {16, 4, 3, 28});
        assert vector2.toString().replace("\n", "-").equals("| 16.0 |-| 4.0 |-| 3.0 |-| 28.0 |-");
    }


    @Test
    public void testDotProduct()
    {
        Vector pt = new Vector(-5, -5, -5, 1);
        Vector dir = new Vector(4.5, 5, 5,0);

        assert (dir.dotProduct(dir) == 70.25);
        assert (pt.dotProduct(dir) == -72.5);
        assert (pt.dotProduct(pt) == 75);

        pt = new Point(2, 0, 2);
        dir = new Direction(-2, 0, -2);

        assert (dir.dotProduct(dir) == 8);
        assert (pt.dotProduct(dir) == -8);
        assert (pt.dotProduct(pt) == 8);
    }

    @Test
    public void testPoint()
    {
        Point p = new Point(20.0, 30.0, 40.0);
        Vector sameAsP = new Vector(20.0, 30.0, 40.0, 1);
        assert p.equals( sameAsP );
        assert sameAsP.equals(p);
        assert p.distance( p ) == 0;

        Point p2 = new Point(20, 30, 30);
        assert p.distance( p2 ) == 10;

        Point p3 = new Point(10, 20, 30);
        assert p.distance(p3) == 17.320508075688775;
    }

}
