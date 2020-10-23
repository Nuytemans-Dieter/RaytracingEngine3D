package objects;

import maths.vector.Point;

public abstract class Positionable {

    protected Point location;

    public Positionable(Point location)
    {
        this.location = location;
    }

    public Point getLocation()
    {
        return location;
    }
}
