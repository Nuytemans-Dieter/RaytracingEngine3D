import interfaces.ITransMatFactory;
import maths.Matrix;

public class TransMatFactory implements ITransMatFactory {

    @Override
    public Matrix getTranslation(int x, int y, int z)
    {
        return new Matrix()
                .modify(3, 0, x)
                .modify(3, 1, y)
                .modify(3, 2, z);
    }

    @Override
    public Matrix getScaling(int x, int y, int z)
    {
        return new Matrix()
                .modify(0, 0, x)
                .modify(1, 1, y)
                .modify(2, 2, z);
    }

    @Override
    public Matrix getRotation(RotationAxis axis, double theta)
    {
        Matrix matrix;

        double cos = Math.cos(theta);
        double sin = Math.sin(theta);

        switch (axis)
        {
            case X:
                matrix = new Matrix()
                        .modify(1, 1, cos)
                        .modify(2, 1, -sin)
                        .modify(1, 2, sin)
                        .modify(2, 2, cos);
                break;
            case Y:
                matrix = new Matrix()
                        .modify(0, 0, cos)
                        .modify(2, 0, sin)
                        .modify(0, 2, -sin)
                        .modify(2, 2, cos);
                break;
            case Z:
                matrix = new Matrix()
                        .modify(0, 0, cos)
                        .modify(1, 0, -sin)
                        .modify(0, 1, sin)
                        .modify(1, 1, cos);
                break;
            default:
                matrix = new Matrix();
                break;
        }

        return matrix;
    }
}
