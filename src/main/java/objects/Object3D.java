package objects;

import graphics.Rgb;
import maths.vector.Point;

public abstract class Object3D {

    protected Point location;
    protected Rgb color = new Rgb(1.0f, 1.0f, 1.0f);

    public abstract boolean isColliding(Ray ray);

    public Rgb getcolor()
    {
        return color;
    }

}
