import interfaces.IMatrix;
import interfaces.ITransMatFactory;
import maths.Matrix;

public class TransMatFactory implements ITransMatFactory {

    @Override
    public IMatrix getTranslation(int x, int y, int z)
    {
        return new Matrix()
                .modify(3, 0, x)
                .modify(3, 1, y)
                .modify(3, 2, z);
    }

    @Override
    public IMatrix getScaling(int x, int y, int z)
    {
        return new Matrix()
                .modify(0, 0, x)
                .modify(1, 1, y)
                .modify(2, 2, z);
    }

    @Override
    public IMatrix getRotation(RotationAxis axis, int theta)
    {
        return null;
    }
}
