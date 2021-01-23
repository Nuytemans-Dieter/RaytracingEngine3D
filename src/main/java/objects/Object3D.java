package objects;

import datacontainers.HitInfo;
import graphics.Rgb;
import maths.Matrix;
import maths.vector.Direction;
import maths.vector.Point;
import objects.materials.Lambertian;


public abstract class Object3D {

    protected Material material = new Lambertian();

    private Matrix transformation = new Matrix();
    private Matrix inverseTransformation = new Matrix();

    public Object3D() {}

    public Object3D(Material material)
    {
        this.material = material;
    }

    public Object3D setMaterial(Material material)
    {
        this.material = material;
        return this;
    }

    /**
     * Get the HitInfo for which a Ray collides with this Object3D
     *
     * @param ray the ray for which the collision is to be checked
     * @return HitInfo that contains zero or more values for t of intersection
     */
    public HitInfo calcHitInfo(Ray ray)
    {
        return this.calcHitInfo( ray, 0 );
    }

    /**
     * Get the HitInfo for which a Ray collides with this Object3D
     * Considers the epsilon value that works as an offset
     *
     * @param ray the ray for which the collision is to be checked
     * @param epsilon the minimum t value to be considered
     * @return HitInfo that contains zero or more values for t of intersection
     */
    public abstract HitInfo calcHitInfo(Ray ray, double epsilon);


    /**
     * Get the Material that is applied to this object
     *
     * @return an instance of the material
     */
    public Material getMaterial()
    {
        return this.material;
    }

    /**
     * Get a copy of the rgb of this object
     *
     * @return copy of the rgb object
     */
    public Rgb getColor()
    {
        return material.getColor().clone();
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

    /**
     * Checks whether or not this object encloses a given position
     *
     * @param location the given position
     * @return Returns null if the point is not enclosed, returns the squared distance to origin otherwise
     */
    public abstract Double enclosedDistance(Point location);
}
