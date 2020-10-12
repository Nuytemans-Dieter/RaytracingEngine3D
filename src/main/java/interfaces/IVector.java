package interfaces;

/**
 * The interface Vector.
 */
public interface IVector {

    /**
     * Get the element at a specified location
     *
     * @param i the location [0, 3]
     * @return the element (double) at this location
     */
    double get(int i);

    /**
     * Get the result of adding another vector to this vector
     * This vector is NOT modified
     * The first three elements are simply added
     * The last element will be equal to whatever vector's last element is highest
     *
     * @param vector the vector to be added to this
     * @return the result of the addition
     */
    IVector add(IVector vector);

    /**
     * Modifies the value at a specified location and returns the resulting IVector
     *
     * @param i     the location [0, 3]
     * @param value the new value
     * @return the resulting IVector
     */
    IVector modify(int i, double value);
}
