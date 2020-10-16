package interfaces;

import maths.Matrix;

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
    Matrix getTranslation(double x, double y, double z);

    /**
     * Creates a 4x4 scaling matrix
     * @param x scaling with respect to the x-axis
     * @param y scaling with respect to the y-axis
     * @param z scaling with respect to the z-axis
     * @return the resulting scaling matrix
     */
    Matrix getScaling(double x, double y, double z);

    /**
     * Creates a 4x4 rotation matrix
     * @param axis the axis around which has to be rotated
     * @param theta the angle of this rotation
     * @return the resulting rotation matrix
     */
    Matrix getRotation(RotationAxis axis, double theta);

}
