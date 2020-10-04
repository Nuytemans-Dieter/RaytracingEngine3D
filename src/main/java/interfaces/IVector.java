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
     * Modifies the value at a specified location and returns the resulting IVector
     *
     * @param i     the location [0, 3]
     * @param value the new value
     * @return the resulting IVector
     */
    IVector modify(int i, double value);
}
