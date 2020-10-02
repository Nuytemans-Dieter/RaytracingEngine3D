package interfaces;

public interface ITransMatFactory {

    enum RotationAxis {
        X,
        Y,
        Z
    }

    /**
     * Creates a 4x4 translation matrix
     * @param x translation on the x-axis
     * @param y translation on the y-axis
     * @param z translation on the z-axis
     * @return the resulting translation matrix
     */
    IMatrix getTranslation(int x, int y, int z);

    /**
     * Creates a 4x4 scaling matrix
     * @param x scaling with respect to the x-axis
     * @param y scaling with respect to the y-axis
     * @param z scaling with respect to the z-axis
     * @return the resulting scaling matrix
     */
    IMatrix getScaling(int x, int y, int z);

    /**
     * Creates a 4x4 rotation matrix
     * @param axis the axis around which has to be rotated
     * @param theta the angle of this rotation
     * @return the resulting rotation matrix
     */
    IMatrix getRotation(RotationAxis axis, int theta);

}
