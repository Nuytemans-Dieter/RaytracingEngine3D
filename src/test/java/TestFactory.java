import interfaces.IMatrix;
import interfaces.ITransMatFactory;
import maths.Matrix;
import org.junit.Test;

public class TestFactory {

    @Test
    public void testTranslation()
    {
        ITransMatFactory fact = new TransMatFactory();
        IMatrix mat = fact.getTranslation(20, 45, -4);
        IMatrix expected = new Matrix(
            new double[][]
                {
                    {1, 0, 0, 20},
                    {0, 1, 0, 45},
                    {0, 0, 1, -4},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));
    }

    @Test
    public void testScaling()
    {
        ITransMatFactory fact = new TransMatFactory();
        IMatrix mat = fact.getScaling(12, -5, 6);
        IMatrix expected = new Matrix(
            new double[][]
                {
                    {12, 0, 0, 0},
                    {0, -5, 0, 0},
                    {0, 0, 6, 0},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));
    }

    @Test
    public void testRotation()
    {
        double angle = Math.PI / 3;
        double sin = Math.sin(angle);
        double cos = Math.cos(angle);

        ITransMatFactory fact = new TransMatFactory();
        IMatrix mat = fact.getRotation(ITransMatFactory.RotationAxis.X, angle);
        IMatrix expected = new Matrix(
            new double[][]
                {
                    {1, 0, 0, 0},
                    {0, cos, -sin, 0},
                    {0, sin, cos, 0},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));

        mat = fact.getRotation(ITransMatFactory.RotationAxis.Y, angle);
        expected = new Matrix(
            new double[][]
                {
                    {cos, 0, sin, 0},
                    {0, 1, 0, 0},
                    {-sin, 0, cos, 0},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));

        mat = fact.getRotation(ITransMatFactory.RotationAxis.Z, angle);
        expected = new Matrix(
            new double[][]
                {
                    {cos, -sin, 0, 0},
                    {sin, cos, 0, 0},
                    {0, 0, 1, 0},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));
    }

}
