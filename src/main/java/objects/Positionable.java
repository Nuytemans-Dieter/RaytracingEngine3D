package objects;

import maths.vector.Point;

public class Positionable {

    protected Point location = new Point(0, 0, 0);


    public Positionable() {}
    public Positionable(Point location) {this.location = location;}

}
