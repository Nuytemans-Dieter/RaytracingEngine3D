package objects;

import graphics.Rgb;
import maths.vector.Direction;
import maths.vector.Point;


public abstract class Object3D {

    protected Point location = new Point(0, 0, 0);
    protected Rgb color = new Rgb(1.0f, 1.0f, 1.0f);

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
        return location;
    }

    /**
     * Move this object by applying a certain direction
     *
     * @param direction the direction (vector) of movement
     */
    public void move(Direction direction) {
        this.location = new Point(location.add( direction ));
    }

}
