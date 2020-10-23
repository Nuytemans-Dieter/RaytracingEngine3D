package objects;

import graphics.Rgb;
import maths.Matrix;
import maths.vector.Direction;
import maths.vector.Point;


public abstract class Object3D extends Positionable {

    protected Rgb color = new Rgb(1.0f, 1.0f, 1.0f);

    private Matrix transformation = new Matrix();
    private Matrix inverseTransformation = new Matrix();

    public Object3D()
    {
        super();
    }


    public Object3D(Point location)
    {
        super(location);
    }

    /**
     * Get the t for which a Ray collides with this Object3D
     *
     * @param ray the ray for which the collision is to be checked
     * @return null when there are no intersections. The closest value of t will be returned
     */
    public abstract Double getCollidingT(Ray ray);

    public Rgb getcolor()
    {
        return color;
    }

    public Point getLocation()
    {
        return this.location;
    }

    /**
     * Removes all current transformations and applies the given transformation matrix
     *
     * @param matrix the new transformation matrix
     */
    public void setTransformation(Matrix matrix)
    {
        this.transformation = matrix;
    }

    /**
     * Add one or more transformations to the current state of transformation
     *
     * @param matrices the transformation matrix to be added to this one
     */
    public void addTransformations(Matrix... matrices)
    {
        for (Matrix mat : matrices)
            this.transformation = this.transformation.multiply(mat);
    }

    public Matrix getTransformation()
    {
        return this.transformation;
    }

    /**
     * Move this object by applying a certain direction
     *
     * @param direction the direction (vector) of movement
     */
    public void move(Direction direction) {
        this.location = new Point(location.add( direction ));
    }


    /**
     * Recalculate the inverse from the current transformation
     */
    public void updateInverse()
    {
        this.inverseTransformation = this.transformation.inverse();
    }


    /**
     * Gets the latest inverse transformation
     * In order the get the most up-to-date matrix: make a call to updateInverse() first
     *
     * @return the inverse Matrix
     */
    public Matrix getInverseCache()
    {
        return this.inverseTransformation;
    }

}
