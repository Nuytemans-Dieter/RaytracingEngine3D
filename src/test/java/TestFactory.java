import interfaces.ITransMatFactory;
import maths.Matrix;
import maths.TransMatFactory;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TestFactory {

    @Test
    public void testTranslation()
    {
        ITransMatFactory fact = new TransMatFactory();
        Matrix mat = fact.getTranslation(20, 45, -4);
        Matrix expected = new Matrix(
            new double[][]
                {
                    {1, 0, 0, 20},
                    {0, 1, 0, 45},
                    {0, 0, 1, -4},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));

        mat = fact.getTranslation(2, 0, 4);
        expected = new Matrix(
            new double[][]
                {
                    {1, 0, 0, 2},
                    {0, 1, 0, 0},
                    {0, 0, 1, 4},
                    {0, 0, 0, 1}
                }
        );
        assert (mat.equals(expected));
    }

    @Test
    public void testScaling()
    {
        ITransMatFactory fact = new TransMatFactory();
        Matrix mat = fact.getScaling(12, -5, 6);
        Matrix expected = new Matrix(
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
        List<Double> angles = new ArrayList<Double>(){{
            add(Math.PI / 3);
            add(0d);
            add(1d);
        }};

        for (double angle : angles)
        {
            double sin = Math.sin(angle);
            double cos = Math.cos(angle);

            ITransMatFactory fact = new TransMatFactory();
            Matrix mat = fact.getRotation(ITransMatFactory.RotationAxis.X, angle);
            Matrix expected = new Matrix(
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

}
