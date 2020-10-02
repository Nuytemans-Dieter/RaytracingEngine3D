import interfaces.IMatrix;
import interfaces.ITransMatFactory;
import maths.Matrix;

public class TransMatFactory implements ITransMatFactory {

    @Override
    public IMatrix getTranslation(int x, int y, int z)
    {
        return new Matrix()
                .setPosition(3, 0, x)
                .setPosition(3, 1, y)
                .setPosition(3, 2, z);
    }

    @Override
    public IMatrix getScaling(int x, int y, int z)
    {
        return new Matrix()
                .setPosition(0, 0, x)
                .setPosition(1, 1, y)
                .setPosition(2, 2, z);
    }

    @Override
    public IMatrix getRotation(RotationAxis axis, int theta)
    {
        return null;
    }
}
